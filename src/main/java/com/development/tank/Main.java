package com.development.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();

        int initTanksCount = Integer.parseInt((String) PropertyMgr.get("initTanksCount"));

        for (int i = 0; i < initTanksCount; i++) {
            tankFrame.tanks.add(new Tank(50 + i * 100, 200, Dir.DOWN,Group.BAD, tankFrame));
        }


        while (true) {
            Thread.sleep(25);
            tankFrame.repaint();
        }
    }

}
