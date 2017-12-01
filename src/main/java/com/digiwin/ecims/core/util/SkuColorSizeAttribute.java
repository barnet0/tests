package com.digiwin.ecims.core.util;

public class SkuColorSizeAttribute {

  protected String color;

  protected String size;

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public SkuColorSizeAttribute() {}

  public SkuColorSizeAttribute(String color, String size) {
    this.color = color;
    this.size = size;
  }
  
}
