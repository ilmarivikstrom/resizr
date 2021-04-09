package org.tudelft.cloud.resizr.service.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tudelft.cloud.resizr.model.jpa.Resource;
import org.tudelft.cloud.resizr.repository.ResourceRepository;

import java.util.List;

@Service
public class ResourcePool {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourcePool(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> getAllAvailableResources() {
        List<Resource> resources = resourceRepository.findAllByAvailableIsTrueAndIsRedundantIsFalse();
        return resources;
    }

    public List<Resource> getResourcesNotMarkedForKill() {
        List<Resource> resourcePool = resourceRepository.findAllByMarkedForKillIsFalse();
        return resourcePool;
    }
}
