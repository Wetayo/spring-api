package wetayo.wetayoapi.bus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus,Integer> {
    Optional<Bus> findByPlateNumber(String plateNumber);
}
