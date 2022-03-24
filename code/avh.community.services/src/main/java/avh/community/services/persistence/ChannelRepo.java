package avh.community.services.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.community.model.Channel;

@Repository
public interface ChannelRepo extends CrudRepository<Channel, String> {
	public List<Channel> findByName(String name);
	public Channel  findByEid(String eid);
}
