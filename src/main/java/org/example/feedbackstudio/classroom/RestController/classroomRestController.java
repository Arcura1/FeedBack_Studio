package org.example.feedbackstudio.classroom.RestController;

import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.example.feedbackstudio.classroom.model.clasroomQueryModel;
import org.example.feedbackstudio.classroom.service.classroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classrooms")
@CrossOrigin(origins = "*")
public class classroomRestController {

    @Autowired
    private final classroomService classroomService;

    public classroomRestController(classroomService classroomService) {
        this.classroomService = classroomService;
    }

    // --- CREATE ---
    // Yeni bir sınıf oluştur
    @PostMapping
    public ResponseEntity<classroomEntity> createClassroom(@RequestBody clasroomQueryModel model) {
        classroomEntity createdClassroom = classroomService.createClassroom(model);
        return ResponseEntity.ok(createdClassroom);
    }

    // --- READ (Tüm Sınıflar) ---
    @GetMapping
    public ResponseEntity<List<classroomEntity>> getAllClassrooms() {
        List<classroomEntity> classrooms = classroomService.getAllClassrooms();
        return ResponseEntity.ok(classrooms);
    }

    // --- READ (ID ile) ---
    @GetMapping("/{id}")
    public ResponseEntity<classroomEntity> getClassroomById(@PathVariable Long id) {
        Optional<classroomEntity> classroomOpt = classroomService.getClassroomById(id);
        return classroomOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    public ResponseEntity<classroomEntity> updateClassroom(@PathVariable Long id,
                                                           @RequestBody clasroomQueryModel updatedClassroom) {
        try {
            classroomEntity updated = classroomService.updateClassroom(id, updatedClassroom);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }
}
