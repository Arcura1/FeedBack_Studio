package org.example.feedbackstudio.homework.service.Specification;

import jakarta.persistence.criteria.Predicate;
import org.example.feedbackstudio.homework.entitiy.HomeworkEntity;
import org.example.feedbackstudio.homework.model.query.HomeworkQueryDTO;
import org.springframework.data.jpa.domain.Specification;

public class HomeworkSpecification {

    public static Specification<HomeworkEntity> filter(HomeworkQueryDTO query) {
        return (root, queryCriteria, builder) -> {
            Predicate predicate = builder.conjunction(); // Başlangıçta boş bir predicate

            if (query.getTitle() != null) {
                predicate = builder.and(predicate, builder.like(root.get("title"), "%" + query.getTitle() + "%"));
            }

            if (query.getDescription() != null) {
                predicate = builder.and(predicate, builder.like(root.get("description"), "%" + query.getDescription() + "%"));
            }

            if (query.getTeacherId() != null) {
                predicate = builder.and(predicate, builder.equal(root.get("teacherId"), query.getTeacherId()));
            }

            if (query.getClassroomId() != null) {
                predicate = builder.and(predicate, builder.equal(root.get("classroomId"), query.getClassroomId()));
            }

            return predicate;
        };
    }
}
