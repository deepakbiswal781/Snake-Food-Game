import javax.swing.*;

public class Frame extends JFrame {
        public Frame(){
            this.add(new Panel());
            this.setTitle("Snake");
            this.setResizable(false);
            this.pack();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
        }
}
