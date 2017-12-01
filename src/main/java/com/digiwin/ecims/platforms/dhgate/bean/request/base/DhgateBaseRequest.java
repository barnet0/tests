package com.digiwin.ecims.platforms.dhgate.bean.request.base;

import com.dhgate.open.client.BizStatusResponse;

public abstract class DhgateBaseRequest<T extends BizStatusResponse> {

  public abstract Class<T> getResponseClass();
}
