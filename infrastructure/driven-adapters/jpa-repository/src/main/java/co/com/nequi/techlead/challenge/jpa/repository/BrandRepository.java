package co.com.nequi.techlead.challenge.jpa.repository;

import co.com.nequi.techlead.challenge.jpa.entity.BrandEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface BrandRepository extends CrudRepository<BrandEntity, Integer>, QueryByExampleExecutor<BrandEntity> {
}
