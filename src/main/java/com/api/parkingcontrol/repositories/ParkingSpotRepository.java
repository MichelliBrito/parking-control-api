package com.api.parkingcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.parkingcontrol.models.ParkingSpotModel;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {

	@Query(nativeQuery = true, value = "SELECT * FROM tb_parking_spot q WHERE q.responsible_name LIKE %?1%")
	List<ParkingSpotModel> findByResponsibleName(@Param(value = "responsibleName") String responsibleName);
	
	List<ParkingSpotModel> findByParkingSpotNumber(@Param(value = "parkingSpotNumber") String parkingSpotNumber);
	
    boolean existsByLicensePlateCar(String licensePlateCar);
    
    boolean existsByParkingSpotNumber(String parkingSpotNumber);
    
    boolean existsByApartmentAndBlock(String apartment, String block);
}
