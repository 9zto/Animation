import javax.swing.*;
import java.awt.*;

//NOTE FROM JACOB: COMMENT YOUR CODE pls

final public class Animate {

    JFrame frame;
    DrawPanel drawPanel;

    private int oneX = -100; // Starting X coordinate
    private int oneY = 100; // Starting Y coordinate
    
    private int twoX = -100; // Starting X coordinate
    private int twoY = 100;
    
    private Color background = new Color(37, 196, 196);
    
    private int frameHeight = 600;
    private int frameWidth = 600;

    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;
    
    boolean day = true;
    
    int dayColorR = 20;
    int dayColorG = 20;
    int dayColorB = 20;
    
    int dayColorR2 = 38;
    int dayColorG2 = 35;
    int dayColorB2 = 81;
    
    int dayColorR3 = 38;
    int dayColorG3 = 35;
    int dayColorB3 = 81;
    //int dayColorA = 100;

    public static void main(String[] args) {
        new Animate().go();
    }

    private void go() {
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation(100, 100);
        moveDot();
    }

    class DrawPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3030379568821478211L;
		
		public void paintComponent(Graphics g) {
        	//White Border
			/*
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            //Black Border
            g.setColor(Color.BLACK);
            g.fillRect(3, 3, this.getWidth()-6, this.getHeight()-6);
            //Inside Color
             * */
			
            background = fadeColor(oneX, background);
            g.setColor(background);
            g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
	        	if(!day) {
	        		if(twoX > .9 * frameWidth) {
	        			g.setColor(new Color(198,38,9));
	    	           	g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
	        		}else if (twoX > .80 * frameWidth) {
	        			g.setColor(new Color(38,35,81));
	    	           	g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
	        		}else {
	        		g.setColor(new Color(20, 20, 20));
		            g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
	        		}
	        	}else {
	        	g.setColor(new Color(198,38,9));
	           	g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
	        
	        }

            g.setColor(new Color(0, 255, 0));
            g.fillOval(-30, 400, 650, 650);
            
            //Dot
            g.setColor(Color.YELLOW);
            g.fillOval(oneX, oneY, 100, 100); // This is the dot
            //g.fillRect(oneX + 2, oneY - 2, 2, 10);
            //g.fillRect(oneX - 2, oneY + 2, 10, 2);
            
            g.setColor(Color.WHITE);
            g.fillOval(twoX, twoY, 100, 100);
            
        }
    }
    
    private Color fadeColor(int x, Color currentColor) {//FADES COLOR BY SWITCHING STUFF
    	if (oneX > .9 * frameWidth) {
           	return new Color(20, 20, 20);
        } else if (oneX > .85 * frameWidth) {
        	
        	return new Color(20, 20, 20);
        } else if (oneX > .80 * frameWidth) {
        	
        	
        	return new Color(38, 35, 81);
        } else if (oneX > .7 * frameWidth) {
        	System.out.println(oneX);
        	double numOfTicks = (.8 * frameWidth - .7*frameWidth);//THIS STUFF IS BORKEN
        	double rChange = ((196-37)/numOfTicks);
        	double gChange = ((31-196)/numOfTicks);
        	double bChange = ((75-196)/numOfTicks);
        	int currentRed = currentColor.getRed();
        	int currentBlue = currentColor.getBlue();
        	int currentGreen = currentColor.getGreen();
        	return new Color((int)(currentRed + rChange), (int)(currentGreen + gChange), (int)(currentBlue + bChange));
        	//end 196, 31, 75
        }else {
        	return new Color(37, 196, 196);
        }
    }

    private void moveDot() {//MOVE THE STUFF
        while(true){
            //checkBounds();
        	if(day) {
            moveLoc();
        	}else {
            moveMoon();
        	}
            try{
                Thread.sleep(10);
            } catch (Exception exc){}
            frame.repaint();
        }
    }
    private void moveLoc(){//MOVE THE SUN
    	//while(day) {
    	if(oneX > frameWidth){
            oneX = -100;
            //debug = false;
            day = false;
            //moveMoon();
        }
    	else {
    		oneY = (int)((Math.pow(oneX - frameWidth/2.5, 2.0) *1/1000)+ frameHeight - 400.0);
    		oneX++;
    	}
    	//}
    }
    
    private void moveMoon(){//MOVE THE MOON
    	//while(!day) {
    	if(twoX > frameWidth){
            twoX = -100;
            day = true;
        }
    	else {
    		twoY = (int)((Math.pow(twoX - frameWidth/2.5, 2.0) *1/1000)+ frameHeight - 400.0);
    		twoX++;
    	}
    	//}
    }
}