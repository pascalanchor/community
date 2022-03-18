package avh.community.services.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.community.model.Channel;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.api.model.in.APIChannelIn;
import avh.community.services.api.model.out.APIChannelOut;
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
}
