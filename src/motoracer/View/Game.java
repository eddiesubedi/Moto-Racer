package motoracer.View;

import Client.Client;
import Client.MessageHandler.ClientMessage;
import ServerClientMessage.Messages;
import motoracer.Model.World;
import ray.rage.Engine;
import ray.rage.game.VariableFrameRateGame;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.scene.SceneManager;

import java.awt.*;
import java.io.IOException;

import static ServerClientMessage.Utils.toStream;

public class Game extends VariableFrameRateGame {
    private World world = new World();
    private Client client;
    public Game(Client client) {
        this.client = client;
        this.client.setUpMessages(world);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ClientMessage message = new ClientMessage(Messages.serverMessageType.LEAVE, client.getUuid());
            client.sendMessage(toStream(message));
        }));
    }

    @Override
    protected void setupCameras(SceneManager sm, RenderWindow rw) {
    }

    @Override
    protected void setupScene(Engine eng, SceneManager sm) throws IOException {
        world.setUpEntities(sm, eng, client);
    }

    @Override
    protected void update(Engine engine) {
        client.processPackets();
        float delta = engine.getElapsedTimeMillis();
        world.updateWorld(delta, engine);
    }


    @Override
    protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge) {
        rs.createRenderWindow(new DisplayMode(1000, 900, 24, 60), false);
    }

    @Override
    protected void setupWindowViewports(RenderWindow rw) {
        rw.addKeyListener(this);
    }
}
