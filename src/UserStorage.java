package src;
import java.util.Hashtable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserStorage implements Serializable{

    Hashtable<String, User> hashtable = new Hashtable<String, User>();


    public void addUser(User user) {
        hashtable.put(user.getName(), user);
    }

    public User getUser(String name) {
        return hashtable.get(name);
    }

    public void removeUser(String name) {
        hashtable.remove(name);
    }

    public boolean containsUser(String name) {
        return hashtable.containsKey(name);
    }

    //Save the current state of user storage to a file
    public boolean saveState() {
    try {
        File file = new File("ServerState.txt");
        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        }

        // Serialize the User objects
        FileOutputStream fileOut = new FileOutputStream("ServerState.txt");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        for (User user : hashtable.values()) {
            objectOut.writeObject(user);
        }
        objectOut.close();
        return true;
    } catch (Exception e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        return false;
    }
}


    //Load the current state of user storage from a file
    public UserStorage readState() {
    try {
        File file = new File("ServerState.txt");
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return new UserStorage();
        }

        FileInputStream fileIn = new FileInputStream("ServerState.txt");
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        UserStorage userStorage = new UserStorage();

        while (fileIn.available() > 0) {
            User user = (User) objectIn.readObject();
            userStorage.addUser(user);
        }

        objectIn.close();
        return userStorage;
    } catch (EOFException eof) {
        // Handle EOFException if needed
        System.out.println("End of file reached.");
        return new UserStorage(); // Or handle it differently
    } catch (Exception e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        return null;
    }
}

    
}
