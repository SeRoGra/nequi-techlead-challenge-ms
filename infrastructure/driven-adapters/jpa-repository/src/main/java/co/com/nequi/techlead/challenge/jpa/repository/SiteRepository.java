package co.com.nequi.techlead.challenge.jpa.repository;

import co.com.nequi.techlead.challenge.jpa.entity.SiteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface SiteRepository extends CrudRepository<SiteEntity, Integer>, QueryByExampleExecutor<SiteEntity> {
}
