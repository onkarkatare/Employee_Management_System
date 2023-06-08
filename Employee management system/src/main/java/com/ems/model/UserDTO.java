package com.ems.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private int id;
	
	@NotNull(message = "User Name cannot be null")
	@Size(max = 20, message = "Max. 20 characters allowed")
	@Size(min = 3, message = "Min. 3 characters required")
	private String userName;
	
	@NotNull(message = "Password cannot be null")
	@Size(max = 20, message = "Max. 20 characters allowed")
	@Size(min = 6, message = "Min. 6 characters required")
	private String password;
	
	private String role;
}
