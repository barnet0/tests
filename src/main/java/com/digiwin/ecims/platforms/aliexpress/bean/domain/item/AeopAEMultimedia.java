package com.digiwin.ecims.platforms.aliexpress.bean.domain.item;

import java.util.List;

/**
 * 商品的多媒体信息。现在多媒体信息只支持视频。
 * 
 * @author 维杰
 *
 */
public class AeopAEMultimedia {

  // 否 多媒体信息。 [ { "aliMemberId": 1006680305, "mediaId": 12345678, "mediaType": "approved",
  // "mediaStatus": "approved", "posterUrl":
  // "http://img01.taobaocdn.com/bao/uploaded/TB1rNdGIVXXXXbTXFXXXXXXXXXX.jpg" } ]
  private List<AeopAEVideo> aeopAEVideos;

  public List<AeopAEVideo> getAeopAEVideos() {
    return aeopAEVideos;
  }

  public void setAeopAEVideos(List<AeopAEVideo> aeopAEVideos) {
    this.aeopAEVideos = aeopAEVideos;
  }


}
