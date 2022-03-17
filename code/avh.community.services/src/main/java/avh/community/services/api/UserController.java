package avh.community.services.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.community.model.Token;
import avh.community.model.User;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.api.model.in.APIUserIn;
import avh.community.services.api.model.out.APITokenOut;
import avh.community.services.api.model.out.APIUserOut;
import avh.community.services.api.transformer.A2BTransformer;
import avh.community.services.logic.UserControllerImpl;

@RestController
public class UserController {

	@Autowired
	private UserControllerImpl usvc;
	
	@PostMapping("/avh/community/api/user")
	public APIUserOut registerUser(@RequestBody APIUserIn user) {
		try {
			User u = A2BTransformer.UserToModel(user);
			User res = usvc.registerUser(u, user.getPassword());
			return A2BTransformer.UserFromModel(res);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
		
	@GetMapping("/avh/community/api/user/login")
	public APITokenOut login(@RequestParam("email") String email, @RequestParam("password") String password) {
		try {
			Token tk = usvc.loginUser(email, password);
			return A2BTransformer.TokenFromModel(tk);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@PutMapping("/avh/community/api/user/renewToken")
	public APITokenOut renewToken(@RequestParam("email") String email, @RequestParam("token") String token) {
		try {
			Token tk = usvc.renewToken(email, token);
			return A2BTransformer.TokenFromModel(tk);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@PutMapping("/avh/community/api/user/password")
	public boolean updateUserPassword(@RequestParam("token") String token,
									  @RequestParam("email") String email,
									  @RequestParam("oldPassword") String oldPassword,
									  @RequestParam("newPassword") String newPassword) {
		try {
			usvc.updateUserPassword(token, email, oldPassword, newPassword);
			return true;
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@GetMapping("/avh/community/api/token/{tokenid}")
	public APIUserOut getUserInfo(@PathVariable String tokenid) {
		try {
			User u = usvc.getUserByToken(tokenid);
			APIUserOut res = A2BTransformer.UserFromModel(u);
			return res;
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
}
