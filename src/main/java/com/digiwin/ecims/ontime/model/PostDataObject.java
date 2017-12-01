package com.digiwin.ecims.ontime.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostDataObject<T> {

  @JsonProperty("plant")
  private String plant;

  @JsonProperty("service")
  private String service;

  @JsonProperty("totalRecord")
  private String totalRecord;

  @JsonProperty("user")
  private String user;

  @JsonProperty("data")
  private List<T> data;

  @JsonProperty("entId")
  private String entId;

  @JsonProperty("companyId")
  private String companyId;

  public PostDataObject() {
    data = new ArrayList<T>();
  }

  public String getPlant() {
    return plant;
  }

  public void setPlant(String plant) {
    this.plant = plant;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getTotalRecord() {
    return totalRecord;
  }

  public void setTotalRecord(int totalRecord) {
    this.totalRecord = String.valueOf(totalRecord);
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public List<T> getData() {
    return data;
  }

  public void addObject(List<T> data) {
    this.data.addAll(data);
  }

  public void setObjects(List<T> data) {
    this.data = data;
  }

  public String getEntId() {
    return entId;
  }

  public void setEntId(String entId) {
    this.entId = entId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }


}
