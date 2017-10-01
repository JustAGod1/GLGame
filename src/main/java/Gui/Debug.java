package Gui;

import Rendering.WorldRenderer;
import Vectors.Vector3;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


/**
 * Created by Yuri on 04.01.17.
 */
public class Debug extends JFrame {
    public static final int i = 6;
    private JLabel PosX;
    private JLabel PosY;
    private JLabel PosZ;
    private JLabel DirX;
    private JLabel DirY;
    private JLabel DirZ;
    private JLabel UpX;
    private JLabel UpY;
    private JLabel UpZ;
    private JButton queryDataButton;
    private JButton applyChangesButton;
    private JPanel root;
    private JTextField PosXIn;
    private JTextField PosYIn;
    private JTextField PosZIn;
    private JTextField DirXIn;
    private JTextField DirYIn;
    private JTextField DirZIn;
    private JTextField UpXIn;
    private JTextField UpYIn;
    private JTextField UpZIn;
    private JLabel resLabel;

    private Debug() {
        super("Debug GUI");


        this.getContentPane().add(root);
        this.setVisible(true);
        this.setSize(this.getPreferredSize());
        this.setMinimumSize(this.getPreferredSize());
        this.queryDataButton.addActionListener(e -> onQueryData());
        this.applyChangesButton.addActionListener(e -> onApplyChanges());
    }

    public static void start() {
        new Debug();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    private void onQueryData() {
        Vector3 pos = WorldRenderer.getInstance().camera.pos;
        Vector3 dir = WorldRenderer.getInstance().camera.dir;
        Vector3 up = WorldRenderer.getInstance().camera.up;

        PosX.setText(String.valueOf(pos.x));
        PosY.setText(String.valueOf(pos.y));
        PosZ.setText(String.valueOf(pos.z));

        DirX.setText(String.valueOf(dir.x));
        DirY.setText(String.valueOf(dir.y));
        DirZ.setText(String.valueOf(dir.z));

        UpX.setText(String.valueOf(up.x));
        UpY.setText(String.valueOf(up.y));
        UpZ.setText(String.valueOf(up.z));
    }

    private void onApplyChanges() {
        Vector3 pos;
        Vector3 dir;
        Vector3 up;
        try {
            pos = new Vector3(Float.parseFloat(PosXIn.getText()), Float.parseFloat(PosYIn.getText()), Float.parseFloat(PosZIn.getText()));
            dir = new Vector3(Float.parseFloat(DirXIn.getText()), Float.parseFloat(DirYIn.getText()), Float.parseFloat(DirZIn.getText()));
            up = new Vector3(Float.parseFloat(UpXIn.getText()), Float.parseFloat(UpYIn.getText()), Float.parseFloat(UpZIn.getText()));
        } catch (NumberFormatException e) {
            resLabel.setText("Ошибка: проверьте входные данные");
            return;
        }

        WorldRenderer.getInstance().camera.setPos(pos);
        WorldRenderer.getInstance().camera.setDir(dir);
        WorldRenderer.getInstance().camera.setUp(up);


        resLabel.setText("Изменения внесены успешно");
        onQueryData();
    }


    static class B {
        public void printHeadFile(String fileName) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(new FileInputStream(fileName));
                System.out.println(scanner.next());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
