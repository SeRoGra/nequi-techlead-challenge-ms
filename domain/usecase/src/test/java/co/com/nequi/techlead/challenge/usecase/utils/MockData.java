package co.com.nequi.techlead.challenge.usecase.utils;

import co.com.nequi.techlead.challenge.model.brand.Brand;

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

}
