package avh.community.services.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.community.model.Message;
import avh.community.model.ChannelThread;

@Repository
public interface MessageRepo extends CrudRepository<Message, String>{
	List<Message> findByThread(ChannelThread thread);
	List<Message> findByEid(String eid);
	
}
