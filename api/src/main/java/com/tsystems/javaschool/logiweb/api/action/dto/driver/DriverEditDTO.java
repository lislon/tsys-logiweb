package com.tsystems.javaschool.logiweb.api.action.dto.driver;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This dto used on driver edit page.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverEditDTO {

    private Integer id;

    @NotNull
    @Size(min = 1, max = 256, message = "Name should be 1-256 chars long.")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 256, message = "Name should be 1-256 chars long.")
    private String lastName;

    @NotNull
    @Size(min = 1, max = 10, message = "Personal code should be 1-10 long.")
    private String personalCode;

    private Driver.Status status;

    @NotNull
    @Min(0)
    private int hoursWorked;

    /**
     * Last known location of driver
     */
    @NotNull
    private Integer cityId;

    // Used only for displaying
    @NotNull(message = "Location must be set")
    private String cityName;

    @NotNull(message = "Email must be set")
    @Email
    private String email;

    @NotNull(message = "Password must be set")
    private String password;

    public DriverDTO extractDriverDTO() {
        DriverDTO dto = new DriverDTO();
        dto.setFirstName(this.getFirstName());
        dto.setLastName(this.getLastName());
        dto.setStatus(this.getStatus());
        dto.setHoursWorked(this.getHoursWorked());
        dto.setCityId(this.getCityId());
        dto.setCityName(this.getCityName());
        dto.setPersonalCode(this.getPersonalCode());
        dto.setId(this.getId());
        return dto;
    }

    public UserDTO extractUserDTO() {
        return new UserDTO(getEmail(), getPassword());
    }
    
    public static DriverEditDTO buildFrom(DriverDTO driverDTO, UserDTO userDTO) {
        DriverEditDTO dto = new DriverEditDTO();
        dto.setFirstName(driverDTO.getFirstName());
        dto.setLastName(driverDTO.getLastName());
        dto.setStatus(driverDTO.getStatus());
        dto.setHoursWorked(driverDTO.getHoursWorked());
        dto.setCityId(driverDTO.getCityId());
        dto.setCityName(driverDTO.getCityName());
        dto.setPersonalCode(driverDTO.getPersonalCode());
        dto.setId(driverDTO.getId());

        dto.setEmail(userDTO.getEmail());
        return dto;
    }

}
