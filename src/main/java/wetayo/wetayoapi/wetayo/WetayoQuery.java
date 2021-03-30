package wetayo.wetayoapi.wetayo;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    @PreAuthorize("hasAuthority('user')")
    public List<RouteStationGraphQLDto> getStations(Double x, Double y, Double distance, DataFetchingEnvironment e) {
        List<Station> stations = stationService.getNearByStations(x, y, distance);
        List<RouteStationGraphQLDto> routeStationDtos = new ArrayList<>(); // = Arrays.asList(modelMapper.map(stations, RouteStationGraphQLDto[].class));

        stations.forEach(station -> {
            RouteStationGraphQLDto tmp = modelMapper.map(station,RouteStationGraphQLDto.class);
            tmp.setDistance(GeometryUtil.calculateDistance(x, y, station.getGps().getX(), station.getGps().getY()));
            routeStationDtos.add(tmp);
        });
        routeStationDtos.sort(Comparator.comparing(RouteStationGraphQLDto::getDistance));

        if(e.getSelectionSet().contains("routes")) {
            for (RouteStationGraphQLDto routeStationDto : routeStationDtos) {
                List<RouteStation> routeStations = routeStationService.findByStationId(routeStationDto.getStationId());
                List<RouteDto> routeGraphQLDtos = Arrays.asList(modelMapper.map(routeStations, RouteDto[].class));
                routeStationDto.setRoutes(routeGraphQLDtos);
            }
        }
        log.info("Query Stations (요청 gps : " + x + ", " + y + ") = " + routeStationDtos);
        return routeStationDtos;
    }

    @PreAuthorize("hasAuthority('bus')")
    public boolean getRide(Integer stationId, Integer routeId) {
        rideService.getRide(stationId, routeId);
        log.info("Query Ride(stationId : " + stationId + ", routeId : " + routeId );
        return true;
    }

    @PreAuthorize("hasAuthority('bus')")
    public List<RouteDto> getRoutes(String regionName) {
        List<Route> routes = routeService.getRoutes("%" + regionName + "%");;
        List<RouteDto> routeDtos = Arrays.asList(modelMapper.map(routes, RouteDto[].class));;

        log.info("Query Routes (요청 지역 이름 : " + regionName + ") = " + routeDtos);
        return routeDtos;
    }
}
