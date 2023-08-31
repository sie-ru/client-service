package com.test.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.client.entity.ClientTypes;
import com.test.client.model.Order;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetClientDTO {

    @Schema(description = "Client ID", example = "1")
    private Long id;

    @Schema(description = "Client short name", example = "ООО 'Компания'")
    private String shortName;

    @Schema(description = "Client full name", example = "Общество с ограниченной отвественностью 'Компания'")
    private String fullName;

    @Schema(description = "Client type")
    private ClientTypes clientTypes;

    @Schema(description = "Client INN")
    private String inn;

    @Schema(description = "Client OKPO")
    private String okpo;

    @Schema(description = "Client order list")
    private List<Order> orders;

    @Schema(description = "Date of creation")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date creationDate;

    @Schema(description = "Date of modification")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date modificationDate;

    public GetClientDTO() {}

    public GetClientDTO(Long id, String shortName, String fullName, ClientTypes clientTypes, String inn, String okpo, List<Order> orders, Date creationDate, Date modificationDate) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
        this.clientTypes = clientTypes;
        this.inn = inn;
        this.okpo = okpo;
        this.orders = orders;
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

    public ClientTypes getClientTypes() {
        return clientTypes;
    }

    public void setClientTypes(ClientTypes clientTypes) {
        this.clientTypes = clientTypes;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetClientDTO that = (GetClientDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(shortName, that.shortName) && Objects.equals(fullName, that.fullName) && Objects.equals(clientTypes, that.clientTypes) && Objects.equals(inn, that.inn) && Objects.equals(okpo, that.okpo) && Objects.equals(orders, that.orders) && Objects.equals(creationDate, that.creationDate) && Objects.equals(modificationDate, that.modificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, fullName, clientTypes, inn, okpo, orders, creationDate, modificationDate);
    }
}
