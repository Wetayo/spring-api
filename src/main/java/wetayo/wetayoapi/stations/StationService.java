package wetayo.wetayoapi.stations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wetayo.wetayoapi.common.Direction;
import wetayo.wetayoapi.utils.GeometryUtil;
import wetayo.wetayoapi.utils.Location;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StationService {
    @PersistenceContext
    private final EntityManager entityManager;

    public List<Station> getNearByStations(Double gpsX, Double gpsY, Double distance) {
        Location northEast = GeometryUtil
                .calculateRangeGps(gpsX, gpsY, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculateRangeGps(gpsX, gpsY, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getGpsX();
        double y1 = northEast.getGpsY();
        double x2 = southWest.getGpsX();
        double y2 = southWest.getGpsY();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        String queryString = "SELECT * FROM STATION AS s WHERE MBRContains(ST_LINESTRINGFROMTEXT("
                + pointFormat + " , s.GPS);";

        Query query = entityManager.createNativeQuery(queryString,Station.class);
        return query.getResultList();
    }


}
