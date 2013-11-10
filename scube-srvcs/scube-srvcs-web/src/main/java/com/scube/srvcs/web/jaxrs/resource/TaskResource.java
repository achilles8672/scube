/**
 * @author Copyright (c) 2010 by Oracle. All Rights Reserved
 *  
 */
package com.scube.srvcs.web.jaxrs.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.scube.srvcs.web.jaxrs.entity.Task;

/**
 * Root Resource Class
 */
@Stateless
@Path("/tasks/")
public class TaskResource extends BaseResource<Task>{
  @PersistenceContext
  private EntityManager em;

  public TaskResource() {
    super(Task.class);
  }  

  protected EntityManager getEntityManager() {
    return em;
  }

  @Context
  private UriInfo uriInfo;

  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.TEXT_PLAIN)
  public Response createTask(Task task) {
    create(task,task.getId());
    return getResponse(task);
  }

  @PUT
  @Produces(MediaType.APPLICATION_XML)
  public Task updateTask(Task task) {
    return update(task, task.getId());
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.TEXT_XML)
  public List<Task> deleteTask(@PathParam("id") String id) {
    return delete(id);
  }
  

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Task> viewTask() {
    return viewAll();
  }

  private Response getResponse(Task task) {
    Response response;
    response = Response.created(uriInfo.getAbsolutePath()).build();

    return response;
  }
}
