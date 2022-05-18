package dto;

import domain.User;

public class DTOUtils {
    public static User getFromDTO(UserDTO usdto){
        Integer id=usdto.getId();
        String firstNmae = usdto.getFirstName();
        String lastName = usdto.getLastName();
        String eMail = usdto.geteMail();
        String password = usdto.getPassword();
        String type = usdto.getType();
        User us = new User(firstNmae, lastName, eMail, password, type);
        us.setID(id);
        return us;
    }

    public static UserDTO getDTO(User user){
        Integer id=user.getID();
        String firstNmae = user.getFirstName();
        String lastName = user.getLastName();
        String eMail = user.geteMail();
        String password = user.getPassword();
        String type = user.getType();
        return new UserDTO(id, firstNmae, lastName, eMail, password, type);
    }

    public static UserDTO[] getDTO(User[] users){
        UserDTO[] frDTO=new UserDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }

    public static User[] getFromDTO(UserDTO[] users){
        User[] friends=new User[users.length];
        for(int i=0;i<users.length;i++){
            friends[i]=getFromDTO(users[i]);
        }
        return friends;
    }
}
