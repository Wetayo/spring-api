package wetayo.wetayoapi.routeStations;

import lombok.*;
import wetayo.wetayoapi.common.CommonColumns;
import wetayo.wetayoapi.common.RouteStationId;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@IdClass(RouteStationId.class)
@Table(name = "ROUTE_STATION")
public class RouteStation extends CommonColumns {
    @Id
    private Integer stationId;

    @Id
    private Integer routeId;

    @NotNull @NotEmpty
    @Column(name = "STATION_NAME")
    private String stationName;

    @NotNull @NotEmpty
    @Column(name = "ROUTE_NUMBER")
    private String routeNumber;

    @NotEmpty
    @Column(name = "UP_DOWN")
    private String upDown;

    @NotNull
    @Column(name = "STATION_ORDER")
    private Integer stationOrder;
}
