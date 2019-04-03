package motoracer.Model;

import ray.rage.asset.texture.Texture;
import ray.rage.asset.texture.TextureManager;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3f;

import java.io.IOException;
import java.util.UUID;

import static ray.rage.rendersystem.Renderable.Primitive.TRIANGLES;
import static ray.rage.rendersystem.states.RenderState.Type.TEXTURE;

public class GamePlayer {
    private SceneNode node;

    public GamePlayer(SceneManager sceneManager, Vector3f localPosition, TextureManager tm, UUID uuid){
        Entity bikeEntity = null;
        try {
            bikeEntity = sceneManager.createEntity(uuid.toString(), "dirt_bike_blender.obj");
            Texture bikeTexture = tm.getAssetByPath("bike_06.png");
            RenderSystem rs = sceneManager.getRenderSystem();
            TextureState state = (TextureState) rs.createRenderState(TEXTURE);
            state.setTexture(bikeTexture);
            bikeEntity.setRenderState(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bikeEntity != null;
        bikeEntity.setPrimitive(TRIANGLES);
        node = sceneManager.getRootSceneNode().createChildSceneNode(bikeEntity.getName() + "Node");
        node.attachObject(bikeEntity);
        node.setLocalPosition(localPosition);
    }
}
