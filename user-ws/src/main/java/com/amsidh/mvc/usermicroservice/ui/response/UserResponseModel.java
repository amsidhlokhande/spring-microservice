package com.amsidh.mvc.usermicroservice.ui.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserResponseModel implements Serializable {
    private static final long serialVersionUID = -1727190265778192429L;
	private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private List<Album> albums;
}
