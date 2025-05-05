package co.com.nequi.techlead.challenge.usecase.brandsmanagement;

import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.brand.gateways.BrandRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BrandManagementUseCase {

    private final BrandRepository brandRepository;

    public Flux<Brand> getAllBrands(){
        return brandRepository.getAllBrands();
    }

    public Mono<Brand> createBrand(String name) {
        return brandRepository.createBrand(name);
    }

    public Mono<Brand> updateBrand(Integer id, String name) {
        return brandRepository.updateBrand(id, name);
    }

}
