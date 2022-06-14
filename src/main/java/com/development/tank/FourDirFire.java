package com.development.tank;

public class FourDirFire implements FireComparator{

    public void StrengthFire(Tank tank) {
        int bx = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            new Bullet(tank.gm,tank.getGroup(),bx,by,dir);
        }
    }
}
