package a2.Model;

import ray.rage.Engine;
import ray.rage.asset.texture.Texture;
import ray.rage.asset.texture.TextureManager;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SkyBox;
import ray.rage.util.Configuration;

import java.awt.geom.AffineTransform;
import java.io.IOException;

class Skybox {
    Skybox(SceneManager sceneManager, Engine engine) throws IOException {
        Configuration config = engine.getConfiguration();
        TextureManager textureManager = engine.getTextureManager();
        textureManager.setBaseDirectoryPath(config.valueOf("assets.skyboxes.path"));
        Texture front = textureManager.getAssetByPath("Front.png");
        Texture back = textureManager.getAssetByPath("Back.png");
        Texture left = textureManager.getAssetByPath("Left.png");
        Texture right = textureManager.getAssetByPath("Right.png");
        Texture top = textureManager.getAssetByPath("Up.png");
        Texture bottom = textureManager.getAssetByPath("Down.png");

        AffineTransform xform = new AffineTransform();
        xform.translate(0, front.getImage().getHeight());
        xform.scale(1d, -1d);

        front.transform(xform);
        back.transform(xform);
        left.transform(xform);
        right.transform(xform);
        top.transform(xform);
        bottom.transform(xform);

        SkyBox skyBox = sceneManager.createSkyBox("SkyBox");
        skyBox.setTexture(front, SkyBox.Face.FRONT);
        skyBox.setTexture(back, SkyBox.Face.BACK);
        skyBox.setTexture(left, SkyBox.Face.LEFT);
        skyBox.setTexture(right, SkyBox.Face.RIGHT);
        skyBox.setTexture(top, SkyBox.Face.TOP);
        skyBox.setTexture(bottom, SkyBox.Face.BOTTOM);
        sceneManager.setActiveSkyBox(skyBox);
    }
}
