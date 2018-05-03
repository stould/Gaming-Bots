import Exceptions.RecognizerException;
import com.sun.glass.ui.Pixels;
import com.sun.glass.ui.Robot;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class TibiaBot extends Bot {

    private Binarization binarization;
    private ShapeRecognizer<Integer> recognizer;

    private Rectangle lifeArea;
    private Rectangle manaArea;

    private int minimumLife;
    private int minimumMana;

    private Map<String, KeyCode> hotkeys;

    public TibiaBot(Robot robot, Map<String, KeyCode> hotkeys){
        super(robot);
        this.hotkeys = hotkeys;
        binarization = new Binarization();
        recognizer = new DigitRecognizer("resources/digits_pattern");
    }

    public void setMinimumLife(int minimumLife) {
        this.minimumLife = minimumLife;
    }

    public void setMinimumMana(int minimumMana) {
        this.minimumMana = minimumMana;
    }

    public void setLifeArea(Rectangle lifeArea) {
        this.lifeArea = lifeArea;
    }

    public void setManaArea(Rectangle manaArea) {
        this.manaArea = manaArea;
    }

    public int getCurrentLife() {

        BufferedImage binaryScreenShot =
                binarization.binarize(
                        binarization.toGray(
                                getScreenShot(lifeArea.x, lifeArea.y, lifeArea.width, lifeArea.height)));

        ImageSeparator separator = new ImageSeparator(binaryScreenShot, false);
        LinkedList<Shape<Integer> > shapes = separator.run();

        int digits[] = new int[shapes.size()];

        for(int i = 0; i < digits.length; i++){
            try {
                digits[i] = recognizer.getShape(shapes.get(i));
            }catch (RecognizerException e){
                e.printStackTrace();
                return -1;
            }
        }

        int currentLife = Helper.toInteger(Arrays.copyOfRange(digits,0, digits.length));
        return currentLife;
    }

    public int getCurrentMana() {
        BufferedImage binaryScreenShot =
                binarization.binarize(
                        binarization.toGray(
                                getScreenShot(manaArea.x, manaArea.y, manaArea.width, manaArea.height)));

        ImageSeparator separator = new ImageSeparator(binaryScreenShot, false);
        LinkedList<Shape<Integer> > shapes = separator.run();

        int digits[] = new int[shapes.size()];

        for(int i = 0; i < digits.length; i++){
            try {
                digits[i] = recognizer.getShape(shapes.get(i));
            }catch (RecognizerException e){
                e.printStackTrace();
                return -1;
            }
        }

        int currentMana = Helper.toInteger(Arrays.copyOfRange(digits,0, digits.length));
        return currentMana;
    }

    public void runSettings(){
        int life = getCurrentLife();
        int mana = getCurrentMana();
        System.out.println("Life = "+life+", Mana = "+mana);
        if(life < minimumLife){
            pressKey(hotkeys.get("life potion"));
        }
        if(mana < minimumMana){
            pressKey(hotkeys.get("mana potion"));
        }
    }
}
