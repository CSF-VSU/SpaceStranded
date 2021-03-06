package ru.vsu.csf.twopeoplestudios.model.characters;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import ru.vsu.csf.twopeoplestudios.model.characters.effects.Effect;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Collectible;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Inventory;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Panel;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.HerbProperty;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herbs;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.KnownHerb;
import ru.vsu.csf.twopeoplestudios.model.contactListener.EntityTypes;
import ru.vsu.csf.twopeoplestudios.model.contactListener.collisionUserData.HeroUserData;
import ru.vsu.csf.twopeoplestudios.model.map.Map;
import ru.vsu.csf.twopeoplestudios.model.weapons.Facing;
import ru.vsu.csf.twopeoplestudios.model.weapons.HeroAttack;
import ru.vsu.csf.twopeoplestudios.model.weapons.MeleeWeapon;
import ru.vsu.csf.twopeoplestudios.model.weapons.RangedWeapon;
import ru.vsu.csf.twopeoplestudios.model.world.World;

import java.util.ArrayList;

public class Hero extends Character {

    private static final float RESTITUTION = 0.0f;
    private static final float FRICTION = 0.1f;
    private static final float DENSITY = 0.0f;

    private static final float HERO_SIZE = 0.5f;

    //private static final float VELOCITY = 15000; //большим цифрам мы обязаны камере и ее настройкам. Не удивляйся
    //private static final float RUN_SPEED = 45000;\
    private static final float VELOCITY = 4;
    private static final float RUN_SPEED = 7;

    //region Declarations
    Vector2 velocity;

    boolean leftPressed;
    boolean rightPressed;
    boolean upPressed;
    boolean downPressed;

    Map map;

    private float maxFl;
    private float maxSt;

    private float hunger;
    private float stamina;

    private float hpRegenSpeed;
    private float flDecreaseSpeed;
    private float stRegenSpeed;

    Inventory inventory;
    Panel panel;

    Herb herbUnderFeet;
    ArrayList<KnownHerb> knownHerbs;
    ArrayList<Effect> activeEffects;

    HeroAttack attack;
    //endregion

    //region Getters/Setters
    public HeroAttack getAttack() {
        return attack;
    }

    public ArrayList<Effect> getActiveEffects() {
        return activeEffects;
    }

    public float getHp() {
        return hp;
    }

    public void inflictDamage(float damage) {
        this.hp -= damage;
    }

    public float getHunger() {
        return hunger;
    }

    public float getStamina() {
        return stamina;
    }

