package server;

import Interfaces.IRepositoryBug;
import Interfaces.IRepositoryUser;
import domain.Bug;
import domain.User;
import services.AppException;
import services.IObserver;
import services.IServices;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServicesImplementation implements IServices {

    private IRepositoryUser repositoryUser;
    private IRepositoryBug repositoryBug;
    private Map<Integer, IObserver> loggedUsers;

    public ServicesImplementation(IRepositoryUser repositoryUser, IRepositoryBug repositoryBug)
    {
        this.repositoryUser = repositoryUser;
        this.repositoryBug = repositoryBug;
        loggedUsers = new ConcurrentHashMap<>();
    }

    @Override
    public void addBug(String name, String description) throws AppException {
        Bug bug = new Bug(name, description);

        this.repositoryBug.add(bug);

        notifyChangeInBugs();
    }

    @Override
    public void deleteBug(Bug bug) throws AppException {

        this.repositoryBug.delete(bug);

        notifyChangeInBugs();
    }

    @Override
    public Collection<Bug> getAllBugs() throws AppException {
        return this.repositoryBug.getAll();
    }

    @Override
    public Collection<User> getAllUsers() throws AppException {
        return this.repositoryUser.getAll();
    }

    @Override
    public void addUser(String firstName, String lastName, String eMail, String password, String type) throws AppException {
        User user = new User(firstName, lastName, eMail, password, type);

        this.repositoryUser.add(user);
        notifyChangeInUsers();
    }

    @Override
    public void deleteUser(User user) throws AppException {
        this.repositoryUser.delete(user);
        notifyChangeInUsers();
    }

    @Override
    public void updateUser(String firstName, String lastName, String eMail, String password, String type, int userId) throws AppException {
        User user = new User(firstName, lastName, eMail, password, type);

        this.repositoryUser.update(user, userId);
        notifyChangeInUsers();
    }

    private final int defaultThreadsNo=5;
    private synchronized void notifyChangeInBugs() throws AppException{
        Iterable<User> users=repositoryUser.getAll();
        System.out.println("Users "+users);

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(User us :users){
            IObserver appClient=loggedUsers.get(us.getID());
            if (appClient!=null)
                executor.execute(() -> {
                    try {
                        appClient.bugsModified();
                    } catch (AppException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }

        executor.shutdown();
    }

    private synchronized void notifyChangeInUsers() throws AppException{
        Iterable<User> users=repositoryUser.getAll();
        System.out.println("Users "+users);

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(User us :users){
            IObserver appClient=loggedUsers.get(us.getID());
            if (appClient!=null)
                executor.execute(() -> {
                    try {
                        appClient.usersModified();
                    } catch (AppException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }

        executor.shutdown();
    }

    @Override
    public synchronized void login(User user, IObserver client) throws AppException{
        User userR=repositoryUser.findById(user.getID());
//        throw new AppException(user.getType() + " " +userR.getType());
        if (userR!=null && Objects.equals(user.getPassword(), userR.getPassword()) && Objects.equals(user.getType(), userR.getType())){
            if(loggedUsers.get(user.getID())!=null)
                throw new AppException("User already logged in.");
            loggedUsers.put(user.getID(), client);
        }else
            throw new AppException("Authentication failed.");
    }

    @Override
    public synchronized void logout(User user, IObserver client) throws AppException{
        IObserver localClient=loggedUsers.remove(user.getID());
        if (localClient==null)
            throw new AppException("User "+user.getID()+" is not logged in.");
    }
}
