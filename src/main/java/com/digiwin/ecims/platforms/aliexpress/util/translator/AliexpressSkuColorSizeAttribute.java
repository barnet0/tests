package com.digiwin.ecims.platforms.aliexpress.util.translator;

import com.digiwin.ecims.core.util.SkuColorSizeAttribute;

public class AliexpressSkuColorSizeAttribute extends SkuColorSizeAttribute {

  public AliexpressSkuColorSizeAttribute() {
    super();
    // TODO Auto-generated constructor stub
  }

  public AliexpressSkuColorSizeAttribute(String color, String size) {
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
