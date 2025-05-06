package co.com.nequi.techlead.challenge.model.brand.gateways;

import co.com.nequi.techlead.challenge.model.brand.Brand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BrandGateway {
    Flux<Brand> getAllBrands();
    Mono<Brand> getBrandById(Integer brandId);
    Mono<Brand> createBrand(String name);
    Mono<Brand> updateBrand(Integer brandId, String name);
}
