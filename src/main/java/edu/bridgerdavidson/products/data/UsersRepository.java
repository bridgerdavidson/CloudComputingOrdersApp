package edu.bridgerdavidson.products.data;

import edu.bridgerdavidson.products.models.OrderEntity;
import edu.bridgerdavidson.products.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
