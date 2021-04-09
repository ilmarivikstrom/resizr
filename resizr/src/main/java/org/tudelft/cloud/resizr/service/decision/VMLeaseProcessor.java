package org.tudelft.cloud.resizr.service.decision;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tudelft.cloud.resizr.model.jpa.CommunicationEntity;
import org.tudelft.cloud.resizr.model.jpa.ResizeRequest;
import org.tudelft.cloud.resizr.model.monitoring.Heartbeat;
import org.tudelft.cloud.resizr.model.monitoring.InstanceLoad;
import org.tudelft.cloud.resizr.model.monitoring.StatusReport;
import org.tudelft.cloud.resizr.repository.CommunicationEntityRepository;
import org.tudelft.cloud.resizr.repository.ResizeRequestRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VMLeaseProcessor extends ReportProcessor {

    private Map<Long, Long> failedHeartbeats = new HashMap<>();
    @Value("${resizr.flatline.count.before.instance.dead}")
    private int flatlineCountBeforeDeclaredDead;

    @Value("${resizr.upper.load.threshold}")
    private int jobCoundUpperThreshold;

    private CommunicationEntityRepository communicationEntityRepository;

    private ResizeRequestRepository resizeRequestRepository;

    public VMLeaseProcessor(CommunicationEntityRepository communicationEntityRepository, ResizeRequestRepository resizeRequestRepository) {
        this.communicationEntityRepository = communicationEntityRepository;
        this.resizeRequestRepository = resizeRequestRepository;
    }

    @Override
    public void process(StatusReport statusReport) {
        System.out.println("Here at VMLeaseProcessor");

        handleSlaveLoadIfAboveUpperThreshold(statusReport.getInstanceLoads());

        handleHeartbeats(statusReport.getHearbeats());

        super.propagate(statusReport);
    }

    private void handleSlaveLoadIfAboveUpperThreshold(List<InstanceLoad> instanceLoads) {
        boolean isAboveUpperLoadThreshold = false;
        if (instanceLoads.size() > 0) {
           isAboveUpperLoadThreshold =  instanceLoads.stream()
                    .allMatch(it -> it.getJobCount() > jobCoundUpperThreshold);
        }
        if (isAboveUpperLoadThreshold) {
            System.out.println("Here at handleSlaveLoadIfAboveUpperThreshold ");
            handleNewResource();
        }
    }

    private void handleHeartbeats(List<Heartbeat> heartbeats) {
        heartbeats.stream().filter(Heartbeat::isFlatline).forEach(it -> {
            failedHeartbeats.put(it.getInstanceId(), failedHeartbeats.getOrDefault(it.getInstanceId(), 0L) + 1);
            if (failedHeartbeats.get(it.getInstanceId()) >= flatlineCountBeforeDeclaredDead) {
                System.out.println("Here at handleHeartbeats ");
                handleDeadInstance(it);
            }
        });

        System.out.println(failedHeartbeats);
    }

    private void handleDeadInstance(Heartbeat heartbeat) {
        // Request resource removal
        handleResourceRemoval(heartbeat.getInstanceName());

        // Remove from heartbeat map
        failedHeartbeats.remove(heartbeat.getInstanceId());

        System.out.println("Calling emergency on " + heartbeat.getInstanceName());

        // Requests a new resource. The redundant one if it is available.
        handleNewResource();

        // transfer jobs of that resource to queue
        List<CommunicationEntity> communicationEntities = communicationEntityRepository.findByInstanceName(heartbeat.getInstanceName());
        List<ResizeRequest> resizeRequests = mapCommunicationEntityToResizeRequest(communicationEntities);
        resizeRequestRepository.saveAll(resizeRequests);
        communicationEntityRepository.deleteAllByInstanceName(heartbeat.getInstanceName());
    }

    private List<ResizeRequest> mapCommunicationEntityToResizeRequest(List<CommunicationEntity> communicationEntityList) {
        List<ResizeRequest> resizeRequests = new ArrayList<>();
        communicationEntityList.forEach(ce -> {
            ResizeRequest resizeRequest = new ResizeRequest();
            resizeRequest.setJobId(ce.getJobId());
            resizeRequest.setX(ce.getX());
            resizeRequest.setY(ce.getY());
            resizeRequest.setImageName(ce.getImageName());
            resizeRequest.setInitialImageHash(ce.getInitialImageHash());
            resizeRequests.add(resizeRequest);
        });
        return resizeRequests;
    }
}
