package Client.MessageHandler;

import Server.MessageHandler.ServerMessage;

public class SucessMessageHandler implements IClientMessageHandler{
    @Override
    public void handleMessage(ServerMessage message) {
        System.out.println(message);
    }
}
