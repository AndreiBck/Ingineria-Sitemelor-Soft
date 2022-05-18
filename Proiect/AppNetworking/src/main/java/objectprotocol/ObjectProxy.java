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
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ObjectProxy implements IServices {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    //private List<Response> responses;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        //responses=new ArrayList<Response>();
        qresponses=new LinkedBlockingQueue<Response>();
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void sendRequest(Request request)throws AppException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new AppException("Error sending object "+e);
        }

    }

    private Response readResponse() throws AppException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws AppException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(UpdateResponse update){
        if (update instanceof UsersModifiedResponse){
            try {
                client.usersModified();
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
        if(update instanceof BugsModifiedResponse)
        {
            try {
                client.bugsModified();
            }catch (AppException e)
            {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void addBug(String name, String description) throws AppException {
        sendRequest(new AddBugRequest(name, description));
    }

    @Override
    public void deleteBug(Bug bug) throws AppException {
        sendRequest(new RemoveBugRequest(bug));
    }

    @Override
    public Collection<Bug> getAllBugs() throws AppException {
        sendRequest(new GetAllBugsRequest());
        Response response=readResponse();
        if(response instanceof ErrorResponse)
        {
            ErrorResponse err =(ErrorResponse)response;
            throw new AppException(err.getMessage());
        }

        GetAllBugsResponse resp =(GetAllBugsResponse) response;
        Bug[] events = resp.getEvents();

        return Arrays.asList(events);
    }

    @Override
    public Collection<User> getAllUsers() throws AppException {
        sendRequest(new GetAllUsersRequest());
        Response response=readResponse();
        if(response instanceof ErrorResponse)
        {
            ErrorResponse err =(ErrorResponse)response;
            throw new AppException(err.getMessage());
        }

        GetAllUsersResponse resp =(GetAllUsersResponse) response;
        User[] events = resp.getEvents();

        return Arrays.asList(events);
    }

    @Override
    public void addUser(String firstName, String lastName, String eMail, String password, String type) throws AppException {
        sendRequest(new AddUserRequest(firstName, lastName, eMail, password, type));
    }

    @Override
    public void deleteUser(User user) throws AppException {
        sendRequest(new RemoveUserRequest(user));
    }

    @Override
    public void updateUser(String firstName, String lastName, String eMail, String password, String type, int userId) throws AppException {
        sendRequest(new UpdateUserRequest(firstName, lastName, eMail, password, type, userId));
    }

    @Override
    public void login(User user, IObserver client) throws AppException {
        initializeConnection();
        UserDTO udto= DTOUtils.getDTO(user);
        sendRequest(new LoginRequest(udto));
        Response response=readResponse();
        if (response instanceof OkResponse){
            this.client=client;
            return;
        }
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new AppException(err.getMessage());
        }
    }

    @Override
    public void logout(User user, IObserver client) throws AppException {
        UserDTO udto= DTOUtils.getDTO(user);
        sendRequest(new LogoutRequest(udto));
        Response response=readResponse();
        closeConnection();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new AppException(err.getMessage());
        }
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (response instanceof UpdateResponse){
                        handleUpdate((UpdateResponse)response);
                    }else{
                        /*responses.add((Response)response);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (responses){
                            responses.notify();
                        }*/
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
