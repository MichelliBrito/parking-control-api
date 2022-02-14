package com.api.parkingcontrol.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.api.parkingcontrol.documentation.Documentation;
import com.api.parkingcontrol.enums.Author;
import com.api.parkingcontrol.enums.CustomStatus;


@Documentation(doc = "insert field status on class", 
               author = Author.ivanSantos, 
               date = "08-02-2022")
public class ParkingSpotDTO {

    @NotBlank
    private String parkingSpotNumber;
	    
    @NotBlank
    @Size(max = 7)
    private String licensePlateCar;
	    
    @NotBlank
    private String brandCar;
	    
    @NotBlank
    private String modelCar;

	@NotBlank
	private String colorCar;
	    
	@NotBlank
	private String responsibleName;
	    
	@NotBlank
	private String apartment;
	    
	@NotBlank
	private String block;
	
	private Boolean active = true;
    
	private CustomStatus status = CustomStatus.NOT_INFORMED;

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

    public String getResponsibleName() { return responsibleName; }

    public void setResponsibleName(String responsibleName) { this.responsibleName = responsibleName; }

    public String getApartment() { return apartment; }

    public void setApartment(String apartment) { this.apartment = apartment; }

    public String getBlock() { return block; }

    public void setBlock(String block) { this.block = block; }

	public Boolean getActive() { return active; }

	public void setActive(Boolean active) { this.active = active; }

	public CustomStatus getStatus() { return status; }

	public void setStatus(CustomStatus status) { this.status = status; }    
}
