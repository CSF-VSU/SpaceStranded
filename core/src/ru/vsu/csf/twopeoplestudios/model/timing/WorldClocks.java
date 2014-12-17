package ru.vsu.csf.twopeoplestudios.model.timing;

import com.badlogic.gdx.Gdx;
import ru.vsu.csf.twopeoplestudios.model.map.Map;

public class WorldClocks {

    private static final float TIME_OF_DAY_DURATION = 6; //in seconds

    private Map map;
    private TimeOfDay timeOfDay;
    private float remainingTime;

    public WorldClocks(Map map) {
        this.map = map;
        timeOfDay = TimeOfDay.MORNING;
        remainingTime = TIME_OF_DAY_DURATION;
    }

    public void update(float delta) {
        remainingTime -= delta;
        if (remainingTime < 0) {
            timeOfDay = timeOfDay.getNext();
            remainingTime = TIME_OF_DAY_DURATION;

            Gdx.app.log("Timing", "Now is the " + timeOfDay.toString());

            if (timeOfDay == TimeOfDay.NIGHT)
                map.unleashHellAtNight();
        }
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }
}
