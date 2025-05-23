package org.example.feedbackstudio.organization.RestController;

import org.example.feedbackstudio.login.authority.authorityenum.AuthorityType;
import org.example.feedbackstudio.login.authority.authorityenum.EffectTypeEnum;
import org.example.feedbackstudio.login.authority.entity.Authority;
import org.example.feedbackstudio.login.authority.repository.AuthorityRepository;
import org.example.feedbackstudio.login.authority.service.AuthorityService;
import org.example.feedbackstudio.login.role.entity.roleEntity;
import org.example.feedbackstudio.login.role.roleTypeEnum.RoleTypeEnum;
import org.example.feedbackstudio.login.role.service.RoleService;
import org.example.feedbackstudio.login.user.dao.UserRepository;
import org.example.feedbackstudio.login.user.entity.User;
import org.example.feedbackstudio.organization.entity.organizationEntity;
import org.example.feedbackstudio.organization.model.OrganizationQueryDTO;
import org.example.feedbackstudio.organization.repository.OrganizationRepository;
import org.example.feedbackstudio.organization.service.organizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/organization")
@CrossOrigin(origins = "*")
public class organizationRestController {

    @Autowired
    private organizationService organizationService;
    @Autowired
    private UserRepository userRepository;

    private final OrganizationRepository organizationRepository;
    private final AuthorityService authorityService;
    private final AuthorityRepository authorityRepository;
    private final RoleService roleService;

    public organizationRestController(OrganizationRepository organizationRepository, AuthorityService authorityService, AuthorityRepository authorityRepository, RoleService roleService) {
        this.organizationRepository = organizationRepository;
        this.authorityService = authorityService;
        this.authorityRepository = authorityRepository;
        this.roleService = roleService;
    }

    // --- CREATE ---
    // POST /organizationEntity
    // İşlev: Yeni bir organizationEntity kaydı oluşturur.
    @PostMapping
    public ResponseEntity<organizationEntity> createOrganization(@RequestBody organizationEntity organization) {
        organizationEntity savedOrganization = organizationRepository.save(organization);
        for (RoleTypeEnum roleTypeEnum : RoleTypeEnum.values()) {
                roleEntity temp = new roleEntity();
                temp.setOrganizationId(savedOrganization.getId());
                temp.setRoleTypeEnum(roleTypeEnum);
                temp.setName(roleTypeEnum.name()+" "+savedOrganization.getName());
                temp.setDescription(roleTypeEnum.name());
                this.roleService.saveRole(temp);
        }
        for (EffectTypeEnum roleType : EffectTypeEnum.values()) {
            Authority temp=new Authority();
            temp.setOrganization(savedOrganization);
            temp.setAuthorityType(AuthorityType.ORGANIZATION);
            temp.setOrganizationId(savedOrganization.getId());
            temp.setDescription("description");
            temp.setName(savedOrganization.getName().toLowerCase()+" "+roleType.toString());
            temp.setEffectTypeEnum(roleType);
            authorityService.saveAuthority(temp);
        }
        User executive = new User();
        executive = userRepository.findById(savedOrganization.getId()).get();

        executive.getRole();
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
            roleService.deleteAllByOrganizationId(id);
            authorityService.deleteAllByOrganizationId(id);
            organizationRepository.deleteById(id);


            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/search")
    public List<organizationEntity> searchOrganizations(@RequestBody OrganizationQueryDTO query) {

        return organizationService.searchOrganizations(query);
    }

}
