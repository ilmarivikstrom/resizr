package org.tudelft.cloud.resizr.service.decision;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tudelft.cloud.resizr.model.monitoring.InstanceLoad;
import org.tudelft.cloud.resizr.model.monitoring.StatusReport;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class VMReleaseProcessor extends ReportProcessor {

    @Value("${resizr.lower.load.threshold}")
    private int jobCountLowerThreshold;

    @Override
    public void process(StatusReport statusReport) {
        System.out.println("Here at SharedMemoryEvictionProcessor");
        handleSlaveLoadIfBelowLowerThreshold(statusReport.getInstanceLoads());
        super.propagate(statusReport);
    }

    private void handleSlaveLoadIfBelowLowerThreshold(List<InstanceLoad> instanceLoads) {
        boolean isBelowLowerLoadThreshold = false;
        if (instanceLoads.size() > 0) {
            isBelowLowerLoadThreshold = instanceLoads.stream()
                    .allMatch(it -> it.getJobCount() < jobCountLowerThreshold);
        }



        if (isBelowLowerLoadThreshold) {
            Optional<InstanceLoad> il = instanceLoads.stream().min(Comparator.comparing(InstanceLoad::getJobCount));
            il.ifPresent(instanceLoad -> handleResourceRemoval(instanceLoad.getInstanceName()));
        }
    }
}
