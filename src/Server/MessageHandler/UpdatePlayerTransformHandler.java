package Server.MessageHandler;

import Client.MessageHandler.ClientMessage;
import Server.JoinedClient;
import Server.ServerUDP;
import ServerClientMessage.Messages;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

public class UpdatePlayerTransformHandler implements IServerMessageHandler {
    @Override
    public void handleMessage(ServerUDP server, ClientMessage clientMessage, InetAddress senderIP, int senderPort) {
        JoinedClient client = (JoinedClient) clientMessage.getData();
        ServerMessage message = new ServerMessage(Messages.clientMessageType.UPDATE_PLAYERS);
        message.setData(client);
        try {
            server.forwardPacketToAll(message, clientMessage.getClientUUID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToClient(ServerUDP server, UUID uuid, ServerMessage message) throws IOException {

    }
}
