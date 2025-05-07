package co.com.nequi.techlead.challenge.usecase.productmanagement;

import co.com.nequi.techlead.challenge.exceptions.BadRequestException;
import co.com.nequi.techlead.challenge.exceptions.NotFoundException;
import co.com.nequi.techlead.challenge.model.product.Product;
import co.com.nequi.techlead.challenge.model.product.dto.UpdateProductCommand;
import co.com.nequi.techlead.challenge.model.product.gateways.ProductGateway;
import co.com.nequi.techlead.challenge.usecase.sitemanagement.SiteManagementUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

import static co.com.nequi.techlead.challenge.enums.ErrorMessage.*;

@RequiredArgsConstructor
public class ProductManagementUseCase {

    private final SiteManagementUseCase siteManagementUseCase;
    private final ProductGateway productGateway;

    public Mono<Product> createProduct(Integer brandId, Integer siteId, String productName, Integer stock) {
        return siteManagementUseCase.getSiteByIdAndBrandId(brandId, siteId)
                .map(site -> Product.builder()
                        .name(productName)
                        .stock(stock)
                        .site(site)
                        .build())
                .flatMap(productGateway::createProduct);
    }


    public Mono<Product> updateProduct(UpdateProductCommand command) {
        return getProductById(command.getProductId())
                .flatMap(product ->  checkIfProductBelongToSite(
                        product, command.getBrandId(), command.getSiteId()))
                .map(product -> product.toBuilder()
                        .name(command.getName())
                        .stock(command.getStock())
                        .build())
                .flatMap(productGateway::updateProduct);
    }

    public Mono<Void> deleteProduct(Integer brandId, Integer siteId, Integer productId) {
        return getProductById(productId)
                .flatMap(product -> this.checkIfProductBelongToSite(product, brandId, siteId))
                .flatMap(product -> productGateway.deleteProduct(productId));
    }

    public Flux<Product> getTopProductsByBrandId(Integer brandId) {
        return siteManagementUseCase.getSitesByBrandId(brandId)
                .flatMap(site -> getTopProductBySiteId(brandId, site.getId()))
                .switchIfEmpty(Mono.error(new NotFoundException(NO_PRODUCTS_FOUND_FOR_BRAND.getMessage())));
    }

    public Flux<Product> getProductsByBrandIdAndSiteId(Integer brandId, Integer siteId) {
        return productGateway.getProductsBySiteId(siteId)
                .filter(product -> brandId.equals(product.getSite().getBrand().getId()));
    }

    private Mono<Product> getProductById(Integer productId) {
        return productGateway.getProductById(productId)
                .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND.getMessage())));
    }

    private Mono<Product> checkIfProductBelongToSite(Product product, Integer brandId, Integer siteId) {
        return Mono.just(product)
                .filter(p -> siteId.equals(p.getSite().getId()))
                .filter(p -> brandId.equals(p.getSite().getBrand().getId()))
                .switchIfEmpty(Mono.error(new BadRequestException(PRODUCT_SITE_MISMATCH.getMessage())));
    }

    private Mono<Product> getTopProductBySiteId(Integer brandId, Integer siteId) {
        return getProductsByBrandIdAndSiteId(brandId, siteId)
                .collectSortedList(Comparator.comparingInt(Product::getStock).reversed())
                .filter(products -> !products.isEmpty())
                .map(products -> products.get(0));
    }

}
