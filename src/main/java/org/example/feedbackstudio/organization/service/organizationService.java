package org.example.feedbackstudio.organization.service;

import org.example.feedbackstudio.organization.entity.organizationEntity;
import org.example.feedbackstudio.organization.model.OrganizationQueryDTO;

import java.util.List;

public interface organizationService {

    public List<organizationEntity> searchOrganizations(OrganizationQueryDTO query);
}
