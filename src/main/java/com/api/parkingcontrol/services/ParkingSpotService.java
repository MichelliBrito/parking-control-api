package com.api.parkingcontrol.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.parkingcontrol.documentation.Documentation;
import com.api.parkingcontrol.dtos.ParkingSpotDTO;
import com.api.parkingcontrol.enums.Author;
import com.api.parkingcontrol.enums.CustomStatus;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;

@Service
public class ParkingSpotService {

	final ParkingSpotRepository parkingSpotRepository;

	public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
		this.parkingSpotRepository = parkingSpotRepository;
	}

	@Documentation(doc = "save parking spot", 
			author = Author.michelliBrito, 
			date = "07-02-2022")
	@Transactional
	public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
		return parkingSpotRepository.save(parkingSpotModel);
	}

	@Documentation(doc = "find out parking spot", 
			author = Author.michelliBrito, 
			date = "07-02-2022")
	public boolean existsByLicensePlateCar(String licensePlateCar) {
		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
	}

	@Documentation(doc = "find out parking spot by number", 
			author = Author.michelliBrito, 
			date = "07-02-2022")
	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	@Documentation(doc = "find out parking spot by apartment and rook", 
			author = Author.michelliBrito, 
			date = "07-02-2022")
	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
	}

	@Documentation(doc = "list all parking spot", 
			author = Author.ivanSantos, 
			date = "07-02-2022")
	public List<ParkingSpotModel> findAll() {
		return parkingSpotRepository
				.findAll().stream()
				.filter(d -> d.getStatus().equals(CustomStatus.BUSY))
				.filter(ParkingSpotModel::getActive)
				.collect(Collectors.toList());
	}

	@Documentation(doc = "find out parking spot by ID", 
			author = Author.michelliBrito, 
			date = "07-02-2022")
	public Optional<ParkingSpotModel> findById(UUID id) {
		return parkingSpotRepository.findById(id);
	}
	
	@Documentation(doc = "find spot by Id", 
			author = Author.ivanSantos, 
			date = "13-02-2022")
	public ParkingSpotModel findByID(UUID id) {
		Optional<ParkingSpotModel> findByID = parkingSpotRepository.findById(id);
		ParkingSpotModel parkingSpotModel = null;
		if (!findByID.isPresent()) {
			throw new RuntimeException("Parking spot by ID = [" + id + "] " + HttpStatus.NOT_FOUND + " ");
		}
		parkingSpotModel = findByID.get();

		return parkingSpotModel;
	}

	@Documentation(doc = "status parking spot", 
	author = Author.ivanSantos, 
	date = "11-02-2022")
	@Transactional
	public Boolean statusSpot(UUID id, Boolean status) {
		/* Ao invés de deletar o registro, inativá-lo */
		ParkingSpotModel spotActive = findByID(id);
		
		if (status == false) {
			spotActive.setActive(false);
			spotActive.setStatus(CustomStatus.FREE);
			parkingSpotRepository.saveAndFlush(spotActive);
		}else {
			spotActive.setActive(true);
			spotActive.setStatus(CustomStatus.BUSY);
			parkingSpotRepository.saveAndFlush(spotActive);
		}
		return status;
	}
	
	@Documentation(doc = "update spot", 
			author = Author.ivanSantos,
			date = "13-02-2022")
	public ParkingSpotModel updateParkingSpot(UUID id, ParkingSpotDTO dto) {
		
		ParkingSpotModel parking = findByID(id);
		
		parking.setParkingSpotNumber(dto.getParkingSpotNumber());
		parking.setLicensePlateCar(dto.getLicensePlateCar());
		parking.setBrandCar(dto.getBrandCar());
		parking.setModelCar(dto.getModelCar());
		parking.setColorCar(dto.getColorCar());
        parking.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		parking.setResponsibleName(dto.getResponsibleName());
		parking.setApartment(dto.getApartment());
		parking.setBlock(dto.getBlock());
		parking.setStatus(CustomStatus.BUSY);
		
		return parkingSpotRepository.saveAndFlush(parking);
	}

	@Documentation(doc = "remove parking spot", 
			author = Author.michelliBrito, 
			date = "07-02-2022")
	@Transactional
	public void delete(ParkingSpotModel parkingSpotModel) {
		parkingSpotRepository.delete(parkingSpotModel);
	}
}
