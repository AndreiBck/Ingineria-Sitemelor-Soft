package service;

import domain.Bug;
import domain.User;
import repository.Interfaces.IRepositoryBug;
import repository.Interfaces.IRepositoryUser;

public class Service implements IAppServices {

    private IRepositoryUser repositoryUser;
    private IRepositoryBug repositoryBug;

    public Service(IRepositoryBug repositoryBug, IRepositoryUser repositoryUser)
    {
        this.repositoryBug = repositoryBug;
        this.repositoryUser = repositoryUser;
    }

    @Override
    public User findUserbyId(int Ide) {
        return repositoryUser.findById(Ide);
    }

    @Override
    public void addBug(String name, String description) {
        Bug bug = new Bug(name, description);
        this.repositoryBug.add(bug);
    }
}
