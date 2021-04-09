package org.tudelft.cloud.resizr.service.decision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tudelft.cloud.resizr.model.jpa.Resource;
import org.tudelft.cloud.resizr.model.monitoring.StatusReport;
import org.tudelft.cloud.resizr.repository.ResourceRepository;
import org.tudelft.cloud.resizr.service.resource.management.ResourceManager;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class ReportProcessor {

    @Autowired
    private VMReleaseProcessor vmReleaseProcessor;

    @Autowired
    private VMLeaseProcessor vmLeaseProcessor;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    private ResourceManager resourceManager;

    private ReportProcessor next;

    private ReportProcessor first;

    @PostConstruct
    private void constructChain() {
        first = vmLeaseProcessor;
        vmLeaseProcessor.setNext(vmReleaseProcessor);
    }

    public void process(StatusReport statusReport) {
        first.process(statusReport);
    }

    void propagate(StatusReport statusReport) {
        if (this.getNext() != null) {
            this.getNext().process(statusReport);
        }
    }

    /**
     * Marks a resource for removal.
     * The Undertaker will take care of it at some point.
     * @param resourceName
     */
    void handleResourceRemoval(String resourceName) {
        boolean lowestInstanceState = resourceRepository.countAllByAvailableIsTrueAndIsRedundantIsFalse() <= 1
                && resourceRepository.countAllByAvailableIsTrueAndIsRedundantIsTrue() <= 1;
        if (!lowestInstanceState) {
            Optional<Resource> resource = resourceRepository.findByName(resourceName);
            if (resource.isPresent()) {
                resource.get().setAvailable(false);
                resource.get().setMarkedForKill(true);
                resourceRepository.save(resource.get());
            }
        }
    }

    /**
     * Tries to invoke the redundant resource.
     * Creates a request for a new resource.
     */
    void handleNewResource() {
        System.out.println("Here at VMLeaseProcessor#handleNewResource");
        callForBackup();
        resourceManager.createRedundant();
    }

    /**
     * Invokes the redundant instance.
     */
    void callForBackup() {
        List<Resource> redundantList = resourceRepository.findAllByIsRedundantIsTrue();
        System.out.println("Invoking redundant instance from list: " + redundantList);
        if (redundantList != null && redundantList.size() > 0) {
            Resource redundantResource = redundantList.get(0);
            redundantResource.setRedundant(false);
            resourceRepository.save(redundantResource);
        }
    }


    /*============ Getters and Setters ==============*/
    private ReportProcessor getNext() {
        return next;
    }

    void setNext(ReportProcessor next) {
        this.next = next;
    }

}
