package Server.MessageHandler;

import Client.MessageHandler.ClientMessage;
import Server.ServerUDP;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class TestMessageHandler implements IServerMessageHandler {
    @Override
    public void handleMessage(ServerUDP server, ClientMessage message, InetAddress senderIP, int senderPort) {
        System.out.println(message.getClientUUID());
        try {
            server.sendPacket("CAT", message.getClientUUID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToClient(ServerUDP server, UUID uuid, ServerMessage message) throws IOException {

    }

}
