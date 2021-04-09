package org.tudelft.cloud.resizr.service.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tudelft.cloud.resizr.model.monitoring.Heartbeat;
import org.tudelft.cloud.resizr.model.monitoring.InstanceLoad;
import org.tudelft.cloud.resizr.model.monitoring.StatusReport;

import java.util.List;

@Service
public class StatusMonitor {


    private final HeartbeatChecker heartbeatChecker;

    private final SlaveLoadChecker slaveLoadChecker;

    @Autowired
    public StatusMonitor(HeartbeatChecker heartbeatChecker, SlaveLoadChecker slaveLoadChecker) {
        this.heartbeatChecker = heartbeatChecker;
        this.slaveLoadChecker = slaveLoadChecker;
    }

    public StatusReport generateStatusReport() {
        StatusReport statusReport = new StatusReport();

        // Add heartbeats of all slaves
        List<Heartbeat> heartbeats = heartbeatChecker.checkHeartbeat();
        statusReport.getHearbeats().addAll(heartbeats);

        // Add instance loads of all slaves
        List<InstanceLoad> instanceLoads = slaveLoadChecker.getLoadInformation();
        statusReport.getInstanceLoads().addAll(instanceLoads);

        return statusReport;
    }
}
