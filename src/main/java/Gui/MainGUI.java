package Gui;

import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;

/**
 * Created by Yuri on 04.01.17.
 */
public class MainGUI extends JFrame {

    public static MainGUI start(GLCanvas canvas) {
        return new MainGUI(canvas);
    }

    private MainGUI(GLCanvas canvas) {
        getContentPane().add(canvas);
        setVisible(true);
        setSize(new Dimension(400, 400));
        //addKeyListener(new KeyboardHandler());
        //canvas.addKeyListener(new KeyboardHandler());
        addComponentListener(new ComponentListener() {

            int pHeight = 0;
            int pWidth = 0;

            @Override
            public void componentResized(ComponentEvent e) {
                int height = e.getComponent().getHeight();
                int width = e.getComponent().getWidth();

                if (pHeight > height || pWidth > width) {
                    e.getComponent().setSize(new Dimension(Math.min(height, width), Math.min(height, width)));
                }

                if (pHeight < height || pWidth < width) {
                    e.getComponent().setSize(new Dimension(Math.max(height, width), Math.max(height, width)));
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }



}
