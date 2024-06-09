/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system;

import javax.persistence.*;
import entities.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 *
 * @author kosta01856
 */
public class SubSystem1 extends MySystem {

    @Resource(lookup = "myCF")
    static ConnectionFactory cf;
    @Resource(lookup = "InterSystemCommunication")
    static Topic topic;
    @Resource(lookup = "S1Queue")
    static Queue queue;
    static JMSProducer producer;
    static JMSContext jmscontext;
    static JMSConsumer QueueConsumer;
    private static SubSystem1 instance = null;

    private SubSystem1() {
        super();
    }

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        jmscontext = SubSystem1.cf.createContext();
        producer = jmscontext.createProducer();
        QueueConsumer = jmscontext.createConsumer(queue, "For='system'");
        QueueConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message m) {
                int selector;
                try {
                    selector = m.getIntProperty("number");
                    switch (selector) {
                        case 1: {
                            String city_name = m.getStringProperty("city_name");
                            String res = SubSystem1.getInstance().createCity(city_name);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 2: {
                            String username = m.getStringProperty("username");
                            String name = m.getStringProperty("name");
                            String city_name = m.getStringProperty("city_name");
                            String email = m.getStringProperty("email");
                            int age = m.getIntProperty("age");
                            String sex = m.getStringProperty("sex");
                            String res = SubSystem1.getInstance().createUser(username, name, email, sex.charAt(0), age, city_name);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 3: {
                            String username = m.getStringProperty("username");
                            String city_name = m.getStringProperty("city_name");
                            String res = SubSystem1.getInstance().changeCity(username, city_name);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 4: {
                            String email = m.getStringProperty("email");
                            String username = m.getStringProperty("username");
                            String res = SubSystem1.getInstance().changeEmail(username, email);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 5: {
                            List<User> list = SubSystem1.getInstance().getAllUsers();
                            String users = "";
                            for (int i = 0; i < list.size(); i++) {
                                User u = list.get(i);
                                users += "username:"+u.getUsername()+" |email:"+ u.getEmail() +" |city: "
                                       +u.getCity().getName()+"|name:"+u.getName()+" |age: "+u.getAge()+" |sex:"+u.getSex() + "\n";
                            }
                            Message msg = jmscontext.createMessage();
                            System.out.println(users);
                            msg.setStringProperty("response", users);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 6: {
                            List<City> list = SubSystem1.getInstance().getAllCities();
                            String users = "";
                            for (int i = 0; i < list.size(); i++) {
                                users += list.get(i).getName() + "\n";
                            }
                            Message msg = jmscontext.createMessage();
                            System.out.println(users);
                            msg.setStringProperty("response", users);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(SubSystem1.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }
        });
        while (true);
    }

    public static SubSystem1 getInstance() {
        if (instance == null) {
            instance = new SubSystem1();
        }
        return instance;
    }

    public String createCity(String name) {
        try {
            initEM();
            startTransaction();
            City city = new City(name);
            save(city);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("CITY CREATION FAILED");
            return "CITY CREATION FAILED";
        } finally {
            rollback();
            closeEM();
        }

    } //1

    public String createUser(String username, String name, String email, char sex, int age, String city_name) {
        try {
            initEM();
            startTransaction();
            City city = em.find(City.class, city_name);
            if (city == null) {
                city = new City(city_name);
            }
            User user = new User();
            user.setUsername(username);
            user.setAge(age);
            user.setCity(city);
            user.setName(name);
            user.setEmail(email);
            user.setSex(sex);
            save(user);
            commitTransaction();
            Message msg = jmscontext.createMessage();
            msg.setStringProperty("user", username);
            msg.setStringProperty("type", "user");
            producer.send(topic, msg);
            return "OK";
        } catch (Exception e) {
            System.out.println("USER CREATION FAILED");
            return "USER CREATION FAILED";
        } finally {
            rollback();
            closeEM();
        }

    } //2

    public String changeCity(String username, String city_name) {
        try {
            initEM();
            startTransaction();
            City city = em.find(City.class, city_name);
            if (city == null) {
                System.out.println("CITY DOES NOT EXIST");
                return "CITY DOES NOT EXIST";
            }
            User user = em.find(User.class, username);
            if (user == null) {
                System.out.println("USER DOES NOT EXIST");
                return "USER DOES NOT EXIST";
            }
            user.setCity(city);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("CHANGE USER'S CITY FAILED");
            return "CHANGE USER'S CITY FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //3

    public String changeEmail(String username, String email) {
        try {
            initEM();
            startTransaction();
            User user = em.find(User.class, username);
            if (user == null) {
                System.out.println("USER DOES NOT EXIST");
                return "USER DOES NOT EXIST";
            }
            user.setEmail(email);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println(email);
            System.out.println("CHANGE USER'S EMAIL FAILED");
            return "CHANGE USER'S EMAIL FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //4

    public List<User> getAllUsers() {
        initEM();
        Query getAllUsers = em.createNamedQuery("User.findAll");
        List<User> list = getAllUsers.getResultList();
        closeEM();
        return list;
    } //5

    public List<City> getAllCities() {
        initEM();
        Query getAllUsers = em.createNamedQuery("City.findAll");
        List<City> list = getAllUsers.getResultList();
        closeEM();
        System.out.println(list.toString());
        return list;
    } //6

}
