import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class MenuIS {
    //Главное меню
//подпункты меню
    JMenuItem newFile;
    JMenuItem openFile;
    JMenuItem saveFile;
    JMenuItem saveAsFile;
    JMenuItem closeFile;
    JMenuItem startEdit;
    JMenuItem stopEdit;
    JMenuItem help1;
    JMenuItem help2;

    JMenu m1, m2, m3;//пункты меню
    JMenuBar mb1;//панель меню


    public MenuIS() {

        m1 = new JMenu("Файл");

        newFile = new JMenuItem("Новый");
        m1.add(newFile);

        openFile = new JMenuItem("Открыть");
        m1.add(openFile);

        saveFile = new JMenuItem("Сохранить");
        m1.add(saveFile);

        saveAsFile = new JMenuItem("Сохранить как");
        m1.add(saveAsFile);

        closeFile = new JMenuItem("Закрыть");
        m1.add(closeFile);

        m2 = new JMenu("Редактирование");

        startEdit = new JMenuItem("Начать редактирование");
        m2.add(startEdit);

        stopEdit = new JMenuItem("Закончить редактирование");
        m2.add(stopEdit);

        m3 = new JMenu("Справка");

        help1 = new JMenuItem("О программе");
        m3.add(help1);

        help2 = new JMenuItem("Описание ИС");
        m3.add(help2);

        mb1 = new JMenuBar();

        mb1.add(m1);
        mb1.add(m2);
        mb1.add(m3);
    }
}
