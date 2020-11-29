import java.util.*;

public class ResultGroup {
    // Внутренняя (основная) таблица (группа)  результатов соревнонований
    //поля (скрыты в классе)
    private final static String GROUP_FORMAT_STRING =
            "Результаты авиарейса: %-s, %-5d строк"; //формат записи о таблице результатов
    private String name; //название таблицы
    private List<Result> results; // список записей о результатах соревнований

    // конструкторы
    public ResultGroup() {
        name = "";  //без названия
        results = new ArrayList<Result>(); //создается пустой список
    }

    public ResultGroup(String name) {
        this.name = name; //задается название группы
        results = new ArrayList<Result>(); //создается пустой список
    }

    public ResultGroup(String name, List list) {
        this.name = name; //задается название группы
        results = new ArrayList<Result>(list); //создается на основе
        // существующего списка
    }

    //метод-сеттеры для private-полей
    public void setName(String name) {
        this.name = name;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    //методы-геттеры для private-полей
    public String getName() {
        return name;
    }

    public List<Result> getResults() {
        return results;
    }

    //Переопределяем метод toString класса Object
    //возвращает строку описания объекта Группа (таблица)
    // результатов соревнований:
    public String toString() {
        return String.format(GROUP_FORMAT_STRING, name, getResultNum());
    }

    //Запросы на вставку, удаление, изменение данных:
    public boolean addResult(Result res) {
        //Добавить результат в таблицу (группу) результатов
        //(результат нельзя добавить, если в группе уже есть
        // результат с таким же ключом)
        if (getResult(res) != null)
            return false; //дополнительная программная проверка уникальности ключа
        if (results.add(res)) return true;
        else return false;
    }

    //одиночное удаление (по образцу)
    public boolean delResult(Result res) {
        //Удалить результат c заданным ключом из группы
        if (results.remove(res)) return true;
        else return false;
    }

    //Групповое удаление (по условию)
    public boolean deleteAvgGoals(Result res) {
        //удалить результаты, в которых число голов выше среднего
        return results.removeAll(inputCity(res.getCity()).results);
    }

    // Обновление (неключевого поля) по ключу
    public boolean updateResultGoals(Result res) {
        //изменить число голов в записи, выбранной по образцу res
        Result r = getResult(res);
        if (r != null) {
            r.setFlyTime(res.getFlyTime());
            r.setStartTime(res.getStartTime());
            return true;
        }
        return false;
    }

    // запросы на выборку данных:
    // возвращает результат с заданным ключом:
    public Result getResult(Result res) {
        for (Result r : results)
            //сравнение ключей (определено в методе equals класса Result)
            if (r.equals(res)) return r; // если ключ найден
        return null; // если ключ не найден
    }

    public int getResultNum() {
        //Возвращает число результатов в группе
        return results.size();
    }

    public ResultGroup inputCity(String delCity){
        ResultGroup group = new ResultGroup(String.format("результаты, с указанным городом"));
        Iterator<Result> iter = results.iterator();
        while (iter.hasNext()) {
            Result res = iter.next();
            if ((res.getCity().equals(delCity))) group.addResult(res);
        }
        return group;
    }

    public ResultGroup filterPlanes(String filter) {
        //вернуть записи о результатах, в которых название команды
        //начинается с заданного буквосочетания (фильтрация данных)
        ResultGroup group = new ResultGroup(name +
                ": результаты для самолетов типа \"" + filter + "\"");
        for (Result res : results)
            if (res.getPlaneType().startsWith(filter)) group.addResult(res);
        return group;
    }

    //Запросы на подведение итогов (итоговые расчеты)
    public int citiesNumber() {
        //общее число команд
        int n = results.size();
        if (n == 0) return 0;
        Set<String> citiesS = new TreeSet<String>();
        for (Result res : results)
            citiesS.add(res.getCity()); //только разные
        return citiesS.size();
    }

    public List<TotalRecord> totalSumCommandGoals_1() { //число рейсов и минимальное кол-во часов полета для каждого типа самолета
        //вернуть суммарное число голов для каждой команды
        //вариант 2 – ускоренный за счет удаления из набора
        //уже обработанных записей
        int n = results.size();
        if (n == 0) return null;
        //Дублируем базовую таблицу (список), т.к. из базовой таблицы
        // записи удалять нельзя
        List<Result> resultsTemp = new ArrayList<Result>(); //создаем дубль
        resultsTemp.addAll(results); //копируем ссылки на записи в список-дубль
        Set<String> citiesS = new TreeSet();
        for (Result res : results)
            citiesS.add(res.getCity()); //только разные названия команд
        List<String> citiesL = new ArrayList(citiesS); //для
        // индексирования
        int m = citiesL.size();
        String cit;
        int sum;
        int temp = 0;
        List<TotalRecord> totRecList = new ArrayList<TotalRecord>();
        for (int i = 0; i < m; i++) {
            cit = citiesL.get(i);
            sum = 0;
         /* Получаем итератор для списка-дубля, т.к. корректно
             удалять требуемые элементы из списка можно только
             используя в цикле метод remove() итератора.*/
            Iterator<Result> iter = resultsTemp.iterator();
            while (iter.hasNext()) {
                temp = temp + 1;
                Result res = iter.next();
                if (cit.equals(res.getCity())) {
                    sum = sum + res.getFlyTime(); // прибавили число голов,
                    // эта запись больше не нужна
                    iter.remove(); //удаляем запись  из временного набора
                }
            }
            totRecList.add(new TotalRecord(cit, sum));
        }
        //System.out.println("Число повторений цикла:" + temp);
        return totRecList;
    }


    //Запросы на сортировку данных
    //Для сортировки списков List используются статические
    //методы sort, определенные в классе Collections
    public ResultGroup sort(Comparator comp) { //coртировка студентов
        //по правилу, задаваемому компаратором comp
        ResultGroup group = new ResultGroup(name, results);
        Collections.sort(group.results, comp);
        return group;
    }
} //class
