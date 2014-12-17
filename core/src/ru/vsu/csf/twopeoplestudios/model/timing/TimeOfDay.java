package ru.vsu.csf.twopeoplestudios.model.timing;

public enum TimeOfDay {
    MORNING,
    DAY,
    EVENING,
    NIGHT;

    public TimeOfDay getNext() {
        switch (this) {
            case MORNING:
                return DAY;
            case DAY:
                return EVENING;
            case EVENING:
                return NIGHT;
            case NIGHT:
                return MORNING;
            default:
                return DAY;
        }
    }
}
