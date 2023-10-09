package com.backendassignment.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecruitmentDetailsResponse {
            private RecruitmentDTO recruitments;
        private List<Long> otherRecruitmentsByCompany;
}
