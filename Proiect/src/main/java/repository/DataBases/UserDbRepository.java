package repository.DataBases;

import domain.User;
import repository.Interfaces.IRepositoryUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

public class UserDbRepository implements IRepositoryUser {
    private JdbcUtils dbUtils;

    public UserDbRepository(Properties props){
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(User elem) {

    }

    @Override
    public void delete(User elem) {

    }

    @Override
    public void update(User elem, Integer id) {

    }

    @Override
    public User findById(Integer id) {
        Connection con= dbUtils.getConnection();
        System.out.println(con);
        User us = new User();
        try(PreparedStatement preStmt=con.prepareStatement("select * from User where IdU = ?")){
            preStmt.setInt(1, id);
            try(ResultSet result = preStmt.executeQuery())
            {
                if(result.next())
                {
                    int IdU = result.getInt("IdU");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String type = result.getString("type");
                    us.setID(IdU);
                    us.setUsername(username);
                    us.setPassword(password);
                    us.setType(type);
                }
            }
        }
        catch (SQLException exp)
        {
            System.err.println("Error BD "+exp);
        }
        return us;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }
}
