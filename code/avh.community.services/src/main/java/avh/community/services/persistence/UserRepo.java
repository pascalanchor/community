package avh.community.services.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.community.model.User;

@Repository
public interface UserRepo extends CrudRepository<User, String> {
}
