import java.util.*;

public class Global {
    //Содержит глобальные (статические) переменные и методы программы,
//посредством которых осуществляется взаимодействие между
//внутренними представлениями базовой таблицы и результатов
// запросов и визуальной таблицей
    public static ResultGroup table; //ссылка на основную таблицу (внутреннее
    //представление БД)
    public static List<Result> results; //ссылка на список, выводимый
    //в визуальный компонент JTable главного окна
    //(список результатов соревнований основной таблицы или список
    //результатов соревнований, возвращенный запросом на выборку
    //данных из основной таблицы)
    static ResultTableModel tableModel; //модель данных для JTable

    public static void updateJTable(List<Result> res) {
        //Обновить данные в визуальном компоненте JTable
        results.clear();
        results.addAll(res);
        tableModel.fireTableDataChanged();
    }
}
