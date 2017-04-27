package com.billy.scriptParser.cmd;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-10 22:38
 * @since 2016-05-10 22:38
 */
public class ShowTextCmd extends CmdBase {
    private String text;

    public ShowTextCmd(String key) {
        super("showtext");
        this.text = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
