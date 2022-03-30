package avh.community.services.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.community.model.Channel;
import avh.community.model.ChannelThread;
import avh.community.model.Subscription;

@Repository
public interface ChannelThreadRepo extends CrudRepository<ChannelThread, String> {
	public List<ChannelThread> findBySubscription(Subscription subscription);
}
