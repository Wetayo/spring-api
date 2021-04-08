package wetayo.wetayoapi.routeStations;

import org.springframework.data.jpa.repository.JpaRepository;
import wetayo.wetayoapi.common.RouteStationId;

import java.util.List;
import java.util.Optional;

public interface RouteStationRepository extends JpaRepository<RouteStation,Integer> {
    List<RouteStation> findByStationId(Integer stationId);
}
