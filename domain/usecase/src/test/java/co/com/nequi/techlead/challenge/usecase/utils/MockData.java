package co.com.nequi.techlead.challenge.usecase.utils;

import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.model.product.Product;
import co.com.nequi.techlead.challenge.model.site.Site;

import java.util.List;

public class MockData {

    public static Brand createFakeBrand(Integer id, String name) {
        return Brand.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static List<Brand> getFakeBrands() {
        return List.of(
                createFakeBrand(1, "Subway"),
                createFakeBrand(2, "Creeps & Waffles"),
                createFakeBrand(3, "Domino's Pizza"));
    }

    public static Site createFakeSite(Integer id, String name, Brand brand) {
        return Site.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .build();
    }

    public static List<Site> getFakeSites(Brand brand) {
        return List.of(
                createFakeSite(1, "Subway St. El Poblado Medellin", brand),
                createFakeSite(2, "Creeps & Waffles Av. 3 Norte", brand),
                createFakeSite(3, "Domino's Pizza B Mercedes Palmira", brand));
    }

    public static Product createFakeProduct(Integer id, String name, Integer stock, Site site) {
        return Product.builder()
                .id(id)
                .name(name)
                .site(site)
                .stock(stock)
                .build();
    }
}
