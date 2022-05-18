package DataBases;

import Interfaces.IRepositoryBug;
import domain.Bug;
import domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Collection;
import java.util.List;

public class BugRepository implements IRepositoryBug {

    static SessionFactory sessionFactory;

    public BugRepository()
    {
        initialize();
    }

    static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    @Override
    public void add(Bug elem) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(elem);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Bug elem) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String hql = "from Bug where IdB= :Code";
                Bug crit = session.createQuery(hql, Bug.class)
                        .setParameter("Code", elem.getID())
                        .setMaxResults(1)
                        .uniqueResult();
                System.err.println("Stergem mesajul " + crit.getID());
                session.delete(crit);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la stergere "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Bug elem, Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Bug bug = session.load( Bug.class, id );
                bug.setDescription(elem.getDescription());
                bug.setName(elem.getName());
                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Bug findById(Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Bug bug = session.load( Bug.class, id );
                tx.commit();
                bug.setDescription(bug.getDescription());
                bug.setName(bug.getName());
                return bug;
            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<Bug> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Bug> bugs = session.createQuery("from Bug", Bug.class).list();
                for (Bug bg : bugs) {
                    bg.setDescription(bg.getDescription());
                    bg.setName(bg.getName());
                }
                tx.commit();
                return bugs;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Collection<Bug> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Bug> bugs = session.createQuery("from Bug", Bug.class).list();
                for (Bug bg : bugs) {
                    bg.setDescription(bg.getDescription());
                    bg.setName(bg.getName());
                }
                tx.commit();
                return bugs;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
