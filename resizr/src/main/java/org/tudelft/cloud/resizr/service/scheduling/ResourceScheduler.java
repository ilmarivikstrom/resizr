package org.tudelft.cloud.resizr.service.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tudelft.cloud.resizr.model.jpa.Resource;

import java.util.List;

@Service
public class ResourceScheduler {

    private final ResourcePool resourcePool;

    @Autowired
    public ResourceScheduler(ResourcePool resourcePool) { this.resourcePool = resourcePool; }

    public Resource getNextSchedulableResource() {
        List<Resource> availableResources = this.resourcePool.getAllAvailableResources();

        // Very simple "least connections" algorithm for load balancing.
        Resource bestCandidate = new Resource();
        for (Resource res : availableResources) {
            if (bestCandidate.getQueueSize() == null) {
                bestCandidate = res;
            }
            if (res.getQueueSize() < bestCandidate.getQueueSize()) {
                bestCandidate = res;
            }
        }
        // Check if no resources are available and report that in stdout
        if (bestCandidate.getQueueSize() == null) {
            System.out.println("No resources in resource pool!");
        }
        return bestCandidate;
    }
}
