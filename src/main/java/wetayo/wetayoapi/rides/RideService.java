package wetayo.wetayoapi.rides;

import graphql.GraphQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wetayo.wetayoapi.routes.Route;
import wetayo.wetayoapi.routes.RouteRepository;
import wetayo.wetayoapi.stations.Station;
import wetayo.wetayoapi.stations.StationRepository;

import java.util.Optional;

@Service
@Slf4j
public class RideService {
    private final RideRepository rideRepository;
    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;

    RideService(RideRepository rideRepository, StationRepository stationRepository, RouteRepository routeRepository) {
        this.rideRepository = rideRepository;
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
    }

    public Ride findByStationIdAndRouteId(Integer stationId,Integer routeId) throws GraphQLException{
        Optional<Ride> ride = rideRepository. findByStationIdAndRouteId(stationId, routeId);
        if(ride.isEmpty()){
            throw new GraphQLException();
        }
        return ride.get();
    }

    public Ride addRide(Integer stationId, Integer routeId){
        Optional<Station> station =  stationRepository.findById( stationId);
        Optional<Route> route =  routeRepository.findById(routeId);

        if(route.isEmpty() || station.isEmpty()){
            throw new GraphQLException("Insert Exception : Not Found Id");
        }
        Optional<Ride> ride = rideRepository.findByStationIdAndRouteId(stationId, routeId);
        if(ride.isPresent()){
            throw new GraphQLException("Insert Exception : Already insert");
        }
        return rideRepository.save(Ride.builder().routeId(routeId).stationId(stationId).build());
    }

    public void deleteRide(Integer stationId, Integer routeId) {
        Optional<Ride> optionalRide = rideRepository. findByStationIdAndRouteId(stationId,routeId);
        if(optionalRide.isEmpty()){
            throw new GraphQLException("Delete Exception : Not exist id");
        }

        rideRepository.delete(optionalRide.get());
    }
}
