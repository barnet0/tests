package com.digiwin.ecims.ontime.service;

public interface OnTimeTaskCheckBusiJob extends OnTimeTaskBusiJob {

  boolean timeOutExecuteCheck(String...args) throws Exception;

}
