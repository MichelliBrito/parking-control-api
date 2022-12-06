package com.api.parkingcontrol.services.impl;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

//@Primary
@Service
public class ParkingSpotServiceImplV2 implements ParkingSpotService {

    @Override
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return null;
    }

    @Override
    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return false;
    }

    @Override
    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return false;
    }

    @Override
    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return false;
    }

    @Override
    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<ParkingSpotModel> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void delete(ParkingSpotModel parkingSpotModel) {

    }
}
