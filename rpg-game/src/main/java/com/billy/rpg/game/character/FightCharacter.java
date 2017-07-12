package com.billy.rpg.game.character;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-12 09:41
 */
public class FightCharacter extends BaseCharacter {
    private String name;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int speed;
    private int attack;
    private int defend;
    // TODO buff & debug

    // TODO 该处是不是与*DataLoader相重复了
    public static FightCharacter loadFromFile() {
        return new FightCharacter();
    }


    @Override
    public void move() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }
}
