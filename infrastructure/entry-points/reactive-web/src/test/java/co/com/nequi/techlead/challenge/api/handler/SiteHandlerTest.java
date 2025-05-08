package co.com.nequi.techlead.challenge.api.handler;

import co.com.nequi.techlead.challenge.api.dto.site.CreateSiteRequest;
import co.com.nequi.techlead.challenge.api.dto.site.UpdateSiteRequest;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.site.Site;
import co.com.nequi.techlead.challenge.usecase.sitemanagement.SiteManagementUseCase;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@ExtendWith(MockitoExtension.class)
class SiteHandlerTest {

    @Mock
    private SiteManagementUseCase siteManagementUseCase;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        SiteHandler handler = new SiteHandler(validator, siteManagementUseCase);
        RouterFunction<ServerResponse> router = route()
                .GET("/brands/{brandId}/sites", handler::getSitesByBrandId)
                .POST("/brands/{brandId}/sites", handler::createSite)
                .PUT("/brands/{brandId}/sites/{siteId}", handler::updateSite)
                .build();
        webTestClient = WebTestClient.bindToRouterFunction(router).build();
    }

    @Test
    void getSitesByBrandId_ShouldReturnListOfSites() {
        Brand brand = new Brand(1, "TestBrand");
        List<Site> sites = List.of(new Site(1, "Site1", brand));
        given(siteManagementUseCase.getSitesByBrandId(1)).willReturn(Flux.fromIterable(sites));

        webTestClient.get()
                .uri("/brands/1/sites")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Site.class).hasSize(1);
    }

    @Test
    void createSite_ShouldReturnCreatedSite() {
        Brand brand = new Brand(1, "TestBrand");
        Site createdSite = new Site(1, "NewSite", brand);

        given(siteManagementUseCase.createSite("NewSite", 1)).willReturn(Mono.just(createdSite));

        webTestClient.post()
                .uri("/brands/1/sites")
                .bodyValue(new CreateSiteRequest("NewSite"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Site.class).isEqualTo(createdSite);
    }

    @Test
    void updateSite_ShouldReturnUpdatedSite() {
        Brand brand = new Brand(1, "TestBrand");
        Site updatedSite = new Site(1, "UpdatedSite", brand);

        given(siteManagementUseCase.updateSite(1, 1, "UpdatedSite"))
                .willReturn(Mono.just(updatedSite));

        webTestClient.put()
                .uri("/brands/1/sites/1")
                .bodyValue(new UpdateSiteRequest("UpdatedSite"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Site.class).isEqualTo(updatedSite);
    }

}