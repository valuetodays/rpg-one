package game.billy.fmj;

import android.content.Context;
import android.view.View;

import java.util.Stack;

public class GameView extends View {
    private Stack<Object> stack = new Stack<>();

    public GameView(Context context) {
        super(context);
    }

}
