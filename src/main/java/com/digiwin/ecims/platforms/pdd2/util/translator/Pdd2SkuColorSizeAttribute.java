package com.digiwin.ecims.platforms.pdd2.util.translator;

import com.digiwin.ecims.core.util.SkuColorSizeAttribute;

public class Pdd2SkuColorSizeAttribute extends SkuColorSizeAttribute {

  public Pdd2SkuColorSizeAttribute() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Pdd2SkuColorSizeAttribute(String color, String size) {
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