    public Vector2 getHeroPosition() {
        return body.getPosition();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Panel getPanel() {
        return panel;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public float getMaxFl() {
        return maxFl;
    }

    public float getMaxSt() {
        return maxSt;
    }

    public void increaseMaxHp(int amount) {
        this.maxHp += amount;
    }
    public void increaseMaxFl(int amount) {
        this.maxFl += amount;
    }
    public void increaseMaxSt(int amount) {
        this.maxSt += amount;
    }
    public void decreaseMaxHp(int amount) {
        this.maxHp -= amount;
    }
    public void decreaseMaxFl(int amount) {
        this.maxFl -= amount;
    }
    public void decreaseMaxSt(int amount) {
        this.maxSt -= amount;
    }
    //endregion

    //region Creating
    public Hero(com.badlogic.gdx.physics.box2d.World world, Map map) {
        charPosition = World.getInstance().getRandomPosition();
        velocity = new Vector2(0, 0);
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;

        this.world = world;
        this.map = map;
        createBody();

        maxHp = 100;
        maxFl = 100;
        maxSt = 100;

        hpRegenSpeed = 0.5f / 5f;
        flDecreaseSpeed = 0.4f / 5f;
        stRegenSpeed = 0.5f / 5f;

        hp = maxHp;
        hunger = maxFl;
        stamina = maxFl;

        herbUnderFeet = null;
        inventory = new Inventory(this);
        panel = new Panel(this);

        inventory.add(new Collectible(100, 5));
        inventory.add(new Collectible(101, 5));
        inventory.add(new Collectible(102, 5));
        inventory.add(new Collectible(103, 5));
        inventory.add(new Collectible(105, 5));
        inventory.add(new Collectible(0, 5));
        inventory.add(new Collectible(1, 5));
        inventory.add(new Collectible(2, 5));

        panel.add(new RangedWeapon(200, this, map));
        panel.add(new MeleeWeapon(250, this));

        knownHerbs = new ArrayList<KnownHerb>();
        activeEffects = new ArrayList<Effect>();

        facing = Facing.DOWN;
        attack = new HeroAttack();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef() {{
            type = BodyType.DynamicBody; //динамическое = можно воздействовать на него силами или импульсами
            position.set(charPosition);
        }};

        body = world.createBody(bodyDef);

        final PolygonShape polygonShape = new PolygonShape() {{
            //setAsBox(MapRenderer.CELL_SIZE / 2f, MapRenderer.CELL_SIZE / 2f);
            setAsBox(HERO_SIZE, HERO_SIZE);
        }};

        FixtureDef fixtureDef = new FixtureDef() {{
            shape = polygonShape;
            filter.categoryBits = EntityTypes.HERO;
            filter.maskBits = EntityTypes.HERO_MASK;
            restitution = RESTITUTION;
            density = DENSITY;
            friction = FRICTION;
        }};

        body.createFixture(fixtureDef);
        body.setFixedRotation(true); //запрещаем телу поворачиваться в процессе столновений или приложения сил и импульсов
        body.setUserData(new HeroUserData(this)); //юзер-дата, по которой из столкновения можно получить ссылку на героя

        polygonShape.dispose();
    }
    //endregion

    //region Update
    public void update(float delta) {
        //region Input and movement
        handleInputFlags();

        Vector2 vel = body.getLinearVelocity();

        if (!leftPressed && !rightPressed) {
            body.setLinearVelocity(vel.x * 0.95f, vel.y); //гасим скорость по х, если не движется влево или вправо
        }
        if (!upPressed && !downPressed) {
            body.setLinearVelocity(vel.x, vel.y * 0.95f); //то же с вверх-вниз
        }

        //величина импульса умножается на delta, чтобы персонаж двигаелся с одинаковой скоростью вне зависимости от фпс
        //todo: распределить импульсы так, чтобы движение по диагонали было с той же скоростью, что и по горизонтали/вертикали
        if (leftPressed && vel.x > -VELOCITY) {
            body.applyLinearImpulse(-RUN_SPEED * delta, 0, body.getPosition().x, body.getPosition().y, true);
        }
        if (rightPressed && vel.x < VELOCITY) {
            body.applyLinearImpulse(RUN_SPEED * delta, 0, body.getPosition().x, body.getPosition().y, true);
        }

        if (upPressed && vel.y < VELOCITY) {
            body.applyLinearImpulse(0, RUN_SPEED * delta, body.getPosition().x, body.getPosition().y, true);
        }
        if (downPressed && vel.y > -VELOCITY) {
            body.applyLinearImpulse(0, -RUN_SPEED * delta, body.getPosition().x, body.getPosition().y, true);
        }

        if (!(leftPressed || rightPressed || downPressed || upPressed)) { //еще немного магии
            body.setLinearVelocity(vel.x * 0.95f, vel.y * 0.95f);
        }
        //endregion

        regen(delta);
        activeEffectsUpdate(delta);
        updateAttack(delta);
    }

    private void updateAttack(float delta) {
        if (attack.decreaseTime(delta)) {
            attack.destroy();
        }
        else
            attack.updatePosition(body.getPosition());
    }

    private void activeEffectsUpdate(float delta) {

        for (int i = 0; i < activeEffects.size(); i++){
            activeEffects.get(i).duration -= delta;

            if (activeEffects.get(i).duration < 0) {
                activeEffects.get(i).onDetach(this);
                activeEffects.remove(i);
            }
        }
    }

    private void regen(float delta) {
        if ((hpRegenSpeed * delta) <= (maxHp - hp))
            hp += hpRegenSpeed * delta;
        else
            hp = maxHp;

        if (flDecreaseSpeed * delta <= hunger)
            hunger -= hpRegenSpeed * delta;
        else
            hunger = 0;

        if ((stRegenSpeed * delta) <= (maxSt - stamina))
            stamina += hpRegenSpeed * delta;
        else
            stamina = maxSt;
    }
    //endregion

    //region Handling input
    private void handleInputFlags() {
        //todo: velocity - вектор скорости
        boolean locked = false;
        if (leftPressed) {
            if (facing == Facing.DOWN) {
                locked = true;
                facing = Facing.DOWN_LEFT;
            }
            else if (facing == Facing.UP) {
                locked = true;
                facing = Facing.UP_LEFT;
            }
            else
                facing = Facing.LEFT;
            velocity.set(-1, velocity.y);
        }
        if (rightPressed) {
            if (facing == Facing.DOWN) {
                locked = true;
                facing = Facing.DOWN_RIGHT;
            }
            else if (facing == Facing.UP) {
                locked = true;
                facing = Facing.UP_RIGHT;
            }
            else
                facing = Facing.RIGHT;
            velocity.set(1, velocity.y);
        }
        if (upPressed) {
            if (facing == Facing.LEFT)
                facing = Facing.UP_LEFT;
            else if (facing == Facing.RIGHT)
                facing = Facing.UP_RIGHT;
            else if(!locked)
                facing = Facing.UP;
            velocity.set(velocity.x, 1);
        }
        if (downPressed) {
            if (facing == Facing.LEFT)
            facing = Facing.DOWN_LEFT;
            else if (facing == Facing.RIGHT)
                facing = Facing.DOWN_RIGHT;
            else if (!locked)
                facing = Facing.DOWN;
            velocity.set(velocity.x, -1);
        }
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                leftPressed = true;
                break;
            case Input.Keys.D:
                rightPressed = true;
                break;
            case Input.Keys.W:
                upPressed = true;
                break;
            case Input.Keys.S:
                downPressed = true;
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                leftPressed = false;
                break;
            case Input.Keys.D:
                rightPressed = false;
                break;
            case Input.Keys.W:
                upPressed = false;
                break;
            case Input.Keys.S:
                downPressed = false;
                break;
        }
    }

    public void keyTyped(char character) {
        switch (character) {
            case 'e':
            case 'у':
                if (herbUnderFeet != null)
                    collectHerb();
                break;
        }
    }
    //endregion

    public void onTouchHerb(Herb herb) {
        this.herbUnderFeet = herb;
    }

    public void onStopTouchingHerb() {
        this.herbUnderFeet = null;
    }

    private void collectHerb() {
        if (this.inventory.add(herbUnderFeet)) {
            stamina -= 15;
            map.destroyHerb(herbUnderFeet);
            herbUnderFeet = null;
        }
    }

    private KnownHerb getKnownHerbWithId(int id) {
        for (KnownHerb h : knownHerbs) {
            if (h.id == id)
                return h;
        }
        return null;
    }

    public void revealHerbProperties(int id) {
        KnownHerb h = getKnownHerbWithId(id);
        if (h != null)
            h.properties.addAll(Herbs.getInstance().getPropertiesOfHerb(id));
        else
            knownHerbs.add(new KnownHerb(id, Herbs.getInstance().getPropertiesOfHerb(id)));
    }

    public ArrayList<HerbProperty> getKnownPropertiesOfHerb(int id) {
        KnownHerb h = getKnownHerbWithId(id);
        return (h != null) ? h.properties : null;
    }

    public void addActiveEffect(Effect effect) {
        this.activeEffects.add(effect);
        effect.onAttach(this);
    }

    public void usePanelItem() {
        if (attack.isAlive())
            return;

        if (panel.getSelectedItem() instanceof MeleeWeapon) {
            MeleeWeapon weapon = (MeleeWeapon) panel.getSelectedItem();
            weapon.attack();
        }
        else if (panel.getSelectedItem() instanceof RangedWeapon) {
            RangedWeapon weapon = (RangedWeapon) panel.getSelectedItem();
            weapon.attack();
        }
    }
}
