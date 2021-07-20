package com.amsidh.mvc.usermicroservice.ui.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1931272976940596553L;

	private String userId;

    @NotNull(message = "First name must not be null")
    @Size(min = 2, message = "First name must be greater than 2 characters")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    @Size(min = 2, message = "Last name must be greater than 2 characters")
    private String lastName;

    @Email( message = "EmailId must be valid email")
    private String emailId;

    @NotNull
    @Size(min = 8, max = 16, message = "Password must be greater than 8 and less than 16 characters")
    private String password;
}
