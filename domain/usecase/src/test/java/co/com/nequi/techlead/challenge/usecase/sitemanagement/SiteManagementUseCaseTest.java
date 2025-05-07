package co.com.nequi.techlead.challenge.usecase.sitemanagement;

import co.com.nequi.techlead.challenge.exceptions.BadRequestException;
import co.com.nequi.techlead.challenge.exceptions.NotFoundException;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.site.Site;
import co.com.nequi.techlead.challenge.model.site.gateways.SiteGateway;
import co.com.nequi.techlead.challenge.usecase.brandsmanagement.BrandManagementUseCase;
import co.com.nequi.techlead.challenge.usecase.utils.MockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static co.com.nequi.techlead.challenge.enums.ErrorMessage.SITE_BRAND_MISMATCH;
import static co.com.nequi.techlead.challenge.enums.ErrorMessage.SITE_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SiteManagementUseCaseTest {

    @Mock
    SiteGateway siteGateway;

    @Mock
    BrandManagementUseCase brandManagementUseCase;

    @InjectMocks
    SiteManagementUseCase siteManagementUseCase;

    @Test
    void createSite_ShouldReturnCreatedSite() {
        Brand brand = MockData.createFakeBrand(1, "Creeps & Waffles");
        Site site = MockData.createFakeSite(1, "C&W B El Poblado Medellin", brand);

        given(brandManagementUseCase.getBrandById(1)).willReturn(Mono.just(brand));
        given(siteGateway.createSite(any(Site.class))).willReturn(Mono.just(site));

        StepVerifier.create(siteManagementUseCase.createSite("C&W B El Poblado Medellin", 1))
                .expectNext(site)
                .verifyComplete();
    }

    @Test
    void updateSite_ShouldReturnUpdatedSite() {
        Brand brand = MockData.createFakeBrand(1, "Creeps & Waffles");
        Site site = MockData.createFakeSite(2, "OldSite", brand);
        Site updatedSite = site.toBuilder().name("UpdatedSite").build();

        given(siteGateway.getSiteById(2)).willReturn(Mono.just(site));
        given(siteGateway.updateSite(any(Site.class))).willReturn(Mono.just(updatedSite));

        StepVerifier.create(siteManagementUseCase.updateSite(1, 2, "UpdatedSite"))
                .expectNext(updatedSite)
                .verifyComplete();
    }

    @Test
    void updateSite_ShouldReturnErrorWhenMismatch() {
        Brand brand = MockData.createFakeBrand(2, "Subway"); // site brandId = 2, input = 1
        Site site = MockData.createFakeSite(3, "Site", brand);

        given(siteGateway.getSiteById(3)).willReturn(Mono.just(site));

        StepVerifier.create(siteManagementUseCase.updateSite(1, 3, "NewName"))
                .expectErrorMatches(e -> e instanceof BadRequestException &&
                        e.getMessage().equals(SITE_BRAND_MISMATCH.getMessage()))
                .verify();
    }

    @Test
    void getSitesByBrandId_ShouldReturnSites() {
        Brand brand = MockData.createFakeBrand(1, "Creeps & Waffles");
        List<Site> sites = MockData.getFakeSites(brand);

        given(brandManagementUseCase.getBrandById(1)).willReturn(Mono.just(brand));
        given(siteGateway.getSitesByBrandId(1)).willReturn(Flux.fromIterable(sites));

        StepVerifier.create(siteManagementUseCase.getSitesByBrandId(1))
                .expectNextSequence(sites)
                .verifyComplete();
    }

    @Test
    void getSitesByBrandId_ShouldReturnErrorWhenNoSites() {
        Brand brand = MockData.createFakeBrand(1, "Creeps & Waffles");

        given(brandManagementUseCase.getBrandById(1)).willReturn(Mono.just(brand));
        given(siteGateway.getSitesByBrandId(1)).willReturn(Flux.empty());

        StepVerifier.create(siteManagementUseCase.getSitesByBrandId(1))
                .expectErrorMatches(e -> e instanceof NotFoundException &&
                        e.getMessage().equals(SITE_NOT_FOUND.getMessage()))
                .verify();
    }

}