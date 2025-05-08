package co.com.nequi.techlead.challenge.usecase.brandsmanagement;

import static co.com.nequi.techlead.challenge.enums.ErrorMessage.BRAND_NOT_FOUND;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.brand.gateways.BrandGateway;
import co.com.nequi.techlead.challenge.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BrandManagementUseCase {

    private final BrandGateway brandGateway;

    public Flux<Brand> getAllBrands(){
        return brandGateway.getAllBrands()
                .switchIfEmpty(Mono.error(new NotFoundException(BRAND_NOT_FOUND.getMessage())));
    }

    public Mono<Brand> getBrandById(Integer brandId) {
        return brandGateway.getBrandById(brandId)
                .switchIfEmpty(Mono.error(new NotFoundException(BRAND_NOT_FOUND.getMessage())));
    }

    public Mono<Brand> createBrand(String name) {
        return brandGateway.createBrand(name);
    }

    public Mono<Brand> updateBrand(Integer brandId, String name) {
        return getBrandById(brandId)
                .flatMap(brand ->  brandGateway.updateBrand(brandId, name));
    }

}
