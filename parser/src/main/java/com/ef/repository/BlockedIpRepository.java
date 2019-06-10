package com.ef.repository;

import com.ef.model.BlockedIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlockedIpRepository extends JpaRepository<BlockedIp, Long> {

    @Modifying
    @Query("delete from BlockedIp")
    void deleteAll();
}
