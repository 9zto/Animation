import javax.swing.*;
import java.awt.*;

final public class AnimationProject {

	JFrame frame;
    DrawPanel drawPanel;

    private int oneX = 100; // Starting X coordinate
    private int oneY = 100; // Starting Y coordinate

    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Animate().go();
	}
	
	private void go() {
		
		
	}
	
	class DrawPanel extends JPanel{
		
	}

}
