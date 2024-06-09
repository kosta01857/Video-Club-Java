/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subsystem3;

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
public class SubSystem3 extends MySystem {

    @Resource(lookup = "myCF")
    static ConnectionFactory cf;
    @Resource(lookup = "InterSystemCommunication")
    static Topic topic;
    @Resource(lookup = "S3Queue")
    static Queue queue;
    static JMSContext jmscontext;
    static JMSConsumer TopicConsumer;
    static JMSProducer producer;
    static JMSConsumer QueueConsumer;

    public static void main(String[] args) {

        jmscontext = SubSystem3.cf.createContext();
        TopicConsumer = jmscontext.createConsumer(topic);
        producer = jmscontext.createProducer();
        TopicConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message m) {
                try {
                    if (m.getStringProperty("type").equals("video")) {
                        SubSystem3.getInstance().createVideo(m.getIntProperty("video"), m.getStringProperty("username"));
                    } else if (m.getStringProperty("type").equals("user")) {
                        SubSystem3.getInstance().createUser(m.getStringProperty("user"));
                    } else {
                        SubSystem3.getInstance().removeVideo(m.getStringProperty("user"), m.getIntProperty("video"));
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(SubSystem3.class.getName()).log(Level.SEVERE, null, ex);
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
                        case 1: {
                            String name = m.getStringProperty("name");
                            int price = m.getIntProperty("price");
                            String res = SubSystem3.getInstance().createPackage(name, price);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 2: {
                            int pID = m.getIntProperty("pID");
                            int price = m.getIntProperty("price");
                            String res = SubSystem3.getInstance().changePackagePrice(pID, price);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 3: {
                            String username = m.getStringProperty("username");
                            int pID = m.getIntProperty("pID");
                            String res = SubSystem3.getInstance().createSubscribtion(username, pID);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 4: {
                            Message msg = jmscontext.createMessage();
                            String subs = "";
                            String username = m.getStringProperty("username");
                            List<Subscribtion> list = SubSystem3.getInstance().getSubscribtionForUser(username);
                            if (list == null) {
                                subs = "FAIL";
                                msg.setStringProperty("response", subs);
                                producer.send(queue, msg);
                            } else {
                                System.out.print(list);
                                for (int i = 0; i < list.size(); i++) {
                                    subs += list.get(i).getDate().toString() + " | " + list.get(i).getPackage11().getName() + " | "
                                            + list.get(i).getPrice() + "\n";
                                }
                                msg.setStringProperty("response", subs);
                                producer.send(queue, msg);
                            }
                            break;
                        }
                        case 5: {
                            List<entities.Package> list = SubSystem3.getInstance().getAllPackages();
                            System.out.print(list);
                            String packages = "";
                            for (int i = 0; i < list.size(); i++) {
                                packages += list.get(i).getName() + "\n";
                            }
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", packages);
                            producer.send(queue, msg);
                            break;
                        }
                        case 6: {
                            String username = m.getStringProperty("username");
                            int videoID = m.getIntProperty("videoID");
                            int watchTime = m.getIntProperty("watchTime");
                            int startTime = m.getIntProperty("startTime");
                            String res = SubSystem3.getInstance().createView(username, videoID, startTime, watchTime);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 7: {
                            String username = m.getStringProperty("username");
                            int videoID = m.getIntProperty("videoID");
                            int grade = m.getIntProperty("grade");
                            String res = SubSystem3.getInstance().createRating(username, videoID, grade);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 8: {
                            System.out.println("CASE 8");
                            String username = m.getStringProperty("username");
                            int videoID = m.getIntProperty("videoID");
                            int new_grade = m.getIntProperty("new_grade");
                            String res = SubSystem3.getInstance().changeRating(username, videoID, new_grade);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 9: {
                            String username = m.getStringProperty("username");
                            int videoID = m.getIntProperty("videoID");
                            String res = SubSystem3.getInstance().removeRating(username, videoID);
                            Message msg = jmscontext.createMessage();
                            msg.setStringProperty("response", res);
                            msg.setStringProperty("For", "server");
                            producer.send(queue, msg);
                            break;
                        }
                        case 10: {
                            int videoID = m.getIntProperty("videoID");
                            List<View> list = SubSystem3.getInstance().getAllViewsForVideo(videoID);
                            System.out.print(list);
                            String views = "";
                            Message msg = jmscontext.createMessage();
                            if (list == null) {
                                views = "FAIL";
                                msg.setStringProperty("response", views);
                                producer.send(queue, msg);
                            } else {
                                for (int i = 0; i < list.size(); i++) {
                                    View v = list.get(i);
                                    views += v.getUser1().getUsername() + " |watchtime:" + v.getWatchTime()
                                            + " |startTime:" + v.getStartTime() + "\n";
                                }
                                msg.setStringProperty("response", views);
                                producer.send(queue, msg);
                            }
                            break;
                        }
                        case 11: {
                            int videoID = m.getIntProperty("videoID");
                            List<Rating> list = SubSystem3.getInstance().getAllRatingsForVideo(videoID);
                            System.out.print(list);
                            String ratings = "";
                            Message msg = jmscontext.createMessage();
                            if (list == null) {
                                ratings = "FAIL";
                                msg.setStringProperty("response", ratings);
                                producer.send(queue, msg);
                            } else {
                                for (int i = 0; i < list.size(); i++) {
                                    ratings += list.get(i).getUser1().getUsername() + " grade:" + list.get(i).getGrade() + "\n";
                                }

                                msg.setStringProperty("response", ratings);
                                producer.send(queue, msg);
                            }
                            break;
                        }

                    }
                } catch (JMSException ex) {
                    Logger.getLogger(SubSystem3.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }
        });
        while (true);
    }
    private static SubSystem3 instance = null;

    private SubSystem3() {
        super();
    }

    public static SubSystem3 getInstance() {
        if (instance == null) {
            instance = new SubSystem3();
        }
        return instance;
    }

    public String createVideo(int videoID, String username) {
        initEM();
        try {
            startTransaction();
            Video video = new Video();
            video.setVideoID(videoID);
            User user = em.find(User.class, username);
            video.setUser(user);
            save(video);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("VIDEO CREATION FAILED");
            return "VIDEO CREATION FAILED";
        } finally {
            rollback();
            closeEM();
        }
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

    public String createPackage(String name, int price) {
        initEM();
        try {
            startTransaction();
            entities.Package p = new entities.Package();
            p.setName(name);
            p.setPrice(price);
            save(p);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("PACKAGE CREATION FAILED");
            return "PACKAGE CREATION FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //1

    public String changePackagePrice(int pID, int new_price) {
        initEM();
        try {
            startTransaction();
            entities.Package p = em.find(entities.Package.class, pID);
            if (p == null) {
                System.out.println("PACKAGE DOESNT EXIST");
                return "PACKAGE DOESNT EXIST";
            }
            p.setPrice(new_price);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("PACKAGE PRICE CHANGE FAILED");
            return "PACKAGE PRICE CHANGE FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //2

    public String createSubscribtion(String username, int pID) {
        initEM();
        try {
            startTransaction();
            entities.Package p = em.find(entities.Package.class, pID);
            if (p == null) {
                System.out.println("PACKAGE DOESNT EXIST");
                return "PACKAGE DOESNT EXIST";
            }
            User user = em.find(User.class, username);
            if (user == null) {
                System.out.println("USER DOES NOT EXIST");
                return "USER DOES NOT EXIST";
            }
            Subscribtion s = new Subscribtion(new SubscribtionPK(pID, username));
            s.setPrice(p.getPrice());
            s.setUser1(user);
            s.setPackage11(p);
            s.setDate(new Date());
            save(s);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("SUBSCRIBTION CREATION FAILED");
            return "SUBSCRIBTION CREATION FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //3

    public List<Subscribtion> getSubscribtionForUser(String username) {
        initEM();
        startTransaction();
        User user = em.find(User.class, username);
        if (user == null) {
            System.out.println("USER DOES NOT EXIST");
            closeEM();
            return null;
        }
        Query q = em.createNamedQuery("Subscribtion.findByUser");
        q.setParameter("user", username);
        List<Subscribtion> list = q.getResultList();
        closeEM();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getPackage11().getName() + " PRICE:" + list.get(i).getPrice() + " DATE:" + list.get(i).getDate());
        }

        return list;
    } //4

    public List<entities.Package> getAllPackages() {
        initEM();
        Query query = em.createNamedQuery("Package.findAll");
        List<entities.Package> list = query.getResultList();
        closeEM();
        System.out.println(list.toString());
        return list;

    } //5

    public String createView(String username, int videoID, int start, int watch) {
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
            View view = new View(new ViewPK(videoID, username));
            view.setUser1(user);
            view.setVideo1(video);
            view.setStartTime(start);
            view.setWatchTime(watch);
            save(view);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("VIEW CREATION FAILED");
            return "VIEW CREATION FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //6

    public String createRating(String username, int videoID, int grade) {
        if (grade < 1 || grade > 5) {
            System.out.println("BAD GRADE");
            return "BAD GRADE";
        }
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
            Rating rating = new Rating(new RatingPK(videoID, username));
            rating.setDate(new Date());
            rating.setGrade(grade);
            rating.setUser1(user);
            rating.setVideo1(video);
            save(rating);
            commitTransaction();
            return "OK";
        } catch (Exception e) {
            System.out.println("CREATE RATING FAILED");
            return "CREATE RATING FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //7

    public String changeRating(String username, int videoID, int new_grade) {
        System.out.println("ENTERED");
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
            Query query = em.createNamedQuery("Rating.findByVideoAndUser");
            query.setParameter("user", username);
            query.setParameter("video", videoID);
            List<Rating> r = query.getResultList();
            System.out.println(r);
            if (r.size() == 1) {
                r.get(0).setGrade(new_grade);
                System.out.println("CHANGED");
                commitTransaction();
                return "OK";
            }
            System.out.println("RATING CHANGE FAILED,QUERY EMPTY");
            return "RATING CHANGE FAILED,QUERY EMPTY";
        } catch (Exception e) {
            System.out.println("RATING CHANGE FAILED");
            return "RATING CHANGE FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //8

    public String removeRating(String username, int videoID) {
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
            Query query = em.createNamedQuery("Rating.findByVideoAndUser");
            query.setParameter("user", username);
            query.setParameter("video", videoID);
            List<Rating> r = query.getResultList();
            if (r.size() == 1) {
                em.remove(r.get(0));
                em.flush();
                commitTransaction();
                return "OK";
            }
            System.out.println("RATING DELETE FAILED,QUERY EMPTY");
            return "RATING DELETE FAILED,QUERY EMPTY";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RATING DELETE FAILED");
            return "RATING DELETE FAILED";
        } finally {
            rollback();
            closeEM();
        }
    } //9

    public List<View> getAllViewsForVideo(int videoID) {
        initEM();
        Video video = em.find(Video.class, videoID);
        if (video == null) {
            System.out.println("VIDEO DOES NOT EXIST");
            closeEM();
            return null;
        }

        Query query = em.createNamedQuery("View.findByVideo");
        query.setParameter("video", videoID);
        List<View> list = query.getResultList();
        closeEM();
        System.out.println(list.toString());
        return list;
    } //10

    public List<Rating> getAllRatingsForVideo(int videoID) {
        initEM();
        Video video = em.find(Video.class, videoID);
        if (video == null) {
            System.out.println("VIDEO DOES NOT EXIST");
            closeEM();
            return null;
        }

        Query query = em.createNamedQuery("Rating.findByVideo");
        query.setParameter("video", videoID);
        List<Rating> list = query.getResultList();
        closeEM();
        System.out.println(list.toString());
        System.out.println(list.get(0).getGrade());
        return list;
    } //11

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
    }
}
