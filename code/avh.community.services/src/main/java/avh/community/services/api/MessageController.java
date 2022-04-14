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

import avh.community.model.Message;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.api.model.in.APIMessageIn;
import avh.community.services.api.model.out.APIMessageOut;
import avh.community.services.api.transformer.A2BTransformer;
import avh.community.services.logic.MessageControllerImpl;

@RestController
public class MessageController {
	@Autowired MessageControllerImpl isvc;
	
	@PostMapping("avh/community/api/comment")
	public APIMessageOut addComment(@RequestParam String token,
			@RequestParam String thread_id,
			@RequestParam String channel_id,
			@RequestBody APIMessageIn msin) {
			
		try {
			Message msg=A2BTransformer.MessageToModel(msin);
			Message res=isvc.addComment(token, thread_id, channel_id, msg);
			return (A2BTransformer.MessageFromModel(res));
		}catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	
	@PostMapping("avh/community/api/reply")
	public APIMessageOut addMessage(@RequestParam String token,
			@RequestParam String thread_id,
			@RequestParam String channel_id,
			@RequestParam String reply_to,
			@RequestBody APIMessageIn msin) {
			
		try {
			Message msg=A2BTransformer.MessageToModel(msin);
			Message res=isvc.addReply(token, thread_id, channel_id, reply_to, msg);
			return (A2BTransformer.MessageFromModel(res));
		}catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	public List<APIMessageOut> getAllThreadsMessages(@RequestParam String token,@RequestParam String thread_id){
		
		return null;
	}
	
	@GetMapping("avh/community/api/messages")
	public List<APIMessageOut> getAllThreadMessages(@RequestParam String token,@RequestParam String thread_id){
		try {
			List<Message> mslist=isvc.getAllThreadsMessages(token, thread_id);
			return(A2BTransformer.messageListFromModel(mslist));
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	
	@PutMapping("avh/community/api/editMessage")
	public APIMessageOut editMessage(@RequestParam String token,
			@RequestParam String message_id,
			@RequestBody APIMessageIn msin) {
		try {
			Message msg=A2BTransformer.MessageToModel(msin);
			return (A2BTransformer.MessageFromModel(isvc.editMessage(token, message_id, msg)));
		}catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@DeleteMapping("avh/community/api/deleteMessage")
	public boolean deleteMessage(@RequestParam String token, @RequestParam String message_id) {
		try {
			return isvc.deleteMessage(token, message_id);
		}catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	
}
