package br.com.wine.winechallenge.persistance.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class ConnectionFactory {

    //private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory("PersistenceUnit");
    public EntityManager getConnetion(){
        return Persistence.createEntityManagerFactory("PersistenceUnit").createEntityManager();
    }


}
