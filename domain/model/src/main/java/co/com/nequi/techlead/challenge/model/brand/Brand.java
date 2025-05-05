package co.com.nequi.techlead.challenge.model.brand;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Brand {

    private Integer id;
    private String name;

}
