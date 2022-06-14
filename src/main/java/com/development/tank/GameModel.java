package com.development.tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);

    java.util.List<Bullet> bullets = new ArrayList<Bullet>();
    java.util.List<Tank> tanks = new ArrayList<Tank>();
    List<Explode> explodes = new ArrayList<Explode>();

    public GameModel(){
        int initTanksCount = Integer.parseInt((String) PropertyMgr.get("initTanksCount"));

        for (int i = 0; i < initTanksCount; i++) {
            tanks.add(new Tank(50 + i * 100, 200, Dir.DOWN,Group.BAD,this));
        }
    }


    public void paint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("子弹的数量" + bullets.size(), 10, 60);
        g.drawString("敌方坦克的数量" + tanks.size(), 10, 80);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);
        /**
         *增强型for循环-》里面用的是迭代器遍历
         * 该迭代器不允许用除它之外的方式进行CRUD(两个值不一致)，会抛异常
         * (我们这里用的是list的remove方法移除)
         * 将其改为普通遍历即可（或调用iterator的remove）
         */
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }

        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }

    }


    public Tank getMainTank() {
        return myTank;
    }
}
