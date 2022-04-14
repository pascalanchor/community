package avh.community.services.api.transformer;

import java.util.ArrayList;
import java.util.List;

import avh.community.model.Channel;
import avh.community.model.ChannelThread;
import avh.community.model.Message;
import avh.community.model.Subscription;
import avh.community.model.Token;
import avh.community.model.User;
import avh.community.services.api.model.in.APIChannelIn;
import avh.community.services.api.model.in.APIChannelThreadIn;
import avh.community.services.api.model.in.APIMessageIn;
import avh.community.services.api.model.in.APISubscriptionIn;
import avh.community.services.api.model.in.APIUserIn;
import avh.community.services.api.model.out.APIChannelOut;
import avh.community.services.api.model.out.APIChannelThreadOut;
import avh.community.services.api.model.out.APIMessageOut;
import avh.community.services.api.model.out.APISubscriptionOut;
import avh.community.services.api.model.out.APITokenOut;
import avh.community.services.api.model.out.APIUserOut;

public class A2BTransformer {
	public static User UserToModel(APIUserIn u) {
		User res = new User();
		res.setEmail(u.getEmail());
		res.setFirstname(u.getFirstname());
		res.setLastname(u.getLastname());
		return res;
	}
	
	public static APIUserOut UserFromModel(User u) {
		APIUserOut res = new APIUserOut(u.getEmail(), u.getFirstname(), u.getLastname());
		return res;
	}
	
	public static APITokenOut TokenFromModel(Token t) {
		APITokenOut res = new APITokenOut(t.getEid(), t.getUser().getEmail(), t.getTokenDate().getTime(), t.getExpiryDate().getTime());
		return res;
	}
	
	public static Channel ChannelToModel(APIChannelIn ch) {
		Channel res = new Channel();
		res.setName(ch.getName());
		res.setDescription(ch.getDescription());
		res.setScope(ch.getScope());
		return res;
	}
	
	public static APIChannelOut ChannelFromModel(Channel ch) {
		return new APIChannelOut(ch.getName(), ch.getDescription(), ch.getScope(), ch.getEid(), ch.getCreationDate().getTime());
	}
	
	public static List<APIChannelOut> ChannelListFromModel(Iterable<Channel> it){
		List<APIChannelOut> list = new ArrayList<>();
		for(Channel ch : it){
			APIChannelOut och = new APIChannelOut();
			och.setName(ch.getName());
			och.setDescription(ch.getDescription());
			och.setScope(ch.getScope());
			och.setId(ch.getEid());
			list.add(och);
	}
	return list;
	}
	
	
	public static APISubscriptionOut subscriptionFromModel(Subscription sb)
	{
		APISubscriptionOut res=new APISubscriptionOut(sb.getUser().getEmail(),sb.getChannel().getEid(),sb.getStatus());
		return res;
	}
	
	public static ChannelThread ThreadToModel(APIChannelThreadIn th) {
		ChannelThread res = new ChannelThread();
		res.setKeywords(th.getKeywords());
		res.setSubject(th.getSubject());
		System.out.println(res.getKeywords());
		return res;
	}
	
	public static APIChannelThreadOut ThreadFromModel(ChannelThread th) {
		APISubscriptionOut sub = subscriptionFromModel(th.getSubscription());
		APIChannelThreadOut res = new APIChannelThreadOut(
				th.getKeywords(),
				th.getSubject(),
				th.getCdate().getTime(),
				sub
				);
		return res;
	}
	
	
	
	public static Message MessageToModel(APIMessageIn msin) {
		Message res=new Message();
		res.setBody(msin.getBody());
		res.setStars(msin.getStars());
		return res;
	}
	
	public static APIMessageOut MessageFromModel(Message msg) {
		
		APISubscriptionOut sub = subscriptionFromModel(msg.getSubscription());
		APIChannelThreadOut th=ThreadFromModel(msg.getThread());
		String reply_to;
		
		if(msg.getMessage()==null)//its a message
			reply_to=null;
		else //its a reply
			reply_to=msg.getMessage().getEid();
		APIMessageOut res=new APIMessageOut(msg.getEid(),
				msg.getBody(),
				msg.getStars(),
				msg.getCdate(),
				reply_to,
				th,
				sub);
		return res;
	}
	
	public static List<APIMessageOut> messageListFromModel(List<Message> mslist){
		List<APIMessageOut> res=new ArrayList<>();
		for(Message msg:mslist) {
			APIMessageOut mso=new APIMessageOut();
			mso.setBody(msg.getBody());
			mso.setChannelThread(A2BTransformer.ThreadFromModel(msg.getThread()));
			mso.setSubscription(A2BTransformer.subscriptionFromModel(msg.getSubscription()));
			System.out.println("subs id: "+msg.getSubscription().getEid() );
			mso.setId(msg.getEid());
			String reply_to;
			if(msg.getMessage()==null)// its a message
				reply_to=null;
			else//its a reply
				reply_to=msg.getMessage().getEid();
			mso.setReply_to(reply_to);
			mso.setStars(msg.getStars());
			mso.setCreationDate(msg.getCdate());
			
			res.add(mso);
			
		}
		return res;
	}
	
	
	
}
