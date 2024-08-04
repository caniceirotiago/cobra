import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Painel extends JPanel {
    public static int realTimeDirection; //This avoids altering the direction more than 1 time in the timer frequency and false collisions
    public static int xCoordinateApple = -1;
    public static int yCoordinateApple = -1;
    public static int xCoordinateSnakeHead;
    public static int yCoordinateSnakeHead;
    public static int numberOfEatedApples = 0;
    public static int lastxCoordinateSnakeHead =-1;
    public static int lastyCoordinateSnakeHead =-1;
    public static int lastxCoordinateSnakeBody =-1;
    public static int lastyCoordinateSnakeBody =-1;
    public static boolean needToAddBodyPart =false;
    public static boolean bodyCollision = false;
    public static ArrayList<Integer> xcoordinatesOfBodyParts = new ArrayList<>();
    public static ArrayList<Integer> ycoordinatesOfBodyParts = new ArrayList<>();

    public static int counterY = 10; //The snake will start at the center
    public static int counterX = 10; //The snake will start at the center
    public static int globalCounter = 0;
    public static boolean collisionWithApple;
    public static boolean createdOnBody = false;
    Timer timer;
    public static int[] randomCoordinatesForApple(){
        int[] coordinates = new int[2];
         //To avoid creating an apple on the body of the snake
        do{
            createdOnBody = false;
            for(int i = 0; i < xcoordinatesOfBodyParts.size(); i++){
                coordinates[0] = (int)Math.floor(Math.random()*21); //x
                coordinates[1] = (int)Math.floor(Math.random()*21); //y
                if(coordinates[0]*40 == xcoordinatesOfBodyParts.get(i) && coordinates[1]*40 == ycoordinatesOfBodyParts.get(i)){
                    createdOnBody =true;
                    System.out.print(xcoordinatesOfBodyParts.get(i) + " ");
                    System.out.print(coordinates[0]*40);
                    System.out.print(ycoordinatesOfBodyParts.get(i) + " ");
                    System.out.print(coordinates[1]*40);
                }
                System.out.println(xcoordinatesOfBodyParts.size());
            }
        } while(createdOnBody);
        return coordinates;
    }

    public Painel() {
        setBackground(Color.LIGHT_GRAY);
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                globalCounter++;
                if(Main.direction == 1) counterX--; //Left
                else if (Main.direction == 2) counterX++; //Right
                else if (Main.direction == 3) counterY--; //Up
                else if (Main.direction == 4) counterY++; //Down
                realTimeDirection =Main.direction;
            }
        });
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawLine(0,840,840,840);

        //There are no colisions with walls. The snake will appear at the contra-lateral wall.
        if(counterY>20) counterY = 0;
        else if(counterY<0) counterY = 20;
        else if(counterX>20) counterX = 0;
        else if(counterX<0) counterX = 20;


        //Making the Snake's head
        xCoordinateSnakeHead = counterX * 40 ;
        yCoordinateSnakeHead = counterY * 40;
        g.setColor(Color.gray);
        g.fillRect(xCoordinateSnakeHead,yCoordinateSnakeHead,40,40);
        g.setColor(Color.white);
        g.fillOval(xCoordinateSnakeHead+5,yCoordinateSnakeHead+5,30,30);
        g.setColor(Color.black);
        g.fillOval(xCoordinateSnakeHead+15,yCoordinateSnakeHead+15,10,10);

        //Creating  Apple
        if(globalCounter == 0 || collisionWithApple){
            int[] anApple = randomCoordinatesForApple();
            xCoordinateApple = anApple[0]*40;
            yCoordinateApple = anApple[1]*40;
            collisionWithApple = false;
            needToAddBodyPart = true;
        }
        g.setColor(Color.red);
        g.fillOval(xCoordinateApple,yCoordinateApple,40,40);

        //Eating an Apple
        if(xCoordinateApple == xCoordinateSnakeHead && yCoordinateApple == yCoordinateSnakeHead) {
            collisionWithApple = true;
            numberOfEatedApples++;
        }



        //Creating the snake's body
        if(lastxCoordinateSnakeHead == -1){
            lastxCoordinateSnakeHead = xCoordinateSnakeHead;
            lastyCoordinateSnakeHead = yCoordinateSnakeHead;
            xcoordinatesOfBodyParts.add(0);
            ycoordinatesOfBodyParts.add(0);
            xcoordinatesOfBodyParts.set(0,lastxCoordinateSnakeHead);
            ycoordinatesOfBodyParts.set(0,lastyCoordinateSnakeHead);
        }
        if(lastxCoordinateSnakeHead != -1){
            lastxCoordinateSnakeBody = xcoordinatesOfBodyParts.get(0);
            lastyCoordinateSnakeBody = ycoordinatesOfBodyParts.get(0);
            xcoordinatesOfBodyParts.set(0,lastxCoordinateSnakeHead);
            ycoordinatesOfBodyParts.set(0,lastyCoordinateSnakeHead);
            lastxCoordinateSnakeHead = xCoordinateSnakeHead;
            lastyCoordinateSnakeHead = yCoordinateSnakeHead;
        }

        g.setColor(Color.BLACK);
        g.fillOval(xcoordinatesOfBodyParts.get(0),ycoordinatesOfBodyParts.get(0),40,40);

        //Adding body parts after eating an apple
        if (needToAddBodyPart) {
            int lastX = xcoordinatesOfBodyParts.get(xcoordinatesOfBodyParts.size() - 1);
            int lastY = ycoordinatesOfBodyParts.get(ycoordinatesOfBodyParts.size() - 1);
            xcoordinatesOfBodyParts.add(lastX);
            ycoordinatesOfBodyParts.add(lastY);
            needToAddBodyPart = false;
        }

        //Update arraylist coordinates
        for (int i = xcoordinatesOfBodyParts.size() - 1; i > 0; i--) {
            xcoordinatesOfBodyParts.set(i, xcoordinatesOfBodyParts.get(i - 1));
            ycoordinatesOfBodyParts.set(i, ycoordinatesOfBodyParts.get(i - 1));
        }

        for(int i= 0; i< xcoordinatesOfBodyParts.size(); i++){
            g.fillRect(xcoordinatesOfBodyParts.get(i),ycoordinatesOfBodyParts.get(i),40,40);
        }

        //Pontuation
        g.drawString(String.valueOf(numberOfEatedApples),800,870);

        //Body Collision Game Over
        for(int i = 0; i < xcoordinatesOfBodyParts.size(); i++){
            if(xCoordinateSnakeHead == xcoordinatesOfBodyParts.get(i) && yCoordinateSnakeHead == ycoordinatesOfBodyParts.get(i) && globalCounter>1){
                bodyCollision =true;
            }
        }
        if(bodyCollision){
            timer.stop();
            g.setColor(Color.GREEN);
            g.drawString("GAME OVER", 400,400);
        }
    }
}
