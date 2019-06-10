package com.ef.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class BlockedIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAddress;
    private String blockReason;
    private LocalDateTime blockingDate;

    public BlockedIp() {
    }

    public BlockedIp(String ipAddress, String blockReason) {
        this.ipAddress = ipAddress;
        this.blockReason = blockReason;
    }

    @PrePersist
    public void prePersist() {
        this.blockingDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public LocalDateTime getBlockingDate() {
        return blockingDate;
    }

    public void setBlockingDate(LocalDateTime blockingDate) {
        this.blockingDate = blockingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockedIp blockedIp = (BlockedIp) o;
        return Objects.equals(id, blockedIp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BlockedIp{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", blockReason='" + blockReason + '\'' +
                ", blockingDate=" + blockingDate +
                '}';
    }
}
