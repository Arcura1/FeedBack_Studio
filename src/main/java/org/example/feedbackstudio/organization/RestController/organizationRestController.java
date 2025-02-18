package org.example.feedbackstudio.organization.RestController;

import org.example.feedbackstudio.organization.entity.organizationEntity;
import org.example.feedbackstudio.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/organization")
@CrossOrigin(origins = "*")
public class organizationRestController {


    private final OrganizationRepository organizationRepository;

    public organizationRestController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    // --- CREATE ---
    // POST /organizationEntity
    // İşlev: Yeni bir organizationEntity kaydı oluşturur.
    @PostMapping
    public ResponseEntity<organizationEntity> createOrganization(@RequestBody organizationEntity organization) {
        organizationEntity savedOrganization = organizationRepository.save(organization);
        return ResponseEntity.ok(savedOrganization);
    }

    // --- READ (All) ---
    // GET /organizationEntity
    // İşlev: Tüm organizationEntity kayıtlarını getirir.
    @GetMapping
    public ResponseEntity<List<organizationEntity>> getAllOrganizations() {
        List<organizationEntity> organizations = organizationRepository.findAll();
        return ResponseEntity.ok(organizations);
    }

    // --- READ (By ID) ---
    // GET /organizationEntity/{id}
    // İşlev: Belirtilen id'ye sahip organizationEntity kaydını getirir.
    @GetMapping("/{id}")
    public ResponseEntity<organizationEntity> getOrganizationById(@PathVariable Long id) {

        Optional<organizationEntity> organizationEntity = organizationRepository.findById(id);
        return organizationEntity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    // PUT /organizationEntity/{id}
    // İşlev: Belirtilen id'ye sahip organizationEntity kaydını günceller.
    @PutMapping("/{id}")
    public ResponseEntity<organizationEntity> updateOrganization(@PathVariable Long id,
                                                           @RequestBody organizationEntity organizationDetails) {
        Optional<organizationEntity> optionalOrganization = organizationRepository.findById(id);

        if(optionalOrganization.isPresent()){
            organizationEntity organizationEntity = optionalOrganization.get();
            organizationEntity.setName(organizationDetails.getName());
            organizationEntity.setAddress(organizationDetails.getAddress());
            organizationEntity.setEmail(organizationDetails.getEmail());
            organizationEntity updatedOrganization = organizationRepository.save(organizationEntity);
            return ResponseEntity.ok(updatedOrganization);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- DELETE ---
    // DELETE /organizationEntity/{id}
    // İşlev: Belirtilen id'ye sahip organizationEntity kaydını siler.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        if(organizationRepository.existsById(id)) {
            organizationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
