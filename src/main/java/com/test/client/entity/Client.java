package com.test.client.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client")
@EntityListeners(AuditingEntityListener.class)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "short_name", nullable = false, length = 60)
    private String shortName;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @OneToOne
    @JoinColumn(name = "client_type", referencedColumnName = "id")
    private ClientTypes clientType;

    @Column(name = "inn", nullable = false, length = 12)
    private String inn;

    @Column(name = "okpo", nullable = true, length = 10)
    private String okpo;

    @CreatedDate
    @Column(name = "creation_data", nullable = false)
    private Date creationDate;

    @LastModifiedDate
    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

    public Client() {}

    public Client(Long id, String shortName, String fullName, ClientTypes clientType, String inn, String okpo, Date creationDate, Date modificationDate) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
        this.clientType = clientType;
        this.inn = inn;
        this.okpo = okpo;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
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

    public ClientTypes getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypes clientType) {
        this.clientType = clientType;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }
}