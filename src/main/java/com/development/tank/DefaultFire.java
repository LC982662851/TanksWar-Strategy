package com.development.tank;

public class DefaultFire implements FireComparator {

    public void StrengthFire(Tank tank) {
        int bx = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        //TODO:原fire方法是通过下面将子弹new到数组的 用策略模式的话不太好用 改成在bullet构造方法中给它放到数组中
        //tf.bullets.add(new Bullet(tf, group, bx, by, this.dir));
        new Bullet(tank.tf, tank.group, bx, by, tank.getDir());
    }
}
