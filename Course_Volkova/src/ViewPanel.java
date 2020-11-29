import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class ViewPanel extends JPanel implements ActionListener {
    //Панель просмотра
    JTextField num1; //левая граница диапазона
    JTextField num2;  //правая граница диапазона
    JTextField tf; //фильтр

    public ViewPanel() {
        setLayout(new GridLayout(7, 2, 2, 2));
        JButton but4 = new JButton("Итог 1:  число рейсов и минимальное кол-во часов в полете для каждого типа самолета");
        JButton but5 = new JButton("Итог 2:  число городов");
        JButton but6 = new JButton("Применить фильтр");
        JButton but8 = new JButton("Сортировать по городу назначения и времени отправления");
        JButton but9 = new JButton("Вывести все");
        tf = new JTextField("");
        JLabel l1 = new JLabel("Введите фильтр для рейсов", JLabel.CENTER);
        JLabel l2 = new JLabel("");
        but4.setActionCommand("Total1");
        but5.setActionCommand("Total2");
        but6.setActionCommand("Filter");
        but8.setActionCommand("Sort2");
        but9.setActionCommand("All");

        JPanel p1 = new JPanel();
        add(l1);
        add(l2);
        add(tf);
        add(but6);
        add(but9);
        add(p1);
        add(but8);
        add(but4);
        add(but5);

        but4.addActionListener(this);
        but5.addActionListener(this);
        but6.addActionListener(this);
        but8.addActionListener(this);
        but9.addActionListener(this);
    }

    private void showTotal1() {
        TotalDialog td = new TotalDialog(MainFrame.frame, "Суммарное число голов для каждой команды:",
                Global.table.totalSumCommandGoals_1());
        td.setVisible(true);
    }

    ;

    private void showTotal2() {
        MainFrame.MSG.setText("   Итоговый запрос на выборку");
        JOptionPane.showMessageDialog(MainFrame.frame,
                String.format("Общее число команд: %5d", Global.table.citiesNumber()));
    }

    private void showFilter() {
        String filter = tf.getText();
        if (filter.equals("")) {
            MainFrame.MSG.setText("     Введите фильтр");
            return;
        }
        MainFrame.MSG.setText(String.format(
                "   Запрос на выборку: выдать записи с рейсом с самолетом типа \"%s\"", filter));
        Global.updateJTable(Global.table.filterPlanes(filter).getResults());
    }

    private void showSort2() { //IN DEV
        MainFrame.MSG.setText(
                "   Запрос на выборку: выдать все записи таблицы с сортировкой по городу назначения и времени отправления");
        Global.updateJTable(Global.table.sort(new CompCityAscStartTimeAsc()).getResults());
    }

    private void showAll() {
        MainFrame.MSG.setText("   Запрос на выборку: выдать все записи таблицы без сортировки");
        Global.updateJTable(Global.table.getResults());
    }

    public void actionPerformed(ActionEvent e) {
        if ("Total1".equals(e.getActionCommand())) showTotal1();
        else if ("Total2".equals(e.getActionCommand())) showTotal2();
        else if ("Filter".equals(e.getActionCommand())) showFilter();
        else if ("Sort2".equals(e.getActionCommand())) showSort2();
        else showAll();
    }
} 