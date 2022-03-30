package avh.community.services.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PutMapping("/avh/community/api/edit_thread")
	public APIChannelThreadOut editThread(@RequestParam String token,@RequestParam String thread_id,
			@RequestBody APIChannelThreadIn th) {
		try {
			ChannelThread mth = A2BTransformer.ThreadToModel(th);
			ChannelThread res = isvc.updateThread(token, thread_id,mth);
			return A2BTransformer.ThreadFromModel(res);
		}
		catch(BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@DeleteMapping("/avh/community/api/remove_thread")
	public APIChannelThreadOut removeThread(@RequestParam String token,@RequestParam String thread_id) {
		try {
			ChannelThread res = isvc.deleteThread(token, thread_id);
			return A2BTransformer.ThreadFromModel(res);
		}
		catch(BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	

	
	@GetMapping("/avh/community/api/threads")
	public List<APIChannelThreadOut> getThreadsByChannel(@RequestParam String token,@RequestParam String channel_id) {
		try {
			List<ChannelThread> mlist = isvc.getThreadsByChannel(token, channel_id);
			return A2BTransformer.ListThreadFromListModel(mlist);
		}
		catch(BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
}
