import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Window2 extends JPanel implements ClientListener {
	JFrame frame = new JFrame();
	// JLabel background;
	// JTextArea message;
	// JPanel chatsOnLeft;
	// JPanel conversation;
	// JButton send = new JButton("Send");

	// private Client client;
	private Font cFont1;
	private Font cFont;
	private Cursor cursor;
	private JPanel convoOnRight;
	private JTabbedPane tabbedPane;
	private HashMap<String, JPanel> tabbedusers;
	private HashMap<String, Integer> FriendList;
	private Dimension DimMax;
	int buttonWidth;
	int buttonHeight;
	private JButton signOut;

	private JLabel friendlabel;

	private Font cFont2;
private String name ;
private int myheight ; 
private int mywidth ; 
Client client ;

private JPanel chatsOnLeft;

private JScrollPane chatsOnLeftScroll;

private GridLayout g;
private Color grey = new Color(24,26,26) ;  
Color golden = new Color(173,122,0) ; 

private JLabel title; 
	public Window2(Client client) { // Add Client client
		
		client.setListener(this);
		tabbedusers = new HashMap<String, JPanel>();
		FriendList = new HashMap<String, Integer>();
    this.name = client.getName() ;
	this.client = client;
		
		System.out.println(client.getName());
	
		myheight = 0 ; 
		mywidth = 0 ; 
		
		
		this.frame = new JFrame(name); // Youssef --> client.getName()
		// this.client = client;
		// this.client.setListener(this);
		try {
			// create the font to use. Specify the size!
			cFont = Font.createFont(Font.TRUETYPE_FONT, new File("GistUprightExtraboldDemo.otf")).deriveFont(40f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("GistUprightExtraboldDemo.otf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}

		try {
			// create the font to use. Specify the size!
			cFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("Lobster.ttf")).deriveFont(30f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Lobster.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		
		
		try {
			// create the font to use. Specify the size!
			cFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("Lobster.ttf")).deriveFont(23f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Lobster.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("25439.png"));
		Point hotspot = new Point(0, 0);
		cursor = toolkit.createCustomCursor(image, hotspot, "Stone");
		frame.setCursor(cursor);
         frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
		frame.setSize((int) (DimMax.width * 0.7), (int) (DimMax.height * 0.7));
		frame.setLocation((int) (DimMax.getWidth() - frame.getWidth()) / 2,
				(int) (DimMax.getHeight() - frame.getHeight()) / 2);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		Icon i = new ImageIcon(getClass().getResource("RWDoMb.jpg"));
		JLabel background = new JLabel(i);
		background.setBounds(frame.getBounds());
		background.setLayout(null);
		frame.setContentPane(background);

		
		buttonHeight = frame.getHeight() / 15;
		buttonWidth = frame.getWidth() / 13;
		convoOnRight = new JPanel();
		convoOnRight.setSize((int) (0.8 * frame.getWidth()), frame.getHeight());
	
		convoOnRight.setLocation((int) (0.2 * frame.getWidth()), 0);
		convoOnRight.setLayout(null);
		convoOnRight.setOpaque(false); 
		

		 title = new JLabel("Hello , " + client.getName()+" ") ; 
		title.setFont(cFont1); 
		title.setForeground(golden);
		title.setBackground(Color.DARK_GRAY);
	    title.setOpaque(false);
		title.setBorder(null);
		title.setSize(title.getPreferredSize()); 
		title.setLocation((frame.getWidth()- title.getWidth())/2, 0);
		frame.add(title) ;
	

		signOut = new JButton("Sign Out");
		signOut.setFont(cFont1);
		signOut.setForeground(Color.red);
		signOut.setBackground(Color.DARK_GRAY);
		signOut.setContentAreaFilled(false);
		signOut.setBorder(null);
		signOut.setFocusable(false);
		
		signOut.setSize(signOut.getPreferredSize());
		signOut.setLocation(convoOnRight.getWidth() - signOut.getWidth()-10, 0);
	
		signOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// Sign Outj
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out , Paaal ?", "Sign out", JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION){
			client.ps.println("bye");
		}
			}
		});
		signOut.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				// signOut.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				signOut.setForeground(Color.RED);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				signOut.setForeground(golden);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		convoOnRight.add(signOut);

//		friendlabel = new JLabel("Youssef");														//TESTING ONLY
//		friendlabel.setSize((int) (0.2 * convoOnRight.getWidth()), (int) (0.08 *						//TESTING ONLY
//		 convoOnRight.getHeight()));																//TESTING ONLY
//		friendlabel.setFont(cFont1);																		//TESTING ONLY
//		friendlabel.setLocation(10, 0);																	//TESTING ONLY	
//		 convoOnRight.add(friendlabel);																	//TESTING ONLY
		 																							//TESTING ONLY
//		 JPanel test = new JPanel();																//TESTING ONLY
//		 test.setSize(convoOnRight.getWidth(),														//TESTING ONLY
//		 frame.getHeight()-signOut.getHeight());													//TESTING ONLY
//		 test.setLocation(0 ,Math.max(signOut.getHeight(),friendlabel.getHeight() ) 													//TESTING ONLY
//		 );																							//TESTING ONLY	
//		 test.setBackground(Color.BLUE);															//TESTING ONLY
//		 convoOnRight.add(test);		
		convoOnRight.setBorder(null);//TESTING ONLY

		tabbedPane = new JTabbedPane();
		tabbedPane.setOpaque(false);
		tabbedPane.setSize(convoOnRight.getWidth(),
				(int) (frame.getHeight() - signOut.getHeight()));
//		myheight = tabbedPane.getHeight() ;  
//		mywidth = tabbedPane.getWidth() ; 
		tabbedPane.setLocation(0, (int) ((int) signOut.getHeight()*1.5));
		tabbedPane.setFont(cFont2);
		
	
		convoOnRight.add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		//tabbedPane.setBackground(new Color(24,26,26));
		//tabbedPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));

		System.setProperty("awt.useSystemAAFontSettings", "on");
		
		System.setProperty("swing.aatext", "true");
		
		frame.add(convoOnRight);
		
		generateLeftPanel();
		
		frame.repaint();
		frame.validate();
		new Thread (new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try { 
					Thread.sleep(1000);
				}
				catch (Exception e ) { 
					
				}
				client.ps.println("getMemberList");
			}
		}) .start () ; 
