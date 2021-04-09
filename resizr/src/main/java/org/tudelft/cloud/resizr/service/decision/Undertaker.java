package org.tudelft.cloud.resizr.service.decision;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tudelft.cloud.resizr.model.jpa.Resource;
import org.tudelft.cloud.resizr.repository.ResourceRepository;
import org.tudelft.cloud.resizr.service.resource.management.ResourceManager;

import java.util.List;

@Component
public class Undertaker {

    private final ResourceManager resourceManager;

    private final ResourceRepository resourceRepository;

    public Undertaker(ResourceManager resourceManager, ResourceRepository resourceRepository) {
        this.resourceManager = resourceManager;
        this.resourceRepository = resourceRepository;
    }

    @Scheduled(cron = "${resizr.undertaker.check.interval}")
    private void killMarkedInstances() {
        List<Resource> markedForKill = resourceRepository.findAllByMarkedForKillIsTrue();
        markedForKill.forEach(resourceManager::delete);
    }

}
