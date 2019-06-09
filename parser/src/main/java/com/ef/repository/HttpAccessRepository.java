package com.ef.repository;

import com.ef.model.HttpAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HttpAccessRepository extends JpaRepository<HttpAccess, Long> {

    @Query(value =
            "   SELECT DISTINCT ip_address " +
            "     FROM http_access " +
            "    WHERE date between :startDate and :finalDate " +
            " GROUP BY ip_address " +
            "   HAVING count(*) > :threshold" ,
            nativeQuery = true)
    List<String> findIpsToBlock(@Param("startDate") LocalDateTime startDate,
                                @Param("finalDate") LocalDateTime finalDate,
                                @Param("threshold") Integer threshold);

    @Modifying
    @Query("delete from HttpAccess")
    void deleteAll();
}
