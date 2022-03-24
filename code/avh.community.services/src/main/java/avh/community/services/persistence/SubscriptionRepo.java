package avh.community.services.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.community.model.Channel;
import avh.community.model.Subscription;
import avh.community.model.User;

@Repository
public interface SubscriptionRepo extends CrudRepository<Subscription, String> {
	public List<Subscription> findByChannel(Channel channel);
	public List<Subscription> findByUser(User user);
	
}
 