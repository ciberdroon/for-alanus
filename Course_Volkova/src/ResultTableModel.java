import javax.swing.table.AbstractTableModel;
import java.util.*;

public class ResultTableModel extends AbstractTableModel {
    //Модель для основной таблицы
    List<Result> results;

    public ResultTableModel(List<Result> results) {
        super();
        this.results = results;
    }

    @Override
    public int getRowCount() {
        return results.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return results.get(r).getRouteId();
            case 1:
                return results.get(r).getPlaneType();
            case 2:
                return results.get(r).getCity();
            case 3:
                return results.get(r).getStartTime();
            case 4:
                return results.get(r).getFlyTime();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int c) {
        String name = "";
        switch (c) {
            case 0:
                name = "ID";
                break;
            case 1:
                name = "Тип самолета";
                break;
            case 2:
                name = "Город назначения";
                break;
            case 3:
                name = "Время отправления";
                break;
            case 4:
                name = "Время полета";
                break;
        }
        return name;
    }
}