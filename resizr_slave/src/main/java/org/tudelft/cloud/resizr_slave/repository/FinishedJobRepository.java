package org.tudelft.cloud.resizr_slave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tudelft.cloud.resizr_slave.model.FinishedJob;

@Repository
public interface FinishedJobRepository extends JpaRepository<FinishedJob, Long> {
}
