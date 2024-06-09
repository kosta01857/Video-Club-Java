/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.centralserver.resources;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author kosta01856
 */
@Path("System2")
public class System2 {
    
    @Resource(lookup="myCF")
    ConnectionFactory cf;

    @Resource(lookup="S2Queue")
    Queue queue;
    
    @POST
    @Path("/createVideo/{title}/{length}/{username}")
    public Response createVideo(
    @PathParam("title") String title,
    @PathParam("length") int length,
    @PathParam("username") String username   
    )throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setIntProperty("length", length);
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 0);
        msg.setStringProperty("title", title);
        msg.setStringProperty("username", username);
        producer.send(queue, msg);
         JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if(response.equals("OK")) response = "Video created: " + title;
        return Response.ok(response).build();
    }
    
    
    @POST
    @Path("/createCategory/{category_name}")
    public Response createCategory(
    @PathParam("category_name") String category_name
    )throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 1);
        msg.setStringProperty("name", category_name);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if(response.equals("OK")) response = "Category created: " + category_name;
        return Response.ok(response).build();
    }
    
    @PUT
    @Path("/changeVideoTitle/{videoID}/{new_title}")
    public Response changeVideoTitle(
    @PathParam("videoID") int videoID,
    @PathParam("new_title") String new_title
    )throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 2);
        msg.setStringProperty("new_title", new_title);
        msg.setIntProperty("videoID", videoID);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if(response.equals("OK")) response = "Video (ID:"+videoID+") title changed to: " + new_title;
        return Response.ok(response).build();
    }
    
    @POST
    @Path("/addVideoToCategory/{videoID}/{category_name}")
    public Response addVideoToCategory(
    @PathParam("videoID") int videoID,
    @PathParam("category_name") String category_name
    )throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 3);
        msg.setStringProperty("category_name", category_name);
        msg.setIntProperty("videoID", videoID);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if(response.equals("OK")) response = "Video (ID:"+videoID+") added to: " + category_name;
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/AllCategories")
    public Response AllCategories()throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setIntProperty("number", 4);
        msg.setStringProperty("For", "system");
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue);
        msg = consumer.receive();
        String response = msg.getStringProperty("response");
        context.close();
        return Response
                .ok(response)
                .build();
    }
    
    
    @GET
    @Path("/AllVideos")
    public Response AllVideos() throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setIntProperty("number", 5);
        msg.setStringProperty("For", "system");
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue);
        msg = consumer.receive();
        String response = msg.getStringProperty("response");
        context.close();
        return Response
                .ok(response)
                .build();
    }
    
    
    @GET
    @Path("/AllCategoriesForVideo/{videoID}")
    public Response allCategoriesForVideo(
    @PathParam("videoID") int videoID
    )throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setIntProperty("number", 6);
        msg.setStringProperty("For", "system");
        msg.setIntProperty("videoID", videoID);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue);
        msg = consumer.receive();
        String response = msg.getStringProperty("response");
        context.close();
        return Response
                .ok(response)
                .build();
    }
    
    @DELETE
    @Path("/removeVideo/{videoID}/{username}")
    public Response removeVideo(
    @PathParam("videoID") int videoID,
    @PathParam("username") String username
    )throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 7);
        msg.setIntProperty("videoID", videoID);
        msg.setStringProperty("username",username);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if(response.equals("OK")) response = "Video (ID:"+videoID+") removed";
        return Response.ok(response).build();
    }
}
