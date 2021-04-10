package bsu.rfe.java.group9.Krasilnikova;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

@SuppressWarnings("serial")

public class MainFrame extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    private JMenuItem stickMenuItem;
    private JMenuItem unstickMenuItem;
    private Field field = new Field();

    public MainFrame() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);
        setExtendedState(MAXIMIZED_BOTH);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Мячи");
        Action addBallAction = new AbstractAction("Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled() && !stickMenuItem.isEnabled() && !unstickMenuItem.isEnabled()) {
                // Ни один из пунктов меню не являются доступными - сделать доступным "Паузу"
                    pauseMenuItem.setEnabled(true);
                    stickMenuItem.setEnabled(true);
                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);
        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Приостановить движение"){
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);
        Action resumeAction = new AbstractAction("Возобновить движение") {
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);
        JMenu magnetismMenu = new JMenu("Магнетизм");
        menuBar.add(magnetismMenu);
        Action stickAction = new AbstractAction("Приклеить мячи к стенкам") {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.magnetized();
                stickMenuItem.setEnabled(false);
                unstickMenuItem.setEnabled(true);
            }
        };
        stickMenuItem = magnetismMenu.add(stickAction);
        stickMenuItem.setEnabled(false);
        Action unstickAction = new AbstractAction("Отклеить мячи от стенок") {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.unmagnetized();
                stickMenuItem.setEnabled(true);
                unstickMenuItem.setEnabled(false);
            }
        };
        unstickMenuItem = magnetismMenu.add(unstickAction);
        unstickMenuItem.setEnabled(false);
// Добавить в центр граничной компоновки поле Field
        getContentPane().add(field, BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
