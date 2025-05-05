package co.com.nequi.techlead.challenge.model.product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {

    private Integer id;
    private String name;
    private Integer stock;
    private Integer siteId;

}
