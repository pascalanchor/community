package avh.community.services.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.community.model.ChannelThread;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.api.model.in.APIChannelThreadIn;
import avh.community.services.api.model.out.APIChannelThreadOut;
import avh.community.services.api.transformer.A2BTransformer;
import avh.community.services.logic.ChannelThreadControllerImpl;

@RestController
public class ChannelThreadController {
	@Autowired
	private ChannelThreadControllerImpl isvc;
	
	@PostMapping("/avh/community/api/thread")
	public APIChannelThreadOut addThreadToChannel(@RequestParam String token,
			@RequestParam String channel_id,
			@RequestBody APIChannelThreadIn th) {
		try {
			ChannelThread mth = A2BTransformer.ThreadToModel(th);
			ChannelThread res = isvc.registerThread(token, mth, channel_id);
			return A2BTransformer.ThreadFromModel(res);
		}
		catch(BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
}
