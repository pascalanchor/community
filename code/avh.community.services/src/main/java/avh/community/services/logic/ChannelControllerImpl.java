package avh.community.services.logic;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.community.model.Channel;
import avh.community.model.Token;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.logic.domains.EChannelScope;
import avh.community.services.logic.domains.EFeature;
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
	
}
