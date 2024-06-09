/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author kosta01856
 */
abstract public class MySystem {
    protected EntityManager em = null;
    private EntityManagerFactory emf = null;
    protected EntityTransaction transaction = null;
    protected MySystem(){
        emf = Persistence.createEntityManagerFactory("SubSystem1PU");
    }
    protected void rollback(){
        if(em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
    }
    protected void closeEM(){
        if(em != null) em.close();
    }
    protected void initEM(){
        em = emf.createEntityManager();
    }
    private void initTransaction(){
        transaction = em.getTransaction();
    }
    protected void commitTransaction(){
        if(em != null)
        this.transaction.commit();
    }
    protected void startTransaction(){
        if(em != null){
          initTransaction();
          transaction.begin();
        }
    }
    protected void save(Object o){
        em.persist(o);
        em.flush();
    }
    protected void remove(Object o){
        em.remove(o);
        em.flush();
    }
}
