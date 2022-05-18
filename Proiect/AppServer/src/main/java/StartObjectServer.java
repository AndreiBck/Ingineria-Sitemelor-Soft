import DataBases.BugRepository;
import DataBases.UserRepository;
import Interfaces.IRepositoryBug;
import Interfaces.IRepositoryUser;
import server.ServicesImplementation;
import services.IServices;
import utils.AbstractServer;
import utils.AppObjectConcurrentServer;
import utils.ServerException;

import java.io.IOException;

import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {

        Properties serverProps=new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/appserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }

        IRepositoryUser repositoryUser = new UserRepository();
        IRepositoryBug repositoryBug = new BugRepository();

        IServices appServices = new ServicesImplementation(repositoryUser, repositoryBug);
        int appServerPort=defaultPort;
        try {
            appServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }

        System.out.println("Starting server on port: "+appServerPort);

        AbstractServer server = new AppObjectConcurrentServer(appServerPort, appServices);

        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
