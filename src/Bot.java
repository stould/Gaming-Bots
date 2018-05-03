import com.sun.glass.ui.Pixels;
import com.sun.glass.ui.Robot;
import javafx.scene.input.KeyCode;

import java.awt.image.BufferedImage;

public abstract class Bot {

    private Robot robot;

    public Bot(Robot robot){
        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }

    public void pressKey(KeyCode key) {
        robot.keyPress(key.impl_getCode());
        robot.keyRelease(key.impl_getCode());
    }


    //need to implement
    public void type(String s){
        for(int i = 0; i < s.length(); i++){
            //pressKey();
        }
    }

    public BufferedImage getScreenShot(int x, int y, int width, int height){
        Pixels pixels = getRobot().getScreenCapture(x, y, width, height);
        BufferedImage screenShot = Helper.convertToImage(pixels);
        return  screenShot;
    }
}
