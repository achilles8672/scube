/**
 * @author Copyright (c) 2010 by Oracle. All Rights Reserved
 *  
 */
package com.scube.srvcs.web.jaxrs.client;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import com.scube.srvcs.web.jaxrs.entity.Task;
import com.scube.srvcs.web.jaxrs.util.Logger;
import com.scube.srvcs.web.jaxrs.util.RequestEnum;

/**
 * Client Configuration Class
 */
public class ConfigClient {
  private final static ConfigClient INSTANCE = new ConfigClient();
  
  private final static String BASE_URI = "http://localhost:7001/jaxrs/rest/tasks";

  private WebResource resource;

  private ConfigClient() {
    ClientConfig config = new DefaultClientConfig();
    this.resource = Client.create(config).resource(UriBuilder.fromUri(BASE_URI).build());
  }

  public static ConfigClient getInstance() {
    return INSTANCE;
  }

  public void invokePOST(Task task) {
    Logger.logRequest(RequestEnum.POST, BASE_URI, null);

    Logger.log("Create Task ...");
    Logger.log(task.toString());

    try {
        ClientResponse response = resource.post(ClientResponse.class, task);

      Logger.log("POST response, created task ... " + response.toString() + "\n");
    } catch (Exception e) {
      Logger.log("Fail to Create Task[id=" + task.getId() + "]! Exceptions: ", e);
    }
  }

  public void invokePOST(String mediaType, String mediaStr) {
    Logger.logRequest(RequestEnum.POST, BASE_URI, null);

    Logger.log("Create Task ...");
    Logger.log(mediaStr);

    try {
      ClientResponse response = resource.type(mediaType)
          .post(ClientResponse.class, mediaStr);

      Logger.log("POST response, created task ... " + response.toString() + "\n");
    } catch (Exception e) {
      Logger.log("Fail to Create Task! Exceptions: ", e);
    }
  }

  public void invokeGET() {
    Logger.logRequest(RequestEnum.GET, BASE_URI, null);

    Logger.log("View All Tasks ...");

    try {
      Logger.log("GET response, all tasks ... " + resource.get(String.class) + "\n");
    } catch (Exception e) {
      Logger.log("Fail to View Tasks! Exceptions: ", e);
    }
  }

  public void invokePUT(Task task) {
    Logger.logRequest(RequestEnum.PUT, BASE_URI, null);

    Logger.log("Update Task ...");
    Logger.log(task.toString());

     // Update Task
    task.setStatus("DONE");
    try {
      ClientResponse response = resource.put(
          ClientResponse.class, task); 

      Logger.log("PUT response, updated task ... " + response.getEntity(String.class) + "\n");
    } catch (Exception e) {
      Logger.log("Fail to Update Task[id=" + task.getId() + "]! Exceptions: ", e);
    }
  }

  public void invokeDELETE(Task task) {
    Logger.logRequest(RequestEnum.DELETE, BASE_URI, task.getId());

    Logger.log("Delete Task ...");
    Logger.log(task.toString());

    try {
      Logger.log("DELETE response, remaining tasks ... "
              + resource.path(task.getId())
                  .delete(String.class));
    } catch (Exception e) {
      Logger.log("Fail to Delete Task[id=" + task.getId() + "]! Exceptions: ", e);
    }
  }
}
