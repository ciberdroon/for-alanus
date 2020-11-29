import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class MainFrame implements ActionListener {
    //Главная форма программы
    String directoryName = "C:/";//имя директории, в которой расположен
    // последний загруженный файл БД
    String fileName = "";// имя последнего загруженного файла базы данных
    File curFile; //текущий файловый объект
    static JFrame frame; //главный фрейм
    JPanel pMain; //главная панель фрейма
    JTable VIS_TABLE;
    EditPanel editPanel; //панель инструментов редактирования
    ViewPanel viewPanel; //панель инструментов просмотра
    JLabel jFileName; //метка для вывода имени файла таблицы
    static JLabel MSG;//сообщение в нижней части окна
    java.util.List<String> LINES; //список для ввода-вывода строк
    //Текстовые массивы, которые содержат сообщения, выдаваемые в окнах меню "Справка"
    static String helpArr1 = "\n     Систему разработал студент группы ИВТ/б-19-2-о\n " +
            "    Шестаков Андрей Алексеевич:\n" +
            "     СевГУ - 2020.\n";

    static String helpArr2 = "\n     Информационная система осуществляет хранение и\n" +
            "     обработку данных об авиарейсах\n" +
            "     аэропорта.\n";

    public MainFrame() {
        //Создаем основные структуры --------------------------------------------
        //1. Внутреннее представление основной таблицы
        Global.table = new ResultGroup("Список записей о результатах");
        //2. Список для вывода в JTable
        Global.results = new ArrayList<Result>();
        //2. Визуальный компонент для таблицы
        Global.tableModel = new ResultTableModel(Global.results);
        VIS_TABLE = new JTable(Global.tableModel);
        //Создаем панель прокрутки и включаем в ее состав  таблицу
        JScrollPane scrtable = new JScrollPane(VIS_TABLE);
        //Устаналиваем размеры прокручиваемой области
        VIS_TABLE.setPreferredScrollableViewportSize(
                new Dimension(250, 100));
        //3. Панель просмотра записей
        viewPanel = new ViewPanel();
        //4. Панель редактирования записей
        editPanel = new EditPanel();
        //---------------------------------------------------------------------
        int WinSizeG = 600;//начальный размер окна по горизонтали
        int WinSizeV = 500;//начальный размер окна по вертикали
        frame = new JFrame("Система хранения и обработки данных");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container myC = frame.getContentPane();
        myC.setLayout(new BorderLayout(5, 5));//должна быть такая компоновка, чтобы отображались
        //полосы прокрутки
        MenuIS s = new MenuIS();
        frame.setJMenuBar(s.mb1);//добавление меню в окно
        //Организация прослушивания пунктов меню
        s.newFile.addActionListener(this);
        s.openFile.addActionListener(this);
        s.saveFile.addActionListener(this);
        s.saveAsFile.addActionListener(this);
        s.closeFile.addActionListener(this);
        s.startEdit.addActionListener(this);
        s.stopEdit.addActionListener(this);
        s.help1.addActionListener(this);
        s.help2.addActionListener(this);
        //начальная сборка главной панели
        pMain = new JPanel(); //главная панель проекта
        pMain.setLayout(new BorderLayout());
        pMain.add(scrtable, BorderLayout.CENTER);
        pMain.add(editPanel, BorderLayout.SOUTH);
        jFileName = new JLabel("Без имени", JLabel.CENTER);
        pMain.add(jFileName, BorderLayout.NORTH);
        // Скроллинг для главной панели
        // Полосы прокрутки должны появляться в случае необходимости,
        // т.е. если мы в процессе выполнения программы
        // изменим размер фрейма так, что он станет меньше предпочтительного
        // размера главной панели (по умолчанию предполагает, что все элементы помещаются целиком)
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane spMain = new JScrollPane(pMain, v, h);//панель прокрутки для pMain
        myC.add(spMain, BorderLayout.CENTER);
        // Метка с названием таблицы
        myC.add(new JLabel("Данные авиарейсов аэропорта", JLabel.CENTER),
                BorderLayout.NORTH);
        // Метка для сообщений системы
        MSG = new JLabel(
                "   Курсовой проект по дисциплине \"Программирование\". СевГУ - 2020");
        MSG.setFont(new Font("Serif", Font.BOLD, 14));
        myC.add(MSG, BorderLayout.SOUTH);
        // устанавливаем размеры и локализацию фрейма
        frame.setSize(WinSizeG, WinSizeV);
        frame.setLocation(10, 10);
        frame.setVisible(true);
    }
    //**********методы для пункта меню "Файл"********************************************

    public void NewFile() {
        Global.table.getResults().clear();
        Global.results.clear();
        Global.tableModel.fireTableDataChanged();
        pMain.remove(viewPanel);
        pMain.add(editPanel, BorderLayout.SOUTH);
        MSG.setText(
                "   Создание базы данных");
    }

    public void setFileFilter(JFileChooser fch) {//установка фильтра для диалога выбора файла
        TextFilter text_filter = new TextFilter();
        fch.setFileFilter(text_filter);
    }

    public void OpenFile() {
        int rez;
        int n;
        MSG.setText("   Открытие файла");
        JFileChooser fch = new JFileChooser(directoryName);
        fch.setDialogTitle("Открытие файла");
        setFileFilter(fch);
        rez = fch.showDialog(frame, "Open");
        if (rez == fch.APPROVE_OPTION) {
            curFile = fch.getSelectedFile();
            fileName = curFile.getAbsolutePath();
            n = fileName.lastIndexOf('\\');
            directoryName = fileName.substring(0, n + 1);
            try {
                LINES = IO.inpLines(fileName);
                if (LINES != null) MSG.setText("   Успешный ввод данных");
                else MSG.setText("   Ошибка ввода данных");
            } catch (Exception e) {
                MSG.setText("   Ошибка ввода данных");
            } finally {
            }
            java.util.List<Result> res = Transfer.StringsToResults(LINES);
            Global.table.getResults().clear();
            for (Result r : res) Global.table.addResult(r);//добавятся только уникальные
            Global.updateJTable(Global.table.getResults());
            pMain.remove(editPanel);
            pMain.add(viewPanel, BorderLayout.SOUTH);
            jFileName.setText(fileName);
        }
    }

    private void SaveDialog() {//открывает окно диалога для сохранения файла
        int rez;
        int n;
        JFileChooser fch = new JFileChooser(directoryName);
        fch.setDialogTitle("Сохранение файла");
        setFileFilter(fch);
        rez = fch.showDialog((Component) frame, "Save");
        if (rez == fch.APPROVE_OPTION) {
            curFile = fch.getSelectedFile();
            fileName = curFile.getAbsolutePath();
            n = fileName.lastIndexOf('\\');
            directoryName = fileName.substring(0, n + 1);
        }
    }

    public void SaveFile(boolean fs) {
        String old_file_name = fileName;
        MSG.setText("   Сохранение файла");
        if (fs) SaveDialog();
        else if (fileName == "") SaveDialog();
        //если файл при первом запуске JFileChooser не был выбран
        //т.е. была нажата кнопка Close:
        if (curFile == null) {
            MSG.setText(
                    "   Файл для сохранения не выбран");
            return;
        }
        if ((!curFile.exists()) || fileName.equals(old_file_name)) {
            LINES = Transfer.ResultsToString(Global.table.getResults());
            try {
                boolean f = IO.outpLines(fileName, LINES);
                if (f) {
                    MSG.setText("    Данные успешно сохранены");
                    jFileName.setText(fileName);
                } else MSG.setText("    Ошибка сохранения данных");
            } catch (Exception e) {
                MSG.setText("    Ошибка сохранения данных");
            } finally {
            }
        } else {
            JOptionPane.showMessageDialog(
                    frame, "Ошибка: файл с заданным именем " + fileName + " существует");
            fileName = old_file_name;
        }
        MSG.setText(
                "   Курсовой проект по дисциплине \"Программирование\". СевГУ - 2020");
    }

    public void CloseWindow() {
        frame.dispose();
    }

//********************методы для пункта меню "Редактирование"*************************************************

    public void StartEdit() {
        pMain.remove(viewPanel);
        pMain.add(editPanel, BorderLayout.SOUTH);
        MSG.setText(
                "   Редактирование базы данных");
    }

    public void StopEdit() {
        pMain.remove(editPanel);
        pMain.add(viewPanel, BorderLayout.SOUTH);
        MSG.setText(
                "   Просмотр базы данных");
    }

    public void actionPerformed(ActionEvent e) {
        String s;
        int l;
        char ch;
        boolean f;
        if ("Новый".equals(e.getActionCommand())) NewFile();
        else if ("Открыть".equals(e.getActionCommand())) OpenFile();
        else if ("Сохранить".equals(e.getActionCommand())) SaveFile(false);
        else if ("Сохранить как".equals(e.getActionCommand())) SaveFile(true);
        else if ("Закрыть".equals(e.getActionCommand())) CloseWindow();
        else if ("Начать редактирование".equals(e.getActionCommand())) StartEdit();
        else if ("Закончить редактирование".equals(e.getActionCommand())) StopEdit();
        else if ("О программе".equals(e.getActionCommand())) {
            HelpDialog helpMSG = new HelpDialog(MainFrame.frame, "О программе", helpArr1, "динозавр.gif");
            helpMSG.setVisible(true);
        } else if ("Описание ИС".equals(e.getActionCommand())) {
            HelpDialog helpMSG1 = new HelpDialog(MainFrame.frame, "Описание информационной системы",
                    helpArr2, "лошадь.gif");
            helpMSG1.setVisible(true);
        }

    }

}