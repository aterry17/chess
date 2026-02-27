import dataaccess.MemAuthDAO;
import dataaccess.MemGameDAO;
import server.Server;
import dataaccess.MemUserDAO;
import service.Service;

public class Main {
    public static void main(String[] args) {

        Server server = new Server();
        server.run(8080);

        System.out.println("♕ 240 Chess Server");
    }
}