package co.com.nequi.techlead.challenge.jpa.repository;

import co.com.nequi.techlead.challenge.jpa.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer>, QueryByExampleExecutor<ProductEntity> {
}
