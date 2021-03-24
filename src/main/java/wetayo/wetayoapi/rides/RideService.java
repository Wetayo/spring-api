package wetayo.wetayoapi.rides;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wetayo.wetayoapi.exceptions.AleadyColumnException;
import wetayo.wetayoapi.exceptions.NotFoundException;
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

    public Ride getRide(Integer stationId,Integer routeId){
        Optional<Ride> ride = rideRepository. findByStationIdAndRouteId(stationId, routeId);
        if(ride.isEmpty()){
            throw new NotFoundException("Query Exception : Not Found Id");
        }
        return ride.get();
    }

    public Ride addRide(Integer stationId, Integer routeId){
        Optional<Station> station =  stationRepository.findById( stationId);
        Optional<Route> route =  routeRepository.findById(routeId);

        if(route.isEmpty() || station.isEmpty()){
            throw new NotFoundException("(Mutation)Insert Exception : Not Found Id");
        }
        Optional<Ride> ride = rideRepository.findByStationIdAndRouteId(stationId, routeId);
        if(ride.isPresent()){
            throw new AleadyColumnException("(Mutation)Insert Exception : Already insert");
        }
        return rideRepository.save(Ride.builder().routeId(routeId).stationId(stationId).build());
    }

    public Ride deleteRide(Integer stationId, Integer routeId)  {
        Optional<Ride> ride = rideRepository. findByStationIdAndRouteId(stationId,routeId);
        if(ride.isEmpty()){
            throw new NotFoundException("(Mutation)Delete Exception : Not Found Id");
        }
        rideRepository.delete(ride.get());
        return ride.get();
    }
}
