public class Main {
    //Запускающий класс
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame MF = new MainFrame();
            }
        });
    }
}
