package wetayo.wetayoapi.bus;

import lombok.*;
import wetayo.wetayoapi.common.CommonColumns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of = "id") @Table(name="BUS")
public class Bus extends CommonColumns {
    @Id
    @Column(name = "BUS_ID")
    private Integer id;

    @Column(name = "PLATE_NUMBER")
    private String plateNumber;

    @Column(name = "COMPANY_NAME")
    private String companyName;
    @Column(name = "COMPANY_ID")
    private Integer companyId;

    @Column(name = "BUS_TYPE")
    private int busType;
    @Column(name = "LOW_PLATE")
    private int lowPlate;
    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "AREA_CODE")
    private Integer areaCode;
    @Column(name = "SIDO_CODE")
    private Integer sidoCode;

    @Column(name = "ADMIN_NAME")
    private String adminName;
}
