/**
 * @author Copyright (c) 2010 by Oracle. All Rights Reserved
 *  
 */
package com.scube.srvcs.web.jaxrs.entity;

import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

/**
 * Entity Class
 */
@Entity
@Table(name = "JavaEE6_Task")
@XmlRootElement
public class Task implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private String id;

  private String subject;

  private String status;
  
  public Task() {
  }

  public Task(String id, String subject, String status) {
    this.id = id;
    this.subject = subject;
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String toString() {
    String task = "Task [id:" + this.id + ", subject:" + this.subject
        + ", status:" + this.status + "]";
    return task;
  }

  // TODO should be recursive for nest object properties
  public String toXML() throws Exception {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();

    String className = this.getClass().getName();
    className = className.substring(className.lastIndexOf(".") + 1)
        .toLowerCase();
    Element root = doc.createElement(className);

    Field[] fields = this.getClass().getDeclaredFields();
    try {
      for (Field field : fields) {
        String fieldName = field.getName();
        field.setAccessible(true);
        if (field.getType().getSimpleName().equalsIgnoreCase("String")) {
          Element item = doc.createElement(fieldName);
          item.appendChild(doc.createTextNode((String) field.get(this)));
          root.appendChild(item);
        }
      }
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    doc.appendChild(root);

    return document2String(doc, "UTF-8", true);
  }

  private String document2String(Document doc, String encode,
      boolean omitXMLDeclaration) throws Exception {
    OutputFormat outputFormat = new OutputFormat("XML", encode, true);
    outputFormat.setOmitXMLDeclaration(omitXMLDeclaration);
    outputFormat.setIndenting(false);
    StringWriter stringWriter = new StringWriter();
    XMLSerializer xmlSerializer = new XMLSerializer(stringWriter, outputFormat);
    xmlSerializer.asDOMSerializer();
    xmlSerializer.serialize(doc);
    return stringWriter.toString();
  }
}
