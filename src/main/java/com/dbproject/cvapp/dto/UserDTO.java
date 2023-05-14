package com.dbproject.cvapp.dto;

import com.dbproject.cvapp.model.MyUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userID;
    private String username;
    private String email;
    private UserDetailsDTO userDetailsDTO;
    private String firstName;
    private String lastName;
    private String phone;

    public UserDTO(MyUser user) {
        this.userID = user.getUserID();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.userDetailsDTO = new UserDetailsDTO(user.getUserDetails());
    }
}
