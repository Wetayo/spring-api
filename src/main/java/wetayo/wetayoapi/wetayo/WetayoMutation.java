package wetayo.wetayoapi.wetayo;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import wetayo.wetayoapi.bus.Bus;
import wetayo.wetayoapi.bus.BusDto;
import wetayo.wetayoapi.bus.BusService;
import wetayo.wetayoapi.rides.Ride;
import wetayo.wetayoapi.rides.RideDto;
import wetayo.wetayoapi.rides.RideService;
import wetayo.wetayoapi.routes.Route;
import wetayo.wetayoapi.routes.RouteService;

@Component
@Slf4j
public class WetayoMutation implements GraphQLMutationResolver {
    private final RideService rideService;
    private final BusService busService;
    private final RouteService routeService;
    private final ModelMapper modelMapper;


    WetayoMutation(RideService rideService,BusService busService,RouteService routeService,ModelMapper modelMapper) {
        this.rideService = rideService;
        this.busService = busService;
        this.routeService = routeService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasAuthority('bus')")
    public RideDto createRide(Integer stationId, Integer routeId) {
        Ride ride = rideService.addRide(stationId,routeId);
        RideDto rideDto = modelMapper.map(ride,RideDto.class);
        log.info("Mutation createRide : " + rideDto);
        return rideDto;
    }

    @PreAuthorize("hasAuthority('bus')")
    public RideDto deleteRide(Integer stationId, Integer routeId) {
        Ride ride = rideService.deleteRide(stationId,routeId);
        RideDto rideDto = modelMapper.map(ride,RideDto.class);
        log.info("Mutation deleteRide : " + rideDto);
        return rideDto;
    }

    @PreAuthorize("hasAuthority('anonymous')")
    public BusDto signInBus(Integer routeId, String plateNumber){
        Bus bus = busService.getBus(plateNumber);
        Route route = routeService.getRoute(routeId);

        BusDto busDto = BusDto.builder()
                                .routeId(routeId)
                                .routeName(route.getRouteNumber())
                                .plateNumber(bus.getPlateNumber())
                                .token("").build();

        log.info("Mutation sign in Bus : " + busDto);
        return busDto;
    }
}