//		
		// client.getAllMemberList();

	}
public void generatbuttons () {
chatsOnLeft.removeAll();
g.setRows(Math.max(12, FriendList.size()));
	Iterable<Entry<String, Integer>> s = FriendList.entrySet();
	for (Entry<String, Integer> entry : s) {
		if (!entry.getKey() .equals(name) ) {
		String friend = entry.getKey();
		JButton button = new JButton(friend);
		button.setEnabled(true);
		button.setBackground(Color.BLACK);
		button.setFont(cFont1);
		button.setForeground(golden);
		button.setFocusable(false);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = button.getText();
				if (tabbedusers.get(username) == null) {
					addTab(username);
					tabbedPane.setSelectedComponent(tabbedusers.get(username));
					tabbedPane.getSelectedComponent().setBackground(golden);
					convoOnRight.repaint(); convoOnRight.validate();

				} else {
					tabbedPane.setSelectedComponent(tabbedusers.get(username));
					tabbedPane.getSelectedComponent().setBackground(golden);
					convoOnRight.repaint(); convoOnRight.validate();
				}
			        System.out.println(username);
				 friendlabel=new JLabel((username));
				friendlabel.setSize(friendlabel.getSize());
				friendlabel.setFont(cFont1);
				friendlabel.setLocation(10, 0);
				frame.validate();frame.repaint();
				
				convoOnRight.repaint(); convoOnRight.validate();
				
			}
		});
		chatsOnLeft.add(button);
		}
	}
	chatsOnLeft.repaint(); chatsOnLeft.validate();
	frame.validate () ; frame.repaint () ; 
}
	public void generateLeftPanel() {
   JLabel onlineClients = new JLabel( " Online Pals : " ) ; 
    onlineClients.setFont(cFont1); 
    onlineClients.setForeground(golden);

    onlineClients.setBorder(null);
    onlineClients.setSize(onlineClients.getPreferredSize()); 
    onlineClients.setLocation(0, 0); 
    frame.add(onlineClients) ; 


		 chatsOnLeft = new JPanel();
	
		 g =new GridLayout( 0,Math.max(1,FriendList.size())) ; 
		 g.setRows(Math.max(11, FriendList.size()));
		 g.setVgap(10);
		 
		chatsOnLeft.setLayout(g);
		

		chatsOnLeft.setSize((int) (0.2*frame.getWidth()),frame.getHeight()-onlineClients.getHeight());
		
		FriendList = new HashMap<String, Integer> () ;
		
		 chatsOnLeftScroll = new JScrollPane(chatsOnLeft);
		 chatsOnLeft.setForeground(Color.darkGray);
			chatsOnLeft.setBackground(Color.darkGray);
			chatsOnLeftScroll.setBackground(Color.darkGray);
			chatsOnLeftScroll.setForeground(Color.darkGray);
			chatsOnLeftScroll.setBorder(BorderFactory.createLineBorder(Color.black, 4,true)) ;
		// chatsOnLeftScroll.setOpaque(false);
		chatsOnLeft.setAutoscrolls(true);
		chatsOnLeftScroll.setSize((int) (0.2*frame.getWidth()) , frame.getHeight()-onlineClients.getHeight());
		

		chatsOnLeftScroll.setLocation(0, onlineClients.getY()+onlineClients.getHeight());
		
		generatbuttons();
		chatsOnLeft.repaint(); chatsOnLeft.validate(); 
		chatsOnLeftScroll.validate(); 
		chatsOnLeftScroll.repaint();
		frame.add(chatsOnLeftScroll);
		frame.add(chatsOnLeftScroll);
		frame.repaint();frame.validate();
		
	}
	

	public void addTab(String friendName) {
		
		JTextField sendMessage = new JTextField("");
		JButton send = new JButton("Send");
		JPanel messages = new JPanel() ;
		mypanel panel1 = new mypanel (messages);
		//panel1.setOpaque(false);
		//panel1.setBackground(null);
//		ImageIcon iii = new ImageIcon(getClass().getResource("RightBackGround.jpg")) ; 

		
//		JLabel tempo = new JLabel(iii); 
	
		tabbedPane.addTab(friendName, null, panel1, null);
		//tabbedPane.setOpaque(false);

       
		//tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedusers.put(friendName, panel1);
				System.out.println(panel1.getSize());
		panel1.setLayout(null);
		panel1.setBorder(null);
		if (myheight == 0){
           myheight= panel1.getHeight() + panel1.getY() ; 
		mywidth = panel1.getWidth() ;
		}

		
	
		sendMessage.setToolTipText("Type in your message here!");
		sendMessage.setFont(cFont1);
	
		sendMessage.setBorder(null);
		sendMessage.setBackground(Color.DARK_GRAY);
		sendMessage.setForeground(Color.LIGHT_GRAY);
		
		send.setFont(cFont);
		send.setForeground(golden);
		send.setBackground(Color.BLACK);
		send.setContentAreaFilled(true);
		send.setBorder(null);
		send.setFocusable(false);
		//send.setOpaque(false);
		send.setSize(send.getPreferredSize());
		send.setSize((int) mywidth, send.getHeight());
		
	
		send.setLocation(0, (int) (myheight - 2*send.getHeight()) ); 
		sendMessage.setSize(mywidth, send.getHeight());
		sendMessage.setLocation(0, (int) ((int) send.getY() -  send.getHeight()) ); 
	
	// remove
																								// -100
		panel1.add(sendMessage);
		panel1.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (friendName == ((mypanel)tabbedPane.getSelectedComponent()).getName()){ 
					if (e.getKeyChar() == KeyEvent.VK_ENTER)
						send.doClick();
				}
			}
		});

		// remove
																														// -100
		send.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				send.setForeground(golden);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				send.setForeground(Color.red);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		GridLayout l = new GridLayout(0, 1) ; 
		l.setVgap(buttonHeight/3);
		int row = 12 ; 
		l.setRows(row);
		messages.setLayout(l);
	//	messages.setOpaque(false);
		messages.setLocation(0, 0) ;
		messages.setBackground(Color.black);
		JScrollPane js = new JScrollPane(messages) ;
 //js.setOpaque(false);
        js.setBackground(Color.black);
		//tempo.setSize(panel1.getSize());
		
    	send.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JLabel message = new JLabel(" "+name + " : " + sendMessage.getText());
			message.setSize(message.getSize());
			message.setFont(cFont2);
			message.setOpaque(false);
			message.setForeground(Color.white);
			
			messages.add(message);
			
		//	System.out.println(messages.getComponents().length);
			if (messages.getComponents().length >12 ) { 
				l.setRows(message.getComponents().length);
			}
			if (FriendList.get(friendName)!=null)
			client.ps.println("SendthisMsg,"+FriendList.get(friendName)+"," + sendMessage.getText());
			else {
				JOptionPane.showConfirmDialog(null, "Don't embarrass yourself pal , This Client already left .." , "Come on paal" , JOptionPane.CLOSED_OPTION ) ;  
			}
			sendMessage.setText("");
			frame.repaint(); frame.validate();
			panel1.repaint(); panel1.validate();
			convoOnRight.repaint(); convoOnRight.validate();
		}
	});
	
	
	

	panel1.add(send);

	js.setSize(send.getWidth() , (int) (sendMessage.getY() )) ; 
	js.setLocation(0, 0); 
	js.setAutoscrolls(true);
	
	js.addMouseWheelListener(new MouseWheelListener() {
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent arg0) {
			// TODO Auto-generated method stub
			panel1.repaint(); panel1.validate();
			convoOnRight.repaint(); convoOnRight.validate();
			frame.repaint(); frame.validate();
		}
	});
	panel1.add(js) ; 
	    // convoOnRight.add(panel1);
		panel1.repaint(); panel1.validate();
		convoOnRight.repaint(); convoOnRight.validate();
		frame.repaint(); frame.validate();
	}


	@Override
	public void OnClientEvent(ClientEvent e) {
		// TODO Auto-generated method stub

		switch (e.getClientEventType()) {
		
		case MESSAGE:
			String name = "" ; 
			Iterable<Entry<String, Integer>> s = FriendList.entrySet();
			for (Entry<String, Integer> entry : s) {
				if (entry.getValue() == e.getClientPort()) {
				
					name = entry.getKey() ; 
					break ; 
			}
			}
			if (tabbedusers.get(name) == null ) { 
				addTab(name); 
				((mypanel)tabbedusers.get(name)).handleMessage(name, e.getMsg());
				tabbedPane.setSelectedComponent(tabbedusers.get(name));
				convoOnRight.repaint(); convoOnRight.validate();
			}
			else 
			{
				((mypanel)tabbedusers.get(name)).handleMessage(name, e.getMsg());
				tabbedPane.setSelectedComponent(tabbedusers.get(name));
				convoOnRight.repaint(); convoOnRight.validate();
			}
				
			break;
		case REFRESH: 
		
		new Thread (new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (chatsOnLeft == null) ;

				FriendList = new HashMap<String, Integer> () ; 
				
			
			String temp [] = e.getMsg().split(",") ; 
			for (int i = 1; i < temp.length; i+=2) {
				if (temp[i].equals("")) { 
					i--;
				}
				else 
				if(!temp[i].equals("")||! temp[i+1 ].equals (""))
				FriendList.put(temp[i+1]	, Integer.parseInt(temp[i])) ;
		
			}
				generatbuttons();
			}
		}).start();
	
		
			break;
		case SIGNOUT:
			System.exit(0); 
			break;
		case ERROR : 
		default:
			break;

		}
	}

	
	@SuppressWarnings("unused")
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

	

	

	public static void main(String[] args) {
		// Client c= new Client("Ahmed",34);
	 //new Window2("Joe");

	}

}
class mypanel extends JPanel  { 
	JPanel j ;
	private Font cFont2; 
	public mypanel(JPanel j ) {
		// TODO Auto-generated constructor stub 
		this.j = j ; 
		try {
			// create the font to use. Specify the size!
			cFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("Lobster.ttf")).deriveFont(23f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Lobster.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}
	public void handleMessage(String name , String msg ) {
		// TODO Auto-generated method stub
		JLabel message = new JLabel(" "+name + " : " + msg);
		message.setSize(message.getPreferredSize());
		message.setFont(cFont2);
		message.setOpaque(false);
		message.setForeground(Color.white);
		j.add(message);
	//	System.out.println(messages.getComponents().length);
		if (j.getComponents().length >12 ) { 
			((GridLayout) j.getLayout()).setRows(message.getComponents().length);
		}
		
	j.repaint(); j.validate(); 
	}
	
	
}
