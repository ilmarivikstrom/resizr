package org.tudelft.cloud.resizr.service.resource.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudelft.cloud.resizr.model.jpa.Resource;
import org.tudelft.cloud.resizr.repository.ResourceRepository;

@Service
public class ResourceManagerImpl implements ResourceManager {

    private final ResourceRepository resourceRepository;
    private final RemoteResourceManager remoteResourceManager;

    @Autowired
    public ResourceManagerImpl(ResourceRepository resourceRepository, RemoteResourceManager remoteResourceManager) {
        this.resourceRepository = resourceRepository;
        this.remoteResourceManager = remoteResourceManager;
    }

    @Override
    @Transactional
    public Resource create() {
        Resource resource = remoteResourceManager.createRemoteResource();
        return resourceRepository.save(resource);
    }

    @Override
    @Transactional
    public Resource createRedundant() {
        Resource resource = remoteResourceManager.createRemoteResource();
        resource.setRedundant(true);
        return resourceRepository.save(resource);
    }

    @Override
    @Transactional
    public void delete(Resource resource) {
        remoteResourceManager.deleteRemoteResource(resource);
        resourceRepository.delete(resource);
    }
}
