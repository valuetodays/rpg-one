package com.billy.scriptParser.cmd;

/**
 * 命令 - attr
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-10 12:31
 * @since 2016-05-10 12:31
 */
public class AttrCmd extends CmdBase {

    private int m;
    private int n;
    private int width;
    private int height;
    
    public AttrCmd() {
        super("attr");
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

   
}
