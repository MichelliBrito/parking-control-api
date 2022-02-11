package com.api.parkingcontrol.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.parkingcontrol.documentation.Documentation;
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

	@Documentation(doc = "list all parking spot", author = Author.ivanSantos, date = "07-02-2022")
	public List<ParkingSpotModel> findAll() {
		return parkingSpotRepository
				.findAll()
				.stream()
				.filter(d -> d.getStatus().equals(CustomStatus.BUSY))
				.collect(Collectors.toList());
	}

	@Documentation(doc = "find out parking spot by ID", author = Author.michelliBrito, date = "07-02-2022")
	public Optional<ParkingSpotModel> findById(UUID id) {
		return parkingSpotRepository.findById(id);
	}

	@Documentation(doc = "remove parking spot", author = Author.michelliBrito, date = "07-02-2022")
	@Transactional
	public void delete(ParkingSpotModel parkingSpotModel) {
		parkingSpotRepository.delete(parkingSpotModel);
	}
}
