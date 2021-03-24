package wetayo.wetayoapi.routes;

import org.springframework.stereotype.Service;
import wetayo.wetayoapi.exceptions.NotFoundException;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findByRegionNameLike(String regionName) {
        List<Route> routes = routeRepository.findByRegionNameLike("%"+regionName+"%");
        if(routes.isEmpty()) throw new NotFoundException("Query Exception : Not Found RegionName");

        return routes;
    }
}