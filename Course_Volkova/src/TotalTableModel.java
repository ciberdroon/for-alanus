import javax.swing.table.AbstractTableModel;
import java.util.*;

public class TotalTableModel extends AbstractTableModel {
    //ћодель таблицы дл¤ итогового запроса
    List<TotalRecord> records;
    String strColName;
    String intColName;

    public TotalTableModel(List<TotalRecord> records, String strColName, String intColName) {
        super();
        this.records = records;
        this.strColName = strColName;
        this.intColName = intColName;
    }

    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return records.get(r).str;
            case 1:
                return records.get(r).num;
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int c) {
        String name = "";
        switch (c) {
            case 0:
                name = strColName;
                break;
            case 1:
                name = intColName;
                break;
        }
        return name;
    }
}