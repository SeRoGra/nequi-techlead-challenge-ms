package co.com.nequi.techlead.challenge.usecase.sitemanagement;

import co.com.nequi.techlead.challenge.exceptions.BadRequestException;
import co.com.nequi.techlead.challenge.exceptions.NotFoundException;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.site.Site;
import co.com.nequi.techlead.challenge.model.site.gateways.SiteGateway;
import co.com.nequi.techlead.challenge.usecase.brandsmanagement.BrandManagementUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static co.com.nequi.techlead.challenge.enums.ErrorMessage.SITE_BRAND_MISMATCH;
import static co.com.nequi.techlead.challenge.enums.ErrorMessage.SITE_NOT_FOUND;

@RequiredArgsConstructor
public class SiteManagementUseCase {

    private final SiteGateway siteGateway;
    private final BrandManagementUseCase brandManagementUseCase;

    public Mono<Site> createSite(String name, Integer brandId) {
        return brandManagementUseCase.getBrandById(brandId)
                .flatMap(brand -> siteGateway.createSite(Site.builder()
                        .name(name)
                        .brand(brand)
                        .build()));
    }

    public Mono<Site> updateSite(Integer branId, Integer siteId, String name) {
        return getSiteById(siteId)
                .filter(site -> branId.equals(site.getBrand().getId()))
                .flatMap(site -> siteGateway.updateSite(site.toBuilder().name(name).build()))
                .switchIfEmpty(Mono.error(new BadRequestException(SITE_BRAND_MISMATCH.getMessage())));
    }

    public Mono<Site> getSiteById(Integer siteId) {
        return siteGateway.getSiteById(siteId)
                .switchIfEmpty(Mono.error(new NotFoundException(SITE_NOT_FOUND.getMessage())));
    }

    public Flux<Site> getAllSitesByBrandId(Integer brandId){
        return brandManagementUseCase.getBrandById(brandId)
                .map(Brand::getId)
                .flatMapMany(siteGateway::getSitesByBrandId)
                .switchIfEmpty(Mono.error(new NotFoundException(SITE_NOT_FOUND.getMessage())));
    }

}
