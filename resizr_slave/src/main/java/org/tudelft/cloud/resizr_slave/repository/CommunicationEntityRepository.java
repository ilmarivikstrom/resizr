package org.tudelft.cloud.resizr_slave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tudelft.cloud.resizr_slave.model.CommunicationEntity;

@Repository
public interface CommunicationEntityRepository extends JpaRepository<CommunicationEntity, Long> {
    CommunicationEntity findTop1ByInstanceNameOrderByDateCreatedDesc(String instanceName);
}
