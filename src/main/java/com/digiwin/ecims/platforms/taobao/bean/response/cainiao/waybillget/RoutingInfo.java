package com.digiwin.ecims.platforms.taobao.bean.response.cainiao.waybillget;

public class RoutingInfo {

  private Consolidation consolidation;

  private Origin origin;

  private Sortation sortation;

  public void setConsolidation(Consolidation consolidation) {
    this.consolidation = consolidation;
  }

  public Consolidation getConsolidation() {
    return this.consolidation;
  }

  public void setOrigin(Origin origin) {
    this.origin = origin;
  }

  public Origin getOrigin() {
    return this.origin;
  }

  public void setSortation(Sortation sortation) {
    this.sortation = sortation;
  }

  public Sortation getSortation() {
    return this.sortation;
  }

}
