import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map.Entry;


public class MainServer implements Serializable {
	
	
	
	private Integer Mainport ; 
	private ServerSocket ClientSocket ; 
	private ServerSocket Serversocket ; 
	private Integer previousport ; 
	private Integer Nextport ; 
	private Socket previousSocket ;
	
	private String host = "localhost" ; 
	private Integer id ; 
	private Socket nextlist ; 
	private Socket nextrefresh ; 
	private Socket nextSpreadThelist ; 
	private Socket nextgetClientInfo ; 
	private Socket nextfindclientandchat;
	
	private Socket prevlist ; 
	private Socket prevrefresh ; 
	private Socket prevSpreadThelist ; 
	private Socket prevgetClientInfo ; 
	private Socket prevfindclientandchat;
    private HashMap <Integer,Socket> sockets  ; 
    private HashMap <Integer,String> users ;
    private HashMap<String,String> users_password ; 
   private  String savedBefore ;
	
public MainServer(Integer mainPort, boolean nextboolean , boolean previousboolean , Integer id   ) {
	// TODO Auto-generated constructor stub
	Mainport = mainPort ; 
	previousport = mainPort-1 ; 
	Nextport= mainPort+1 ;
	this.id = id ; 
	savedBefore = "Server"+id+".ser" ; 
	sockets =new HashMap<Integer, Socket> () ;
	users = new HashMap<Integer,String>() ;
   users_password = new HashMap<String, String> ()  ; 
   try { 
            load(); 
   }
   catch (Exception e ) { 
	   System.out.println("No Saved Files Before ! ");
   }
	
	try {
		ClientSocket = new ServerSocket(mainPort) ;	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	} 

	if (nextboolean) { 
		//next Port Sockets 
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try { 
					Serversocket = new ServerSocket(Nextport) ; 
					nextfindclientandchat = Serversocket.accept() ; 
					nextlist = Serversocket.accept() ; 
					nextrefresh = Serversocket.accept() ; 
					nextSpreadThelist = Serversocket.accept() ;
					nextgetClientInfo = Serversocket.accept();
				
					
					
				}catch(Exception e ) { 
					
				}
				new Thread (new Runnable() {
					
					

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// Thread for sending a client a msg 
						try {
							
							
							new Thread ( new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try { 
										while (nextfindclientandchat.isConnected()) { 
											BufferedReader bf = new BufferedReader(new InputStreamReader(nextfindclientandchat.getInputStream()) ) ; 
											while (!bf.ready()) ; 
											String temp = bf.readLine() ; 
											new Thread (new Runnable() {
												
												@Override
												public void run() {
													// TODO Auto-generated method stub
													try { 
														if (temp.startsWith(sendmsg)) { 
															
															String arr [] = temp.split(",") ; 
															Integer idd = Integer.parseInt(arr[1]) ; 
															Integer portfrom = Integer.parseInt(arr[2]) ; 
															Integer portto = Integer.parseInt(arr[3]) ; 
															String msg = ReconstructString(4, arr) ; 
															if (sockets.get(portto)!=null)  { 
																PrintStream pss = new PrintStream(sockets.get(portto).getOutputStream()) ; 
																pss.println(portfrom +","+msg) ; 
																
															}
															else {
																if (prevfindclientandchat!=null) { 
																	PrintStream pss = new PrintStream (prevfindclientandchat.getOutputStream()) ; 
																	pss.println(temp);
																}
																else { 
																	PrintStream pss = new PrintStream (nextfindclientandchat.getOutputStream()) ; 
																	pss.println(ClientNotFound+","+idd+","+portfrom+","+portto+","+msg);
																}
															}
														}
														else if (temp.startsWith(ClientNotFound) ) { 
															String arr [] = temp.split(",") ; 
															Integer idd = Integer.parseInt(arr[1]) ; 
															Integer portfrom = Integer.parseInt(arr[2]) ; 
															Integer portto = Integer.parseInt(arr[3]) ; 
															String msg = ReconstructString(4, arr) ; 
															if (id == idd ) { 
																PrintStream pss = new PrintStream(sockets.get(portfrom).getOutputStream()) ; 
																pss.println(ClientNotFound);
															}
															else { 
																if (prevfindclientandchat!=null) { 
																	PrintStream pss = new PrintStream (prevfindclientandchat.getOutputStream()) ; 
																	pss.println(temp);
																}
															}
														}
													}
													catch (Exception e ) { 
														System.out.println(e.getMessage());
													}
												}
											}).start(); 
											
											
										}
									}
									catch (Exception e ) { 
										System.out.println(e.getMessage());
									}
								}
							}) .start () ;
							
						}
					catch (Exception e ) { 
						System.out.println(e.getMessage()); 
					}
					
						
						// Thread 
						
						
					}
				}) .start(); 
				new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try { 
										while (nextrefresh.isConnected()) { 
											try { 
												
												BufferedReader bf = new BufferedReader(new InputStreamReader(nextrefresh.getInputStream()) ) ; 
												while (!bf.ready()) ; 
												String temp = bf.readLine() ; 
												System.out
												.println("Final list = " + temp);
												 Iterable<Entry<Integer,Socket>>s = sockets.entrySet() ;
													for (Entry<Integer,Socket> entry : s) {
																PrintStream pssss = new PrintStream(entry.getValue().getOutputStream()) ; 
															
																pssss.println(temp);
																Thread.sleep(100);
													}
													if (prevrefresh!=null){
														PrintStream ps = new PrintStream(prevrefresh.getOutputStream()) ; 
														ps.println(temp); 
													}
														
												 
											}
											catch (Exception e ) { 
												System.out.println(e.getMessage());
											}
										}
									}
									catch (Exception e ){ 
										System.out.println(e.getMessage());
									}
									
								}
							}).start();
						}
					catch (Exception e ) { 
						System.out.println(e.getMessage()); 
					}
						
					}
				}) .start () ; 
	        new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							 
                  new Thread (new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try { 
										while (nextSpreadThelist.isConnected()) { 
											try { 
												BufferedReader bf = new BufferedReader(new InputStreamReader(nextSpreadThelist.getInputStream()) ) ; 
												while (!bf.ready()) ; 
												String temp = bf.readLine() ; 
												System.out
														.println(temp);
												if (temp.startsWith(getMemberList)) { 
													
													if (prevSpreadThelist!=null ) { 
														PrintStream pss = new PrintStream(prevSpreadThelist.getOutputStream()) ; 
														pss.println(temp);
													} 
													else { 
														
														String t = "" ; 
														Iterable<Entry<Integer,String>>s = users.entrySet() ;
														for (Entry<Integer,String> entry : s) {
																	t+= "" + entry.getKey() +"," +entry.getValue() +","; 
														}
														String arr [] = temp.split(",") ; 
														
														Integer idd = Integer.parseInt(arr[1]) ; 
														//Integer port = Integer.parseInt(arr[2]) ; 
														
														String out = requestlist+","+idd + ","+t ; 
														PrintStream pss = new PrintStream(nextSpreadThelist.getOutputStream()) ; 
														pss.println(out);
													}
													
												}
												else if (temp.startsWith(requestlist)) { 
													String arr [] = temp.split(",") ; 
													Integer Idd = Integer.parseInt(arr[1]) ; 
													if (Idd == id ) { 
													//	Integer port = Integer.parseInt(arr[2]) ; 
														String list = ReconstructString(2, arr ) ; 
//														
														String t = "," ; 
														Iterable<Entry<Integer,String>>s = users.entrySet() ;
														for (Entry<Integer,String> entry : s) {
																	t+= "" + entry.getKey() +"," +entry.getValue() +","; 
														}
														//pss.println(Refreshh+","+list+t);
														String out = Refreshh+","+list+t ; 
														System.out
																.println(out);
														if (nextrefresh!=null) { 
															PrintStream pss = new PrintStream(nextrefresh.getOutputStream());  
															pss.println (out) ; 
														}
														 if (prevrefresh!= null) { 
																PrintStream pss = new PrintStream(prevrefresh.getOutputStream());  
																pss.println (out) ; 
														 }
														 Iterable<Entry<Integer,Socket>>s1 = sockets.entrySet() ;
															for (Entry<Integer,Socket> entry : s1) {
																		PrintStream pssss = new PrintStream(entry.getValue().getOutputStream()) ; 
																		pssss.println(out);
															}

													}
													else { 
														String t = "" ; 
														Iterable<Entry<Integer,String>>s = users.entrySet() ;
														for (Entry<Integer,String> entry : s) {
																	t+= "" + entry.getKey() +"," +entry.getValue() +","; 
														}
														String x = temp+","+t ; 
														if (prevSpreadThelist!=null) { 
															PrintStream pss = new PrintStream(prevSpreadThelist.getOutputStream()) ; 
															pss.println(x); 
														}
													}
													
													
												}
												
											}
											catch (Exception e) { 
												System.out.println(e.getMessage());
											}
											
										}
										
									}
									catch (Exception e ) { 
										System.out.println(e.getMessage());
									}
								}
							}) .start(); 
							
						
					
						}
					catch (Exception e ) { 
						System.out.println(e.getMessage()); 
					}
					}
				}) .start();
				
				//Thread for getting the list from the next server 
				
				new Thread ( new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							
							new Thread (new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try { 
										while (nextlist.isConnected()) { 
											try { 
												BufferedReader bf = new BufferedReader(new InputStreamReader(nextlist.getInputStream()) ) ; 
												while (!bf.ready()) ; 
												String temp = bf.readLine() ;
												System.out
														.println(temp);
												if (temp.startsWith(getMemberList)) { 
													
													if (prevlist!=null ) { 
														PrintStream pss = new PrintStream(prevlist.getOutputStream()) ; 
														pss.println(temp);
													} 
													else { 
											
														String t = "" ; 
														Iterable<Entry<Integer,String>>s = users.entrySet() ;
														for (Entry<Integer,String> entry : s) {
																	t+= "" + entry.getKey() +"," +entry.getValue() +","; 
														}
														String arr [] = temp.split(",") ; 
														
														Integer idd = Integer.parseInt(arr[1]) ; 
														Integer port = Integer.parseInt(arr[2]) ; 
														
														String out = requestlist+","+idd + ","+port+ ","+t ; 
														PrintStream pss = new PrintStream(nextlist.getOutputStream()) ; 
														pss.println(out);
													}
													
												}
												
												else if (temp.startsWith(requestlist)) { 
													String arr [] = temp.split(",") ; 
													Integer Idd = Integer.parseInt(arr[1]) ; 
													if (Idd == id ) { 
														Integer port = Integer.parseInt(arr[2]) ; 
														String list = ReconstructString(3, arr ) ; 
														PrintStream pss = new PrintStream(sockets.get(port ).getOutputStream() ) ; 
														String t = "," ; 
														Iterable<Entry<Integer,String>>s = users.entrySet() ;
														for (Entry<Integer,String> entry : s) {
																	t+= "" + entry.getKey() +"," +entry.getValue() +","; 
														}
														pss.println(Refreshh+","+list+t);
													}
													else { 
														String t = "" ; 
														Iterable<Entry<Integer,String>>s = users.entrySet() ;
														for (Entry<Integer,String> entry : s) {
																	t+= "" + entry.getKey() +"," +entry.getValue() +","; 
														}
														String x = temp+","+t ; 
														if (prevlist!=null) { 
															PrintStream pss = new PrintStream(prevlist.getOutputStream()) ; 
															pss.println(x); 
														}
													}
													
													
												}
												
											}
											catch (Exception e) { 
												System.out.println(e.getMessage());
											}
											
										}
										
									}
									catch (Exception e ) { 
										System.out.println(e.getMessage());
									}
								}
							}) .start(); 
							
						}
					catch (Exception e ) { 
						System.out.println(e.getMessage()); 
					}
						
					}
				}) .start(); 
			
				new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							
							new Thread (new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
									while (nextgetClientInfo.isConnected()) { 
										BufferedReader bf = new BufferedReader(new InputStreamReader(nextgetClientInfo.getInputStream()) ) ; 
										while (!bf.ready()) ; 
										String temp = bf.readLine() ;
										System.out.println(temp);
										new Thread (
												
												new Runnable() {
													
													@Override
													public void run() {
														// TODO Auto-generated method stub
														try { 
															if (temp.startsWith("ClientFound") ) {
																String arr [] = temp.split(",");
																Integer idd = Integer.parseInt(arr[1]); 
															if (idd == id ) { 
																Integer port = Integer.parseInt(arr[2]) ; 
																String pass = arr[4] ; 
																//String User = arr[3] ; 
																PrintStream pss = new PrintStream(sockets.get(port).getOutputStream()) ; 
																pss.println(pass); 
															}else {
																if (prevgetClientInfo!= null ) {
																	PrintStream pss = new PrintStream(prevgetClientInfo.getOutputStream()) ; 
																	pss.println(temp);
																}
															}
															}
															else if (temp.startsWith("GetMeThisClientInfo") ) { 
																System.out
																		.println("Here");
																String arr [] = temp.split(",") ; 
																Integer id  = Integer.parseInt(arr [1] ) ;
																Integer port = Integer.parseInt(arr[2]) ; 
																String User = arr[3] ; 
																if (users_password.get(User) == null ) {
																	if (prevgetClientInfo == null ) { 
																		PrintStream pss = new PrintStream(nextgetClientInfo.getOutputStream() ) ; 
																		pss.println("ClientNotFound"+"," + id +","+port + "," +User);
																	}
																	else {
																		PrintStream pss = new PrintStream(prevgetClientInfo.getOutputStream()) ; 
																		pss.println(temp);
 																	}
																}
																else {
																PrintStream pss = new PrintStream(nextgetClientInfo.getOutputStream() ) ; 
																pss.println("ClientFound,"+id+","+ port +","+User+ ","+users_password.get(User)); 
																System.out
																		.println("found");
																
																}
																
															}
															//"ServerMessage : Rejected"
															else if (temp.startsWith("ClientNotFound")) { 
																String arr [] = temp.split(",") ; 
																Integer idd = Integer.parseInt(arr[1]) ; 
																if (idd == id ) { 
																	Integer port = Integer.parseInt(arr[2]) ; 
																	PrintStream pss = new PrintStream(sockets.get(port).getOutputStream()) ; 
																	pss.println("ServerMessage : Rejected"); 
																}
																else if (prevgetClientInfo!= null ) {
																	PrintStream pss = new PrintStream(prevgetClientInfo.getOutputStream()) ; 
																	pss.println(temp);
																}
																	
																
															}
														}
														catch (Exception e ) {
															System.out.println(e.getMessage());
														}
													}
												}) .start(); 
										
									}
									} 
									catch (Exception e ) { 
										System.out.println(e.getMessage()); 
									}
									
								}
							}) .start () ; 
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						}
						
					}
				}).start();
				
			}
		}).start();
		
		
		
		
	}
	if (previousboolean ) { 
		
		//previous port Sockets 
	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
			
					prevfindclientandchat = new Socket(host, previousport) ; 
					Thread.sleep(30);
					prevlist = new Socket(host, previousport) ;
					Thread.sleep(30);
					prevrefresh = new Socket(host, previousport) ;
					Thread.sleep(30);
					prevSpreadThelist = new Socket(host, previousport) ; 
					Thread.sleep(30);
					prevgetClientInfo = new Socket(host, previousport) ; 
					
					
					
				}  catch (Exception e) {
					// TODO Auto-generated catch block
				System.out.println("Please make sure that the previous Server is running ");
				System.exit(0);
				} 
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						// Threads to be created with their sockets 
						// Thread 1 : when getting a find me a client from the previous server 
						
						new Thread (new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
								
								
								new Thread (new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										while (prevfindclientandchat.isConnected() ) { 
											try { 
											BufferedReader bf = new BufferedReader(new InputStreamReader(prevfindclientandchat.getInputStream()) ) ; 
											while (!bf.ready()) ; 
											String temp = bf.readLine() ; 
											System.out.println("Chat : "+ temp);
											new Thread (
													new Runnable() {
														
														@Override
														public void run() {
															// TODO Auto-generated method stub
															try {
															
															
														if (temp.startsWith(sendmsg)) {
															
															String [] arr =temp.split(",") ; 
															Integer portFrom = Integer.parseInt(arr[2]) ; 
															Integer portto = Integer.parseInt(arr[3]) ; 
															Integer Idd = Integer.parseInt(arr[1]) ; 
															String msg = ReconstructString(4, arr) ; 	
															if (sockets.get(portto) !=null) { 
																
																PrintStream pss = new PrintStream(sockets.get(portto).getOutputStream()) ; 
																pss.println(portFrom+","+msg); 
															}
															else { 
																if (nextfindclientandchat!= null ) {
																	PrintStream pss = new PrintStream(nextfindclientandchat.getOutputStream()) ; 
																	pss.println(temp); 
																}
																else {
																	PrintStream pss = new PrintStream(prevfindclientandchat.getOutputStream()) ; 
																	pss.println(ClientNotFound+","+Idd+","+portFrom+ "," + portto + "," + msg);
																}
															}
														
														
														}
														else if (temp.startsWith(ClientNotFound)) { 
															String [] arr =temp.split(",") ;
															Integer idd = Integer.parseInt(arr[1]) ; 
															Integer portfrom = Integer.parseInt(arr[2]) ; 
															Integer portto = Integer.parseInt(arr[3]) ; 
															String msg = ReconstructString(4, arr) ; 
 															if (idd == id ) { 
																if (nextfindclientandchat== null ) {
																PrintStream pss = new PrintStream(sockets.get(portfrom).getOutputStream()) ; 
																pss.println(ClientNotFound); 
																} 
																else { 
																	PrintStream pss = new PrintStream(nextfindclientandchat.getOutputStream()) ; 
																	pss.println(sendmsg+"," + id+ ","+portfrom +","+portto+","+msg) ; 
																}
															}
															else { 
																if (nextfindclientandchat != null ) { 
																	PrintStream pss = new PrintStream(nextfindclientandchat.getOutputStream() ) ; 
																	pss.println(temp);
																}
																
															}
														}
															}	
															catch (Exception e ) { 
															
														}	
														}
													}) .start();
											
											
											} 
											catch (Exception e ) { 
												System.out.println(e.getMessage());
											}
										}
									}
									
								} 
								).start();
								} 
								catch (Exception e ){ 
									System.out.println(e.getMessage());
								}
							}
						}) .start () ;
						
						
						
						
						// Thread 2 : when getting the list to the previous server 
						new Thread (new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
							 
								
								new Thread(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										while (prevlist.isConnected()) { 
											try {
											BufferedReader bf = new BufferedReader(new InputStreamReader(prevlist.getInputStream()) ) ; 
											while (!bf.ready()) ; 
											String temp = bf.readLine() ; 
											
											new Thread (new Runnable() {
												
												@Override
												public void run() {
													// TODO Auto-generated method stub
													try {
													if (temp.startsWith(getMemberList)) { 
														String arr [] = temp.split(",") ; 
														Integer Idd = Integer.parseInt(arr[1]) ; 
														Integer port = Integer.parseInt(arr[2]) ; 
														if (nextlist == null ) {
															String x = "" ;
															if (arr.length>3) { 
																x+= ReconstructString(3, arr) ; 
																x+="," ; 
															}
															String t = requestlist +","+ Idd +"," +port +","+x; 
															
															Iterable<Entry<Integer,String>>s = users.entrySet() ;
														for (Entry<Integer,String> entry : s) {
																	t+= "" + entry.getKey() +"," +entry.getValue() +","; 
														}
														PrintStream pss = new PrintStream(prevlist.getOutputStream()) ; 
														pss.println(t); 
 															
														}
														else 
														{
															PrintStream pss =  new PrintStream(nextlist.getOutputStream()) ; 
															pss.println(temp);
														}
													}
													else if (temp.startsWith(requestlist)) { 
														String arr[] = temp.split(",") ; 
														Integer Idd = Integer.parseInt(arr[1]) ; 
														if (id==Idd) { 
															if (nextlist!=null ) { 
																Integer port = Integer.parseInt(arr[2]) ; 
																String list = ReconstructString(3, arr) ;
																PrintStream pss = new PrintStream(nextlist.getOutputStream()) ; 
																pss.println(getMemberList+","+Idd+","+port+ ","+list);
															}
															else { 
																Integer port = Integer.parseInt(arr[2]) ; 
																System.out
																		.println(temp);
																String list = ReconstructString(3, arr) ;
																String t = "" ; 
																Iterable<Entry<Integer,String>>s = users.entrySet() ;
																for (Entry<Integer,String> entry : s) {
																			t+= "" + entry.getKey() +"," +entry.getValue() +","; 
																}
																if(list.equals("")){
																	list = t ;
																}
																else 
																	list += ","+t ;
																System.out
																		.println("The List " +list);
																PrintStream pss = new PrintStream(sockets.get(port).getOutputStream()) ; 
																pss.println(Refreshh+","+list);
																
															}
														} else {
															if (nextlist!=null) { 
																String t = "" ; 
																Iterable<Entry<Integer,String>>s = users.entrySet() ;
																for (Entry<Integer,String> entry : s) {
																			t+= "" + entry.getKey() +"," +entry.getValue() +","; 
																}
																String x = temp +t; 
																PrintStream pss = new PrintStream(nextlist.getOutputStream()) ; 
																pss.println(x);
																
															}
														}
															
													}
													} 
													catch (Exception e ){ 
														System.out.println(e.getMessage());
													}
													
												}
											}) .start();
											
											
											
											} 
											
											
											catch (Exception e) { 
												System.out.println(e.getMessage());
											}
										}
										
									}
								}).start(); 
								} 
								catch (Exception e ){ 
									System.out.println(e.getMessage());
								}
							}
						}) .start () ;
						
						
						
						// Thread 3 : when refreshing , get the list of every online user 
	new Thread (new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try { 
						while (prevrefresh.isConnected()) { 
							try { 
								
								BufferedReader bf = new BufferedReader(new InputStreamReader(prevrefresh.getInputStream()) ) ; 
								while (!bf.ready()) ; 
								String temp = bf.readLine() ; 
								System.out
								.println("Final list = " + temp);
								 Iterable<Entry<Integer,Socket>>s = sockets.entrySet() ;
									for (Entry<Integer,Socket> entry : s) {
												PrintStream pssss = new PrintStream(entry.getValue().getOutputStream()) ; 
												
												pssss.println(temp);
												Thread.sleep(100);
									}
									if (nextrefresh!=null){
										PrintStream ps = new PrintStream(nextrefresh.getOutputStream()) ; 
										ps.println(temp); 
									}
										
								 
							}
							catch (Exception e ) { 
								System.out.println(e.getMessage());
							}
						}
					}
					catch (Exception e ){ 
						System.out.println(e.getMessage());
					}
					
				}
			}).start();
			
			} 
			catch (Exception e ){ 
				System.out.println(e.getMessage());
			}
		}
	}) .start () ;
	
	
						// Thread 4 : spread the list over to all servers 
	new Thread (new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
		
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (prevSpreadThelist.isConnected()) { 
						try {
						BufferedReader bf = new BufferedReader(new InputStreamReader(prevSpreadThelist.getInputStream()) ) ; 
						while (!bf.ready()) ; 
						String temp = bf.readLine() ; 
						System.out.println(temp);
						new Thread (new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
								if (temp.startsWith(getMemberList)) { 
									String arr [] = temp.split(",") ; 
									Integer Idd = Integer.parseInt(arr[1]) ; 
									//Integer port = Integer.parseInt(arr[2]) ; 
									if (nextSpreadThelist == null ) {
										String x = "" ;
										
											x+= ReconstructString(2, arr) ; 
										
										String t = requestlist +","+ Idd  +","+x; 
										
										Iterable<Entry<Integer,String>>s = users.entrySet() ;
									for (Entry<Integer,String> entry : s) {
												t+= "" + entry.getKey() +"," +entry.getValue() +","; 
									}
									PrintStream pss = new PrintStream(prevSpreadThelist.getOutputStream()) ; 
									pss.println(t); 
											
									}
									else 
									{
										PrintStream pss =  new PrintStream(nextSpreadThelist.getOutputStream()) ; 
										pss.println(temp);
									}
								}
								else if (temp.startsWith(requestlist)) { 
									String arr[] = temp.split(",") ; 
									Integer Idd = Integer.parseInt(arr[1]) ; 
									if (id==Idd) { 
										if (nextSpreadThelist!=null ) { 
											//Integer port = Integer.parseInt(arr[2]) ; 
											String list = ReconstructString(2, arr) ;
											PrintStream pss = new PrintStream(nextSpreadThelist.getOutputStream()) ; 
											pss.println(getMemberList+","+Idd+ ","+list);
										}
										else { 
										//	Integer port = Integer.parseInt(arr[2]) ; 
											String list = ReconstructString(2, arr) ;
//											PrintStream pss = new PrintStream(sockets.get(port).getOutputStream()) ; 
//											pss.println(Refreshh+","+list);
											String t = "" ;
											Iterable<Entry<Integer,String>>s = users.entrySet() ;
											for (Entry<Integer,String> entry : s) {
														t+= "" + entry.getKey() +"," +entry.getValue() +","; 
											}
											System.out.println("t = " +t);
											if(list.equals("")){
												list = t ;
											}
											else 
												list += ","+t ;
											
											String out = Refreshh+","+list ; 
											System.out.println(out + "ta7t");
											if (nextrefresh!=null) { 
												PrintStream pss = new PrintStream(nextrefresh.getOutputStream());  
												pss.println (out) ; 
											}
											 if (prevrefresh!= null) { 
													PrintStream pss = new PrintStream(prevrefresh.getOutputStream());  
													pss.println (out) ; 
											 }
											 Iterable<Entry<Integer,Socket>>s1 = sockets.entrySet() ;
												for (Entry<Integer,Socket> entry : s1) {
															PrintStream pssss = new PrintStream(entry.getValue().getOutputStream()) ; 
															pssss.println(out);
												}

											
										}
									} else {
										if (nextSpreadThelist!=null) { 
											String t = "" ; 
											Iterable<Entry<Integer,String>>s = users.entrySet() ;
											for (Entry<Integer,String> entry : s) {
														t+= "" + entry.getKey() +"," +entry.getValue() +","; 
											}
											String x = temp +t; 
											PrintStream pss = new PrintStream(nextSpreadThelist.getOutputStream()) ; 
											pss.println(x);
											
										}
									}
										
								}
								} 
								catch (Exception e ){ 
									System.out.println(e.getMessage());
								}
								
							}
						}) .start();
						
						
						
						} 
						
						
						catch (Exception e) { 
							System.out.println(e.getMessage());
						}
					}
					
				}
			}).start(); 
			
			
			} 
			catch (Exception e ){ 
				System.out.println(e.getMessage());
			}
		}
	}) .start () ;
						// Thread 5 : get info about a certain user 
	new Thread (new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			
			
			new Thread (new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
					while (prevgetClientInfo.isConnected()) { 
						BufferedReader bf = new BufferedReader(new InputStreamReader(prevgetClientInfo.getInputStream()) ) ; 
						while (!bf.ready()) ; 
						String temp = bf.readLine() ; 
						new Thread (
								
								new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										try { 
											if (temp.startsWith("ClientFound") ) {
												String arr [] = temp.split(",");
												Integer idd = Integer.parseInt(arr[1]); 
											if (idd == id ) { 
												Integer port = Integer.parseInt(arr[2]) ; 
												String pass = arr[4] ; 
												//String User = arr[3] ; 
												PrintStream pss = new PrintStream(sockets.get(port).getOutputStream()) ; 
												pss.println(pass); 
											}else {
												if (nextgetClientInfo!= null ) {
													PrintStream pss = new PrintStream(nextgetClientInfo.getOutputStream()) ; 
													pss.println(temp);
												}
											}
											}
											else if (temp.startsWith("GetMeThisClientInfo") ) { 
												String arr [] = temp.split(",") ; 
												Integer id  = Integer.parseInt(arr [1] ) ;
												Integer port = Integer.parseInt(arr[2]) ; 
												String User = arr[3] ; 
												if (users_password.get(User) == null ) {
													if (nextgetClientInfo == null ) { 
														PrintStream pss = new PrintStream(prevgetClientInfo.getOutputStream() ) ; 
														pss.println("ClientNotFound"+"," + id +","+port + "," +User);
													}
													else {
														PrintStream pss = new PrintStream(nextgetClientInfo.getOutputStream()) ; 
														pss.println(temp);
														}
												}
												else {
												PrintStream pss = new PrintStream(prevgetClientInfo.getOutputStream() ) ; 
												pss.println("ClientFound,"+id+","+ port +","+User+ ","+users_password.get(User)); 
												
												}
												
											}
											//"ServerMessage : Rejected"
											else if (temp.startsWith("ClientNotFound")) { 
												String arr [] = temp.split(",") ; 
												Integer idd = Integer.parseInt(arr[1]) ; 
												if (idd == id ) { 
													Integer port = Integer.parseInt(arr[2]) ; 
													if (nextgetClientInfo== null ) {
													PrintStream pss = new PrintStream(sockets.get(port).getOutputStream()) ; 
													pss.println("ServerMessage : Rejected"); 
													} 
													else if (nextgetClientInfo!= null ) {
														PrintStream pss = new PrintStream(nextgetClientInfo.getOutputStream()) ; 
														pss.println(temp);
													}
												}
												else if (nextgetClientInfo!= null ) {
													PrintStream pss = new PrintStream(nextgetClientInfo.getOutputStream()) ; 
													pss.println(temp);
												}
													
												
											}
										}
										catch (Exception e ) {
											System.out.println(e.getMessage());
										}
									}
								}) .start(); 
						
					}
					} 
					catch (Exception e ) { 
						System.out.println(e.getMessage()); 
					}
					
				}
			}) .start () ; 
			
			
			} 
			catch (Exception e ){ 
				System.out.println(e.getMessage());
			}
		}
	}) .start () ;
						
	
	
	
	
	
					}
				}).start();
				
			}
		}).start();
		
	}
	
	
	
	//clientPart 
	
	new Thread (new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true ) { 
				try {
				Socket s = ClientSocket.accept() ;
				
				PrintStream ps = new PrintStream(s.getOutputStream()) ; 
				BufferedReader bf = new BufferedReader(new InputStreamReader (s.getInputStream())) ; 
				
				while (!bf.ready()) ; 
				String name = bf.readLine() ; 
				if (!name.equals("") ){
					users.put(s.getPort(), name) ; 
					// refresh ; 
					Refresh();
				}
				sockets.put(s.getPort(), s);
				new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						while (s.isConnected()) { 
							try {
							while (!bf.ready()) ; 
							
							String temp = bf.readLine() ; 
							System.out.println(temp);
							if (temp.startsWith("RegisterMeThisClient")) {
								String arr [] = temp.split(",") ; 
								users_password.put(arr[1], arr[2]) ; 
								save (savedBefore) ;
								new Thread (new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										save (savedBefore) ; 
									}
								} ) .start () ; 
								
								
								// Refresh
							}
							else if (temp.startsWith("RequestToSignIn")) {
								String arr [] = temp.split(",") ; 
								String User = arr[2] ; 
								Integer port = Integer.parseInt(arr[1]); 
								if (users_password.get(User)== null ) { 
									if (prevgetClientInfo==null && nextgetClientInfo == null ) {
										ps.println("ServerMessage : Rejected"); 
									}
									else if (prevgetClientInfo!=null ) {
										PrintStream pstemp = new PrintStream(prevgetClientInfo.getOutputStream()) ; 
										System.out.println("Sending to previous Server");
										System.out.println(prevgetClientInfo);
										pstemp.println("GetMeThisClientInfo,"+id +","+ port +"," + User );
									}
									else if (nextgetClientInfo!= null){
										PrintStream pstemp = new PrintStream(nextgetClientInfo.getOutputStream()) ; 
										pstemp.println("GetMeThisClientInfo,"+id +"," + port +"," + User );
									}
								}
								else {
									ps.println(users_password.get(User));
								}
								
							}
							else if (temp.startsWith("RequestToCreateANewUser")) { 
								String arr [] = temp.split(",") ; 
								int port = s.getPort() ; 
								String User = arr[1] ; 
								
								if (users_password.get(User)== null ) { 
									if (prevgetClientInfo==null && nextgetClientInfo == null ) {
										ps.println("ServerMessage : Rejected"); 
									}
									else if (prevgetClientInfo!=null ) {
										PrintStream pstemp = new PrintStream(prevgetClientInfo.getOutputStream()) ; 
										pstemp.println("GetMeThisClientInfo,"+id +","+ port +"," + User );
									}
									else if (nextgetClientInfo!= null){
										PrintStream pstemp = new PrintStream(nextgetClientInfo.getOutputStream()) ; 
										pstemp.println("GetMeThisClientInfo,"+id +"," + port +"," + User );
									}
								}
								else {
									ps.println("ServerMessage : Rejected");
								}
								
								
							}
							else if (temp.startsWith("SendthisMsg")){
								System.out.println(temp);
								String arr [] = temp.split(",") ; 
								Integer portto = Integer.parseInt(arr[1]) ; 
								String msg = ReconstructString(2, arr) ; 
								if (users.get(portto)!= null ) { 
									PrintStream pss = new PrintStream(sockets.get(portto).getOutputStream()) ; 
									pss.println(s.getPort() +"," + msg) ; 
								}
								else { 
									if (prevfindclientandchat== null && nextfindclientandchat == null ) { 
									// Not Found 
										System.out.println(ClientNotFound) ;
 										
									}
									else	 if (prevfindclientandchat != null ) { 
										PrintStream pss = new PrintStream(prevfindclientandchat.getOutputStream()) ; 
										pss.println("SendthisMsg,"+id+","+s.getPort()+","+portto + ","+msg) ; 
										
									}
									else	 if (nextfindclientandchat != null ) { 
										PrintStream pss = new PrintStream(nextfindclientandchat.getOutputStream()) ; 
										pss.println("SendthisMsg,"+id+","+s.getPort()+","+portto + ","+msg) ; 
									}
									
									
								}
							}
							else if (temp.equalsIgnoreCase(removeSpaces("bye"))){
								System.out.println(users.get(s.getPort())+" is disconnected. ");
								
								sockets.remove(s.getPort()) ; 
								users.remove(s.getPort()) ; 
								ps.println("Bye") ; 
								bf.close();
								ps.close();
								s.close();
								Refresh ();
								// refresh ... 
								
								
							} else if (temp.equalsIgnoreCase("getMemberList")) { 
								System.out.println("request recieved ");
								if (prevlist == null && nextlist == null) {
									String t = Refreshh+"," ; 
									
									Iterable<Entry<Integer,String>>s = users.entrySet() ;
									for (Entry<Integer,String> entry : s) {
												t+= "" + entry.getKey() +"," +entry.getValue()+"," ; 
									}
									ps.println(t);
								}
								else	 if (prevlist != null ) { 
									PrintStream pss = new PrintStream(prevlist.getOutputStream()) ; 
									System.out.println("Sent to prev");
									pss.println(getMemberList+","+id+","+s.getPort());
									
								}
								else	 if (nextlist!=null ) { 
									PrintStream pss = new PrintStream(nextlist.getOutputStream()) ; 
									pss.println (getMemberList+","+id+","+s.getPort());
								}
							}
							else if (temp.equalsIgnoreCase("getMeServerMemberList")) {
								String t = "ThisIsMyList:" ; 
								
								Iterable<Entry<Integer,String>>s = users.entrySet() ;
								for (Entry<Integer,String> entry : s) {
											t+= "" + entry.getKey() +"," +entry.getValue()+"\n" ; 
								}
								ps.println(t);
							}
							
							} 
							catch (Exception e ) { 
								
							}
						}
					}

				
				}) .start(); 
				
				}
				catch (Exception e ){
					System.out.println(e.getMessage()); 
				}
				
				
			}
			
		}
	}).start () ; 
	
	
}
String serverErrorMsg = "ServerMessage : Rejected" ;
String ClientNotFound = "ServerError404 : Client not found ." ; 
String sendmsg = "SendthisMsg" ; 
String Refreshh = "RefreshyourList" ; 
String requestlist = "RequestTheList" ;
String getMemberList = "getMemberList" ;


