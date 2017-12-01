package com.digiwin.ecims.platforms.aliexpress.bean.domain.item;

/**
 * 这个Domain保存了一段视频信息。它有以下几个属性： 1）aliMemberId：卖家的主账户ID， 2）mediaId：视频ID， 3）mediaStatus：视频的状态
 * 4）mediaType：视频的类型， 5）posterUrl：封面图片的URL
 * 
 * @author 维杰
 *
 */
public class AeopAEVideo {

  // 否 卖家主账户ID 1006680305
  private Long aliMemberId;

  // 否 视频ID 12345678
  private Long mediaId;

  // 否 视频的类型 video
  private String mediaType;

  // 否 视频的状态 approved
  private String mediaStatus;

  // 否 视频封面图片的URL http://img01.taobaocdn.com/bao/uploaded/TB1rNdGIVXXXXbTXFXXXXXXXXXX.jpg
  private String posterUrl;

  public Long getAliMemberId() {
    return aliMemberId;
  }

  public void setAliMemberId(Long aliMemberId) {
    this.aliMemberId = aliMemberId;
  }

  public Long getMediaId() {
    return mediaId;
  }

  public void setMediaId(Long mediaId) {
    this.mediaId = mediaId;
  }

  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  public String getMediaStatus() {
    return mediaStatus;
  }

  public void setMediaStatus(String mediaStatus) {
    this.mediaStatus = mediaStatus;
  }

  public String getPosterUrl() {
    return posterUrl;
  }

  public void setPosterUrl(String posterUrl) {
    this.posterUrl = posterUrl;
  }


}
