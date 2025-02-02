package Chatting_App;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ebrar
 */
public class Server extends Thread {

    public ServerSocket serverSocket;
    public int serverPort = 5000;
    public Thread listen;
    public ObjectOutputStream clientOutput;
    public ObjectInputStream clientInput;
    public int clientId;
    public ArrayList<SClient> clients;
    public ArrayList<String> rooms;

    public Server(int serverPort) throws IOException {
        this.serverPort = serverPort;
        this.serverSocket = new ServerSocket(this.serverPort);
        this.clientId = 0;
        this.clients = new ArrayList<>();
        this.rooms = new ArrayList<>();

    }

    public void StartServer() {

        this.start();
    }

    public void RemoveClient(int id) {

        SClient refClient = null;
        for (SClient client : clients) {
            if (client.id == id) {
                refClient = client;
                break;
            }
        }
        if (refClient != null) {
            this.clients.remove(refClient);
        }
        RefreshClientList();

    }

    public void RemoveClient(SClient client) {
        this.clients.remove(client);
        RefreshClientList();
    }

    public void RefreshClientList() {
        Frm_Server.lst_clientsModel.removeAllElements();
        clients.forEach(client -> {
            Frm_Server.lst_clientsModel.addElement("id: " + client.id
                    + " ip: " + client.clientSocket.getInetAddress().toString()
                    + "port: " + client.clientSocket.getPort());
        });
    }

    public void SendAll(Message message) {
        clients.forEach(client -> {
            try {
                client.clientOutput.writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

            }
        });
    }

    public void SendToClient(SClient client, Message message) {

        try {
            client.clientOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
    public void SendToRoom(SClient client, Message message){

                try {
                    client.clientOutput.writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
 
  
    
    public void SendToClient(int index, String message) {
        try {
            this.clients.get(index).clientOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {

        try {

            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept(); //blocking
                this.clientId++;
                SClient newClient = new SClient(this, client, this.clientId);
                this.clients.add(newClient);
                newClient.Listen();
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
