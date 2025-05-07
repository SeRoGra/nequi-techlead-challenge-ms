package co.com.nequi.techlead.challenge.model.site.gateways;

import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.site.Site;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SiteGateway {
    Mono<Site> createSite(Site site);
    Mono<Site> updateSite(Site site);
    Mono<Site> getSiteById(Integer siteId);
    Flux<Site> getSitesByBrandId(Integer brandId);
}
