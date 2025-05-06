package co.com.nequi.techlead.challenge.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    BRAND_NOT_FOUND("The requested brand was not found or may have been deleted"),
    SITE_NOT_FOUND("The requested site was not found or may have been deleted"),
    PRODUCT_NOT_FOUND("The requested product was not found or may have been deleted");

    private final String message;


}
