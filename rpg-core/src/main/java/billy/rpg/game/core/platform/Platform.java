package billy.rpg.game.core.platform;

import android.content.Context;

public class Platform {
    public static enum Type {
        ANDROID,
        DESKTOP
    }

    private static Type activeType = Type.DESKTOP;
    private static Context ctx;
    public static void setType(Type type) {
        activeType = type;
    }
    public static void setContext(Context context) {
        ctx = context;
    }
    public static Type getType() {
        return activeType;
    }

    public static Context getCtx() {
        return ctx;
    }
}
