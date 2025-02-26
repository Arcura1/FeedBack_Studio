package org.example.feedbackstudio.classroom.RestController;

import org.example.feedbackstudio.classroom.entitiy.classroomEntity;
import org.example.feedbackstudio.classroom.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classrooms")
@CrossOrigin(origins = "*")
public class clasroomRestController {

    private final ClassroomRepository classroomRepository;

    public clasroomRestController(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    // --- CREATE ---
    // POST /classrooms
    // İşlev: Yeni bir classroomEntity oluşturur.
    @PostMapping
    public ResponseEntity<classroomEntity> createClassroom(@RequestBody classroomEntity classroom) {
        classroomEntity savedClassroom = classroomRepository.save(classroom);
        return ResponseEntity.ok(savedClassroom);
    }

    // --- READ (Tüm Kayıtlar) ---
    // GET /classrooms
    // İşlev: Tüm classroomEntity kayıtlarını getirir.
    @GetMapping
    public ResponseEntity<List<classroomEntity>> getAllClassrooms() {
        List<classroomEntity> classrooms = classroomRepository.findAll();
        return ResponseEntity.ok(classrooms);
    }

    // --- READ (By ID) ---
    // GET /classrooms/{id}
    // İşlev: Belirtilen id'ye sahip classroomEntity kaydını getirir.
    @GetMapping("/{id}")
    public ResponseEntity<classroomEntity> getClassroomById(@PathVariable Long id) {
        Optional<classroomEntity> classroomOpt = classroomRepository.findById(id);
        return classroomOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    // PUT /classrooms/{id}
    // İşlev: Belirtilen id'ye sahip classroomEntity kaydını günceller.
    @PutMapping("/{id}")
    public ResponseEntity<classroomEntity> updateClassroom(@PathVariable Long id,
                                                           @RequestBody classroomEntity classroomDetails) {
        Optional<classroomEntity> classroomOpt = classroomRepository.findById(id);
        if (classroomOpt.isPresent()) {
            classroomEntity classroom = classroomOpt.get();
            classroom.setName(classroomDetails.getName());
            classroom.setFloor(classroomDetails.getFloor());
            classroom.setRoomNumber(classroomDetails.getRoomNumber());
            classroom.setCapacity(classroomDetails.getCapacity());
            classroom.setHasProjector(classroomDetails.getHasProjector());
            classroom.setHasWhiteboard(classroomDetails.getHasWhiteboard());
            classroom.setHasAirConditioning(classroomDetails.getHasAirConditioning());
            classroom.setDescription(classroomDetails.getDescription());
            // İlişkili organization bilgisi de güncellenebilir (varsa)
            classroom.setOrganization(classroomDetails.getOrganization());

            classroomEntity updatedClassroom = classroomRepository.save(classroom);
            return ResponseEntity.ok(updatedClassroom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- DELETE ---
    // DELETE /classrooms/{id}
    // İşlev: Belirtilen id'ye sahip classroomEntity kaydını siler.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        if (classroomRepository.existsById(id)) {
            classroomRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
