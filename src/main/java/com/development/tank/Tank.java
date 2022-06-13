package com.development.tank;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Tank {

    private int x, y;
    public Dir dir = Dir.DOWN;
    private static final int speed = 2;

    Rectangle rect = new Rectangle();

    private Random random = new Random();

    public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();

    private boolean moving = true;
    private boolean living = true;
    //通过Enum类型的group 来判断敌我阵营
    public Group group = Group.BAD;

    //加上一个TankFrame的引用(把从tank这new出来的子弹对象放置到tankFrame里的子弹对象)
    public TankFrame tf;

    //可以考虑将其作为参数 或者成员变量
    private FireComparator fc;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.height = HEIGHT;
        rect.width = WIDTH;

        //初始化的时候，对group的好坏进行判断，分别造不同的子弹
        if(group == Group.GOOD) {
            String goodFSName = (String) PropertyMgr.get("GoodFS");

            try {
                //通过配置文件 读取类路径 load到内存new出对象
                fc = (FireComparator) Class.forName(goodFSName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {fc = new DefaultFire();}
    }


    public void move() {
        if (!moving) {
            return;
        }
        switch (dir) {
            case LEFT:
                x -= speed;
                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case RIGHT:
                x += speed;
                break;
            default:
                break;
        }


        //用随机数来控制发生频率
        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            this.fire();
        }

        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            randomDir();
        }
        boundsCheck();

        //update rect
        rect.x = this.x;
        rect.y = this.y;

    }

    //不让坦克跑出边界
    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
    }

    private void randomDir() {
        //Dir.values() 得到一个枚举数组，里面存放了枚举类值
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void paint(Graphics g) {

        //移除死去的坦克
        if (!living) tf.tanks.remove(this);

        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.bulletD, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
        }
        move();
    }

//    public void fire() {
//        int bx = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
//        int by = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
//        tf.bullets.add(new Bullet(tf, group, bx, by, this.dir));
//    }


    public void fire() {
         fc.StrengthFire(this);
    }

    //记得坦克的list也得移除
    public void die() {
        this.living = false;
    }
}
