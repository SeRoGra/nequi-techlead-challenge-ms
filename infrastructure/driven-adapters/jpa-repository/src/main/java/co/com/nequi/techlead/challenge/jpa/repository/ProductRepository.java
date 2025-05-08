package co.com.nequi.techlead.challenge.jpa.repository;

import co.com.nequi.techlead.challenge.jpa.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer>, QueryByExampleExecutor<ProductEntity> {
    List<ProductEntity> findProductsBySiteId(Integer siteId);
}
