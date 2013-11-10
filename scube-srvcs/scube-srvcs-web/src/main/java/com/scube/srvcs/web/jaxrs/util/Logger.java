/**
 * @author Copyright (c) 2010 by Oracle. All Rights Reserved
 *  
 */
package com.scube.srvcs.web.jaxrs.util;


/**
 * Logger Class
 */

public class Logger {

  public static void log(String msg) {
    System.out.println("[JAX-RS]: " + msg);
  }

  public static void log(String msg, Exception e) {
    log(msg);
    e.printStackTrace();
  }

  public static void logRequest(RequestEnum request, String baseURI, String id) {
    Logger.log(id == null? "Request URI: " + baseURI: "Request URI: " + baseURI + "/" + id);
    Logger.log(request.toString() + " Method invoked ...");
  }
}