private void Refresh() {
	// TODO Auto-generated method stub 
	System.out.println("Refresh all lists");
	new Thread (new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
		
			try {
				if (prevSpreadThelist == null && nextSpreadThelist == null  ){
					String t = Refreshh + "," ; 
					Iterable<Entry<Integer,String>>s = users.entrySet() ;
					for (Entry<Integer,String> entry : s) {
								t+= "" + entry.getKey() +"," +entry.getValue() +","; 
					}
				
					try {
					Iterable<Entry<Integer,Socket>>ss = sockets.entrySet() ;
					
					for (Entry<Integer,Socket> entry : ss) {
						try {
								PrintStream pps = new PrintStream(entry.getValue().getOutputStream()) ; 
								
								pps.println(t);
								//Thread.sleep(100);
						} 
						catch (Exception e ) { 
							System.out.println(e.getMessage());
						}
					} 
					}
					catch (Exception e ) { 
						
					}
								
					}
					
					
				
				else if (prevSpreadThelist != null ) { 
					PrintStream pss = new PrintStream(prevSpreadThelist.getOutputStream()) ; 
					pss.println(getMemberList+","+id);
				}
				else if (nextSpreadThelist!=null ) { 
					PrintStream pss = new PrintStream(nextSpreadThelist.getOutputStream()) ; 
					pss.println (getMemberList+","+id);
				}
				} 
				catch (Exception e ){ 
					System.out.println(e.getMessage());
				}
			
		}
	}).start();
	
}

public void save (String path) {
	
	
	
	try   {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				new File(path)));
		// Serializing the object and writing it on the hard disk.
		oos.writeObject(this.users_password);
		// Updating the last saved file path to the current file.
		savedBefore = path;
		// close the output stream.
		oos.close();
	      } catch(IOException i ) 
	      {
	          i.printStackTrace();
	      }
}

public void load () throws IOException, ClassNotFoundException {
	HashMap s1 ;  
	String fileName = savedBefore ; 
	
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        s1 = (HashMap<String, String>) in.readObject();
        this.users_password = s1 ; 
               try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
        try {
			fileIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
		}
    
	}

public static String ReconstructString (int Startindex , String [] arr ) { 
String msg = "" ; 
	for (int i = Startindex; i < arr.length; i++) {
		msg += arr[i]+","; 
	}
	
	return msg ; 
}
public static boolean startWith (String s , String s1){ 
	if (s1.length() < s.length()) 
		return false ; 
	return s.equalsIgnoreCase(s1.substring(0, s.length())) ; 
}
public static String removeSpaces (String s ) {  
	String x = "" ; 
	
	for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i)!= ' ') { 
			x+=s.charAt(i) ;
			}
	}
	return x ; 
	
}

}
