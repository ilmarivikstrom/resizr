package org.tudelft.cloud.resizr_slave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tudelft.cloud.resizr_slave.model.CommunicationEntity;
import org.tudelft.cloud.resizr_slave.model.FinishedJob;
import org.tudelft.cloud.resizr_slave.model.ResizeRequest;
import org.tudelft.cloud.resizr_slave.repository.CommunicationEntityRepository;
import org.tudelft.cloud.resizr_slave.repository.FinishedJobRepository;
import org.tudelft.cloud.resizr_slave.repository.ResizeRequestRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class WorkerService {

    private final CommunicationEntityRepository communicationEntityRepository;

    private final FinishedJobRepository finishedJobRepository;

    private final ResizeRequestRepository resizeRequestRepository;

    private final ImageResizerService imageResizerService;

    // Get the instance UUID from environment variable which should be set by Brain (via Docker).
    private final String instanceUUID = System.getenv("INSTANCE_UUID");

    @Autowired
    public WorkerService(CommunicationEntityRepository communicationEntityRepository, FinishedJobRepository finishedJobRepository, ResizeRequestRepository resizeRequestRepository, ImageResizerService imageResizerService) {
        this.communicationEntityRepository = communicationEntityRepository;
        this.finishedJobRepository = finishedJobRepository;
        this.resizeRequestRepository = resizeRequestRepository;
        this.imageResizerService = imageResizerService;
    }

    private static FinishedJob mapCommunicationEntityToFinishedJob(CommunicationEntity ce) {
        FinishedJob finishedJob = new FinishedJob();
        finishedJob.setJobId(ce.getJobId());
        finishedJob.setResizeId(ce.getResizeId());
        finishedJob.setInstanceName(ce.getInstanceName());
        finishedJob.setX(ce.getX());
        finishedJob.setY(ce.getY());
        finishedJob.setImageName(ce.getImageName());
        finishedJob.setInitialImageBlob(ce.getInitialImageBlob());
        finishedJob.setInitialImageHash(ce.getInitialImageHash());
        finishedJob.setResizedImageBlob(ce.getResizedImageBlob());
        return finishedJob;
    }

    @Scheduled(cron = "${resizr.slave.internal.queue.request.check}")
    void doWork()  {
        CommunicationEntity nextWork = communicationEntityRepository.findTop1ByInstanceNameOrderByDateCreatedDesc(instanceUUID);
        System.out.println("Communication entity repository returned: " + nextWork);
        if (nextWork == null) {
            return;
        }

        System.out.println("Should resize this:\n" + nextWork.getInitialImageBlob());
        try {
            nextWork = imageResizerService.getResizedJob(nextWork);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException s) {
            s.printStackTrace();
        }

        FinishedJob fj = mapCommunicationEntityToFinishedJob(nextWork);
        finishedJobRepository.save(fj);
        communicationEntityRepository.deleteById(nextWork.getId());

        // Deleting corresponding ResizeRequest as the resize job is now done.
        Iterable<ResizeRequest> toBeDeleted = resizeRequestRepository.findAll();

        for (ResizeRequest request : toBeDeleted) {
            if (request.getId() == nextWork.getResizeId()) {
                System.out.println("Jee");
                resizeRequestRepository.delete(request);
            }
        }
    }
}
