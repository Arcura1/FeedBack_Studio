package org.example.feedbackstudio.classroom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class clasroomQueryModel {

        private String name;
        private Integer floor;
        private String roomNumber;
        private Integer capacity;
        private Boolean hasProjector;
        private Boolean hasWhiteboard;
        private Boolean hasAirConditioning;
        private String description;
        private Long organizationId; // Sadece Organization ID alıyoruz

}
