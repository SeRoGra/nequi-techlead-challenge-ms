package co.com.nequi.techlead.challenge.jpa.adapter;

import co.com.nequi.techlead.challenge.jpa.entity.ProductEntity;
import co.com.nequi.techlead.challenge.jpa.helper.AdapterOperations;
import co.com.nequi.techlead.challenge.jpa.repository.ProductRepository;
import co.com.nequi.techlead.challenge.model.product.gateways.ProductGateway;
import co.com.nequi.techlead.challenge.model.product.Product;
import co.com.nequi.techlead.challenge.model.product.gateways.ProductGateway;
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
        return Mono.defer(() -> Mono.just(product));
    }

    @Override
    public Mono<Product> updateProductName(String productId, String name) {
        return null;
    }

    @Override
    public Mono<Product> updateProductStock(String productId, Integer stock) {
        return null;
    }

    @Override
    public Mono<Product> deleteProduct(String brandId, String siteId, String productId) {
        return null;
    }

    @Override
    public Flux<Product> getProducts() {
        return null;
    }
}
