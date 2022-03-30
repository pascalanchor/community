package avh.community.services.api;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import avh.community.model.Channel;
import avh.community.model.Subscription;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.api.model.in.APIChannelIn;
import avh.community.services.api.model.in.APISubscriptionIn;
import avh.community.services.api.model.out.APIChannelOut;
import avh.community.services.api.model.out.APISubscriptionOut;
import avh.community.services.api.transformer.A2BTransformer;
import avh.community.services.logic.ChannelControllerImpl;

@RestController
public class ChannelController {
	@Autowired private ChannelControllerImpl isvc;
	
	@PostMapping("/avh/community/api/channel")
	public APIChannelOut registerChannel(@RequestParam String token,  @RequestBody APIChannelIn ch) {
		try {
			Channel mch = A2BTransformer.ChannelToModel(ch);
			Channel res = isvc.registerChannel(token, mch);
			return A2BTransformer.ChannelFromModel(res);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	
	
	@PostMapping("avh/community/api/channel/joinRequest")
	public APISubscriptionOut requestToJoinChannel(@RequestParam String token,@RequestBody APISubscriptionIn sbi) {
		try {
			
			Subscription sb=isvc.requestToJoinChannel(token, sbi);
			return(A2BTransformer.subscriptionFromModel(sb));
			
		}catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
		
	}
	
	
	@GetMapping("/avh/community/api/channels")
	public List<APIChannelOut> getAllChannels(@RequestParam String token){
		List<APIChannelOut> list = new ArrayList<>();
		list = A2BTransformer.ChannelListFromModel(isvc.getAllChannels(token));
		return list;
	}
	
	@GetMapping("avh/community/api/channelByid")
	public APIChannelOut getChannelById(@RequestParam String token,@RequestParam String channel_id) {
		try {
			return(A2BTransformer.ChannelFromModel(isvc.getChannelById(token, channel_id)));
		}catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@PutMapping("avh/community/api/updateChannel")
	public APIChannelOut updateChannel(@RequestParam String token,@RequestParam String channel_id,@RequestBody APIChannelIn chin)
	{
		try {
			System.out.println("in controller the name is:"+chin.getName());
			Channel mch = A2BTransformer.ChannelToModel(chin);
			return(A2BTransformer.ChannelFromModel(isvc.updateChannel(token, channel_id,mch)));
		}catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	
	@GetMapping("avh/community/api/channel/userSubscriptions")
	public List<APISubscriptionOut> getUserSubscriptions(@RequestParam String token,@RequestParam("email") String user_email){
		try {
			List<APISubscriptionOut> list=new ArrayList<>();
			list=A2BTransformer.subscriptionListFromModel(isvc.getUserSubscriptions(token, user_email));
			return list;
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
}
