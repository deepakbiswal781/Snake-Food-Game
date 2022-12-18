import javax.swing.*;
import  java.awt.*;
import  java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class Panel extends JPanel implements ActionListener{
    static int width=1200;
    static int height=600;
    static int unit=50;
    Timer timer;
    static final int delay=160;
    Random random;
    int fx,fy;
    int body=3;
    char dir='R';
    int score=0;
    boolean flag=false;
    static final int size=(width*height)/(unit*unit);
    final int xsnake[]=new int[size];
    final int ysnake[]=new int[size];
    Panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        random=new Random();
        this.addKeyListener(new Key());
        game_start();
    }
    public void game_start(){
        spawnfood();
        flag=true;
        timer=new Timer(delay,this);
        timer.start();
    }
    public void spawnfood(){
        fx=random.nextInt((int)(width/unit))*unit;
        fy=random.nextInt((int)(height/unit))*unit;
    }
    public void checkHit(){
        for(int i=body;i>0;i--)
        {
            if((xsnake[0]==xsnake[i]) && (ysnake[0]==ysnake[i])){
                flag=false;
            }
        }
        if(xsnake[0]<0){
            flag=false;
        }
        else if(xsnake[0]>width){
            flag=false;
        }
        else if(ysnake[0]<0){
            flag=false;
        }
        else if(ysnake[0]>height){
            flag=false;
        }
        if(flag==false){
            timer.stop();
        }
    }
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }
    public void draw(Graphics graphic){
        if(flag){
            graphic.setColor(Color.red);
            graphic.fillOval(fx,fy,unit,unit);

            for(int i=0;i<body;i++)
            {
                if(i==0){
                    graphic.setColor(Color.GREEN);
                    graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
                }
                else {
                    graphic.setColor(Color.ORANGE);
                    graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
                }
            }
            graphic.setColor(Color.BLUE);
            graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
            FontMetrics f=getFontMetrics(graphic.getFont());
            graphic.drawString("SCORE:"+score,(width - f.stringWidth("SCORE:"+score))/2,graphic.getFont().getSize());
        }
        else{
            gameOver(graphic);
        }
    }
    public void gameOver(Graphics graphic){
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f=getFontMetrics(graphic.getFont());
        graphic.drawString("SCORE:"+score,(width - f.stringWidth("SCORE:"+score))/2,graphic.getFont().getSize());

        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,80));
        FontMetrics f2=getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over!",(width - f2.stringWidth("Game Over!"))/2,height/2);

        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f3=getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to Replay",(width - f3.stringWidth("Press R to Replay"))/2,height/2-180);
    }
    public void move(){
        for(int i=body;i>0;i--){
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }
        switch (dir){
            case 'U':
                ysnake[0]=ysnake[0]-unit;
                break;
            case 'D':
                ysnake[0]=ysnake[0]+unit;
                break;
            case 'L':
                xsnake[0]=xsnake[0]-unit;
                break;
            case 'R':
                xsnake[0]=xsnake[0]+unit;
                break;
        }
    }
    public void checkScore(){
        if((fx==xsnake[0]) && (fy==ysnake[0])){
            body++;
            score++;
            spawnfood();
        }
    }
    public class Key extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                if(dir!='R'){
                    dir='L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(dir!='L'){
                    dir='R';
                }
                break;
            case KeyEvent.VK_UP:
                if(dir!='D'){
                    dir='U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if(dir!='U'){
                    dir='D';
                }
                break;
            case KeyEvent.VK_R:
                if(!flag){
                    score=0;
                    body=3;
                    dir='R';
                    Arrays.fill(xsnake,0);
                    Arrays.fill(ysnake,0);
                    game_start();
                }
                break;
        }
    }
    }
    @Override
    public void actionPerformed(ActionEvent arg0){
        if(flag){
            move();
            checkScore();
            checkHit();
        }
        repaint();
    }
}
