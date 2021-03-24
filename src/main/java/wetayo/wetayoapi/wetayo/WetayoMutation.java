package wetayo.wetayoapi.wetayo;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import wetayo.wetayoapi.rides.Ride;
import wetayo.wetayoapi.rides.RideDto;
import wetayo.wetayoapi.rides.RideService;

@Component
@Slf4j
public class WetayoMutation implements GraphQLMutationResolver {
    private final RideService rideService;
    private final ModelMapper modelMapper;

    WetayoMutation(RideService rideService,ModelMapper modelMapper) {
        this.rideService = rideService;
        this.modelMapper = modelMapper;
    }

    public RideDto createRide(Integer stationId, Integer routeId) {
        Ride ride = rideService.addRide(stationId,routeId);
        log.info("createRide Mutation : " + ride);
        return modelMapper.map(ride,RideDto.class);
    }

    public RideDto deleteRide(Integer stationId, Integer routeId) {
        Ride ride = rideService.deleteRide(stationId,routeId);
        log.info("deleteRide Mutation : " + ride);
        return modelMapper.map(ride,RideDto.class);
    }
}
