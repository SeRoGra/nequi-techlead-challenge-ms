package co.com.nequi.techlead.challenge.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PathParam {

    BRAND_ID("brandId"),
    SITE_ID("siteId"),
    PRODUCT_ID("productId");

    private final String name;

}
