package wetayo.wetayoapi.routes;

import org.springframework.stereotype.Service;
import wetayo.wetayoapi.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getRoutes(String regionName) {
        List<Route> routes = routeRepository.findByRegionNameLike("%"+regionName+"%");
        if(routes.isEmpty()) throw new NotFoundException("Query Exception : Not Found RegionName (404)");

        return routes;
    }

    public Route getRoute(Integer id){
        Optional<Route> route = routeRepository.findById(id);
        if(route.isEmpty()) throw new NotFoundException("Query Exception : Not Found Id" +
                " (404)");

        return route.get();
    }
}