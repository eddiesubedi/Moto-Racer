package Server.MessageHandler;

import Client.MessageHandler.ClientMessage;
import Server.Server;
import ServerClientMessage.Messages;
import ServerClientMessage.Utils;
import ray.networking.server.IClientInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class SendJoinMessageHandler implements IServerMessageHandler {
    @Override
    public void handleMessage(Server server, ClientMessage message, InetAddress senderIP, int senderPort) {
        System.out.println(message.getMessageType()+" "+message.getClientUUID());
        try {
            IClientInfo a = server.getServerSocket().createClientInfo(senderIP, senderPort);
            server.addClient(a, message.getClientUUID());
            sendMessageToClient(server, message.getClientUUID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToClient(Server server, UUID uuid) throws IOException {
        ServerMessage message = new ServerMessage(Messages.clientMessageType.SUCEESS);
        server.sendPacket(Utils.toStream(message), uuid);
    }
}
