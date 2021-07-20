package com.amsidh.mvc.usermicroservice.ui.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserUpdateRequestModel implements Serializable {
    private static final long serialVersionUID = -7031985734195616119L;

	@NotNull(message = "First name must not be null")
    @Size(min = 2, message = "First name must be greater than 2 characters")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    @Size(min = 2, message = "Last name must be greater than 2 characters")
    private String lastName;
}
