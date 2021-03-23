package wetayo.wetayoapi.routes;

import graphql.GraphQLException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findByRegionNameLike(String regionName) {
        List<Route> routes = routeRepository.findByRegionNameLike("%"+regionName+"%");
        if(routes.isEmpty()) throw new GraphQLException("Empty");

        return routes;
    }
}