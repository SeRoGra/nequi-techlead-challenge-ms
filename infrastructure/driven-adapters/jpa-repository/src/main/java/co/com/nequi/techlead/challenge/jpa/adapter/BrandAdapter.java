package co.com.nequi.techlead.challenge.jpa.adapter;

import co.com.nequi.techlead.challenge.jpa.helper.AdapterOperations;
import co.com.nequi.techlead.challenge.jpa.entity.BrandEntity;
import co.com.nequi.techlead.challenge.jpa.repository.BrandRepository;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.brand.gateways.BrandGateway;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class BrandAdapter extends AdapterOperations<Brand, BrandEntity, Integer, BrandRepository>
        implements BrandGateway {

    public BrandAdapter(BrandRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Brand.class));
    }

    @Override
    public Flux<Brand> getAllBrands() {
        return Flux.fromIterable(findAll());
    }

    @Override
    public Mono<Brand> getBrandById(Integer brandId) {
        return Mono.justOrEmpty(findById(brandId));
    }

    @Override
    public Mono<Brand> createBrand(String name) {
        return Mono.defer(() -> Mono.just(
                save(Brand.builder()
                        .name(name)
                        .build())
        ));
    }

    @Override
    public Mono<Brand> updateBrand(Integer brandId, String name) {
        return Mono.defer(() -> Mono.just(
                save(Brand.builder()
                        .id(brandId)
                        .name(name)
                        .build())
        ));
    }

}
