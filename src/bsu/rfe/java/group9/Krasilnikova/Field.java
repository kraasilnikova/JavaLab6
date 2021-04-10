package bsu.rfe.java.group9.Krasilnikova;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class Field extends JPanel {
    // Флаг приостановленности движения
    public boolean paused;
    public int magnet = 0;
    private int change;
    // Динамический список скачущих мячей
    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);
    // Класс таймер отвечает за регулярную генерацию событий ActionEvent
// При создании его экземпляра используется анонимный класс, реализующий интерфейс ActionListener
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
// Задача обработчика события ActionEvent - перерисовка окна
            repaint();
        }
    });
    // Конструктор класса BouncingBall
    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();
    }
    // Унаследованный от JPanel метод перерисовки компонента
    public void paintComponent(Graphics g) {
// Вызвать версию метода, унаследованную от предка
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (BouncingBall ball: balls) {
            ball.paint(canvas);
        }
    }
    public void addBall() {
//Заключается в добавлении в список нового экземпляра BouncingBall
// Всю инициализацию положения, скорости, размера, цвета BouncingBall выполняет сам в конструкторе
        balls.add(new BouncingBall(this));
    }
    // Метод синхронизированный, т.е. только один поток может одновременно быть внутри
    public synchronized void pause() {
        paused = true;
    }
    // Метод синхронизированный, т.е. только один поток может одновременно быть внутри
    public synchronized void resume() {
        paused = false;
// Будим все ожидающие продолжения потоки
        notifyAll();
    }
    public synchronized void magnetized() {
        magnet = 1;
    }
    public synchronized void unmagnetized() {
        magnet = 2;
        notifyAll();
    }
    // Синхронизированный метод проверки, может ли мяч двигаться (не включен ли режим паузы?)
    public synchronized void canMove(BouncingBall ball) throws InterruptedException {
        if (paused) {
// Если режим паузы включен, то поток, зашедший внутрь данного метода, засыпает
            wait();
        }
    }
    public synchronized void canStick (BouncingBall ball) throws InterruptedException {
        if (magnet == 1)
            wait();
    }
}
