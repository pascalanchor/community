package avh.community.services.logic;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.community.model.Channel;
import avh.community.model.ChannelThread;
import avh.community.model.Message;
import avh.community.model.Subscription;
import avh.community.model.Token;
import avh.community.model.User;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.api.model.APIMessage;
import avh.community.services.logic.domains.EChannelScope;
import avh.community.services.logic.domains.EFeature;
import avh.community.services.logic.domains.ESubscriptionStatus;
import avh.community.services.logic.security.AuthorizationManager;
import avh.community.services.persistence.ModelRepo;

@Component
public class MessageControllerImpl {
	@Autowired private ModelRepo rep ;
	@Autowired private AuthorizationManager amgr;
	
	public Message addComment(String token,String thread_id,String channel_id,Message msg) {
		
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to start a new Thread", tk.getUser().getEmail()));
		//check the channel existence
		Optional<Channel> och = rep.getChannelRepo().findById(channel_id);
		Channel ch=new Channel();
		if(och.isEmpty())
			throw new BusinessException(String.format("invalid channel id :%s",channel_id));
		ch=och.get();
		//check the thread existence
		Optional<ChannelThread> ochth=rep.getChannelThreadRepo().findById(thread_id);
		ChannelThread chth=new ChannelThread();
		if(ochth.isEmpty())
			throw new BusinessException(String.format("invalid thread id :%s",channel_id));
		chth=ochth.get();
		//check if the user subscribed to this channel
		User u=tk.getUser();
		List<Subscription> sublist=rep.getSubscriptionRepo().findByUser(u);
		if(sublist.isEmpty())
			throw new BusinessException(String.format("the user %s did not subscribe to any channel",u.getEmail()));
		
		//check if the user subscribed to this channel
		Subscription sub=new Subscription();
		int x=0;
		for(Subscription itsub:sublist) {
			if((itsub.getChannel()).equals(ch)){
					sub=itsub;
					x=1;
					break;
				}
			}
		
		if(x==0)
			throw new BusinessException(String.format("the user %s did not subscribe to  channel %s",u.getEmail(),channel_id));
		
		EChannelScope cs = EChannelScope.fromString(ch.getScope());
		ESubscriptionStatus ss = ESubscriptionStatus.fromString(sub.getStatus());
		if(cs == EChannelScope.Private && ss != ESubscriptionStatus.active)
			throw new BusinessException(String.format("user %s does not have access to channel %s",tk.getUser().getEmail(),ch.getName()));
		
		//register the message 
		msg.setEid(UUID.randomUUID().toString());
		msg.setCdate(new Timestamp(System.currentTimeMillis()));
		msg.setThread(chth);
		msg.setSubscription(sub);
		rep.getMessageRepo().save(msg);
		return msg;
	}
	

	
public Message addReply(String token,String thread_id,String channel_id,String reply_to,Message msg) {
		//check user token
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to start a new Thread", tk.getUser().getEmail()));
		//check the channel existence
		Optional<Channel> och = rep.getChannelRepo().findById(channel_id);
		Channel ch=new Channel();
		if(och.isEmpty())
			throw new BusinessException(String.format("invalid channel id :%s",channel_id));
		ch=och.get();
		//check the thread existence
		Optional<ChannelThread> ochth=rep.getChannelThreadRepo().findById(thread_id);
		ChannelThread chth=new ChannelThread();
		if(ochth.isEmpty())
			throw new BusinessException(String.format("invalid thread id :%s",channel_id));
		chth=ochth.get();
		//check if the user subscribed to this channel
		User u=tk.getUser();
		List<Subscription> sublist=rep.getSubscriptionRepo().findByUser(u);
		if(sublist.isEmpty())
			throw new BusinessException(String.format("the user %s did not subscribe to any channel",u.getEmail()));
		
		//check if the user subscribed to this channel
		Subscription sub=new Subscription();
		int x=0;
		for(Subscription itsub:sublist) {
			if((itsub.getChannel()).equals(ch)){
					sub=itsub;
					x=1;
					break;
				}
			}
		
		if(x==0)
			throw new BusinessException(String.format("the user %s did not subscribe to  channel %s",u.getEmail(),channel_id));
		
		EChannelScope cs = EChannelScope.fromString(ch.getScope());
		ESubscriptionStatus ss = ESubscriptionStatus.fromString(sub.getStatus());
		if(cs == EChannelScope.Private && ss != ESubscriptionStatus.active)
			throw new BusinessException(String.format("user %s does not have access to channel %s",tk.getUser().getEmail(),ch.getName()));
		Message reply_tomsg=null;
		List<Message> omsg=rep.getMessageRepo().findByEid(reply_to);
		if(omsg.isEmpty() && reply_to !=null)//error message id
			throw new BusinessException(String.format("invalid message id: %s",reply_to));
		if(!omsg.isEmpty())
			reply_tomsg=omsg.get(0);
		
		//register the reply
		msg.setEid(UUID.randomUUID().toString());
		msg.setCdate(new Timestamp(System.currentTimeMillis()));
		msg.setThread(chth);
		msg.setSubscription(sub);
		msg.setMessage(reply_tomsg);
		rep.getMessageRepo().save(msg);
		
		return msg;
	}


	public List<Message> getAllThreadsMessages(String token,String thread_id)
	{
		//check token
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to start a new Thread", tk.getUser().getEmail()));
		//get thread entity
		Optional<ChannelThread> ocht=rep.getChannelThreadRepo().findById(thread_id);
		if(ocht.isEmpty())
			throw new BusinessException(String.format("invalid thread id: %s",thread_id));
		ChannelThread cht=ocht.get();
		//get all messages related to this thread
		List<Message> mslist=rep.getMessageRepo().findByThread(cht);
		
		return mslist;
	}

	public Message editMessage(String token,String message_id,Message newmsg) {
		//check token
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to start a new Thread", tk.getUser().getEmail()));
		// message id validation	
		List<Message> lmsg=rep.getMessageRepo().findByEid(message_id);
		if(lmsg==null || lmsg.isEmpty())
			throw new BusinessException(String.format("invalid message id :", message_id));
		Message oldMessage=lmsg.get(0);
		//now change the message details
		oldMessage.setBody(newmsg.getBody());
		oldMessage.setStars(newmsg.getStars());
		rep.getMessageRepo().save(oldMessage);
		return oldMessage;
			
	}
	
	
	public boolean deleteMessage(String token,String message_id) {
		
		//check token
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to start a new Thread", tk.getUser().getEmail()));
		// message id validation	
		List<Message> lmsg=rep.getMessageRepo().findByEid(message_id);
			if(lmsg==null || lmsg.isEmpty())
				throw new BusinessException(String.format("invalid message id :", message_id));
		 
		Message msg=lmsg.get(0);
		
		//check the user permission
		User u=tk.getUser();
		if(!(msg.getSubscription().getUser().getEmail()).equals(u.getEmail()))
			throw new BusinessException(String.format(" this user: %s does not have permission to delete this message:%s :",u.getEmail(), message_id));
		
		//delete the message
		rep.getMessageRepo().delete(msg);
		
		//check if the message is deleted successfully
		lmsg=rep.getMessageRepo().findByEid(message_id);
		if(lmsg.isEmpty())//the message is deleted 
			return true;
		else// the message is not deleted
			return false;
	}
}
