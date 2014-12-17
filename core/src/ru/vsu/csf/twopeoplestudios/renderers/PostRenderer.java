package ru.vsu.csf.twopeoplestudios.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.vsu.csf.twopeoplestudios.model.map.Map;
import ru.vsu.csf.twopeoplestudios.model.timing.WorldClocks;

public class PostRenderer {

    private WorldClocks worldClocks;

    public PostRenderer(Map map) {
        worldClocks = new WorldClocks(map);
    }

    public void render(SpriteBatch batch, float delta) {
        worldClocks.update(delta);

        worldClocks.getTimeOfDay();//todo: do stuff

        //batch.setColor(r,g,b,a);
        //batch.begin();
        //batch.draw(картинка цвета, гдеХ, гдеУ, Values.SCREEN_WIDTH, Values.SCREEN_HEIGHT);
        //batch.end()
    }
}
