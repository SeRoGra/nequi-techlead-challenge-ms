package co.com.nequi.techlead.challenge.usecase.brandsmanagement;

import co.com.nequi.techlead.challenge.exceptions.NotFoundException;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.brand.gateways.BrandGateway;
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

import static co.com.nequi.techlead.challenge.enums.ErrorMessage.BRAND_NOT_FOUND;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BrandManagementUseCaseTest {

    @Mock
    BrandGateway brandGateway;

    @InjectMocks
    BrandManagementUseCase brandManagementUseCase;

    @Test
    void getAllBrands_ShouldReturnListOfBrands() {
        // Arrange
        List<Brand> fakeBrands = MockData.getFakeBrands();

        given(brandGateway.getAllBrands())
                .willReturn(Flux.fromIterable(fakeBrands));

        // Act
        Flux<Brand> result = brandManagementUseCase.getAllBrands();

        // Assert
        StepVerifier.create(result)
                .expectNextSequence(fakeBrands)
                .verifyComplete();
    }

    @Test
    void getBrandById_ShouldReturnBrand_WhenExists() {
        // Arrange
        Brand brand = MockData.createFakeBrand(1, "Qbano");
        given(brandGateway.getBrandById(1))
                .willReturn(Mono.just(brand));

        // Act
        Mono<Brand> result = brandManagementUseCase.getBrandById(1);

        // Assert
        StepVerifier.create(result)
                .expectNext(brand)
                .verifyComplete();
    }

    @Test
    void getBrandById_ShouldReturnError_WhenNotFound() {
        // Arrange
        given(brandGateway.getBrandById(99)).willReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(brandManagementUseCase.getBrandById(99))
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException &&
                        throwable.getMessage().equals(BRAND_NOT_FOUND.getMessage()))
                .verify();
    }

    @Test
    void createBrand_ShouldReturnCreatedBrand() {
        // Arrange
        String name = "Creeps & Waffles";
        Brand brand = MockData.createFakeBrand(1, name);
        given(brandGateway.createBrand(name)).willReturn(Mono.just(brand));

        // Act
        Mono<Brand> result = brandManagementUseCase.createBrand(name);

        // Assert
        StepVerifier.create(result)
                .expectNext(brand)
                .verifyComplete();
    }

    @Test
    void updateBrand_ShouldUpdateAndReturnBrand_WhenExists() {
        // Arrange
        Integer brandId = 1;
        String newName = "Updated Brand";
        Brand existingBrand = MockData.createFakeBrand(brandId, "Old Brand");
        Brand updatedBrand = MockData.createFakeBrand(brandId, newName);

        given(brandGateway.getBrandById(brandId)).willReturn(Mono.just(existingBrand));
        given(brandGateway.updateBrand(brandId, newName)).willReturn(Mono.just(updatedBrand));

        // Act
        Mono<Brand> result = brandManagementUseCase.updateBrand(brandId, newName);

        // Assert
        StepVerifier.create(result)
                .expectNext(updatedBrand)
                .verifyComplete();
    }

    @Test
    void updateBrand_ShouldReturnError_WhenBrandNotFound() {
        // Arrange
        Integer brandId = 999;
        String name = "McDonald's";
        given(brandGateway.getBrandById(brandId)).willReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(brandManagementUseCase.updateBrand(brandId, name))
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException &&
                        throwable.getMessage().equals(BRAND_NOT_FOUND.getMessage()))
                .verify();
    }
}