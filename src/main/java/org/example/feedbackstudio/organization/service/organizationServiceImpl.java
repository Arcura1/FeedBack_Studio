package org.example.feedbackstudio.organization.service;

import org.example.feedbackstudio.organization.Specification.OrganizationSpecification;
import org.example.feedbackstudio.organization.entity.organizationEntity;
import org.example.feedbackstudio.organization.model.OrganizationQueryDTO;
import org.example.feedbackstudio.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class organizationServiceImpl implements organizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<organizationEntity> searchOrganizations(OrganizationQueryDTO query) {
        // Specification ile sorguyu oluşturuyoruz
        return organizationRepository.findAll(OrganizationSpecification.filter(query));
    }
}
