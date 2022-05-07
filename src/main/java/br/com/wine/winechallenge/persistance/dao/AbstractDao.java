package br.com.wine.winechallenge.persistance.dao;

import br.com.wine.winechallenge.persistance.model.Cep;
import br.com.wine.winechallenge.persistance.model.Cidade;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class AbstractDao<T extends Serializable> {

    private Class<T> clazz;

    protected SessionFactory sessionFactory;

    protected final void setClazz(final Class<T> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet);
    }

    public T findOne(final long id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    public T findOne(final String id) {
        Session s = getCurrentSession();
        s.beginTransaction();
        return (T) s.get(clazz, id);
    }



    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + clazz.getName()).list();
    }

    public T create(final T entity) {
        Preconditions.checkNotNull(entity);
        Session s = getCurrentSession();

        Transaction trans = s.getTransaction();
        getCurrentSession().saveOrUpdate(entity);
        trans.commit();

        s.close();

        return entity;
    }

    @SuppressWarnings("unchecked")
    public T update(final T entity) {
        Preconditions.checkNotNull(entity);
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(final T entity) {
        Preconditions.checkNotNull(entity);
        getCurrentSession().delete(entity);
    }

    public void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        Preconditions.checkState(entity != null);
        delete(entity);
    }

    protected Object namedQuery(String queryID, Map<String, Object> params) {
        Preconditions.checkState(!Strings.isNullOrEmpty(queryID));

        Query namedQuery = getCurrentSession().getNamedQuery(queryID);
        return assembleQueryParams(namedQuery, params).getResultList();
    }

    private Query assembleQueryParams(final Query query, final Map<String, Object> params) {

        if (params != null) {
            for (Map.Entry<String, Object> parameter : params.entrySet()) {
                query.setParameter(parameter.getKey(), parameter.getValue());
            }
        }

        return query;
    }

    protected Session getCurrentSession() {
        try {
            return HibernateUtil.getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
