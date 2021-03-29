package wetayo.wetayoapi.bus;

import org.springframework.stereotype.Service;
import wetayo.wetayoapi.exceptions.NotFoundException;

import java.util.Optional;

@Service
public class BusService {
    BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Bus getBus(String plateNumber){
        Optional<Bus> bus = busRepository.findByPlateNumber(plateNumber);
        if(bus.isEmpty()) throw new NotFoundException("Query Exception : Not Found PlateNumber (404)");
        return bus.get();
    }
}
