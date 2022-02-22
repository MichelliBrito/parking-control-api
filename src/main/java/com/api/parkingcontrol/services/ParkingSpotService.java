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

	@Documentation(doc = "save parking spot", author = Author.michelliBrito, date = "07-02-2022")
	@Transactional
	public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
		return parkingSpotRepository.save(parkingSpotModel);
	}

	@Documentation(doc = "find out parking spot", author = Author.michelliBrito, date = "07-02-2022")
	public boolean existsByLicensePlateCar(String licensePlateCar) {
		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
	}

	@Documentation(doc = "find out parking spot by number", author = Author.michelliBrito, date = "07-02-2022")
	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	@Documentation(doc = "find out parking spot by apartment and rook", author = Author.michelliBrito, date = "07-02-2022")
	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
	}

	@Documentation(doc = "list all actives parking spot", author = Author.ivanSantos, date = "07-02-2022")
	public List<ParkingSpotModel> findAll() {
		return parkingSpotRepository
				.findAll().stream()
				.filter(d -> d.getStatus().equals(CustomStatus.BUSY))
				.filter(ParkingSpotModel::getActive)
				.collect(Collectors.toList());
	}
	
	@Documentation(doc = "list inactives parking spot", author = Author.ivanSantos, date = "17-02-2022")
	public List<ParkingSpotModel> findInactives() {
		return parkingSpotRepository
				.findAll().stream()
				.filter(d -> d.getStatus().equals(CustomStatus.FREE))
				.filter(e->e.getActive() == false)
				.collect(Collectors.toList());
	}

	@Documentation(doc = "find out parking spot by ID", author = Author.michelliBrito, date = "07-02-2022")
	public Optional<ParkingSpotModel> findById(UUID id) {
		return parkingSpotRepository.findById(id);
	}

	@Documentation(doc = "find spot by Id", author = Author.ivanSantos, date = "13-02-2022")
	public ParkingSpotModel findByID(UUID id) {
		Optional<ParkingSpotModel> findByID = parkingSpotRepository.findById(id);
		ParkingSpotModel parkingSpotModel = null;
		if (!findByID.isPresent()) {
			throw new RuntimeException("Parking spot by ID = [" + id + "] " + HttpStatus.NOT_FOUND + " ");
		}
		parkingSpotModel = findByID.get();

		return parkingSpotModel;
	}
	
	@Documentation(doc = "find parking by spot number", 
			author = Author.ivanSantos, 
			date = "22-02-2022")
	public List<ParkingSpotModel> findParkingSpotNumber(String spotNumber) {
		List<ParkingSpotModel> findSpotNumber = parkingSpotRepository.findByParkingSpotNumber(spotNumber);
		if (!findSpotNumber.isEmpty()) {
		}else {
			throw new RuntimeException("Parking spot number = [" + spotNumber + "] " + HttpStatus.NOT_FOUND + " ");
		}
		return findSpotNumber;
	}
	
	@Documentation(doc = "find out parking spot by owner name", 
			author = Author.ivanSantos, 
			date = "22-02-2022")
	public List<ParkingSpotModel> findOwnerName(String ownerName) {
		List<ParkingSpotModel> findOwnerName = parkingSpotRepository.findByResponsibleName(ownerName);
		if (!findOwnerName.isEmpty()) {
		}else {
			throw new RuntimeException("Owner name = [" + ownerName + "] " + HttpStatus.NOT_FOUND + " ");
		}
		return findOwnerName;
	}

	@Documentation(doc = "status parking spot", 
			author = Author.ivanSantos, 
			date = "11-02-2022")
	@Transactional
	public ParkingSpotModel statusSpot(UUID id, Boolean status) {
		/* Ao invés de deletar o registro, inativá-lo */
		ParkingSpotModel spotActive = findByID(id);

		if (spotActive.getActive() == true) {
			spotActive.setActive(false);
			spotActive.setStatus(CustomStatus.FREE);
			parkingSpotRepository.saveAndFlush(spotActive);
		} else {
			spotActive.setActive(true);
			spotActive.setStatus(CustomStatus.BUSY);
			parkingSpotRepository.saveAndFlush(spotActive);
		}
		return spotActive;
	}

	@Documentation(doc = "generate parking spot number", 
				   author = Author.ivanSantos, 
				   date = "14-02-2022")
	public String generateNumber(ParkingSpotModel psm) {
		
		UUID key = UUID.randomUUID();
		String accessKey = null;
		if (psm.getParkingSpotNumber() == null) {
			accessKey = key.toString().substring(0, 8).toUpperCase();
		}
		return accessKey;
	}

	@Documentation(doc = "update spot", 
			author = Author.ivanSantos, 
			date = "13-02-2022")
	public ParkingSpotModel updateParkingSpot(UUID id, ParkingSpotDTO dto) {

		ParkingSpotModel parking = findByID(id);
		String accessKey = generateNumber(parking);
		
		parking.setLicensePlateCar(dto.getLicensePlateCar());
		parking.setBrandCar(dto.getBrandCar());
		parking.setModelCar(dto.getModelCar());
		parking.setColorCar(dto.getColorCar());
		parking.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		parking.setResponsibleName(dto.getResponsibleName());
		parking.setApartment(dto.getApartment());
		parking.setBlock(dto.getBlock());
		parking.setStatus(CustomStatus.BUSY);
		if (accessKey != null) {
			parking.setParkingSpotNumber(accessKey);
		}

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
