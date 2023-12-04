import java.util.Hashtable;

public class UserStorage {

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
    
}
