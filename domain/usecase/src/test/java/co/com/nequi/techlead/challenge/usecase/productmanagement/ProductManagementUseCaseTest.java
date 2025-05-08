package co.com.nequi.techlead.challenge.usecase.productmanagement;

import co.com.nequi.techlead.challenge.exceptions.BadRequestException;
import co.com.nequi.techlead.challenge.exceptions.NotFoundException;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.product.Product;
import co.com.nequi.techlead.challenge.model.product.dto.UpdateProductCommand;
import co.com.nequi.techlead.challenge.model.product.gateways.ProductGateway;
import co.com.nequi.techlead.challenge.model.site.Site;
import co.com.nequi.techlead.challenge.usecase.sitemanagement.SiteManagementUseCase;
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

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductManagementUseCaseTest {

    @Mock
    SiteManagementUseCase siteManagementUseCase;

    @Mock
    ProductGateway productGateway;

    @InjectMocks
    ProductManagementUseCase useCase;

    @Test
    void createProduct_ShouldReturnProduct() {
        // Arrange
        Brand brand = MockData.createFakeBrand(1, "Creeps & Waffles");
        Site site = MockData.createFakeSite(1,"C&W Pasoancho Cali", brand);
        Product expectedProduct = MockData.createFakeProduct(1, "Ice Waffle", 10, site);

        // Act
        given(siteManagementUseCase.getSiteByIdAndBrandId(2, 1)).willReturn(Mono.just(site));
        given(productGateway.createProduct(any())).willReturn(Mono.just(expectedProduct));

        // Assert
        StepVerifier.create(useCase.createProduct(2, 1, "Ice Waffle", 10))
                .expectNext(expectedProduct)
                .verifyComplete();
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        // Arrange
        Brand brand = MockData.createFakeBrand(1, "Creeps & Waffles");
        Site site = MockData.createFakeSite(1,"C&W Pasoancho Cali", brand);
        Product existingProduct = MockData.createFakeProduct(1, "Old Product", 5, site);

        UpdateProductCommand cmd = new UpdateProductCommand(
                brand.getId(), site.getId(), 1, "Updated", 20);

        Product updatedProduct = existingProduct.toBuilder()
                .name("Updated").stock(20).build();

        // Act
        given(productGateway.getProductById(1)).willReturn(Mono.just(existingProduct));
        given(productGateway.updateProduct(updatedProduct)).willReturn(Mono.just(updatedProduct));

        // Assert
        StepVerifier.create(useCase.updateProduct(cmd))
                .expectNext(updatedProduct)
                .verifyComplete();
    }

    @Test
    void updateProduct_ShouldThrowException_WhenMismatch() {
        // Arrange
        Brand brand = MockData.createFakeBrand(1, "Creeps & Waffles");
        Site site = MockData.createFakeSite(99, "C&W Pasoancho Cali", brand);
        Product product = MockData.createFakeProduct(1, "Ice cream", 10, site);
        UpdateProductCommand cmd = new UpdateProductCommand(2, 1, 1, "New", 15);

        // Act
        given(productGateway.getProductById(1)).willReturn(Mono.just(product));

        // Assert
        StepVerifier.create(useCase.updateProduct(cmd))
                .expectError(BadRequestException.class)
                .verify();
    }

    @Test
    void deleteProduct_ShouldComplete() {
        // Arrange
        Brand brand = MockData.createFakeBrand(2, "Creeps & Waffles");
        Site site = MockData.createFakeSite(1,"C&W Pasoancho Cali", brand);
        Product product = MockData.createFakeProduct(1, "milkshake", 10, site);

        // Act
        given(productGateway.getProductById(1)).willReturn(Mono.just(product));
        given(productGateway.deleteProduct(1)).willReturn(Mono.empty());

        // Assert
        StepVerifier.create(useCase.deleteProduct(2, 1, 1))
                .verifyComplete();
    }

    @Test
    void getTopProductsByBrandId_ShouldReturnTopProducts() {
        // Arrange
        Brand brand = MockData.createFakeBrand(1, "Creeps & Waffles");
        Site site = MockData.createFakeSite(1,"C&W Pasoancho Cali", brand);

        Product topProduct = MockData.createFakeProduct(1, "Ice Waffle", 50, site);
        Product anyProduct = MockData.createFakeProduct(2, "milkshake", 20, site);

        List<Product> products = List.of(topProduct, anyProduct);

        // Act
        given(siteManagementUseCase.getSitesByBrandId(1)).willReturn(Flux.just(site));

        given(productGateway.getProductsBySiteId(site.getId()))
                .willReturn(Flux.fromIterable(products));

        // Assert
        StepVerifier.create(useCase.getTopProductsByBrandId(1))
                .expectNext(topProduct)
                .verifyComplete();
    }

    @Test
    void getTopProductsByBrandId_ShouldThrow_WhenNoProducts() {
        // Arrange
        Brand brand = MockData.createFakeBrand(1, "Creeps & Waffles");
        Site site = MockData.createFakeSite(1,"C&W Pasoancho Cali", brand);

        // Act
        given(siteManagementUseCase.getSitesByBrandId(2)).willReturn(Flux.just(site));
        given(productGateway.getProductsBySiteId(1)).willReturn(Flux.empty());

        // Assert
        StepVerifier.create(useCase.getTopProductsByBrandId(2))
                .expectError(NotFoundException.class)
                .verify();
    }

}