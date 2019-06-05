package com.wallethub.feeder.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class HttpAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    private String ipAddress;
    private String protocolInfo;
    private String httpStatusCode;
    private String requestDetail;

    public HttpAccess() {
    }

    public HttpAccess(LocalDateTime time, String ipAddress, String protocolInfo, String httpStatusCode, String requestDetail) {
        this.time = time;
        this.ipAddress = ipAddress;
        this.protocolInfo = protocolInfo;
        this.httpStatusCode = httpStatusCode;
        this.requestDetail = requestDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getProtocolInfo() {
        return protocolInfo;
    }

    public void setProtocolInfo(String protocolInfo) {
        this.protocolInfo = protocolInfo;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getRequestDetail() {
        return requestDetail;
    }

    public void setRequestDetail(String requestDetail) {
        this.requestDetail = requestDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpAccess that = (HttpAccess) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "HttpAccess{" +
                "id=" + id +
                ", time=" + time +
                ", ipAddress='" + ipAddress + '\'' +
                ", protocolInfo='" + protocolInfo + '\'' +
                ", httpStatusCode='" + httpStatusCode + '\'' +
                ", requestDetail='" + requestDetail + '\'' +
                '}';
    }
}
