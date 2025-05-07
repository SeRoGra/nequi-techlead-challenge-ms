package co.com.nequi.techlead.challenge.jpa.repository;

import co.com.nequi.techlead.challenge.jpa.entity.SiteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface SiteRepository extends CrudRepository<SiteEntity, Integer>, QueryByExampleExecutor<SiteEntity> {
    List<SiteEntity> findSitesByBrandId(Integer brandId);
}
