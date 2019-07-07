package readingready.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class Dao<T> implements IDao<T>{

    private EntityManagerFactory emf;
    private EntityManager em;

    public Dao() {
        emf = Persistence.createEntityManagerFactory("ReadingReadyPU");
        em = emf.createEntityManager();
    }
    
    @Override
    public void create(T t) {
        em.persist(t);
    }

    @Override
    public void update(T t) {
        em.persist(t);
    }

    @Override
    public void delete(Class<T> entityType, int id) {
        final T t = em.getReference(entityType, id);
        em.remove(t);
    }

    @Override
    public T find(Class<T> entityType, int id) {
        return em.getReference(entityType, id);
    }

    @Override
    public List<T> findAll(Class<T> entityType) {
        System.out.println(entityType.getSimpleName());
        return em.createNamedQuery(entityType.getSimpleName() + ".findAll", entityType).getResultList();
    }
    
    public EntityManager getEM(){
        return em;
    }
}
