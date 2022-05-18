package utils;

import objectprotocol.ObjectWorker;
import services.IServices;

import java.net.Socket;

public class AppObjectConcurrentServer extends AbsConcurrentServer{
    private IServices chatServer;

    public AppObjectConcurrentServer(int port, IServices chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ObjectWorker worker=new ObjectWorker(chatServer, client);
        Thread tw=new Thread(worker);
        return tw;
    }
}
