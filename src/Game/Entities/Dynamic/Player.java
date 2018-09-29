package Game.Entities.Dynamic;

import Main.GameSetUp;
import Main.Handler;



import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JOptionPane;
import Game.GameStates.State;

public class Player {

    public int lenght;
    public boolean justAte;
    private Handler handler;

    public int xCoord;
    public int yCoord;
    public int points;

    public int moveCounter;
    public int speed=5;
    public String direction;

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
        lenght= 1;
        points=0;

    }

    public void tick(){
    	
        moveCounter++;
        if(moveCounter>=speed) {
            checkCollisionAndMove();
            moveCounter=0;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)){
            if(direction != "Down") {
        	direction="Up";}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)){
        	if(direction != "Up") {
        	direction="Down";}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)|| handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)){
        	if(direction != "Right") {
        	direction="Left";}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)|| handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)){
        	if(direction != "Left") {
        	direction="Right";}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
        	if(lenght <= 1000) {
        	Eat();
        	handler.getWorld().appleOnBoard = true;
        	}
        	
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)) {
        	//render(); Pause the game on pressed, there is an image for so.
        	State.setState(handler.getGame().pauseState);
        	
        	
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
        	        	//run faster
        	speed--;}
        	
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
        	speed++;    	
        }

    }
//Here I can change the size of the game for example if a ireduce the size of the world i 
    //have to reduce the xCoord and y
    
    
    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
                if(xCoord==0){
                    kill();
                }else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
                }else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                    kill();
                }else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
                }else{
                    yCoord++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;


        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }

    }

    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
        //told by others to use a gfont on render method, after looking for Graphics class, then i knew.
        // Points 
        Font pointTxt = new Font("Arial", Font.PLAIN,20);
        g.setFont(pointTxt);
        g.drawString("points :" +points,500 , 550);
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(Color.GREEN);

                if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }
                //selfCrash new variable to set the value of the body to it
                //for the for loop it keeps iterating therefore its never stops the command, how to solve this
                //possibly a while,keep increasing until so it stops, but with a while the program is very slow, need guidence.
                 
                	for(int actualBody=0;actualBody< handler.getWorld().body.size();actualBody++) {
                
                	if(xCoord== handler.getWorld().body.get(actualBody).x && yCoord== handler.getWorld().body.get(actualBody).y) {
                		kill(); //cannot press Yes, cause of the For loop.
//                	JOptionPane.showMessageDialog(null, "Game Over", "", JOptionPane.INFORMATION_MESSAGE);
//                	System.exit(0);
                	
                		
                	}}}
                	
                	}
    }

          
    

    public void Eat(){
        lenght++;
         points=points +5; //points increment
        Tail tail= null;
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
        //implement the if eat increase points label
       // System.out.println(points);
    }

    public void kill(){
        lenght = 0;
        int again;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=false;
                
            }
        }
        
       again=JOptionPane.showConfirmDialog(null, " Final score:   " +points, "Game Over, Try again?", JOptionPane.YES_NO_OPTION);
        if(again==0) {
        
        	State.setState(handler.getGame().menuState);}
        else
        	System.exit(0);
        
        //create a frame with Game Over message if killed.
        //GameOver();
        
    }
    
    
    
    	
    		
    	
    
    

    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
}
