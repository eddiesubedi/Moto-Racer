package a2.View;

import a2.Model.World;
import ray.rage.Engine;
import ray.rage.game.VariableFrameRateGame;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.scene.SceneManager;

import java.awt.*;
import java.io.IOException;

public class Game extends VariableFrameRateGame {
    private World world = new World();
    @Override
    protected void setupCameras(SceneManager sm, RenderWindow rw) {
    }

    @Override
    protected void setupScene(Engine eng, SceneManager sm) throws IOException {
        world.setUpEntities(sm, eng);
    }

    @Override
    protected void update(Engine engine) {
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
