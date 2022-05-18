package DataBases;

import Interfaces.IRepositoryUser;
import domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Collection;
import java.util.List;

public class UserRepository implements IRepositoryUser {

    static SessionFactory sessionFactory;

    public UserRepository()
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
    public void add(User elem) {
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
    public void delete(User elem) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String hql = "from User where IdU= :Code";
                User crit = session.createQuery(hql, User.class)
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
    public void update(User elem, Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                User user = session.load( User.class, id );
                user.setFirstName(elem.getFirstName());
                user.setLastName(elem.getLastName());
                user.seteMail(elem.geteMail());
                user.setPassword(elem.getPassword());
                user.setType(elem.getType());
                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public User findById(Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                User user = session.load( User.class, id );
                tx.commit();
                user.setFirstName(user.getFirstName());
                user.setLastName(user.getLastName());
                user.seteMail(user.geteMail());
                user.setPassword(user.getPassword());
                user.setType(user.getType());
                return user;
            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<User> users = session.createQuery("from User ", User.class).list();
                for (User us : users) {
                    us.setFirstName(us.getFirstName());
                    us.setLastName(us.getLastName());
                    us.seteMail(us.geteMail());
                    us.setPassword(us.getPassword());
                    us.setType(us.getType());
                }
                tx.commit();
                return users;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Collection<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<User> users = session.createQuery("from User ", User.class).list();
                for (User us : users) {
                    us.setFirstName(us.getFirstName());
                    us.setLastName(us.getLastName());
                    us.seteMail(us.geteMail());
                    us.setPassword(us.getPassword());
                    us.setType(us.getType());
                }
                tx.commit();
                return users;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

//    public static void main(String[] args) {
//        User usr = new User("Gigi", "Pop", "pop.gigi@gmail.com", "1234", "programator");
//        usr.setID(1);
//        UserORMRepository test = new UserORMRepository();
//        //test.add(usr);
//        //test.delete(usr);
//        User user = test.findById(1);
//        System.out.println(user.getFirstName());
//        close();
//        return;
//    }

}
