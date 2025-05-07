package co.com.nequi.techlead.challenge.jpa.adapter;

import co.com.nequi.techlead.challenge.jpa.entity.SiteEntity;
import co.com.nequi.techlead.challenge.jpa.helper.AdapterOperations;
import co.com.nequi.techlead.challenge.jpa.repository.SiteRepository;
import co.com.nequi.techlead.challenge.model.site.Site;
import co.com.nequi.techlead.challenge.model.site.gateways.SiteGateway;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class SiteAdapter extends AdapterOperations<Site, SiteEntity, Integer, SiteRepository>
        implements SiteGateway {

    public SiteAdapter(SiteRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Site.class));
    }

    @Override
    public Mono<Site> createSite(Site site) {
        return Mono.defer(() -> Mono.just(save(site)));
    }

    @Override
    public Mono<Site> updateSite(Site site) {
        return Mono.defer(() -> Mono.just(save(site)));
    }

    @Override
    public Mono<Site> getSiteById(Integer siteId) {
        return Mono.justOrEmpty(findById(siteId));
    }

    @Override
    public Flux<Site> getSitesByBrandId(Integer brandId) {
        return Flux.fromIterable(repository.findSitesByBrandId(brandId))
                .map(this::toEntity);
    }

}
