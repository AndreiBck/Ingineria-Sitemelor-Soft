package services;

import domain.Bug;
import domain.User;
import java.util.Collection;

public interface IServices {

    //Verificator
    void addBug(String name, String description) throws AppException;

    //Programator
    void deleteBug(Bug bug) throws AppException;
    Collection<Bug> getAllBugs() throws AppException;

    //Administrator
    Collection<User> getAllUsers() throws AppException;
    void addUser(String firstName, String lastName, String eMail, String password, String type) throws AppException;
    void deleteUser(User user) throws AppException;
    void updateUser(String firstName, String lastName, String eMail, String password, String type, int userId) throws AppException;

    //All
    void login(User user, IObserver client)  throws AppException;
    void logout(User user, IObserver client)  throws AppException;

}
