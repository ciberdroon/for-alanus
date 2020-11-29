import java.awt.*;
import javax.swing.*;
import java.awt.event.*;//важно


public class EditPanel extends JPanel {
    //Панель редактирования
    JTextField tf1;
    JTextField tf2;
    JTextField tf3;
    JTextField tf4;
    JTextField tf5;

    public EditPanel() {
        setLayout(new GridLayout(3, 3, 2, 2));
        JButton but1 = new JButton("Добавить");
        JButton but2 = new JButton("Обновить");
        JButton but3 = new JButton("Удалить");
        JButton but4 = new JButton("Удалить группу записей:");
        tf1 = new JTextField("");
        tf2 = new JTextField("");
        tf3 = new JTextField("");
        tf4 = new JTextField("");
        tf5 = new JTextField("");
        JLabel l1 = new JLabel("");
        JLabel l2 = new JLabel("  с указанным городом");
        add(tf1);
        add(tf2);
        add(tf3);
        add(tf4);
        add(tf5);
        add(but1);
        add(but2);
        add(l1);
        add(but3);
        add(but4);
        add(l2);
        but1.addActionListener(new ActionListener() { //анонимный слушатель
                                   public void actionPerformed(ActionEvent e) {
                                       insert();
                                   }
                               }
        );
        but2.addActionListener(new ActionListener() { //анонимный слушатель
                                   public void actionPerformed(ActionEvent e) {
                                       update();
                                   }
                               }
        );
        but3.addActionListener(new ActionListener() { //анонимный слушатель
                                   public void actionPerformed(ActionEvent e) {
                                       delete();
                                   }
                               }
        );
        but4.addActionListener(new ActionListener() { //анонимный слушатель
                                   public void actionPerformed(ActionEvent e) {
                                       deleteGroup();
                                   }
                               }
        );
    }

    private void insert() {
        int n1, n4, n5;
        String str1, str2, str3, str4, str5;
        str1 = tf1.getText();
        str2 = tf2.getText();
        str3 = tf3.getText();
        str4 = tf4.getText();
        str5 = tf5.getText();
        if (str1.equals("") || str2.equals("") || str3.equals("") || str4.equals("") || str5.equals("")) {
            MainFrame.MSG.setText("Задайте значения полей");
            return;
        }
        try {//Контролируем исключения при преобразовании из String в число
            n1 = Integer.parseInt(str1);
            n4 = Integer.parseInt(str4);
            n5 = Integer.parseInt(str5);
        }//try
        catch (NumberFormatException e) {// обработчик исключения для try
            MainFrame.MSG.setText("   Задайте правильно id, время отправления и количество часов в полете");
            return;
        }
        MainFrame.MSG.setText(
                "   Запрос на добавление записи в таблицу");
        if (!Global.table.addResult(new Result(n1, str2, str3, n4, n5)))
            MainFrame.MSG.setText(
                    "   Запись не добавлена, возможно нарушена уникальность ключа");
        Global.updateJTable(Global.table.getResults());
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
    }

    private void update() {
        int n1, n4, n5;
        String str1, str2, str3, str4, str5;
        str1 = tf1.getText();
        str2 = tf2.getText();
        str3 = tf3.getText();
        str4 = tf4.getText();
        str5 = tf5.getText();
        if (str1.equals("") || str2.equals("") || str3.equals("") || str4.equals("") || str5.equals("")) {
            MainFrame.MSG.setText("Задайте значения полей");
            return;
        }
        try {//Контролируем исключения при преобразовании из String в число
            n1 = Integer.parseInt(str1);
            n4 = Integer.parseInt(str4);
            n5 = Integer.parseInt(str5);
        }//try
        catch (NumberFormatException e) {// обработчик исключения для try
            MainFrame.MSG.setText("   Задайте правильно id, время отправления и количество часов в полете");
            return;
        }
        MainFrame.MSG.setText(
                "   Запрос на обновление записи в таблице");
        if (!Global.table.updateResultGoals(new Result(n1, str2, str3, n4, n5)))
            MainFrame.MSG.setText(
                    "   Запись не обновлена, возможно записи с таким ключом нет");
        Global.updateJTable(Global.table.getResults());
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
    }

    private void delete() {
        int n1;
        String str1, str2;
        str1 = tf1.getText();
        str2 = tf2.getText();
        if (str1.equals("") || str2.equals("")) {
            MainFrame.MSG.setText("Задайте значения полей ключа");
            return;
        }
        try {//Контролируем исключения при преобразовании из String в число
            n1 = Integer.parseInt(str1);
        }//try
        catch (NumberFormatException e) {// обработчик исключения для try
            MainFrame.MSG.setText("   Задайте правильно id");
            return;
        }
        MainFrame.MSG.setText(
                "   Запрос на удаление записи по ключу");
        if (!Global.table.delResult(new Result(n1, str2, "", 0, 0)))
            MainFrame.MSG.setText(
                    "   Запись не удалена, возможно записи с таким ключом нет");
        Global.updateJTable(Global.table.getResults());
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
    }

    private void deleteGroup() {
        String str3;
        str3 = tf3.getText();
        if (!Global.table.deleteAvgGoals(new Result(0, "", str3, 0, 0)))
            MainFrame.MSG.setText(
                    "   Записи не удалены, возможно таких записей нет");
        Global.updateJTable(Global.table.getResults());
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
    }

    ;
} 