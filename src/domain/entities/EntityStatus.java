package domain.entities;

public enum EntityStatus {

    IDLE(4),
    WALKING(6),
    RUNNING(6),
    JUMPING(6),
    ATTACK1(6),
    DEATH(6);

    public final int frames;

    EntityStatus(int frames) {
        this.frames = frames;
    }
}
