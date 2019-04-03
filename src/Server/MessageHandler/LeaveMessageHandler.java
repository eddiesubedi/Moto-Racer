package Server.MessageHandler;

import Client.MessageHandler.ClientMessage;
import Server.ServerUDP;
import ServerClientMessage.Messages;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class LeaveMessageHandler implements IServerMessageHandler {
    @Override
    public void handleMessage(ServerUDP server, ClientMessage clientMessage, InetAddress senderIP, int senderPort) {
        int removeClientIndex = -1;
        for (int i = 0; i < server.getConnectedClients().size(); i ++) {
            if(server.getConnectedClients().get(i).getUuid().equals(clientMessage.getClientUUID())){
                removeClientIndex = i;
            }
        }
        if(removeClientIndex!=-1){
            server.getConnectedClients().remove(removeClientIndex);
            ServerMessage serverMessage = new ServerMessage(Messages.clientMessageType.REMOVE_PLAYER);
            serverMessage.setData(clientMessage.getClientUUID());
            try {
                server.forwardPacketToAll(serverMessage,clientMessage.getClientUUID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(server.getConnectedClients().size());
        }
    }

    @Override
    public void sendMessageToClient(ServerUDP server, UUID uuid, ServerMessage message) throws IOException {

    }

}
