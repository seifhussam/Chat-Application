import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Window1 extends JFrame {
	private JFrame frame;
	private JLabel user;
	private JLabel pass;
	private JTextField usernameta;
	private JPasswordField passwordta;
	private JLabel loading;
	private JButton login;
	private JButton create;
	private Font cFont;
	private Font cFont1;
	private Cursor cursor;
	private JLabel title;
	private JButton createnewpal;
	private JButton Exit;
	Color golden = new Color(173,122,0) ; 
	public Window1() {
		this.frame = new JFrame("Pals");
      
		try {
			// create the font to use. Specify the size!
			cFont = Font.createFont(Font.TRUETYPE_FONT,
					new File("GistUprightExtraboldDemo.otf")).deriveFont(70f);
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(
					"GistUprightExtraboldDemo.otf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}

		try {
			// create the font to use. Specify the size!
			cFont1 = Font.createFont(Font.TRUETYPE_FONT,
					new File("Lobster.ttf")).deriveFont(50f);
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(
					"Lobster.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}

		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("25439.png"));
		Point hotspot = new Point(0, 0);
		cursor = toolkit.createCustomCursor(image, hotspot, "Stone");
		frame.setCursor(cursor);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();

		frame.setSize((int) (DimMax.width * 0.7), (int) (DimMax.height * 0.7));
		frame.setResizable(false);
		frame.setLocation((int) (DimMax.getWidth() - frame.getWidth()) / 2,
				(int) (DimMax.getHeight() - frame.getHeight()) / 2);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		Icon i = new ImageIcon(getClass().getResource("RWDoMb.jpg"));
		JLabel background = new JLabel(i);
		background.setBounds(frame.getBounds());
		background.setLayout(null);
		frame.setContentPane(background);
		frame.repaint();
		frame.validate();
        title = new JLabel ("Paaaaaals") ;  
       title.setForeground(golden);
title.setFont(cFont);
// System.out.println(0.3*frame.getWidth() + " " +
// 0.1*frame.getHeight());
title.setSize(title.getPreferredSize());
//user.setSize(((int) (0.2 * frame.getWidth())),
//		((int) (0.2 * frame.getHeight())));
title.setLocation((frame.getWidth() - title.getWidth())/2 , 30);
frame.add(title) ;


		this.user = new JLabel("Username");
		user.setForeground(golden);
		user.setFont(cFont1);
		// System.out.println(0.3*frame.getWidth() + " " +
		// 0.1*frame.getHeight());
		user.setSize(user.getPreferredSize());
//		user.setSize(((int) (0.2 * frame.getWidth())),
//				((int) (0.2 * frame.getHeight())));
		user.setLocation(((int)(0.2*frame.getWidth())),((int)(0.3*frame.getHeight())));
		user.validate();
		user.repaint();

		this.pass = new JLabel("Password");
		pass.setForeground(golden);
		pass.setFont(cFont1);
		pass.setSize(pass.getPreferredSize());
		pass.setLocation(user.getX() , user.getY() + user.getHeight() + 50);

		ImageIcon i2 = new ImageIcon(getClass().getResource("loader2.gif"));
	
		this.loading = new JLabel(i2);
		// loading.setFont(new Font(Font.DIALOG,Font.BOLD,100));
		loading.setSize(loading.getPreferredSize());
		loading.setLocation(frame.getWidth()- loading.getWidth() , frame.getHeight() - loading.getHeight());
		// loading.setBounds(500,500,500,500);
		loading.setVisible(false);

		this.usernameta = new JTextField();
		usernameta.setBackground(Color.DARK_GRAY);
		usernameta.setForeground(Color.WHITE);
		usernameta.setBorder(null);
		usernameta.setSize(user.getWidth()*2 , user.getHeight());
		usernameta.setLocation(user.getX() + user.getWidth() + 40 , user.getY());
		usernameta.setFont(cFont1);

		this.passwordta = new JPasswordField();
		passwordta.setBackground(Color.DARK_GRAY);
		passwordta.setForeground(Color.WHITE);
		passwordta.setEchoChar('*');
		passwordta.setSize(usernameta.getSize());
		passwordta.setBorder(null);
		passwordta.setLocation(usernameta.getX() , pass.getY());
		passwordta.setFont(cFont1);

		this.login = new JButton("Login");
		login.setBackground(Color.DARK_GRAY);
		
		login.setForeground(golden);
		login.setFont(cFont1);
		login.setFocusable(false);
	login.setBorder(null); 
		login.setContentAreaFilled(false); 
		login.setOpaque(false); 
		login.addMouseListener(new MouseListener() {
			
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
				login.setForeground(golden);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				login.setForeground(Color.red);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		login.setSize(login.getPreferredSize());
		login.setLocation(frame.getWidth()- login.getWidth(),(int) (frame.getHeight() - login.getHeight()));
		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!(removeSpaces(usernameta.getText()).equals(""))) {
new Thread (new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		initiate2ndWindow(removeSpaces(usernameta.getText()),
				removeSpaces(passwordta.getText()), true);
	}
}) .start();
				
					login.setVisible(false);
					frame.repaint();
					frame.validate();
				}

				else
					JOptionPane.showConfirmDialog(null,
							"Please enter a valid Username . ", "Login Error",
							JOptionPane.CLOSED_OPTION);
			}
		});

		this.create = new JButton("Sign Up");
		create.setBackground(Color.DARK_GRAY);
		
		create.setForeground(golden);
		create.setFont(cFont1);
		create.setSize(create.getPreferredSize());
		create.setFocusable(false);
		create.setLocation(0 , (int) (frame.getHeight() - create.getHeight()));
		create.setBorder(null); 
		create.setContentAreaFilled(false);; 
		create.setOpaque(false); 
		create.addMouseListener(new MouseListener() {
			
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
				create.setForeground(golden);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				create.setForeground(Color.red);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		create.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				usernameta.setText(""); 
				passwordta.setText(""); 
				
				

				JLabel confirm = new JLabel("Confirm Password");
				confirm.setForeground(golden);
				confirm.setFont(cFont1);
				confirm.setSize(confirm.getPreferredSize());
				confirm.setLocation(pass.getX()- (confirm.getWidth() - pass.getWidth()),pass.getY()+confirm.getHeight()+50 );
				background.add(confirm);

				JPasswordField copasswordta = new JPasswordField();
				copasswordta.setBackground(Color.DARK_GRAY);
				copasswordta.setForeground(Color.WHITE);
				copasswordta.setFont(cFont1);
				copasswordta.setSize(passwordta.getSize());
				copasswordta.setLocation(passwordta.getX(), confirm.getY());
				copasswordta.setEchoChar('*');
				copasswordta.setBorder(null);
				
				
				background.add(copasswordta);

				create.setVisible(false);
				login.setVisible(false);

				 createnewpal = new JButton("New Paaaaaal");
				createnewpal.setBackground(Color.DARK_GRAY);
				
				createnewpal.setForeground(golden);
				createnewpal.setOpaque(false); 
				createnewpal.setFocusable(false); 
			//	createnewpal.setBackground(null) ; 
				createnewpal.setContentAreaFilled(false);
				createnewpal.setBorder(null);
				createnewpal.addMouseListener(new MouseListener() {
					
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
						createnewpal.setForeground(golden	);
					}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						createnewpal.setForeground(Color.red);
					}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				createnewpal.setFont(cFont1);
				
				createnewpal.setBorder(null); 
				createnewpal.setFocusable(false);
			
				createnewpal.setSize(createnewpal.getPreferredSize());

				createnewpal.setLocation(frame.getWidth() - createnewpal.getWidth() ,(int) ((int) frame.getHeight() - (createnewpal.getHeight()))) ; 
				createnewpal.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e) {
						
						if (removeSpaces(usernameta.getText()).equals(""))
							JOptionPane.showConfirmDialog(null,
									"Please enter a valid Username . ",
									"Login Error", JOptionPane.CLOSED_OPTION);

						else if (removeSpaces(passwordta.getText()).equals(""))
							JOptionPane.showConfirmDialog(null,
									"Please enter a valid Password . ",
									"Login Error", JOptionPane.CLOSED_OPTION);

						else if ((!(removeSpaces(copasswordta.getText())
								.equals(passwordta.getText())))
								&& (!(passwordta.getText().equals("")))) {
							JOptionPane
									.showConfirmDialog(
											null,
											"Passwords do not match. Please confirm the password again. ",
											"Login Error",
											JOptionPane.CLOSED_OPTION);
						} else {
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									initiate2ndWindow(
											removeSpaces(usernameta.getText()),
											removeSpaces(passwordta.getText()), false);
								}
							}).start();
							
							
						
							frame.repaint();
							frame.validate();

						}
					}
				});
				background.add(createnewpal);
				frame.repaint(); frame.validate();

			}
		});

		// JLabel[][] labelList = new JLabel[6][3];
		// for (int i = 0; i < 6; i++) {
		// for (int j = 0; j < 3; j++) {
		// labelList[i][j] = new JLabel();
		// labelList[i][j].setLayout(new BorderLayout());
		// }
		// }
		// labelList[2][1].add(user, BorderLayout.CENTER);
		// labelList[3][1].add(space, BorderLayout.CENTER);
		// labelList[5][2].add(loading,BorderLayout.CENTER);
		// labelList[4][1].add(login, BorderLayout.CENTER);
		// for (int i = 0; i < 6; i++) {
		// for (int j = 0; j < 3; j++) {
		// background.add(labelList[i][j]);
		// }
		// }
		
		Exit = new JButton("Exit") ; 
		Exit.setFont(cFont1); 
		Exit.setSize(Exit.getPreferredSize()); 
		Exit.setLocation(frame.getWidth()- Exit.getWidth() , 0);
		Exit.setFocusable(false); 
		Exit.setOpaque(false); 
		Exit.setContentAreaFilled(false);
		Exit.setBorder(null);
		Exit.setForeground(Color.red);
		Exit.addMouseListener(new MouseListener() {
			
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
				Exit.setForeground(Color.red) ; 
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Exit.setForeground(golden) ; 
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		Exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit , paaaal ? ", "Exit", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
		background.add (Exit) ; 
		background.add(user);
		background.add(pass);
		background.add(login);
		background.add(create);
		background.add(usernameta);
		background.add(passwordta);
		background.add(loading);
		frame.repaint();
		frame.validate();
		// System.out.println(frame.getHeight());
		// System.out.println(frame.getWidth());
	}

	public void initiate2ndWindow(String user, String pass, boolean bb) {
		// TODO Auto-generated method stub
	
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (createnewpal!=null)
				createnewpal.setVisible(false); 
				frame.repaint();frame.validate();
				if (bb) {
					loading.setVisible(true);
					try {
						Socket s = new Socket(host, hostport);
						PrintStream ps = new PrintStream(s.getOutputStream());
						ps.println("");
						Thread.sleep(40);
						//System.out.println(s.getPort());
						ps.println("RequestToSignIn," + s.getLocalPort() + ","
								+ user);

						BufferedReader bf = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
						boolean b = true;

						while (!bf.ready()) ;

						String temp = bf.readLine();
                         System.out.println(temp);
						String passFromTheServer = "";
						if (temp.equals("ServerMessage : Rejected")) {
							b = false;
						} else {
							passFromTheServer = temp;
						}
	
						if ( (!b || !passFromTheServer.equals(removeSpaces(pass)))) {
							
							JOptionPane
									.showConfirmDialog(
											null,
											"Ooops , Please check that username and password you entered is correct , paaaaaal",
											"SignInError",
											JOptionPane.CLOSED_OPTION);
							loading.setVisible(false);
							login.setVisible(true);
							s.close();

						} else {
							new Thread (new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
										ps.println("getMemberList"); 
										
										while (!bf.ready()) ; 
										
										System.out.println("No response ");
									
										String [] arr = bf.readLine().split("," ) ; 
								boolean  b = true ; 
										for (int i = 1; i < arr.length; i++) {
										if(arr[i].equals(user)) {
											b = false ;
											break;
										}
										}
										if (b) {
									Client client = new Client(removeSpaces(user),
											hostport);
								    new Window2(client);
									loading.setVisible(true);
								
									frame.setVisible(false);
									frame.repaint();
									frame.validate();
								
										}
										else { 
											JOptionPane.showConfirmDialog(null, "This user is already signed in ", "SignIn Error", JOptionPane.CLOSED_OPTION);
											loading.setVisible(false);
											login.setVisible(true);
										}
										s.close();
									} 
									catch (Exception e) { 
										e.printStackTrace();
									}
								}
							}) .start();
  
							

						}

						
					} catch (Exception e) {
					System.out.println(e.getMessage());
						JOptionPane
								.showConfirmDialog(
										null,
										"The Server is not online , Please try again later paaaaaal ",
										"Error", JOptionPane.CLOSED_OPTION);
						loading.setVisible(false);
						login.setVisible(true);
					}
				} else {
					loading.setVisible(true);
					try {
						Socket s = new Socket(host, hostport);
						PrintStream ps = new PrintStream(s.getOutputStream());
						BufferedReader bf = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
						ps.println("");
						Thread.sleep(40);

						ps.println("RequestToCreateANewUser,"+   user + "," + pass);

					

						while (!bf.ready()){
						System.out.println(" bf is not ready ");	
						}

						String temp = bf.readLine();
						System.out.println("Temp is not there yet");
						
						System.out.println("Temp + " +temp);
						System.out.println(temp);

						if (!temp.equalsIgnoreCase("ServerMessage : Rejected")) {
							JOptionPane
									.showConfirmDialog(null,
											"Come on paaal , be creative . This username already exsists","Error",JOptionPane.CLOSED_OPTION);
							loading.setVisible(false);
							createnewpal.setVisible(true); 
							frame.repaint();frame.validate();

						} else {
							ps.println("RegisterMeThisClient," + user + ","
									+ pass);
							Client client = new Client(removeSpaces(user),
									hostport);

							 new Window2(client);
							 
							 
							frame.setVisible(false);
							frame.repaint();
							frame.validate();
						}
					} catch (Exception e) {
            e.printStackTrace();
					}
				}
			}
		}).start();

	}

	public static String removeSpaces(String s) {
		String x = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ')
				x += s.charAt(i);

		}

		return x;

	}

	String host = "192.168.43.192";
	int hostport = 6006;
	
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
		new Window1();
	}
}
