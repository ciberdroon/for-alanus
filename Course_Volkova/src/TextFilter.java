import java.io.File;
import javax.swing.filechooser.*;

//фильтр файлов *.txt, *.bd
//Нужен, чтобы в окне диалога выбора файлов отображались
// только файлы с указанными расширениями
public class TextFilter extends FileFilter {

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        //Получаем расширение файла
        String extension = getExtension(f); //метод определен ниже
        if (extension != null) {
            if (extension.equals("txt") ||
                    extension.equals("bd")) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //Описание фильтра
    public String getDescription() {
        return "Текстовые файлы";
    }

    //метод класса, не входящий в родительский класс FileFilter
    private static String getExtension(File f) {
        //возвращает расширение файла f
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}

