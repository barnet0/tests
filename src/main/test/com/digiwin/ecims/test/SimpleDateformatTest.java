package com.digiwin.ecims.test;

import com.digiwin.ecims.core.util.DateTimeTool;

public class SimpleDateformatTest {

  public static class TestSimpleDateFormatThreadSafe extends Thread {

    public void run() {
      while (true) {
        try {
          this.join(2000);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
        System.out.println(this.getName() + ":" + DateTimeTool.parse("2013-05-24 06:02:20"));
//        Calendar cal = Calendar.getInstance();
//        Date date = cal.getTime();
//        System.out.println(this.getName() + ", Date: " + date.toString() + ", Formatted Date: " + DateTimeTool.formatToMillisecond(date));
      }
    }

  }

  public static void main(String[] args) {
    for(int i = 0; i < 3; i++){
      new TestSimpleDateFormatThreadSafe().start();
  }
  }

}

