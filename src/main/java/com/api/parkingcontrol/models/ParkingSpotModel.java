package com.api.parkingcontrol.models;

import javax.persistence.*;

import com.api.parkingcontrol.enums.CustomStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PARKING_SPOT")
public class ParkingSpotModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(nullable = false, unique = true, length = 10)
    private String parkingSpotNumber;
    
    @Column(nullable = false, unique = true, length = 7)
    private String licensePlateCar;
    
    @Column(nullable = false, length = 70)
    private String brandCar;
    
    @Column(nullable = false, length = 70)
    private String modelCar;
    
    @Column(nullable = false, length = 70)
    private String colorCar;
    
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    
    @Column(nullable = false, length = 130)
    private String responsibleName;
    
    @Column(nullable = false, length = 30)
    private String apartment;
    
    @Column(nullable = false, length = 30)
    private String block;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomStatus status;
    
    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public String getParkingSpotNumber() { return parkingSpotNumber; }

    public void setParkingSpotNumber(String parkingSpotNumber) { this.parkingSpotNumber = parkingSpotNumber; }

    public String getLicensePlateCar() { return licensePlateCar; }

    public void setLicensePlateCar(String licensePlateCar) { this.licensePlateCar = licensePlateCar; }

    public String getBrandCar() { return brandCar; }

    public void setBrandCar(String brandCar) { this.brandCar = brandCar; }

    public String getModelCar() { return modelCar; }

    public void setModelCar(String modelCar) { this.modelCar = modelCar; }

    public String getColorCar() { return colorCar; }

    public void setColorCar(String colorCar) { this.colorCar = colorCar; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }

    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }

    public String getResponsibleName() { return responsibleName; }

    public void setResponsibleName(String responsibleName) { this.responsibleName = responsibleName; }

    public String getApartment() { return apartment; }

    public void setApartment(String apartment) { this.apartment = apartment; }

    public String getBlock() { return block; }

    public void setBlock(String block) { this.block = block; }
    
    public CustomStatus getStatus() { return status; }

	public void setStatus(CustomStatus status) { this.status = status; }

	public Boolean getActive() { return active; }

	public void setActive(boolean active) { this.active = active; }

	@Override
    public String toString() {
    	final StringBuilder sb = new StringBuilder("ParkingSpotModel{");
    	sb.append("id=").append(id);
    	sb.append(", parkingSpotNumber=").append(parkingSpotNumber);
    	sb.append(", licensePlateCar=").append(licensePlateCar);
    	sb.append(", brandCar=").append(brandCar);
    	sb.append(", modelCar=").append(modelCar);
    	sb.append(", colorCar=").append(colorCar);
    	sb.append(", registrationDate=").append(registrationDate);
    	sb.append(", responsibleName=").append(responsibleName);
    	sb.append(", apartment=").append(apartment);
    	sb.append(", block=").append(block);
    	sb.append(", status=").append(getStatus());
    	sb.append(", active=").append(active);
    	sb.append("}");
    	
    	return sb.toString();
    }
}
