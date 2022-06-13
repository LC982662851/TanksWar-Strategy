package com.development.tank;

import java.awt.*;

public class Bullet {

    private TankFrame tf = null;
    public static int WIDTH = ResourceMgr.bulletD.getWidth(), HEIGHT = ResourceMgr.bulletD.getHeight();
    private static final int SPEED = 10;

    Rectangle rect = new Rectangle();

    private int x, y;
    private Dir dir;

    //衡量子弹的寿命(当它飞出界面，就应该被判定为死亡)
    //    ->否则将会 出现一个严重BUG：
    //游戏结束前，打出多少子弹，bullets集合将会new出多少子弹-》内存过大会溢出
    private boolean living = true;

    //通过Enum类型的group 来判断敌我阵营
    private Group group = Group.BAD;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Bullet(TankFrame tf, Group group, int x, int y, Dir dir) {
        this.tf = tf;
        this.group = group;
        this.x = x;
        this.y = y;
        this.dir = dir;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        tf.bullets.add(this);
    }


    public void paint(Graphics g) {
        if (!living) {
            tf.bullets.remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
        }

        move();
    }

    public void move() {

        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            default:
                break;
        }

        //update rect
        rect.x = this.x;
        rect.y = this.y;

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            living = false;
        }

    }

    public void collideWith(Tank tank) {

        //关闭队友伤害，如果子弹所属的阵营和坦克阵营一样，则不会发生伤害碰撞
        if (this.group == tank.getGroup()) return;

        //TODO: 用一个rectangle 来记录子弹的位置 不然太多了
        /**
         * 原先每一次paint 遍历所有的子弹和坦克 ，看看有没有发生碰撞
         *
         *现在修改成-》》我我每个坦克和子弹new出来的时候，new一个Rectangle
         * 这个Rect伴随着坦克和子弹的移动，改变x,y
         *
         * -》这样就不用每一次paint重新new出来所有的子弹和坦克对象了
         */
//        Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
//        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);

        //如果两个矩形 有交集 说明发生碰撞
        if (rect.intersects(tank.rect)) {
            tank.die();
            this.die();

            int ex = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
            int ey = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;

            //new 一个爆炸
            tf.explodes.add(new Explode(ex, ey, tf));
        }
    }

    private void die() {
        this.living = false;
    }
}
