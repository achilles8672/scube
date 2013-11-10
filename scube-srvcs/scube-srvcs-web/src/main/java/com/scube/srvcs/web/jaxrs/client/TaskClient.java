/**
 * @author Copyright (c) 2010 by Oracle. All Rights Reserved
 *  
 */
package com.scube.srvcs.web.jaxrs.client;

import javax.ws.rs.core.MediaType;

import com.scube.srvcs.web.jaxrs.entity.Task;

/**
 * Client Class
 */
public class TaskClient {

  private void run() throws Exception {
    ConfigClient client = ConfigClient.getInstance();

    Task t1 = new Task("001", "JEE6 Use Cases", "DOING");
    client.invokePOST(t1);

    Task t2 = new Task("002", "JEE6 Functional Specification", "TO DO");
    client.invokePOST(MediaType.APPLICATION_XML, t2.toXML());

    client.invokeGET();

    client.invokePUT(t1);

    client.invokeDELETE(t1);
  }

  public static void main(String[] args) throws Exception {
    TaskClient client = new TaskClient();
    client.run();
  }
}
