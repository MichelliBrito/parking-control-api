package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.documentation.Documentation;
import com.api.parkingcontrol.documentation.Request;
import com.api.parkingcontrol.dtos.ParkingSpotDTO;
import com.api.parkingcontrol.enums.Author;
import com.api.parkingcontrol.enums.CustomStatus;
import com.api.parkingcontrol.enums.RequestMethod;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;

@RestController
@RequestMapping(path = "/parking-spot")
public class ParkingSpotController {

	final ParkingSpotService parkingSpotService;

	public ParkingSpotController(ParkingSpotService parkingSpotService) {
		this.parkingSpotService = parkingSpotService;
	}

	@Documentation(doc = "save parking spot", 
			author = Author.michelliBrito, 
			api = @Request(method = RequestMethod.POST, 
			url = "/parking-spot/save-parking-spot"), 
			date = "07-02-2022")
	@PostMapping(path = "/save")
	public ResponseEntity<?> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDto) {
		if (parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
		}
		if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
		}
		if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Conflict: Parking Spot already registered for this apartment/block!");
		}
		ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		parkingSpotModel.setStatus(CustomStatus.BUSY);
		parkingSpotModel.setActive(true);
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
	}

	@Documentation(doc = "get all data actives/busy", 
			author = Author.michelliBrito, 
			api = @Request(method = RequestMethod.GET, 
			url = "/parking-spot/get-all"), date = "07-02-2022")
	@GetMapping(path = "/get-all")
	public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots() {
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
	}

	@Documentation(doc = "get one parking spot by ID", 
			author = Author.michelliBrito, api = @Request(method = RequestMethod.GET, 
			url = "/parking-spot/id"), 
			date = "07-02-2022")
	@GetMapping(path = "/get-one/{id}")
	public ResponseEntity<?> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}

	@Documentation(doc = "delete parking spot by ID", 
			author = Author.michelliBrito, 
			api = @Request(method = RequestMethod.DELETE, 
			url = "/parking-spot/delete/id"), date = "07-02-2022")
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<?> deleteParkingSpot(@PathVariable(value = "id") UUID id) {
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
		}
		parkingSpotService.delete(parkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
	}

	@Documentation(doc = "status parking spot", 
			author = Author.ivanSantos, 
			api = @Request(method = RequestMethod.PUT, 
			url = "/parking-spot/change-status/id/status"), 
			date = "12-02-2022")
	@PutMapping(path = "/change-status/{id}/{status}")
	public ResponseEntity<?> statusSpot(@PathVariable(value = "id") UUID id, @PathVariable(value = "status") Boolean status) {
		parkingSpotService.statusSpot(id,status);
		return ResponseEntity.status(HttpStatus.OK).body("Change Parking Spot status successfully");
	}

	@Documentation(doc = "update parking spot", 
			author = Author.ivanSantos, 
			api = @Request(method = RequestMethod.POST, 
			url = "/parking-spot/update/id"), 
			date = "13-02-2022")
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateParkingSpot(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid ParkingSpotDTO parkingSpotDto) {
		ParkingSpotModel parkingSpot = parkingSpotService.updateParkingSpot(id, parkingSpotDto);
		
		return ResponseEntity.ok().body(parkingSpot);
	}
}
