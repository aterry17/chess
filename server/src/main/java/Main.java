import server.Server;
import dataaccess.MemUserDAO;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.run(8080);


        // trying a simple example
        MemUserDAO userDAO = new MemUserDAO();
        var service = new UserService();

        System.out.println("♕ 240 Chess Server");
    }
}