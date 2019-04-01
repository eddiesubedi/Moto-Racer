package Server.MessageHandler;

import Client.MessageHandler.ClientMessage;
import Server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public interface IServerMessageHandler {
    public void handleMessage(Server server, ClientMessage message, InetAddress senderIP, int senderPort);
    public void sendMessageToClient(Server server, UUID uuid) throws IOException;
}
