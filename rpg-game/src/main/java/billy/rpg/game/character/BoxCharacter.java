package billy.rpg.game.character;

public class BoxCharacter extends BaseCharacter {
    private int tileNum;

    public BoxCharacter(int tileNum) {
        this.tileNum = tileNum;
    }

    /**
     * 宝箱的不可移动
     */
    @Override
    public void move() {    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return super.getPosY();
    }

    public int getTileNum() {
        return tileNum;
    }

    public void initPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
}

