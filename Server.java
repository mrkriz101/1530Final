// A Java Server
import java.net.*;
import java.io.*;
 
public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
    UserStorage userStorage = new UserStorage();
    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");
 
            socket = server.accept();
            System.out.println("Client accepted");
 
            // takes input from the client socket
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
 
            String line = "";
 
            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println("Searching for user " + line);
                    User user;
                    if(userStorage.containsUser(line))
                    {
                        System.out.println("User found sending user:");
                        user = userStorage.getUser(line);
                    
                    }
                    else
                    {
                        System.out.println("User not found creating new user: ");
                        user = new User(line);
                        userStorage.addUser(user);
                    }
                    //send the user back to the client
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    System.out.println("Sending user " + user.getName());
                    oos.writeObject(user);
                    oos.flush();
                    byte[] userBytes = baos.toByteArray();
                    socket.getOutputStream().write(userBytes);
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");
 
            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        Server server = new Server(1234);
    }
}