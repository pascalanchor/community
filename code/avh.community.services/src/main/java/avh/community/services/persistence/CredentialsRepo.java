package avh.community.services.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.community.model.Credentials;
import avh.community.model.User;

@Repository
public interface CredentialsRepo extends CrudRepository<Credentials, String> {
	public List<Credentials> findByUser(User user);
}

