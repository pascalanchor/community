package avh.community.services.logic;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.community.model.Channel;
import avh.community.model.ChannelThread;
import avh.community.model.Subscription;
import avh.community.model.Token;
import avh.community.model.User;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.logic.domains.EChannelScope;
import avh.community.services.logic.domains.EFeature;
import avh.community.services.logic.domains.ESubscriptionStatus;
import avh.community.services.logic.security.AuthorizationManager;
import avh.community.services.persistence.ModelRepo;

@Component
public class ChannelThreadControllerImpl {
	@Autowired private ModelRepo rep;
	@Autowired private AuthorizationManager amgr;
	
	private User getUserFromToken(String token) {
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user is not authenticated to perform this action"));
		return tk.getUser();
	}
	
	private Channel getChannelById(String channel_id) {
		Optional<Channel> och = rep.getChannelRepo().findById(channel_id);
		try {
			return och.get();
		}
		catch(Exception e)
		{
			throw new BusinessException(String.format("channel with id %s does not exist", channel_id));
		}
	}
	
	private Subscription getUserSubscriptionToChannel(User user, Channel channel) {
		List<Subscription> subList = rep.getSubscriptionRepo().findByUser(user);
		if(subList == null || subList.size()==0)
			throw new BusinessException(String.format("user %s does not have any subscriptions",user.getEmail()));
		//3.2-find and get user subscription to channel
		Subscription usub = new Subscription(); 
		for(Subscription sub: subList) {
			if(sub.getChannel().equals(channel)) {
				usub = sub;
				break;
			}				
		}	
		if(usub.isEmpty())
			throw new BusinessException(String.format("user %s is not subscribed to channel %s",user.getEmail(),channel.getName()));
		return usub;
	}
	
	private void checkStatusAndScope(User user, Channel channel, Subscription subscription) {
		EChannelScope cs = EChannelScope.fromString(channel.getScope());
		ESubscriptionStatus ss = ESubscriptionStatus.fromString(subscription.getStatus());
		if(cs == EChannelScope.Private && ss != ESubscriptionStatus.active)
			throw new BusinessException(String.format("user %s does not have access to channel %s",user.getEmail(),channel.getName()));
	}
	
	private ChannelThread getThreadById(String thread_id) {
		Optional<ChannelThread> oth = rep.getChannelThreadRepo().findById(thread_id);
		try {
			return oth.get();
		}
		catch(Exception e)
		{
			throw new BusinessException(String.format("thread with id %s does not exist", thread_id));
			}
	}
	
	public ChannelThread registerThread(String token, ChannelThread thread, String channel_id) {
		//1-check token 
		User user = getUserFromToken(token);
		//2-check channels' existence
		Channel channel = getChannelById(channel_id);
		//3-check user subscription
		Subscription subscription = getUserSubscriptionToChannel(user, channel);
		//4-check scope and status
		checkStatusAndScope(user, channel, subscription);
		//5-fill the rest of properties for thread
		thread.setEid(UUID.randomUUID().toString());
		thread.setCdate(new Timestamp(System.currentTimeMillis()));
		thread.setSubscription(subscription);
		rep.getChannelThreadRepo().save(thread);
		//6-return thread
		return thread;
	}
	
	public List<ChannelThread> getThreadsByChannel(String token, String channel_id){
		//1-check token
		User user = getUserFromToken(token);
		//2-check channels' existence
		Channel channel = getChannelById(channel_id);
		//3-check user subscription
		Subscription subscription = getUserSubscriptionToChannel(user, channel);
		//3.3-check all conditions (channel scope and subscription status) 
		checkStatusAndScope(user, channel, subscription);
		//5-get the list of threads for channel subscription
		List<ChannelThread> list = rep.getChannelThreadRepo().findBySubscription(subscription);
		//6-return the list
		return list;	
	}
	
	public ChannelThread updateThread(String token, String thread_id, ChannelThread threadUpdate) {
		//1-check token
		User user = getUserFromToken(token);
		//2-find thread
		ChannelThread thread = getThreadById(thread_id);
		//3-check if thread belongs to user and update it
		if(thread.getSubscription().getUser().getEmail().equals(user.getEmail()))
		try {
			thread.setKeywords(threadUpdate.getKeywords());
			thread.setSubject(threadUpdate.getSubject());
			thread.setCdate(new Timestamp(System.currentTimeMillis()));
			return rep.getChannelThreadRepo().save(thread);
		      }
		catch(BusinessException e) {
			throw new BusinessException(String.format("couldn't update this thread"));
		}
		else throw new BusinessException(String.format("the user %s is not authorized to update this thread", user.getEmail()));
	}
	
	public ChannelThread deleteThread(String token, String thread_id) {
		//1-check token
		User user = getUserFromToken(token);
		//2-find thread
		ChannelThread thread = getThreadById(thread_id);
		//3-delete and return deleted thread
		if(thread.getSubscription().getUser().getEmail().equals(user.getEmail()))
		try {
		      rep.getChannelThreadRepo().delete(thread);
		      }
		catch(BusinessException e) {
			throw new BusinessException(String.format("couldn't delete this thread"));
		}
		else throw new BusinessException(String.format("the user %s is not authorized to delete this thread", user.getEmail()));

		return thread;
	}
}
