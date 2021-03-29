package wetayo.wetayoapi.bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusDto {
    private String token;
    @NotNull
    private Integer routeId;

    @NotEmpty
    private String plateNumber;

    @NotEmpty
    private String routeName;
}
