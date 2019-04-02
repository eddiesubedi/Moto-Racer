package Server.MessageHandler;

import Client.MessageHandler.ClientMessage;
import Server.ServerUDP;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public interface IServerMessageHandler {
    public void handleMessage(ServerUDP server, ClientMessage message, InetAddress senderIP, int senderPort);
    public void sendMessageToClient(ServerUDP server, UUID uuid, ServerMessage message) throws IOException;
}
