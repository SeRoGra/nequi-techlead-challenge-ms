package co.com.nequi.techlead.challenge.jpa.adapter;

import co.com.nequi.techlead.challenge.jpa.entity.SiteEntity;
import co.com.nequi.techlead.challenge.jpa.helper.AdapterOperations;
import co.com.nequi.techlead.challenge.jpa.repository.SiteRepository;
import co.com.nequi.techlead.challenge.model.brand.Brand;
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
    public Flux<Site> getAllSites() {
        return Flux.fromIterable(findAll());
    }

    @Override
    public Mono<Site> createSite(String name, Integer brandId) {
        return Mono.defer(() -> Mono.just(
                save(Site.builder()
                        .name(name)
                        .brand(Brand.builder()
                                .id(brandId)
                                .build())
                        .build())
        ));
    }

    @Override
    public Mono<Site> updateSite(Integer id, String name) {
        return Mono.defer(() -> Mono.just(
                save(Site.builder()
                        .id(id)
                        .name(name)
                        .build())
        ));
    }

}
