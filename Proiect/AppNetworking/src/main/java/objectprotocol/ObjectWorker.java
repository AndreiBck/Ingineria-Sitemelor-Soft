package objectprotocol;

import domain.Bug;
import domain.User;
import dto.DTOUtils;
import dto.UserDTO;
import services.AppException;
import services.IObserver;
import services.IServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;

public class ObjectWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ObjectWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Object response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse((Response) response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private Response handleRequest(Request request){
        Response response=null;
        if (request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq=(LoginRequest)request;
            UserDTO udto=logReq.getUser();
            User user= DTOUtils.getFromDTO(udto);
            try {
                server.login(user, this);
                return new OkResponse();
            } catch (AppException e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof LogoutRequest){
            System.out.println("Logout request");
            LogoutRequest logReq=(LogoutRequest)request;
            UserDTO udto=logReq.getUser();
            User user= DTOUtils.getFromDTO(udto);
            try {
                server.logout(user, this);
                connected=false;
                return new OkResponse();

            } catch (AppException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof  GetAllBugsRequest)
        {
            System.out.println("Get all bugs request");
            try{
                Collection<Bug> events = server.getAllBugs();
                Bug[]rez = events.toArray(new Bug[events.size()]);
                return new GetAllBugsResponse(rez);
            }
            catch (AppException e)
            {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof  GetAllUsersRequest)
        {
            System.out.println("Get all users request");
            try{
                Collection<User> events = server.getAllUsers();
                User[]rez = events.toArray(new User[events.size()]);
                return new GetAllUsersResponse(rez);
            }
            catch (AppException e)
            {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof AddUserRequest)
        {
            System.out.println("Add user!");
            AddUserRequest logReq = (AddUserRequest)request;
            try{
                server.addUser(logReq.getFirstName(), logReq.getLastName(), logReq.geteMail(), logReq.getPassword(), logReq.getType());
                response = new UsersModifiedResponse();
            }catch (AppException e)
            {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof AddBugRequest)
        {
            System.out.println("Add bug!");
            AddBugRequest logReq = (AddBugRequest)request;
            try {
                server.addBug(logReq.getName(), logReq.getDescription());
                response = new BugsModifiedResponse();
            }catch (AppException e)
            {
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof RemoveBugRequest)
        {
            System.out.println("Remove bug!");
            RemoveBugRequest logReq = (RemoveBugRequest) request;
            try{
                server.deleteBug(logReq.getBug());
                response = new BugsModifiedResponse();
            } catch (AppException e)
            {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof RemoveUserRequest)
        {
            System.out.println("Remove user!");
            RemoveUserRequest logReq = (RemoveUserRequest) request;
            try {
                server.deleteUser(logReq.getUser());
                response = new UsersModifiedResponse();
            }
            catch (AppException e)
            {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof UpdateUserRequest)
        {
            System.out.println("Update user!");
            UpdateUserRequest logReq = (UpdateUserRequest) request;
            try{
                server.updateUser(logReq.getFirstName(), logReq.getLastName(), logReq.geteMail(), logReq.getPassword(), logReq.getType(), logReq.getIdU());
                response = new UsersModifiedResponse();
            }
            catch (AppException e)
            {
                return new ErrorResponse(e.getMessage());
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    @Override
    public void bugsModified() throws AppException {
        try {
            sendResponse(new BugsModifiedResponse());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void usersModified() throws AppException {
        try {
            sendResponse(new UsersModifiedResponse());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
