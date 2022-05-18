package services;

public interface IObserver {
    void bugsModified() throws AppException;
    void usersModified() throws AppException;
}
