import server.Server;
import dataaccess.MemUserDAO;
import service.Service;

public class Main {
    public static void main(String[] args) {

        MemUserDAO userDAO = new MemUserDAO();
        var service = new Service(userDAO);
        Server server = new Server(service);
        server.run(8080);

        System.out.println("♕ 240 Chess Server");
    }
}