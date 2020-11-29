import java.util.Objects;

public class Result implements Comparable<Result> {
    //запись внутренней таблицы
    //пол¤
    // формат строки, представл¤ющей запись о результате:
    private final static String REZ_FORMAT_STRING = "%10d | %10s | %8s | %8d | %8d";

    private int routeId, startTime, flyTime;
    private String planeType, city;

    // конструктор без параметров
    public Result() {
        routeId = 0;
        planeType = "";
        city = "";
        startTime = 0;
        flyTime = 0;
    }

    // конструктор с параметрами
    public Result(int routeId, String planeType, String city, int startTime, int flyTime) {
        this.routeId = routeId;
        this.startTime = startTime;
        this.flyTime = flyTime;
        this.planeType = planeType;
        this.city = city;
    }

    //методы-геттеры
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFlyTime() {
        return flyTime;
    }

    public void setFlyTime(int flyTime) {
        this.flyTime = flyTime;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    //ѕереопредел¤етс¤ метод toString класса Object
    //(возвращает строку описани¤ объекта)
    @Override
    public String toString() {
        return String.format(REZ_FORMAT_STRING, routeId, planeType, city, startTime, flyTime);
    }

    //ѕереопредел¤етс¤ метод equals класса Object
    //(задает способ сравнени¤ объектов на равенство,
    //возвращает true, если запускающий объект равен объекту-параметру)
    @Override
    public boolean equals(Object ob) {
        if (ob == this) return true; // ссылки равны Ц один
        // и тот же объект
        if (ob == null) return false; //в метод передана null-ссылка
        if (getClass() != ob.getClass()) return false; //объекты разных классов
        Result rez = (Result) ob; // преобразуем Object в Rezalt
        //провер¤етс¤ равенство ключей текущей записи и записи-параметра
        return (routeId == rez.routeId) &&
                city.equals(rez.city);
    }

    //ѕереопредел¤етс¤ метод hashCode класса Object
    //¬озвращает хэш-код объекта
    //(у равных объектов должны быть равные hash-коды)


    @Override
    public int hashCode() {
        return Objects.hash(routeId, startTime, flyTime, planeType, city);
    }

    //ќпредел¤ем метод —оmpareTo интерфейса —omporable
    //ƒл¤ определени¤ пор¤дка (естественного) перечислени¤ элементов
    public int compareTo(Result rez) {  //CHECK
        //сравниваютс¤ только пол¤, составл¤ющие ключ!
        //по возрастанию названи¤ команды,
        //в рамках одной команды -
        //по возрастанию названи¤ чемпионата
        // сначала сравниваем названи¤ команд
        int c = city.compareTo(rez.city);
        if (c < 0) return -1;
        if (c > 0) return 1;
        //названи¤ команд равны (одна и та же команда)
        //сравниваем названи¤ чемпионатов
        if (startTime < rez.startTime) return -1;
        if (startTime > rez.startTime) return 1;
        return 0; //названи¤ чемпионатов равны   (этого не будет, т.к. ключи
        // разных строк Ѕƒ не могут быть равны друг другу)
    }
}



