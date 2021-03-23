package wetayo.wetayoapi.stations;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;
import wetayo.wetayoapi.common.CommonColumns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true) @Table(name = "STATION")
public class Station extends CommonColumns {
    @Id
    @Column(name = "STATION_ID")
    @NotNull
    private Integer id;

    @Column(name = "STATION_NAME")
    @NotEmpty @NotNull
    private String stationName;

    @Column(name = "GPS", columnDefinition = "POINT")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private Point gps;

    @Column(name = "REGION_NAME")
    @NotEmpty @NotNull
    private String regionName;

    @Column(name = "CENTER_ID")
    @NotNull
    private Integer centerId;

    @Column(name = "CENTER_YN")
    @NotEmpty @NotNull
    private String centerYn;

    @Column(name = "MOBILE_NUMBER")
    @NotEmpty @NotNull
    private String mobileNumber;

    @Column(name = "DISTRICT_CODE")
    @NotNull
    private Integer districtCode;
}
