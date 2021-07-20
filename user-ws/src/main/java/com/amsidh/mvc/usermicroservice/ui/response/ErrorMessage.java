package com.amsidh.mvc.usermicroservice.ui.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class ErrorMessage implements Serializable {
    private static final long serialVersionUID = -5361474112090433752L;
	private Date timestamp;
    private String errorMessage;
}
