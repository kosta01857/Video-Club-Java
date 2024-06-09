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
@Path("System3")
public class System3 {

    @Resource(lookup = "myCF")
    ConnectionFactory cf;

    @Resource(lookup = "S3Queue")
    Queue queue;

    @POST
    @Path("/createPackage/{name}/{price}")
    public Response createPackage(
            @PathParam("name") String name,
            @PathParam("price") int price
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 1);
        msg.setIntProperty("price", price);
        msg.setStringProperty("name", name);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue, "For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if (response.equals("OK")) {
            response = "Package created: " + name + "price: " + price;
        }
        return Response.ok(response).build();
    }

    @POST
    @Path("/createRating/{videoID}/{username}/{grade}")
    public Response createRating(
            @PathParam("videoID") int videoID,
            @PathParam("username") String username,
            @PathParam("grade") int grade
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 7);
        msg.setIntProperty("grade", grade);
        msg.setIntProperty("videoID", videoID);
        msg.setStringProperty("username", username);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue, "For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if (response.equals("OK")) {
            response = "Rating created for Video " + videoID + "from User: " + username + " grade: " + grade;
        }
        return Response.ok(response).build();
    }

    @POST
    @Path("/createSub/{pID}/{username}")
    public Response createSub(
            @PathParam("pID") int pID,
            @PathParam("username") String username
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 3);
        msg.setIntProperty("pID", pID);
        msg.setStringProperty("username", username);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue, "For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if (response.equals("OK")) {
            response = "Subscribtion created for user " + username + "| package: " + pID;
        }
        return Response.ok(response).build();
    }

    @POST
    @Path("/createView/{videoID}/{username}/{starttime}/{watchtime}")
    public Response createView(
            @PathParam("videoID") int videoID,
            @PathParam("username") String username,
            @PathParam("starttime") int starttime,
            @PathParam("watchtime") int watchtime
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 6);
        msg.setIntProperty("videoID", videoID);
        msg.setStringProperty("username", username);
        msg.setIntProperty("startTime", starttime);
        msg.setIntProperty("watchTime", watchtime);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue, "For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if (response.equals("OK")) {
            response = "View created for Video " + videoID + "from User: " + username;
        }
        return Response.ok(response).build();
    }

    @GET
    @Path("/AllPackages")
    public Response allPackages() throws JMSException {
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
    @Path("/AllSubsForUser/{username}")
    public Response allCategoriesForVideo(
            @PathParam("username") String username
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setIntProperty("number", 4);
        msg.setStringProperty("For", "system");
        msg.setStringProperty("username", username);
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
    @Path("/AllViewsForVideo/{videoID}")
    public Response allViewsForVideo(
            @PathParam("videoID") int videoID
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setIntProperty("number", 10);
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

    @GET
    @Path("/AllRatingsForVideo/{videoID}")
    public Response allRatingsForVideo(
            @PathParam("videoID") int videoID
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setIntProperty("number", 11);
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

    @PUT
    @Path("/changePrice/{pID}/{new_price}")
    public Response changePackagePrice(
            @PathParam("pID") int pID,
            @PathParam("new_price") int new_price
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 2);
        msg.setIntProperty("price", new_price);
        msg.setIntProperty("pID", pID);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue, "For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if (response.equals("OK")) {
            response = "Package (ID:" + pID + ") price changed to: " + new_price;
        }
        return Response.ok(response).build();
    }

    @PUT
    @Path("/changeRating/{videoID}/{username}/{grade}")
    public Response changePackagePrice(
            @PathParam("videoID") int videoID,
            @PathParam("username") String username,
            @PathParam("grade") int grade
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 8);
        msg.setStringProperty("username", username);
        msg.setIntProperty("new_grade", grade);
        msg.setIntProperty("videoID", videoID);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue, "For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if (response.equals("OK")) {
            response = "Rating for Video ID:" + videoID + " from user:" + username + " changed to: " + grade;
        }
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/removeRating/{videoID}/{username}")
    public Response removeVideo(
            @PathParam("videoID") int videoID,
            @PathParam("username") String username
    ) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 9);
        msg.setIntProperty("videoID", videoID);
        msg.setStringProperty("username", username);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue, "For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if (response.equals("OK")) {
            response = "Rating for Video ID:" + videoID + "from User: " + username + " removed";
        }
        return Response.ok(response).build();
    }
}
