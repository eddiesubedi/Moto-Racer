package Client;

import ray.rage.game.Game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Pattern;

public class ClientLauncher {
    private static String serverAddress;
    private static int serverPort;
    private static Client client;

    public static void main(String[] args) throws IOException {
        processArgs(args);
        connectToServer();

        Game game = new motoracer.View.Game(client);
        try {
            game.startup();
            game.run();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            game.shutdown();
            game.exit();
        }
    }

    private static void processArgs(String[] args) {
        if (args.length >= 2) {
            String ip = args[0];
            String port = args[1];
            String IPAddressPattern =
                    "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
            String portPatten = "^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
            Pattern p = Pattern.compile(portPatten +"|"+ IPAddressPattern);
            if(p.matcher(ip).matches()){
                serverAddress = ip;
            } else{
                System.out.println("Invalid IP");
                System.exit(-1);
            }
            if(p.matcher(port).matches()){
                serverPort = Integer.parseInt(port);
            } else{
                System.out.println("Invalid Ip Port Number");
                System.exit(-1);
            }

        } else {
            System.out.println("Incorrect amount of args");
            System.exit(-1);
        }
    }

    private static void connectToServer() throws IOException {
        client = new Client(InetAddress.getByName(serverAddress), serverPort);
    }
}
