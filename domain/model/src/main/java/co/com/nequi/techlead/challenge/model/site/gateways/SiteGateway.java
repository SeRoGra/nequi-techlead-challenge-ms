package co.com.nequi.techlead.challenge.model.site.gateways;

import co.com.nequi.techlead.challenge.model.site.Site;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SiteGateway {
    Flux<Site> getAllSites();
    Mono<Site> createSite(String name, Integer brandId);
    Mono<Site> updateSite(Integer id, String name);
}
