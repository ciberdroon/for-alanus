import java.io.*;
import java.util.*;

public class IO {
    //статические методы ввода и вывода
    public static List<String> inpLines(String fileName)
            throws IOException {
        // ввод строк из текстового файла с именем fileName в список строк
        List<String> lines = new ArrayList<String>(); //создали список строк
        String line;//строка, считанная из файла
        BufferedReader inp = null;// ссылка на входной поток
        try {//Контролируем исключения ввода-вывода
            //Открываем поток ввода
            inp = new BufferedReader(new FileReader(fileName));
            //Вводим строки из файла
            while ((line = inp.readLine()) != null) {// ввод очередной строки
                //если строка пустая, или состоит из одних пробелов пропускаем ее
                line = line.trim();
                if (line.equals("")) continue;
                lines.add(line);
            }
        } catch (IOException e) {// Ошибка ввода данных - обработчик для try
            return null;
        } finally {
            if (inp != null) inp.close();
        }//finally
        if (lines.isEmpty()) return null;
        return lines;
    }

    public static boolean outpLines(String fileName, List<String> lines)
            throws IOException {
        // вывод списка строк в текстовый файл с именем fileName
        PrintWriter out = null; // ссылка на выходной поток
        if ((lines == null) || lines.isEmpty()) return false; //если список null или пуст
        try {//Контролируем исключения ввода-вывода
            //Открываем поток вывода
            out = new PrintWriter(new FileWriter(fileName));
            int n = lines.size();
            for (int i = 0; i < n; i++) {
                out.println(lines.get(i).trim());
            }
        }//try
        catch (IOException e) {// Ошибка вывода данных - обработчик для try
            return false;
        } finally {
            if (out != null) out.close();
        }//finally
        return true;
    }
}