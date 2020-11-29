import java.util.*;

//Компаратор:
//обеспечивает сортировку по возрастанию чемпионата, 
// в рамках одного чемпионата по убыванию балла
public class CompCityAscStartTimeAsc implements Comparator {
    public int compare(Object ob1, Object ob2) {
        Result res1 = (Result) ob1;
        Result res2 = (Result) ob2;
        if (res1.getCity().compareTo(res2.getCity()) < 0) return -1;
        else if (res1.getCity().compareTo(res2.getCity()) > 0) return 1;
            //чемпионат один и тот же
        else if (res1.getStartTime() < res2.getStartTime()) return -1;
        else if (res1.getStartTime() == res2.getStartTime()) return 0;
        else return 1;
    }
    //нет необходимости переопределять метод equals
}