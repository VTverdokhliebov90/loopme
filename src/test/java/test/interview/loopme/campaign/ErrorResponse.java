package test.interview.loopme.campaign;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
class ErrorResponse {
    private Date timestamp;
    private Integer status;
    private List<String> errors;
}
