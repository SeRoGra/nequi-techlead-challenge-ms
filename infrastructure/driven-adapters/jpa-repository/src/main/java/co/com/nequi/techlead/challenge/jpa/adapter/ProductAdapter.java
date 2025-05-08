package co.com.nequi.techlead.challenge.jpa.adapter;

import co.com.nequi.techlead.challenge.jpa.entity.ProductEntity;
import co.com.nequi.techlead.challenge.jpa.helper.AdapterOperations;
import co.com.nequi.techlead.challenge.jpa.repository.ProductRepository;
import co.com.nequi.techlead.challenge.model.product.gateways.ProductGateway;
import co.com.nequi.techlead.challenge.model.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class ProductAdapter extends AdapterOperations<Product, ProductEntity, Integer, ProductRepository>
        implements ProductGateway {

    public ProductAdapter(ProductRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        return Mono.defer(() -> Mono.just(save(product)));
    }

    public Mono<Product> updateProduct(Product product) {
        return Mono.defer(() -> Mono.just(save(product)));
    }

    @Override
    public Mono<Void> deleteProduct(Integer productId) {
        return Mono.fromRunnable(() -> repository.deleteById(productId));
    }

    @Override
    public Mono<Product> getProductById(Integer productId) {
        return Mono.justOrEmpty(findById(productId));
    }

    @Override
    public Flux<Product> getProductsBySiteId(Integer siteId) {
        return Flux.fromIterable(repository.findProductsBySiteId(siteId))
                .map(this::toEntity);
    }

}
