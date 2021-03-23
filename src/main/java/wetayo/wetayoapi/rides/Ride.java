package wetayo.wetayoapi.rides;

import lombok.*;
import wetayo.wetayoapi.common.CommonColumns;
import wetayo.wetayoapi.common.RouteStationId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Builder
@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@IdClass(RouteStationId.class)
@Table(name = "RIDE")
public class Ride extends CommonColumns {
    @Id
    private Integer stationId;

    @Id
    private Integer routeId;
}
