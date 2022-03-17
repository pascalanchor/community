package avh.community.services.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.community.model.Token;
import avh.community.model.User;

@Repository
public interface TokenRepo extends CrudRepository<Token, String> {
	public List<Token> findByUser(User u);
	public void deleteAllByUser(User u);
}
