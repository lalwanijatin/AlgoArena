package com.algoarena.usersubmissions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionsDTO {

    private UUID problemId;
    private String problemName;
    private String status;
    private Date submissionDate;
    private String code;
}
