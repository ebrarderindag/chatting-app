package Chatting_App;

/**
 *
 * @author Ebrar
 */
public class Room {
    int id;
    String name;
    Client creator;
    int userCount;

    public Room(int id, String name, int userCount) {
        this.id = id;
        this.name = name;
        this.userCount = userCount;
    }
        public Room(String name, Client c) {
        this.name = name;
        this.creator = c;

    }
    
}
