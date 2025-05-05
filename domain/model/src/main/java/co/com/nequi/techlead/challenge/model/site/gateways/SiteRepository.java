package co.com.nequi.techlead.challenge.model.site.gateways;

import co.com.nequi.techlead.challenge.model.site.Site;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SiteRepository {
    Flux<Site> getSites();
    Mono<Site> createSite(Integer brandId, String name);
    Mono<Site> updateSite(String id, String name);
}
