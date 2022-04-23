package service;

import domain.User;

import java.util.Collection;
import java.util.List;

public interface IAppServices {
    User findUserbyId(int Ide);
    void addBug(String name, String description);
}
