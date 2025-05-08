package co.com.nequi.techlead.challenge.model.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductCommand {
    private Integer brandId;
    private Integer siteId;
    private Integer productId;
    private String name;
    private Integer stock;
}
