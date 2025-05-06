package co.com.nequi.techlead.challenge.model.product.gateways;

import co.com.nequi.techlead.challenge.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateway {

    Mono<Product> createProduct(String name, String stock, String siteId);
    Mono<Product> updateProductName(String id, String name);
    Mono<Product> updateProductStock(String id, String stock);
    Mono<Product> deleteProduct(String brandId, String siteId, String productId);
    Flux<Product> getProducts();

}
