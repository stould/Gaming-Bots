import com.sun.glass.ui.Pixels;
import com.sun.glass.ui.Robot;
import com.sun.javafx.runtime.VersionInfo;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main extends Application {

    private final int DELAY_TIME = 200;

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group(), 2000, 2000);
        stage.setTitle(VersionInfo.getRuntimeVersion());
        stage.setScene(scene);


        Rectangle lifeArea = new Rectangle(1236, 139, 50, 13);
        Rectangle manaArea = new Rectangle(1236, 154, 50, 13);

        Map<String, KeyCode> hotkeys = new HashMap<>();
        hotkeys.put("life potion", KeyCode.F1);
        hotkeys.put("mana potion", KeyCode.F2);

        TibiaBot bot = new TibiaBot(com.sun.glass.ui.Application.GetApplication().createRobot(), hotkeys);
        bot.setLifeArea(lifeArea);
        bot.setManaArea(manaArea);
        bot.setMinimumLife(220);
        bot.setMinimumMana(450);

        while (true) {
            bot.runSettings();
            try {
                Thread.sleep(DELAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}