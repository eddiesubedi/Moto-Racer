package Server.MessageHandler;

import Client.MessageHandler.ClientMessage;
import Server.Config;
import Server.JoinedClient;
import Server.ServerUDP;
import ServerClientMessage.Messages;
import ServerClientMessage.Transform;
import ServerClientMessage.Utils;
import ray.networking.server.IClientInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

public class JoinMessageHandler implements IServerMessageHandler {
    @Override
    public void handleMessage(ServerUDP server, ClientMessage clientMessage, InetAddress senderIP, int senderPort) {
        try {
            IClientInfo clientInfo = server.getServerSocket().createClientInfo(senderIP, senderPort);
            if(server.getConnectedClients().size() < Config.Server.maxClients){
                addClientToServer(server, clientInfo, clientMessage);
                notifyClientAboutOtherJoinedClients(server, clientMessage.getClientUUID());
                notifyOtherClientsAboutThisJoinedClient(server, clientMessage.getClientUUID());
            } else {
                ServerMessage serverMessage = new ServerMessage(Messages.clientMessageType.FAIL);
                clientInfo.sendPacket(Utils.toStream(serverMessage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyOtherClientsAboutThisJoinedClient(ServerUDP server, UUID uuid) throws IOException {
        ServerMessage message = new ServerMessage(Messages.clientMessageType.ADD_PLAYER);
        message.setData(server.getConnectedClients());
        server.forwardPacketToAll(message, uuid);
    }

    private void notifyClientAboutOtherJoinedClients(ServerUDP server, UUID uuid) throws IOException {
        ArrayList<JoinedClient> clients = new ArrayList<>();
        server.getConnectedClients().forEach(client -> {
            if(!client.getUuid().equals(uuid)) {
                clients.add(client);
            }
        });
        if(clients.size()>0) {
            ServerMessage message = new ServerMessage(Messages.clientMessageType.ADD_PLAYER);
            message.setData(clients);
            sendMessageToClient(server, uuid, message);
        }
    }

    private void keepTrackOfClient(ServerUDP server, Transform transform, UUID uuid) {
        JoinedClient joinedClient = new JoinedClient(transform, uuid);
        server.addJoinedClient(joinedClient);
    }

    private void addClientToServer(ServerUDP server, IClientInfo a, ClientMessage message) throws IOException {
        server.addClient(a, message.getClientUUID());
        ServerMessage successMessage = new ServerMessage(Messages.clientMessageType.SUCCESS);
        Transform transform = new Transform();
        if(server.getConnectedClients().size()>0){
            float x = server.getConnectedClients().size();
            transform.setLocalPosition(new float[]{x,0,0});
            System.out.println(x);
        }
        successMessage.setData(transform);
        sendMessageToClient(server, message.getClientUUID(), successMessage);
        keepTrackOfClient(server,transform, message.getClientUUID());
    }

    @Override
    public void sendMessageToClient(ServerUDP server, UUID uuid, ServerMessage message) throws IOException {
        server.sendPacket(Utils.toStream(message), uuid);
    }
}
