import java.util.*;

public class Transfer {
    //Статические методы перевода строка - объект и обратно
    public static List<Result> StringsToResults(List<String> lines) {
        if (lines == null || lines.isEmpty()) return null;
        //Создаем список объектов класса Result
        List<Result> results = new ArrayList<Result>();
        for (int i = 0; i < lines.size(); i++) {
            String[] words = lines.get(i).split(","); //разбор строки на
            //слова
            if (words.length != 5) return null;
            //Cоздаем новый результат из слов введенной строки
            Result r = new Result();
            r.setPlaneType(words[1].trim());
            r.setCity(words[2].trim());
            try { //Контролируем исключения при преобразовании
                // из String в int
                r.setRouteId(Integer.parseInt(words[0].trim()));
                r.setStartTime(Integer.parseInt(words[3].trim()));
                r.setFlyTime(Integer.parseInt(words[4].trim()));
            } catch (NumberFormatException e) { // обработчик исключения
                return null;
            }
            results.add(r);  //добавляем результат соревнований к списку
        }
        return results;
    }

    public static List<String> ResultsToString(List<Result> results) {
        if (results == null || results.isEmpty()) return null;
        //Создаем список строк
        List<String> lines = new ArrayList<String>();
        //Формируем очередную строку из полей очередного объекта
        //списка results и добавляем ее к списку строк
        for (Result res : results) //для каждого элемента списка results
            lines.add(String.format("%10d, %10s, %10s, %5d, %5d", res.getRouteId(), res.getPlaneType(), res.getCity(), res.getStartTime(), res.getFlyTime()));
        return lines;
    }
}
