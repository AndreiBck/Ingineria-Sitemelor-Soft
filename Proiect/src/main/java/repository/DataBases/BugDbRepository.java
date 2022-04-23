package repository.DataBases;

import domain.Bug;
import repository.Interfaces.IRepositoryBug;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

public class BugDbRepository implements IRepositoryBug {
    private JdbcUtils dbUtils;

    public BugDbRepository(Properties props){
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Bug elem) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Bug(name, description) values (?,?)")) {
            preStmt.setString(1, elem.getName());
            preStmt.setString(2, elem.getDescription());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex)
        {
            System.err.println("Error BD "+ex);
        }
    }

    @Override
    public void delete(Bug elem) {

    }

    @Override
    public void update(Bug elem, Integer id) {

    }

    @Override
    public Bug findById(Integer id) {
        return null;
    }

    @Override
    public Iterable<Bug> findAll() {
        return null;
    }

    @Override
    public Collection<Bug> getAll() {
        return null;
    }
}
