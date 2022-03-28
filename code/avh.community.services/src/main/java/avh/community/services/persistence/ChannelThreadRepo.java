package avh.community.services.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.community.model.ChannelThread;

@Repository
public interface ChannelThreadRepo extends CrudRepository<ChannelThread, String> {
	public ChannelThread save(ChannelThread th);
}
