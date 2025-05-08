package co.com.nequi.techlead.challenge.model.product.gateways;

import co.com.nequi.techlead.challenge.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateway {

    Mono<Product> createProduct(Product product);
    Mono<Product> updateProduct(Product product);
    Mono<Void> deleteProduct(Integer productId);
    Mono<Product> getProductById(Integer productId);
    Flux<Product> getProductsBySiteId(Integer siteId);

}
