package ru.vsu.csf.twopeoplestudios.renderers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Inventory;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Items;
import ru.vsu.csf.twopeoplestudios.model.craft.CraftPart;
import ru.vsu.csf.twopeoplestudios.model.craft.Recipe;

import java.util.LinkedList;
import java.util.List;

public class CraftRecipe extends Table {

    public static final float ITEM_HEIGHT = 80;

    public static final float CRAFT_ITEM_HEIGHT = 60;
    public static final float CRAFT_ITEM_WIDTH = 60;

    private int id;
    private boolean isSelected;

    public CraftRecipe(final Recipe r, final Inventory inventory) {
        super();

        this.setBackground(new TextureRegionDrawable((UISpriteHolder.skinCraftItem.getRegion("craftItemUnselected"))) {{
            setSize(400, 80);
        }});
        this.setBounds(getX(), getY(), 400, 80);

        this.isSelected = false;
        this.id = r.id;
        if (r.in != null) {
            List<Image> icons = new LinkedList<Image>() {{
                for (CraftPart part : r.in)
                    add(new Image(Items.getInstance().getItemTexture(part.id)));
            }};
            List<Label> labels = new LinkedList<Label>() {{
                for (CraftPart part : r.in) {
                    add(new Label("x" + part.count, UISpriteHolder.skinLbls));
                }
            }};
            List<Label> cnts = new LinkedList<Label>() {{
                for (CraftPart part : r.in) {
                    add(new Label("<" + inventory.getItemCount(part.id) + ">", UISpriteHolder.skinGreenLbls));
                }
            }};

            for ( int i = 0; i < r.in.size(); i++) {
                this.add(icons.get(i)).height(CRAFT_ITEM_HEIGHT).width(CRAFT_ITEM_WIDTH).pad(10, 10, 10, 0);
                this.add(labels.get(i)).width(20).padRight(10);
                this.add(cnts.get(i)).width(20).padRight(15);
            }
        }

        Image arrow = new Image(new TextureRegion(new Texture(Gdx.files.internal("gfx/ui/craftArrow.png"))));
        Image out1 = new Image(Items.getInstance().getItemTexture(r.out.id));

        this.add(arrow).height(CRAFT_ITEM_HEIGHT).width(CRAFT_ITEM_WIDTH).pad(10).right();
        this.add(out1).height(CRAFT_ITEM_HEIGHT).width(CRAFT_ITEM_WIDTH).pad(10, 10, 10, 0).right();
        this.add(new Label("x" + r.out.count, UISpriteHolder.skinLbls)).width(20).padRight(10).right();
        this.add(new Label("<" + inventory.getItemCount(r.out.id) + ">", UISpriteHolder.skinGreenLbls)).width(20).right();

        this.layout();
        this.invalidate();
    }

    public void select() {
        setBackground(new TextureRegionDrawable((UISpriteHolder.skinCraftItem.getRegion("craftItemSelected"))) {{
            setSize(400, 80);
        }});
        layout();
        invalidate();
        this.isSelected = true;
    }

    public void unselect() {
        setBackground(new TextureRegionDrawable((UISpriteHolder.skinCraftItem.getRegion("craftItemUnselected"))) {{
            setSize(400, 80);
        }});
        layout();
        invalidate();
        this.isSelected = false;
    }

    public int getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
