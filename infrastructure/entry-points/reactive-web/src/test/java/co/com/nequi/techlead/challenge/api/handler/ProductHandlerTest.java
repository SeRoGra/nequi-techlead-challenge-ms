package co.com.nequi.techlead.challenge.api.handler;

import co.com.nequi.techlead.challenge.api.dto.product.CreateProductRequest;
import co.com.nequi.techlead.challenge.api.dto.product.UpdateProductRequest;
import co.com.nequi.techlead.challenge.api.utils.MockData;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.product.Product;
import co.com.nequi.techlead.challenge.model.product.dto.UpdateProductCommand;
import co.com.nequi.techlead.challenge.model.site.Site;
import co.com.nequi.techlead.challenge.usecase.productmanagement.ProductManagementUseCase;
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
class ProductHandlerTest {

    @Mock
    ProductManagementUseCase useCase;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ProductHandler handler = new ProductHandler(useCase);
        RouterFunction<ServerResponse> router = route()
                .GET("/api/brands/{brandId}/sites/{siteId}/products", handler::getProductsBySiteId)
                .POST("/api/brands/{brandId}/sites/{siteId}/products", handler::createProduct)
                .PUT("/api/brands/{brandId}/sites/{siteId}/products/{productId}", handler::updateProduct)
                .DELETE("/api/brands/{brandId}/sites/{siteId}/products/{productId}", handler::deleteProduct)
                .GET("/api/brands/{brandId}/top-products-by-site", handler::getTopProductsByBrandId)
                .build();
        webTestClient = WebTestClient.bindToRouterFunction(router).build();
    }

    @Test
    void getProductsBySiteId_ShouldReturnListOfProducts() {
        Brand brand = MockData.createFakeBrand(1, "BrandName");
        Site site = MockData.createFakeSite(1, "SiteName", brand);
        List<Product> products = MockData.getFakeProducts(site);

        given(useCase.getProductsByBrandIdAndSiteId(1, 1)).willReturn(Flux.fromIterable(products));

        webTestClient.get()
                .uri("/api/brands/1/sites/1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(products.size());
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        CreateProductRequest request = new CreateProductRequest("Product A", 10);
        Brand brand = MockData.createFakeBrand(1, "Brand");
        Site site = MockData.createFakeSite(1, "Site", brand);
        Product product = MockData.createFakeProduct(1, "Product A", 10, site);

        given(useCase.createProduct(1, 1, request.getName(), request.getStock())).willReturn(Mono.just(product));

        webTestClient.post()
                .uri("/api/brands/1/sites/1/products")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        UpdateProductRequest request = new UpdateProductRequest("Updated Product", 20);
        Brand brand = MockData.createFakeBrand(1, "Brand");
        Site site = MockData.createFakeSite(1, "Site", brand);
        Product product = MockData.createFakeProduct(1, request.getName(), request.getStock(), site);

        UpdateProductCommand command = UpdateProductCommand.builder()
                .brandId(brand.getId())
                .siteId(site.getId())
                .productId(product.getId())
                .name(request.getName())
                .stock(request.getStock())
                .build();

        given(useCase.updateProduct(command))
                .willReturn(Mono.just(product));

        webTestClient.put()
                .uri("/api/brands/1/sites/1/products/1")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() {
        given(useCase.deleteProduct(1, 1, 1)).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/brands/1/sites/1/products/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void getTopProductsByBrandId_ShouldReturnList() {
        Brand brand = MockData.createFakeBrand(1, "Brand");
        Site site = MockData.createFakeSite(1, "Site", brand);
        Product topProduct = MockData.createFakeProduct(1, "Top", 100, site);

        given(useCase.getTopProductsByBrandId(1)).willReturn(Flux.just(topProduct));

        webTestClient.get()
                .uri("/api/brands/1/top-products-by-site")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(1);
    }

}