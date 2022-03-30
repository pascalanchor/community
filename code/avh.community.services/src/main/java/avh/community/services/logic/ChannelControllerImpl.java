package avh.community.services.logic;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.community.model.Channel;
import avh.community.model.Subscription;
import avh.community.model.Token;
import avh.community.model.User;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.api.model.in.APIChannelIn;
import avh.community.services.api.model.in.APISubscriptionIn;
import avh.community.services.logic.domains.EChannelScope;
import avh.community.services.logic.domains.EFeature;
import avh.community.services.logic.domains.ESubscriptionStatus;
import avh.community.services.logic.security.AuthorizationManager;
import avh.community.services.persistence.ModelRepo;

@Component
public class ChannelControllerImpl {
	@Autowired private ModelRepo rep;
	@Autowired private AuthorizationManager amgr;
	
	public Channel registerChannel(String token, Channel ch) {
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to define channel", tk.getUser().getEmail()));
		// check token validity
		// check right for token user
		List<Channel> och = rep.getChannelRepo().findByName(ch.getName());
		if ((och != null) && (och.size() > 0))
			throw new BusinessException(String.format("the channel name must be unique %s", ch.getName()));
		EChannelScope sc = EChannelScope.fromString(ch.getScope());
		if (sc == null)
			throw new BusinessException(String.format("the channel %s has an undefined scope %s", ch.getName(), ch.getScope()));
		
		ch.setEid(UUID.randomUUID().toString());
		ch.setCreationDate(new Timestamp(System.currentTimeMillis()));
		rep.getChannelRepo().save(ch);
		return ch;
	}
	
	public List<Subscription> getUserSubscriptions(String token,String user_email)
	{
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to subscribe channel", tk.getUser().getEmail()));
		
		Optional<User> ou=rep.getUserRepo().findById(user_email);
		if(ou.isEmpty())
			throw new BusinessException(String.format("the user does not exist"));
		List<Subscription> osb=rep.getSubscriptionRepo().findByUser(ou.get());
		return osb;
	}
	
	
	public Subscription requestToJoinChannel(String token,APISubscriptionIn sbi) {
		
		Token tk = amgr.isAuthorized(token, EFeature.Subscription);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to subscribe channel", tk.getUser().getEmail()));
		Subscription sb=new Subscription();
		Optional<User> ou = rep.getUserRepo().findById(sbi.getUser_email());
		if(ou.isEmpty())
			throw new BusinessException(String.format("the user does not exist"));
		Channel ch=rep.getChannelRepo().findByEid(sbi.getChannel_id());
		
		if(ch==null)
			throw new BusinessException(String.format("error channel id %s",sbi.getChannel_id()));
		
		//getting all user's requests
		List<Subscription> osb=rep.getSubscriptionRepo().findByUser(ou.get());
		//i assumed that the user email & channel id must be unique
		for(Subscription itsb:osb)
		{
			if((itsb.getChannel().getEid()).equals(sbi.getChannel_id()))
				throw new BusinessException(String.format("the user:%s  is already requested to join this channel:%s ",sbi.getUser_email(),sbi.getChannel_id()));
			
		}
		EChannelScope sc = EChannelScope.fromString(ch.getScope());
		ESubscriptionStatus sbs;
		sb.setEid(UUID.randomUUID().toString());
		sb.setUser(ou.get());
		sb.setChannel(ch);
		//specify the subscription status
		if(sc==EChannelScope.Private)
			sbs=ESubscriptionStatus.fromString("pending");
		else
			sbs=ESubscriptionStatus.fromString("active");
		sb.setStatus(sbs.getLabel());
		rep.getSubscriptionRepo().save(sb);
		return sb;
		
	}
	
	
	public Iterable<Channel> getAllChannels(String token){
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to define channel", tk.getUser().getEmail()));
		Iterable<Channel> och = rep.getChannelRepo().findAll();
		return och;
	}
	
	
	public Channel getChannelById(String token,String channel_id)
	{
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to define channel", tk.getUser().getEmail()));
		Channel ch=rep.getChannelRepo().findByEid(channel_id);
		if(ch==null)
			throw new BusinessException(String.format("invalid channel id: %s",channel_id));
		
		return(ch);
		
	}
	
	public Channel updateChannel(String token,String channel_id,Channel ch)
	{
		Token tk = amgr.isAuthorized(token, EFeature.Channel);
		if (tk == null)
			throw new BusinessException(String.format("the user %s is not authorized to define channel", tk.getUser().getEmail()));
		Channel ch1=rep.getChannelRepo().findByEid(channel_id);
		List<Channel> ch2=rep.getChannelRepo().findByName(ch.getName());
		if(ch1==null)
			throw new BusinessException(String.format("invalid channel id: %s",channel_id));
		
		if((!ch2.isEmpty())&&(ch2.get(0).getEid()!=ch1.getEid()))
			throw new BusinessException(String.format("the channel name must be unique %s", ch.getName()));
		ch1.setName(ch.getName());
		ch1.setScope(ch.getScope());
		ch1.setDescription(ch.getDescription());
		rep.getChannelRepo().save(ch1);
		return ch1;
		
	}
	
	
	
}
