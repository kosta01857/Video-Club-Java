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
@Path("System1")
public class System1 {

    @Resource(lookup="myCF")
    ConnectionFactory cf;

    @Resource(lookup="S1Queue")
    Queue queue;

    @POST
    @Path("/createUser/{username}/{name}/{sex}/{email}/{age}/{city_name}")
    public Response createUser(
        @PathParam("username") String username,
        @PathParam("name") String name,
        @PathParam("sex") String sex,
        @PathParam("email") String email,
        @PathParam("age") int age,
        @PathParam("city_name") String city_name
    ) throws JMSException
    {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setIntProperty("age", age);
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 2);
        msg.setStringProperty("name", name);
        msg.setStringProperty("username", username);
        msg.setStringProperty("email", email);
        msg.setStringProperty("sex", sex);
        msg.setStringProperty("city_name", city_name);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if(response.equals("OK")) response = "User created: " + username;
        return Response.ok(response).build();
    }
    @GET
    public Response AllUsers() throws JMSException{
         JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        Message msg = context.createMessage();
        msg.setIntProperty("number", 5);
        msg.setStringProperty("For", "system");
        //while(consumer.receiveNoWait()!=null);
        producer.send(queue, msg);
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        return Response
                .ok(response)
                .build();
    }
    @GET
    @Path("/Cities")
    public Response allCities() throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setIntProperty("number", 6);
        msg.setStringProperty("For", "system");
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        return Response
                .ok(response)
                .build();
    }
    @POST
    @Path("/createCity/{city_name}")
    public Response createCity(
        @PathParam("city_name") String city_name
    ) throws JMSException
    {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 1);
        msg.setStringProperty("city_name", city_name);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if(response.equals("OK")) response = "City created: " + city_name;
        return Response.ok(response).build();
    }
    @PUT
    @Path("/changeCity/{city_name}/{username}")
    public Response changeCity(@PathParam("city_name") String city_name,
            @PathParam("username")String username
            ) throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 3);
        msg.setStringProperty("city_name", city_name);
        msg.setStringProperty("username", username);
        
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if(response.equals("OK")) response = "City changed for User: "+ username +"| New City is: " + city_name;
        return Response.ok(response).build();
    }
    @PUT
    @Path("/changeEmail/{email}/{username}")
    public Response changeEmail(@PathParam("email") String email,
            @PathParam("username")String username
            ) throws JMSException{
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        Message msg = context.createMessage();
        msg.setStringProperty("For", "system");
        msg.setIntProperty("number", 4);
        msg.setStringProperty("email", email);
        msg.setStringProperty("username", username);
        System.out.print(email);
        producer.send(queue, msg);
        JMSConsumer consumer = context.createConsumer(queue,"For='server'");
        msg = consumer.receive();
        msg.acknowledge();
        String response = msg.getStringProperty("response");
        context.close();
        if(response.equals("OK")) response = "Email changed for User: "+"username | New email is: " + email;
        return Response.ok(response).build();
 
    }
}
