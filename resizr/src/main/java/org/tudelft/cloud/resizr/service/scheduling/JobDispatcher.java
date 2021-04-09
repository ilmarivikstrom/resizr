package org.tudelft.cloud.resizr.service.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.tudelft.cloud.resizr.model.jpa.FinishedJob;
import org.tudelft.cloud.resizr.model.jpa.ResizeRequest;
import org.tudelft.cloud.resizr.model.jpa.Resource;
import org.tudelft.cloud.resizr.model.jpa.CommunicationEntity;
import org.tudelft.cloud.resizr.repository.FinishedJobRepository;
import org.tudelft.cloud.resizr.repository.ResizeRequestRepository;
import org.tudelft.cloud.resizr.repository.CommunicationEntityRepository;
import org.tudelft.cloud.resizr.service.monitoring.HeartbeatChecker;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@Service
public class JobDispatcher {

    private final ResizeRequestRepository resizeRequestRepository;

    private final ResourceScheduler resourceScheduler;

    private final CommunicationEntityRepository communicationEntityRepository;

    private final FinishedJobRepository finishedJobRepository;

    @Value("${resizr.cache.time.minutes}    ")
    private int cacheTimeMinutes;

    @Autowired
    public JobDispatcher(ResourceScheduler resourceScheduler,
                         ResizeRequestRepository resizeRequestRepository,
                         CommunicationEntityRepository communicationEntityRepository,
                         FinishedJobRepository finishedJobRepository) {
        this.resourceScheduler = resourceScheduler;
        this.resizeRequestRepository = resizeRequestRepository;
        this.communicationEntityRepository = communicationEntityRepository;
        this.finishedJobRepository = finishedJobRepository;
    }

    @Scheduled(cron = "${resizr.internal.queue.request.check}")
    public void scheduleJobs() throws IOException, SQLException {

        List<ResizeRequest> resizeRequestList = resizeRequestRepository.findAllByIsProcessedIsFalse();

        System.out.println("finding resize request");
        System.out.println(resizeRequestList);
        for (ResizeRequest resizeRequest : resizeRequestList) {
            List<FinishedJob> finishedJobFromCache = finishedJobRepository.findTop1ByXAndYAndInitialImageHashAndDateCreatedAfterOrderByDateCreatedDesc(
                    resizeRequest.getX(),
                    resizeRequest.getY(),
                    resizeRequest.getInitialImageHash(),
                    System.currentTimeMillis() - cacheTimeMinutes*(60*1000L));
            if (finishedJobFromCache.size() == 0) {
                System.out.println("Did not find entry in cache. Sending the job to an instance!");
                sendToInstance(resizeRequest);
            } else {
                System.out.println("Found an entry in cache!");
                addToFinishedRepository(resizeRequest, finishedJobFromCache.get(0));
            }
        }
    }

    private void sendToInstance(ResizeRequest resizeRequest) throws IOException, SQLException {
        System.out.println(resizeRequest);
        Resource nextResource = resourceScheduler.getNextSchedulableResource();

        CommunicationEntity ce = new CommunicationEntity();
        ce.setJobId(resizeRequest.getJobId());
        ce.setResizeId(resizeRequest.getId());
        ce.setInstanceName(nextResource.getName());
        ce.setX(resizeRequest.getX());
        ce.setY(resizeRequest.getY());
        ce.setImageName(resizeRequest.getImageName());
        ce.setInitialImageHash(resizeRequest.getInitialImageHash());
        byte[] imageContent = Files.readAllBytes(Paths.get("src/main/resources/" + resizeRequest.getImageName()));
        ce.setInitialImageBlob(new SerialBlob(imageContent));
        communicationEntityRepository.save(ce);

        // update job status so we don't pick it up again
        resizeRequest.setProcessed(true);
        resizeRequestRepository.save(resizeRequest);
    }

    private void addToFinishedRepository(ResizeRequest resizeRequest, FinishedJob finishedJob) {
        FinishedJob newFinishedJob = new FinishedJob();
        newFinishedJob.setJobId(finishedJob.getJobId());
        newFinishedJob.setResizeId(finishedJob.getResizeId());
        newFinishedJob.setInstanceName(finishedJob.getInstanceName());
        newFinishedJob.setX(finishedJob.getX());
        newFinishedJob.setY(finishedJob.getY());
        newFinishedJob.setImageName(finishedJob.getImageName());
        newFinishedJob.setInitialImageBlob(finishedJob.getInitialImageBlob());
        newFinishedJob.setInitialImageHash(finishedJob.getInitialImageHash());
        newFinishedJob.setResizedImageBlob(finishedJob.getResizedImageBlob());
        finishedJobRepository.save(newFinishedJob);

        // Delete resizeRequest from repository as the job is finished.
        resizeRequestRepository.delete(resizeRequest);
    }
}
