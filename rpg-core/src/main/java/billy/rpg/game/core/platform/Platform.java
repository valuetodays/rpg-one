package billy.rpg.game.core.platform;

public class Platform {
    public static enum Type {
        ANDROID,
        DESKTOP
    }

    private static Type activeType = Type.DESKTOP;
    public static void setType(Type type) {
        activeType = type;
    }

    public static Type getType() {
        return activeType;
    }
}
