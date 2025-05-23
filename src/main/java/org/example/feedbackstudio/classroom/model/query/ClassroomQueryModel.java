package org.example.feedbackstudio.classroom.model.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassroomQueryModel {
    private String name;
    private Integer floor;
    private Boolean hasProjector;
    private Boolean hasWhiteboard;
    private Boolean hasAirConditioning;
    private Long organizationId;
    private Long userId;
}
