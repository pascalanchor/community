package avh.community.services.api.transformer;

import avh.community.model.Channel;
import avh.community.model.Token;
import avh.community.model.User;
import avh.community.services.api.model.in.APIChannelIn;
import avh.community.services.api.model.in.APIUserIn;
import avh.community.services.api.model.out.APIChannelOut;
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
	
}
