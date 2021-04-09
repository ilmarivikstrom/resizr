package org.tudelft.cloud.resizr.service.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tudelft.cloud.resizr.model.monitoring.InstanceLoad;
import org.tudelft.cloud.resizr.repository.CommunicationEntityRepository;

import java.util.List;

@Component
public class SlaveLoadChecker {


    @Autowired
    CommunicationEntityRepository communicationEntityRepository;

    public List<InstanceLoad> getLoadInformation() {
        List<InstanceLoad> instanceLoadList = communicationEntityRepository.getJobsPerInstance();

        return instanceLoadList;
    }
}
