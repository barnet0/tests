package com.digiwin.ecims.platforms.dhgate.util.translator;

import com.digiwin.ecims.core.util.SkuColorSizeAttribute;

public class DhgateSkuColorSizeAttribute extends SkuColorSizeAttribute {

  public DhgateSkuColorSizeAttribute() {
    super();
    // TODO Auto-generated constructor stub
  }

  public DhgateSkuColorSizeAttribute(String color, String size) {
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
