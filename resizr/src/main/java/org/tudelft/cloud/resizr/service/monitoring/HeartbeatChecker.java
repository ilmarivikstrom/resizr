package org.tudelft.cloud.resizr.service.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tudelft.cloud.resizr.model.monitoring.Heartbeat;
import org.tudelft.cloud.resizr.service.scheduling.ResourcePool;

import java.util.ArrayList;
import java.util.List;

@Component
public class HeartbeatChecker {

    @Value("${resizr.slave.healthcheck.suffix}")
    String healthCheckSuffix;

    @Autowired
    ResourcePool resourcePool;

    @Autowired
    RestTemplate restTemplate;

    public List<Heartbeat> checkHeartbeat() {
        List<Heartbeat> heartbeats = new ArrayList<>();
        resourcePool.getResourcesNotMarkedForKill().forEach(it -> {
            String url = it.getUrl() + healthCheckSuffix;
            try {
                ResponseEntity<Heartbeat> heartbeatRequest = restTemplate.getForEntity(url, Heartbeat.class);
                Heartbeat heartbeat = heartbeatRequest.getBody();
                if (heartbeat != null) {
                    heartbeat.setInstanceId(it.getId());
                    heartbeat.setInstanceName(it.getName());
                    heartbeat.setInstanceUrl(it.getUrl());
                    System.out.println(heartbeat);
                    heartbeats.add(heartbeat);
                    System.out.println("Heartbeat: " + heartbeat);
                } else {
                    throw new Exception("Heartbeat is null");
                }
            } catch (Exception e) {
                System.out.println("URL " + url + " is down: " + e.getMessage());
                Heartbeat flatlined = new Heartbeat();
                flatlined.setInstanceId(it.getId());
                flatlined.setInstanceName(it.getName());
                flatlined.setInstanceUrl(it.getUrl());
                flatlined.setFlatline(true);
                heartbeats.add(flatlined);
            }
        });
        return heartbeats;
    }
}
