package Client.MessageHandler;

import Server.MessageHandler.ServerMessage;
import ServerClientMessage.Transform;
import motoracer.Model.World;

import java.util.ArrayList;
import java.util.UUID;

public class SetupAIHandler implements IClientMessageHandler{
    @Override
    public void handleMessage(ServerMessage message, World world) {
        ArrayList<Transform> aiInitialTransform = (ArrayList<Transform>) message.getData();
        for (int i = 0; i < aiInitialTransform.size(); i++) {
            System.out.println(aiInitialTransform.get(0));
            world.addAI(aiInitialTransform.get(i),"ai"+i);
        }
    }
}
