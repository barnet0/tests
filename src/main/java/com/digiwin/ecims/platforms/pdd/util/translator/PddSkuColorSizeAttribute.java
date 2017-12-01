package com.digiwin.ecims.platforms.pdd.util.translator;

import com.digiwin.ecims.core.util.SkuColorSizeAttribute;

public class PddSkuColorSizeAttribute extends SkuColorSizeAttribute {

  public PddSkuColorSizeAttribute() {
    super();
    // TODO Auto-generated constructor stub
  }

  public PddSkuColorSizeAttribute(String color, String size) {
    super(color, size);
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    if (color == null || color.length() == 0) {
      return size;
    }
    if (size == null || size.length() == 0) {
      return color;
    }
    return color + ";" + size;
  }
}
