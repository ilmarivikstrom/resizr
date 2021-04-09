package org.tudelft.cloud.resizr.model.monitoring;

import java.util.ArrayList;
import java.util.List;

public class StatusReport {
    private List<Heartbeat> hearbeats = new ArrayList<>();
    private List<InstanceLoad> instanceLoads = new ArrayList<>();

    public List<Heartbeat> getHearbeats() {
        return hearbeats;
    }

    public List<InstanceLoad> getInstanceLoads() {
        return instanceLoads;
    }
}
