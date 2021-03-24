package wetayo.wetayoapi.wetayo;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import wetayo.wetayoapi.rides.RideService;
import wetayo.wetayoapi.routeStations.RouteStation;
import wetayo.wetayoapi.routeStations.RouteStationService;
import wetayo.wetayoapi.routes.Route;
import wetayo.wetayoapi.routes.RouteDto;
import wetayo.wetayoapi.routes.RouteService;
import wetayo.wetayoapi.stations.Station;
import wetayo.wetayoapi.stations.StationService;
import wetayo.wetayoapi.utils.GeometryUtil;

import java.util.Arrays;
import java.util.List;


@Component
@Slf4j
public class WetayoQuery implements GraphQLQueryResolver {
    private final StationService stationService;
    private final RouteStationService routeStationService;
    private final RideService rideService;
    private final RouteService routeService;

    private final ModelMapper modelMapper;


    WetayoQuery(StationService stationService, ModelMapper modelMapper, RouteStationService routeStationService
            , RideService rideService, RouteService routeService) {
        this.stationService = stationService;
        this.modelMapper = modelMapper;
        this.routeStationService = routeStationService;
        this.rideService = rideService;
        this.routeService = routeService;
    }

    public List<RouteStationGraphQLDto> getStations(Double x, Double y, Double distance) {
        List<Station> stations = stationService.getNearByStations(x, y, distance);
        List<RouteStationGraphQLDto> routeStationDtos = Arrays.asList(modelMapper.map(stations, RouteStationGraphQLDto[].class));

        int index = 0;
        for (RouteStationGraphQLDto routeStationGraphQLDto : routeStationDtos) {
            List<RouteStation> routeStations = routeStationService.findByStationId(routeStationGraphQLDto.getStationId());
            List<RouteDto> routeGraphQLDtos = Arrays.asList(modelMapper.map(routeStations, RouteDto[].class));
            routeStationGraphQLDto.setRoutes(routeGraphQLDtos);
            routeStationGraphQLDto.setDistance(GeometryUtil.calculateDistance(x, y,
                    stations.get(index).getGps().getX(), stations.get(index++).getGps().getY()));
        }
        routeStationDtos.sort((o1, o2) -> o1.getDistance().compareTo(o2.getDistance()));
        log.info("getStationAndRoutes Query : " + routeStationDtos);

        return routeStationDtos;
    }

    public boolean getRide(Integer stationId, Integer routeId) {
        rideService.getRide(stationId, routeId);
        log.info("getRide Query( stationId : " + stationId + ", routeId : " + routeId );
        return true;
    }

    public List<RouteDto> getRoutes(String regionName) {
        List<Route> routes = routeService.findByRegionNameLike("%" + regionName + "%");;
        List<RouteDto> routeDtos = Arrays.asList(modelMapper.map(routes, RouteDto[].class));;

        log.info("getRoutes Query : " + routeDtos);
        return routeDtos;
    }
}
