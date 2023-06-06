package Chatting_App;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Ebrar
 */
public class Client {

    Socket client_socket;
    String server_ip = "localhost"; //127.0.0.1
    int server_port = 5000;
    String room = "lobby";
    int roomId;
    Listen listen;
    MainScreen main_screen;
    String username;
    ObjectOutputStream client_output;
    ObjectInputStream client_input;

    public Client(MainScreen main_screen) {
        this.main_screen = main_screen;
    }

    public void Start(String user) throws IOException {
        client_socket = new Socket(server_ip, server_port);
        client_input = new ObjectInputStream(client_socket.getInputStream());
        client_output = new ObjectOutputStream(client_socket.getOutputStream());
        listen = new Listen(this);
        listen.start();
        username = user;
        Message name = new Message(Message.Message_Type.Connect);
        name.content = user;
        Send(name);
    }

    public void Send(Message msg) {
        try {
            client_output.writeObject(msg);
            client_output.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class Listen extends Thread {

        Client client;
        DefaultListModel<String> modelRoomList = new DefaultListModel<>();

        Listen(Client c) {
            client = c;
        }

        @Override
        public void run() {
            while (client.client_socket.isConnected()) {
                try {
                    Message received = (Message) (client.client_input.readObject());
                    switch (received.type) {
                        case UpdateClientList:
                            ArrayList clients = (ArrayList) received.content;
                            MainScreen.users.removeAllElements();
                            for (int i = 0; i < clients.size(); i++) {
                                MainScreen.users.addElement(clients.get(i).toString());
                            }
                            break;
                        case update_RoomList:
                            ArrayList<String> rooms = (ArrayList) received.content;
                            MainScreen.Rooms = rooms;
                            MainScreen.lst_roomsModel.removeAllElements();
                            for (int i = 0; i < rooms.size(); i++) {
                                MainScreen.lst_roomsModel.addElement(rooms.get(i));
                            }
                            break;
                        case user_chat:
                            MainScreen.lst_clientsModel.addElement(received.sender + " : " + received.content.toString());
                            break;
                        case room_chat:
                            MainScreen.lst_messageModel.addElement(received.sender + " : " + received.content.toString());
                            break;
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
