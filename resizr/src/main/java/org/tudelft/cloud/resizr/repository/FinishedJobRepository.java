package org.tudelft.cloud.resizr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tudelft.cloud.resizr.model.jpa.FinishedJob;

import java.util.List;

@Repository
public interface FinishedJobRepository extends JpaRepository<FinishedJob, Long> {
    List<FinishedJob> findTop1ByXAndYAndInitialImageHashAndDateCreatedAfterOrderByDateCreatedDesc(Double x, Double y, String hash, Long epoch);
}
