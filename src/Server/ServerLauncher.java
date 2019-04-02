package Server;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerLauncher {
    private ServerLauncher(int serverPort){
        try {
            new ServerUDP(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        if(args.length>=1 ){
            String portPatten = "^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
            Pattern p = Pattern.compile(portPatten);
            Matcher m = p.matcher( args[0] );
            if(m.matches()){
                new ServerLauncher(Integer.parseInt(args[0]));
            } else {
                System.out.println("Invalid port number");
            }
        } else {
            System.out.println("Incorrect Argument");
        }
    }
}
