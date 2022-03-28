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
	
	public ChannelThread registerThread(String token, ChannelThread th, String channel_id) {
		//1-check token 
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to start a new Thread", tk.getUser().getEmail()));
		//2-check channels' existence
		Optional<Channel> och = rep.getChannelRepo().findById(channel_id);
		Channel ch = new Channel();
		try {
			ch = och.get();
		}
		catch(Exception e)
		{
			throw new BusinessException(String.format("channel with id %s does not exist", channel_id));
		}
		ch = och.get();
		//3-check channels' accessibility (subscription status) for user
		//3.1-get list of user subscriptions
		List<Subscription> subList = rep.getSubscriptionRepo().findByUser(tk.getUser());
		if(subList == null || subList.size()==0)
			throw new BusinessException(String.format("user %s does not have any subscriptions",tk.getUser().getEmail()));
		//3.2-find and get user subscription to channel
		Subscription usub = new Subscription(); 
		for(Subscription sub: subList) {
			if(sub.getChannel().equals(ch)) {
				usub = sub;
				break;
			}				
		}
		System.out.println(usub.getEid()+"haha");	
		if(usub.isEmpty())
			throw new BusinessException(String.format("user %s is not subscribed to channel %s",tk.getUser().getEmail(),ch.getName()));
		//3.3-check all conditions (channel scope and subscription status) 
		EChannelScope cs = EChannelScope.fromString(ch.getScope());
		ESubscriptionStatus ss = ESubscriptionStatus.fromString(usub.getStatus());
		if(cs == EChannelScope.Private && ss != ESubscriptionStatus.active)
			throw new BusinessException(String.format("user %s does not have access to channel %s",tk.getUser().getEmail(),ch.getName()));
		//4-fill the rest of properties for thread
		th.setEid(UUID.randomUUID().toString());
		th.setCdate(new Timestamp(System.currentTimeMillis()));
		th.setSubscription(usub);
		rep.getChannelThreadRepo().save(th);
		return th;
	}
	
}
