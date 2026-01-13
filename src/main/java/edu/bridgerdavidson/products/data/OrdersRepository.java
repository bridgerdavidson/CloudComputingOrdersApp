package edu.bridgerdavidson.products.data;

import edu.bridgerdavidson.products.models.OrderEntity;
import edu.bridgerdavidson.products.models.OrderModel;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<OrderEntity, Integer> {
}
