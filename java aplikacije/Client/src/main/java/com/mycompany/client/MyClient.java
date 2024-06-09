/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author kosta01856
 */
@ApplicationPath("/console")
public class MyClient {

/**
 *
 * @author kosta01856
 */

    static String System2 = "Commands:"+"\n"+ "0-createVideo"+"\n"+"1-createCategory"+"\n"+"2-changeVideoTitle"
            +"\n"+"3-addVideoToCategory"+"\n"+"4-AllCategories"+"\n"+"5-AllVideos"+"\n"+"6-AllCategoriesForVideo\n7-removeVideo\n";
    static String System3 = "Commands:\n1-createPackage\n2-changePackagePrice\n3-createSubscribtion\n4-getSubsForUser\n"
            + "5-AllPackages\n6-createView\n7-createRating\n8-changeRating\n9-removeRating\n10-AllViewsForVideo\n11-AllRatingsForVideo";
    static String System1 = "Commands:"+"\n"+ "1-createCity"+"\n"+"2-createUser"+"\n"+"3-changeCity"+"\n"+"4-changeEmail"+"\n"+
            "5-AllUsers"+"\n"+"6-AllCities"+"\n";
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static void post(String CentralServerUrl){
        String responseData = client
                    .target(CentralServerUrl)
                    .request().post(null,String.class);
        System.out.print(responseData);
    }
    static void get(String CentralServerUrl){
        String responseData = client
                    .target(CentralServerUrl)
                    .request().get(String.class);
        System.out.print(responseData);
    }
    static void put(String CentralServerUrl){
        Entity<String> entity = Entity.entity("",MediaType.APPLICATION_JSON);
        String responseData = client
                    .target(CentralServerUrl)
                    .request().put(entity, String.class);
        System.out.print(responseData);
                    
    }
    static void delete(String CentralServerUrl){
         String responseData = client
                    .target(CentralServerUrl)
                    .request().delete(String.class);
         System.out.print(responseData);
    }
    static Client client = ClientBuilder.newClient();
    static String getUserData() throws IOException{
        String username;
        String email;
        int age;
        String sex;
        String city;
        String name;
        System.out.print("username: ");
        username = reader.readLine();
        System.out.print("name: ");
        name = reader.readLine();
        System.out.print("sex: ");
        sex = reader.readLine();
        System.out.print("email: ");
        email = reader.readLine();
        System.out.print("age: ");
        age = Integer.parseInt(reader.readLine());
        System.out.print("city: ");
        city = reader.readLine();
        return "http://localhost:8080/CentralServer/resources/System1/createUser/"+
                    username+"/"+name+"/"+sex+"/"+email+"/"+age+"/"+city;
    }
    static String getVideoData()throws IOException{
        String title;
        String username;
        int length;
        System.out.print("title:");
        title = reader.readLine();
        System.out.print("length:");
        length = Integer.parseInt(reader.readLine());
        System.out.print("username:");
        username = reader.readLine();
        return "http://localhost:8080/CentralServer/resources/System2/createVideo/"+title+"/"+length+"/"+username;
    }
    static void System1() throws IOException{
        while(true){
            String str = reader.readLine();
            if(str.equals("help")) System.out.println(System1);
            else if(str.equals("back")) return;
            else if(str.equals("\n") || str.equals("")) {}
            else switch(Integer.parseInt(str)){
                case 1:{
                    System.out.print("City name: ");
                    String city_name = reader.readLine();
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System1/createCity/"+city_name;
                    post(CentralServerUrl);
                    break;
                }
                case 2:{
                    String CentralServerUrl = getUserData();
                    post(CentralServerUrl);
                    break;
                }
                case 3:{
                    System.out.print("username: ");
                    String username = reader.readLine();
                    System.out.print("City name: ");
                    String city_name = reader.readLine();
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System1/changeCity/"+city_name+"/"+username;
                    put(CentralServerUrl);
                    break;
                }
                case 4:{
                    System.out.print("username: ");
                    String username = reader.readLine();
                     System.out.print("email: ");
                    String email = reader.readLine();
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System1/changeEmail/"+email+"/"+username;
                    put(CentralServerUrl);
                    break;
                }
                case 5:{
                    String CentralServerUrl ="http://localhost:8080/CentralServer/resources/System1";
                    get(CentralServerUrl);
                    break;
                }
                case 6:{
                    String CentralServerUrl ="http://localhost:8080/CentralServer/resources/System1/Cities";
                    get(CentralServerUrl);
                    break;
                }
                default:{
                    System.out.println("");
                }
            }
        }
    }
    static void System2()throws IOException{
        while(true){
            String str = reader.readLine();
            if(str.equals("help")) System.out.println(System2);
            else if(str.equals("back")) return;
            else if(str.equals("\n") || str.equals("")) {}
            else switch(Integer.parseInt(str)){
                case 0:{
                    String CentralServerUrl = getVideoData();
                    post(CentralServerUrl);
                    break;
                }
                case 1:{
                    System.out.print("Category name: ");
                    String name = reader.readLine();
                    post("http://localhost:8080/CentralServer/resources/System2/createCategory/"+name);
                    break;
                }
                case 2:{
                    System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    System.out.print("new title:");
                    String new_title = reader.readLine();
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System2/changeVideoTitle/"+videoID+"/"+new_title;
                    put(CentralServerUrl);
                    break;
                }
                case 3:{
                    System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    System.out.print("category:");
                    String category = reader.readLine();
                     String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System2/addVideoToCategory/"+videoID+"/"+category;
                     post(CentralServerUrl);
                    break;
                }
                case 4:{
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System2/AllCategories";
                    get(CentralServerUrl);
                    break;
                }
                case 5:{
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System2/AllVideos";
                    get(CentralServerUrl);
                    break;
                }
                case 6:{
                    System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System2/AllCategoriesForVideo/"+videoID;
                    get(CentralServerUrl);
                    break;
                }
                case 7:{
                    System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    System.out.print("username:");
                    String username = reader.readLine();
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System2/removeVideo/"+videoID+"/"+username;
                    delete(CentralServerUrl);
                    break;
                }
                default:{
                    System.out.println("");
                }
            }
        }
    }
    static void System3()throws IOException{
        while(true){
            String str = reader.readLine();
            if(str.equals("help")) System.out.println(System3);
            else if(str.equals("back")) return;
            else if(str.equals("\n") || str.equals("")) {}
            else switch(Integer.parseInt(str)){
                case 1:{
                    System.out.print("package name:");
                    String name = reader.readLine();
                    System.out.print("price:");
                    int price = Integer.parseInt(reader.readLine());
                    post("http://localhost:8080/CentralServer/resources/System3/createPackage/"+ name + "/" + price);
                    break;
                }
                case 2:{
                    System.out.print("package id:");
                    int pID = Integer.parseInt(reader.readLine());
                    System.out.print("new price:");
                    int price = Integer.parseInt(reader.readLine());
                    put("http://localhost:8080/CentralServer/resources/System3/changePrice/"+ pID + "/" + price);
                    break;
                }
                case 3:{
                    System.out.print("username:");
                    String username = reader.readLine();
                    System.out.print("package ID:");
                    int pID = Integer.parseInt(reader.readLine());
                    post("http://localhost:8080/CentralServer/resources/System3/createSub/"+ pID + "/" + username);
                    break;
                }
                case 4:{
                    System.out.print("username:");
                    String username = reader.readLine();
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System3/AllSubsForUser/"+username;
                    get(CentralServerUrl);
                    break;
                }
                case 5:{
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System3/AllPackages";
                    get(CentralServerUrl);
                    break;
                }
                case 6:{
                    System.out.print("username:");
                    String username = reader.readLine();
                    System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    System.out.print("startTime:");
                    int startTime = Integer.parseInt(reader.readLine());
                    System.out.print("watchTime:");
                    int watchTime = Integer.parseInt(reader.readLine());
                    post("http://localhost:8080/CentralServer/resources/System3/createView/"+ videoID + "/" + username+"/"+startTime+"/"+watchTime);
                    break;
                }
                case 7:{
                    System.out.print("username:");
                    String username = reader.readLine();
                    System.out.print("grade:");
                    int grade = Integer.parseInt(reader.readLine());
                    System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    post("http://localhost:8080/CentralServer/resources/System3/createRating/"+ videoID + "/" + username + "/"+ grade);
                    break;
                }
                case 8:{
                    System.out.print("username:");
                    String username = reader.readLine();
                    System.out.print("grade:");
                    int grade = Integer.parseInt(reader.readLine());
                    System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    put("http://localhost:8080/CentralServer/resources/System3/changeRating/"+ videoID + "/"+ username + "/" + grade);
                    break;
                }
                case 9:{
                    System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    System.out.print("username:");
                    String username = reader.readLine();
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System3/removeRating/"+videoID+"/"+username;
                    delete(CentralServerUrl);
                    break;
                }
                case 10:{
                    System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System3/AllViewsForVideo/"+videoID;
                    get(CentralServerUrl);
                    break;
                }
                case 11:{
                     System.out.print("videoID:");
                    int videoID = Integer.parseInt(reader.readLine());
                    String CentralServerUrl = "http://localhost:8080/CentralServer/resources/System3/AllRatingsForVideo/"+videoID;
                    get(CentralServerUrl);
                    break;
                }
                default:{
                    System.out.println("");
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        System.out.println("Type help for options");
        while(true){
             String str = reader.readLine();
             if(str.equals("help")) System.out.println("1-System1\n2-System2\n3-System3");
             else switch(Integer.parseInt(str)){
                 case 1:{
                     System1();
                     break;
                 }
                 case 2:{
                     System2();
                     break;
                 }
                 case 3:{
                     System3();
                     break;
                 }
             }
        }
    }
}
