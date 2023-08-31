package com.test.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.client.entity.ClientTypes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveClientDTO {

    @NotNull
    @Size(max = 60, message = "shortName must not be longer than 60 characters")
    private String shortName;

    @NotNull
    @Size(max = 255, message = "fullName must not be longer than 255 characters")
    private String fullName;

    private ClientTypes clientTypes;

    @NotNull
    @Size(max = 12, message = "fullName must not be longer than 12 characters")
    private String inn;

    @Size(max = 10, message = "okpo must not be longer than 10 characters")
    private String okpo;

    public SaveClientDTO() {}

    public SaveClientDTO(String shortName, String fullName, ClientTypes clientTypes, String inn, String okpo) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.clientTypes = clientTypes;
        this.inn = inn;
        this.okpo = okpo;
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
}
