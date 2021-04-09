package org.tudelft.cloud.resizr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tudelft.cloud.resizr.model.jpa.CommunicationEntity;
import org.tudelft.cloud.resizr.model.monitoring.InstanceLoad;

import java.util.List;

@Repository
public interface CommunicationEntityRepository extends JpaRepository<CommunicationEntity, Long> {
    CommunicationEntity findByResizeId(Long resizeId);

    @Query("select new org.tudelft.cloud.resizr.model.monitoring.InstanceLoad(ce.instanceName, count(ce)) from CommunicationEntity ce group by ce.instanceName")
    List<InstanceLoad> getJobsPerInstance();

    List<CommunicationEntity> findByInstanceName(String instanceName);

    void deleteAllByInstanceName(String instanceName);
}
