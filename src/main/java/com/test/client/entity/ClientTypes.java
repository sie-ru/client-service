package com.test.client.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "client_types")
public class ClientTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "short_name", nullable = false, length = 60)
    private String shortName;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(name = "client_type_code", nullable = false, length = 10)
    private String clientTypeCode;

    public ClientTypes() {}

    public ClientTypes(Long id, String shortName, String fullName, String clientTypeCode) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
        this.clientTypeCode = clientTypeCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getClientTypeCode() {
        return clientTypeCode;
    }

    public void setClientTypeCode(String clientTypeCode) {
        this.clientTypeCode = clientTypeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientTypes that = (ClientTypes) o;
        return Objects.equals(id, that.id) && Objects.equals(shortName, that.shortName) && Objects.equals(fullName, that.fullName) && Objects.equals(clientTypeCode, that.clientTypeCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, fullName, clientTypeCode);
    }
}
