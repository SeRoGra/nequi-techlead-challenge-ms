package co.com.nequi.techlead.challenge.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    BRAND_NOT_FOUND("The requested brand was not found or may have been deleted"),
    SITE_NOT_FOUND("The requested site was not found or may have been deleted"),
    PRODUCT_NOT_FOUND("The requested product was not found or may have been deleted"),
    NO_PRODUCTS_FOUND_FOR_BRAND("No products found in any of the brand's sites"),

    SITE_BRAND_MISMATCH("The Site does not exist or does not belong to the specified brand"),
    PRODUCT_SITE_MISMATCH("The Product does not exist or does not belong to the specified site");


    private final String message;


}
