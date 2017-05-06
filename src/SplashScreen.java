import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class SplashScreen extends JFrame {
	
	
	
private Cursor cursor;
private Font cFont;
private Font cFont1;

public SplashScreen() {
	// TODO Auto-generated constructor stub
	
	try {
	    //create the font to use. Specify the size!
	   cFont = Font.createFont(Font.TRUETYPE_FONT, new File("GistUprightExtraboldDemo.otf")).deriveFont(70f);
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    //register the font
	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("GistUprightExtraboldDemo.otf")));
	} catch (IOException e) {
	    e.printStackTrace();
	} catch(FontFormatException e) {
	    e.printStackTrace();
	}
	try {
	    //create the font to use. Specify the size!
	   cFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("Lobster.ttf")).deriveFont(25f);
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    //register the font
	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Lobster.ttf")));
	} catch (IOException e) {
	} catch(FontFormatException e) {
	    e.printStackTrace();
	}
	
	 System.setProperty("awt.useSystemAAFontSettings","on");
	 System.setProperty("swing.aatext", "true");
	 Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(getClass().getResource("25439.png"));
        Point hotspot = new Point(0, 0);
         cursor = toolkit.createCustomCursor(image, hotspot, "Stone");
        setCursor(cursor) ;
        setLayout(null);
		Icon i = new  ImageIcon(getClass().getResource("Polygon2.jpg")) ;
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		
		JLabel temp  = new JLabel(i) ;
		setContentPane(temp);
	    setSize((int)(DimMax.width*0.5) ,(int) (DimMax.height *0.4));
	    setLocation((int)(DimMax.getWidth() - this.getWidth() )/2 , (int)(DimMax.getHeight() - this.getHeight() )/2 );
		setUndecorated(true); 
		setVisible(true);
		
		JLabel text = new JLabel("Welcome to Pals .. ");
		text.setFont(cFont);
		text.setSize(text.getPreferredSize());
		text.setForeground(Color.white);
			text.setLocation(  (this.getWidth() - text.getWidth())/2 ,(this.getHeight() - text.getHeight())/2); 
			add(text) ; 
			
			Runnable r = new Runnable() {
				   public void run() {
				    try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    setVisible(false);
				    dispose(); 
				    
				    try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				   new Window1() ;  
				    
				    
				   }
				 };
		
			Thread t  = new Thread(r) ; 
			t.start();
			
	repaint(); 
	validate() ;
}
public static void main(String[] args) {
	new SplashScreen() ; 
}
}
