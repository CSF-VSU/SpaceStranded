package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Items;
import ru.vsu.csf.twopeoplestudios.renderers.ui.CraftRecipe;
import ru.vsu.csf.twopeoplestudios.renderers.ui.UISpriteHolder;

import java.util.LinkedList;

public class TestFuckingScreenImSoTiredOfNonScrollableShiiit extends AbstractScreen{

    private Stage stage;
    private Table container;

    public TestFuckingScreenImSoTiredOfNonScrollableShiiit(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        final ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        Skin scrollSkin = new Skin() {{
            add("default", scrollPaneStyle, ScrollPane.ScrollPaneStyle.class);
        }};

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(true);

        container = new Table() {{
            //setFillParent(true);
            setBounds(getX(), getY(), 600, 400);
        }
        };

        Table table = new Table();
        table.debug();

        final ScrollPane scrollPane = new ScrollPane(table, scrollSkin);

        table.pad(10).defaults().expandX().space(4);
        for (int i = 0; i < 10; i++) {
            table.row();
            /*table.add(new CraftRecipe(new LinkedList<Integer>(){{
                add(102);
                add(104);
                add(103);
            }
            }, 105)).right();
            table.row();
            table.add(new CraftRecipe(new LinkedList<Integer>(){{
                add(103);
                add(101);
            }
            }, 106)).right();
            table.row();
            table.add(new CraftRecipe(new LinkedList<Integer>(){{
                add(104);
            }
            }, 100)).right();*/
            /*table.add(new Image(Items.getInstance().getItemTexture(104)) {{
                setSize(50, 50);
            }
            }).expandX().fill();

            Slider.SliderStyle sliderStyle = new Slider.SliderStyle() {{
                background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("gfx/ui/test/selection.png"))));
                knob = new TextureRegionDrawable(new TextureRegion(UISpriteHolder.skinPrgBar.getRegion("prBarFilled")));
            }
            };
            Slider slider = new Slider(0, 100, 1, true, sliderStyle);
            table.add(slider);*/
        }

        scrollPane.layout();
        scrollPane.invalidate();
        scrollPane.setCancelTouchFocus(false);
        container.add(scrollPane).expand().fill().colspan(4);
        stage.addActor(container);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
