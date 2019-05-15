package Client.MessageHandler;

import Server.MessageHandler.ServerMessage;
import ServerClientMessage.Transform;
import motoracer.Model.World;

import java.util.ArrayList;

public class UpdateAIHandler implements IClientMessageHandler {
    @Override
    public void handleMessage(ServerMessage message, World world) {
        ArrayList<Transform> transforms = (ArrayList<Transform>) message.getData();
        for (int i = 0; i < transforms.size(); i++) {
            world.updateAITransforms(transforms.get(i), "ai" + i + "Node");
        }
    }
}
