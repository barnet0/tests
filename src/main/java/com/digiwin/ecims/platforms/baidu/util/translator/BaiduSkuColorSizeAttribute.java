package com.digiwin.ecims.platforms.baidu.util.translator;

import com.digiwin.ecims.core.util.SkuColorSizeAttribute;

public class BaiduSkuColorSizeAttribute extends SkuColorSizeAttribute {

  public BaiduSkuColorSizeAttribute() {
    super();
  }

  public BaiduSkuColorSizeAttribute(String color, String size) {
    super(color, size);
  }

  @Override
  public String toString() {
    return color + ";" + size;
  }
}
