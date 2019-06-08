package com.ef.service;

import com.ef.model.BlockedIp;
import com.ef.model.Duration;
import com.ef.repository.BlockedIpRepository;
import com.ef.repository.HttpAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class HttpAccessService {

    private final HttpAccessRepository httpAccessRepository;
    private final BlockedIpRepository blockedIpRepository;

    @Autowired
    public HttpAccessService(HttpAccessRepository httpAccessRepository, BlockedIpRepository blockedIpRepository) {
        this.httpAccessRepository = httpAccessRepository;
        this.blockedIpRepository = blockedIpRepository;
    }

    public List<String> blockIps(@NonNull LocalDateTime startDate,
                                 @NonNull Duration duration,
                                 @NonNull Integer threshold) {
        LocalDateTime finalDate = this.calculateFinalDate(startDate, duration);
        List<String> ipsToBlock = this.httpAccessRepository.findIpsToBlock(startDate, finalDate, threshold);
        final String blockingReason = String.format("IP had more than %d access between %s and %s",
                threshold, startDate, finalDate);
        ipsToBlock.stream().forEach(ip -> {
            this.blockedIpRepository.save(new BlockedIp(ip, blockingReason));
        });
        return ipsToBlock;
    }

    protected LocalDateTime calculateFinalDate(LocalDateTime startDate, Duration duration) {
        if(duration == Duration.DAILY) {
            return startDate.plus(1, ChronoUnit.DAYS);
        }
        return startDate.plus(1, ChronoUnit.HOURS);
    }
}
