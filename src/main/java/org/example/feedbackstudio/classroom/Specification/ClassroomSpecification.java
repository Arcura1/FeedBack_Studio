package org.example.feedbackstudio.classroom.Specification;

import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.example.feedbackstudio.classroom.model.query.ClassroomQueryModel;
import org.springframework.data.jpa.domain.Specification;

public class ClassroomSpecification {

    public static Specification<classroomEntity> withFilters(ClassroomQueryModel query) {
        return Specification
                .where(hasName(query.getName()))
                .and(hasFloor(query.getFloor()))
                .and(hasProjector(query.getHasProjector()))
                .and(hasWhiteboard(query.getHasWhiteboard()))
                .and(hasAirConditioning(query.getHasAirConditioning()))
                .and(hasOrganizationId(query.getOrganizationId()))
                .and(hasUserId(query.getUserId()));
    }

    private static Specification<classroomEntity> hasName(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    private static Specification<classroomEntity> hasFloor(Integer floor) {
        return (root, query, cb) -> floor == null ? null :
                cb.equal(root.get("floor"), floor);
    }

    private static Specification<classroomEntity> hasProjector(Boolean projector) {
        return (root, query, cb) -> projector == null ? null :
                cb.equal(root.get("hasProjector"), projector);
    }

    private static Specification<classroomEntity> hasWhiteboard(Boolean whiteboard) {
        return (root, query, cb) -> whiteboard == null ? null :
                cb.equal(root.get("hasWhiteboard"), whiteboard);
    }

    private static Specification<classroomEntity> hasAirConditioning(Boolean airConditioning) {
        return (root, query, cb) -> airConditioning == null ? null :
                cb.equal(root.get("hasAirConditioning"), airConditioning);
    }

    private static Specification<classroomEntity> hasOrganizationId(Long orgId) {
        return (root, query, cb) -> orgId == null ? null :
                cb.equal(root.get("organizationId"), orgId);
    }

    private static Specification<classroomEntity> hasUserId(Long userId) {
        return (root, query, cb) -> userId == null ? null :
                cb.equal(root.get("userId"), userId);
    }
}
