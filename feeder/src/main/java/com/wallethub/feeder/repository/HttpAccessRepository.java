package com.wallethub.feeder.repository;

import com.wallethub.feeder.model.HttpAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HttpAccessRepository extends JpaRepository<HttpAccess, Long> {
}
