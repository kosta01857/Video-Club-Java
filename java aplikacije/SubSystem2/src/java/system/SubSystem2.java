/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system;

import entities.*;
import java.util.Date;
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
import javax.persistence.Query;

/**
 *
 * @author kosta01856
 */
public class SubSystem2 extends MySystem {

    @Resource(lookup = "myCF")
    static ConnectionFactory cf;
    @Resource(lookup = "InterSystemCommunication")
    static Topic topic;
    @Resource(lookup = "S2Queue")
    static Queue queue;
    static JMSProducer producer;
    static JMSContext jmscontext;
    static JMSConsumer TopicConsumer;
    static JMSConsumer QueueConsumer;

    public static void main(String[] args) {
        jmscontext = SubSystem2.cf.createContext();
        producer = jmscontext.createProducer();
        TopicConsumer = jmscontext.createConsumer(topic, "type='user'");
        TopicConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message m) {
                try {
                    SubSystem2.getInstance().createUser(m.getStringProperty("user"));
                } catch (JMSException ex) {
                    Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        QueueConsumer = jmscontext.createConsumer(queue, "For='system'");
        QueueConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message m) {
                int selector;
                try {
                    selector = m.getIntProperty("number");
                    switch (selector) {
                        case 0: {
                            int length = m.getIntProperty("length");
                            String title = m.getStringProperty("title");
                            String username = m.getStringProperty("username");
                            String res = SubSystem2.getInstance().createVideo(title, length, username);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 1: {
                            String name = m.getStringProperty("name");
                            String res = SubSystem2.getInstance().createCategory(name);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 2: {
                            String new_title = m.getStringProperty("new_title");
                            int videoID = m.getIntProperty("videoID");
                            String res = SubSystem2.getInstance().changeVideoTitle(videoID, new_title);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 3: {
                            int videoID = m.getIntProperty("videoID");
                            String category_name = m.getStringProperty("category_name");
                            String res = SubSystem2.getInstance().addVideoToCategory(videoID, category_name);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 4: {
                            List<Category> list = SubSystem2.getInstance().getAllCategories();
                            String cateogry = "";
                            for (int i = 0; i < list.size(); i++) {
                                cateogry += list.get(i).getName() + "\n";
                            }
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", cateogry);
                             msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 5: {
                            List<Video> list = SubSystem2.getInstance().getAllVideos();
                            String videos = "";
                            for (int i = 0; i < list.size(); i++) {
                                videos += list.get(i).getTitle() + " ID:" + list.get(i).getVideoID() + "\n";
                            }
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", videos);
                             msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 6: {
                            int videoID = m.getIntProperty("videoID");
                            List<VideoCategory> list = SubSystem2.getInstance().getCategoriesForVideo(videoID);
                            if (list == null) {
                                Message msg = jmscontext.createMessage();
                                msg.setStringProperty("response", "VIDEO DOES NOT EXIST");
                                producer.send(queue, msg);
                            } else {
                                System.out.print(list);
                                String users = "";
                                for (int i = 0; i < list.size(); i++) {
                                    users += list.get(i).getCategory1().getName() + "\n";
                                }
                                Message msg = jmscontext.createMessage();
                                msg.setStringProperty("response", users);
                                 msg.setStringProperty("For", "server");
                                producer.send(queue, msg);
                            }
                            break;
                        }
                        case 7: {
                            int videoID = m.getIntProperty("videoID");
                            String username = m.getStringProperty("username");
                            String res = SubSystem2.getInstance().removeVideo(username, videoID);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                        }
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }
        });
        while (true);
    }
    private static SubSystem2 instance = null;

    private SubSystem2() {
        super();
    }

    public static SubSystem2 getInstance() {
        if (instance == null) {
            instance = new SubSystem2();
        }
        return instance;
    }

    public String createUser(String username) {
        try {
            initEM();
            startTransaction();
            User user = new User();
            user.setUsername(username);
            save(user);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("USER CREATION FAILED");
            return "USER CREATION FAILED";
        } finally {
            rollback();
            closeEM();
        }
    }

    public String createVideo(String title, double length, String username) {
        initEM();
        try {
            startTransaction();
            User user = em.find(User.class, username);
            if (user == null) {
                System.out.println("USER DOES NOT EXIST");
                return "USER DOES NOT EXIST";
            }
            Video video = new Video();
            video.setTitle(title);
            video.setUser(user);
            video.setLength(length);
            video.setUploadDate(new Date());
            save(video);
            commitTransaction();
            Message msg = jmscontext.createMessage();
            msg.setIntProperty("video", video.getVideoID());
            msg.setStringProperty("type", "video");
            msg.setStringProperty("username", video.getUser().getUsername());
            producer.send(topic, msg);
            return "OK";
        } catch (JMSException e) {
            System.out.println("JMS FAILED");
            return "JMS FAILED";
        } catch (Exception e) {
            System.out.println("VIDEO CREATION FAILED");
            return "VIDEO CREATION FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //0

    public String createCategory(String name) {
        initEM();
        try {
            startTransaction();
            Category c = new Category(name);
            save(c);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("CATEGORY CREATION FAILED");
            return "CATEGORY CREATION FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //1

    public String changeVideoTitle(int videoID, String new_title) {
        initEM();
        try {
            startTransaction();
            Video video = em.find(Video.class, videoID);
            if (video == null) {
                System.out.println("VIDEO DOES NOT EXIST");
                return "VIDEO DOES NOT EXIST";
            }
            video.setTitle(new_title);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("CHANGE VIDEO TITLE FAILED");
            return "CHANGE VIDEO TITLE FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //2

    public String addVideoToCategory(int videoID, String category_name) {
        initEM();
        try {
            startTransaction();
            Category c = em.find(Category.class, category_name);
            if (c == null) {
                System.out.println("CATEGORY DOES NOT EXIST");
                return "CATEGORY DOES NOT EXIST";
            }
            Video video = em.find(Video.class, videoID);
            if (video == null) {
                System.out.println("VIDEO DOES NOT EXIST");
                return "VIDEO DOES NOT EXIST";
            }
            VideoCategory vc = new VideoCategory(new VideoCategoryPK(category_name, videoID));
            vc.setCategory1(c);
            vc.setVideo1(video);
            save(vc);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("ADD CATEGORY TO VIDEO FAILED");
            return "ADD CATEGORY TO VIDEO FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //3

    public List<Category> getAllCategories() {
        initEM();
        Query query = em.createNamedQuery("Category.findAll");
        List<Category> list = query.getResultList();
        closeEM();
        System.out.println(list.toString());
        return list;
    } //4

    public List<Video> getAllVideos() {
        initEM();
        Query query = em.createNamedQuery("Video.findAll");
        List<Video> list = query.getResultList();
        closeEM();
        System.out.println(list.toString());
        return list;
    } //5

    public List<VideoCategory> getCategoriesForVideo(int videoID) {
        initEM();
        Video video = em.find(Video.class, videoID);
        if (video == null) {
            System.out.println("VIDEO DOES NOT EXIST");
            closeEM();
            return null;
        }
        Query q = em.createNamedQuery("VideoCategory.findByVideo");
        q.setParameter("video", videoID);
        List<VideoCategory> list = q.getResultList();
        closeEM();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getCategory1() + " " + list.get(i).getVideo1());
        }
        return list;

    } //6

    public String removeVideo(String username, int videoID) {
        initEM();
        try {
            startTransaction();
            User user = em.find(User.class, username);
            if (user == null) {
                System.out.println("USER DOES NOT EXIST");
                return "USER DOES NOT EXIST";
            }
            Video video = em.find(Video.class, videoID);
            if (video == null) {
                System.out.println("VIDEO DOES NOT EXIST");
                return "VIDEO DOES NOT EXIST";
            }
            if (video.getUser().equals(user)) {
                em.remove(video);
                em.flush();
                commitTransaction();
                Message msg = jmscontext.createMessage();
                msg.setIntProperty("video", video.getVideoID());
                msg.setStringProperty("type", "remove");
                msg.setStringProperty("user", username);
                producer.send(topic, msg);
                return "OK";
            }
            System.out.println("YOU CAN ONLY DELETE YOUR OWN VIDEOS");
            return "YOU CAN ONLY DELETE YOUR OWN VIDEOS";
        } catch (Exception e) {
            System.out.println("VIDEO DELETION FAILED");
            return "VIDEO DELETION FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //7
}
