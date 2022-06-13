package Test;

import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestImage {

    @Test
   public void test(){
        try {
            //把游戏打包给别人，所以这些路径不能写死，
            // 别人得匹配这些路径
            // 所以放在src目录下会好些
//            BufferedImage image = ImageIO.read(new File("/home/liangchen/桌面/images/bulletD.gif"));

            BufferedImage image2 = ImageIO.read(TestImage.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            Assert.assertNotNull(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         *是从Classpath路径下去寻找的，不是从src路径下去寻找的，
         * 所以要把images当做资源文件放在resources下，
         * 这样编译后才能在classpath下找到(即classes下)
         *
         */
    }

}
