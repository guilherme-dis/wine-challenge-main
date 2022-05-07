package br.com.wine.winechallenge.persistance.dao;

import br.com.wine.winechallenge.persistance.model.Cep;
import br.com.wine.winechallenge.persistance.model.Cidade;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private HibernateUtil() {
    }
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {

                Configuration ac = new Configuration();

                ac.addAnnotatedClass(Cep.class);
                ac.addAnnotatedClass(Cidade.class);

                sessionFactory = ac.configure().buildSessionFactory();

            } catch (Throwable ex) {

                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }

            return sessionFactory;

        } else {
            return sessionFactory;
        }
    }
}