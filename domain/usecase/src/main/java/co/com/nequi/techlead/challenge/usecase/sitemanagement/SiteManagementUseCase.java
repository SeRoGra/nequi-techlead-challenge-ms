package co.com.nequi.techlead.challenge.usecase.sitemanagement;

import co.com.nequi.techlead.challenge.model.site.Site;
import co.com.nequi.techlead.challenge.model.site.gateways.SiteGateway;
import co.com.nequi.techlead.challenge.usecase.brandsmanagement.BrandManagementUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SiteManagementUseCase {

    private final SiteGateway siteGateway;
    private final BrandManagementUseCase brandManagementUseCase;

    public Flux<Site> getAllSites(){
        return siteGateway.getAllSites();
    }

    public Mono<Site> createSite(String name, Integer brandId) {
        return brandManagementUseCase.getBrandById(brandId)
                .flatMap(brand -> siteGateway.createSite(name, brandId));
    }

    public Mono<Site> updateSite(Integer id, String name) {
        return siteGateway.updateSite(id, name);
    }


}
