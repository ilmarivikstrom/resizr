package org.tudelft.cloud.resizr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tudelft.cloud.resizr.model.jpa.Resource;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Optional<Resource> findByName(String name);

    List<Resource> findAllByMarkedForKillIsTrue();

    List<Resource> findAllByMarkedForKillIsFalse();

    List<Resource> findAllByIsRedundantIsTrue();

    List<Resource> findAllByIsRedundantIsTrueAndAvailableIsFalse();

    List<Resource> findAllByAvailableIsTrueAndIsRedundantIsFalse();

    Long countAllByAvailableIsTrueAndIsRedundantIsFalse();

    Long countAllByAvailableIsTrueAndIsRedundantIsTrue();

}

