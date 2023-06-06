package Chatting_App;

/**
 *
 * @author Ebrar
 */
public class Message implements java.io.Serializable {

    public static enum Message_Type {
        Connect, UpdateClientList, user_chat, CreateRoom, update_RoomList, room_chat, Refresh, JoinRoom
    }

    public Message_Type type;
    public Object content;
    String sender;
    String reciever;

    public Message(Message_Type t) {
        this.type = t;
    }

    public Message(Message_Type type, Object content, String sender, String reciever) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.reciever = reciever;
    }

    public Message(Message_Type type, Object content, String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
    }

}
