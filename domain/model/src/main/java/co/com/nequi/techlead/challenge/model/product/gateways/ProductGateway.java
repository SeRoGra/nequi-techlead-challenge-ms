package co.com.nequi.techlead.challenge.model.product.gateways;

import co.com.nequi.techlead.challenge.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateway {

    Mono<Product> createProduct(Product product);
    Mono<Product> updateProductName(String productId, String name);
    Mono<Product> updateProductStock(String productId, Integer stock);
    Mono<Product> deleteProduct(String brandId, String siteId, String productId);
    Flux<Product> getProducts();

}
