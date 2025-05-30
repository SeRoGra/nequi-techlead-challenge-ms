package co.com.nequi.techlead.challenge.api.handler;

import co.com.nequi.techlead.challenge.api.dto.brand.CreateBrandRequest;
import co.com.nequi.techlead.challenge.api.dto.brand.UpdateBrandRequest;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.usecase.brandsmanagement.BrandManagementUseCase;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@ExtendWith(MockitoExtension.class)
class BrandHandlerTest {

    @Mock
    private BrandManagementUseCase brandManagementUseCase;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        BrandHandler handler = new BrandHandler(validator, brandManagementUseCase);
        RouterFunction<ServerResponse> router = route()
                .GET("/brands", handler::getAllBrands)
                .POST("/brands", handler::createBrand)
                .PUT("/brands/{brandId}", handler::updateBrand)
                .build();
        webTestClient = WebTestClient.bindToRouterFunction(router).build();
    }

    @Test
    void getAllBrands_ShouldReturnList() {
        Brand brand = new Brand(1, "Creeps & Waffles");
        given(brandManagementUseCase.getAllBrands()).willReturn(Flux.just(brand));

        webTestClient.get()
                .uri("/brands")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Brand.class)
                .hasSize(1)
                .contains(brand);
    }

    @Test
    void createBrand_ShouldReturnBrand() {
        String name = "Creeps & Waffles";
        Brand brand = new Brand(1, name);
        given(brandManagementUseCase.createBrand(name)).willReturn(Mono.just(brand));

        webTestClient.post()
                .uri("/brands")
                .bodyValue(new CreateBrandRequest(name))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Brand.class)
                .isEqualTo(brand);
    }

    @Test
    void updateBrand_ShouldReturnUpdatedBrand() {
        Integer brandId = 1;
        String newName = "Domino's Pizza";
        Brand updated = new Brand(brandId, newName);
        given(brandManagementUseCase.updateBrand(brandId, newName)).willReturn(Mono.just(updated));

        webTestClient.put()
                .uri("/brands/{brandId}", brandId)
                .bodyValue(new UpdateBrandRequest(newName))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Brand.class)
                .isEqualTo(updated);
    }

}