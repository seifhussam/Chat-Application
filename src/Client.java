import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Serializable  { 
	 PrintStream ps ; 
	private ClientListener listener ; 
	private String name ; 
	public void getAllMemberList () {
		if (ps!=null)
		ps.println("getMemberList") ; 
	}
	public Client(String name , int localport) throws UnknownHostException, IOException {
		// TODO Auto-generated constructor stub
		this.name = name; 
		@SuppressWarnings("resource")
		Socket s = new Socket("192.168.43.192" , localport) ; 
		System.out.println(s.getLocalPort());
		System.out.println("Conneted");
		ps = new PrintStream(s.getOutputStream()) ; 
		ps.println(name);
		
		new Thread (new Runnable () {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BufferedReader bf = null ; 
				try {
					while (true ) { 
					  bf = new BufferedReader(new InputStreamReader(s.getInputStream())) ;
				
			//	System.out.println("waiting for a msg");
					while (!bf.ready() )  ;
			//		System.out.println("Received a msg");
					String message = bf.readLine() ;
					System.out.println(message); 
					
					if (message.equalsIgnoreCase("bye")) {						
						if (listener!=null) {
							ClientEvent e = new ClientEvent(ClientEventType.SIGNOUT) ; 
						listener.OnClientEvent(e);
						}
					}
					
					
					else if (message.startsWith("RefreshyourList") ){ 
					System.out.println(message);
						ClientEvent e = new ClientEvent(message , ClientEventType.REFRESH) ; 
						if (listener!=null) {
							listener.OnClientEvent(e);
							//System.out.println("Listener Activated");
						}
					}
					else if (message.equals("ServerError404 : Client not found .")) {
						System.out.println(message);
						ClientEvent e = new ClientEvent(message , ClientEventType.ERROR) ; 
						if (listener!=null) {
							listener.OnClientEvent(e);
							//System.out.println("Listener Activated");
						}
					}
 					
					else  if (message.startsWith("getMemberList")) { 
						ps.println(message);
					//	System.out.println("Here at : getMemberList");
					}
					else if (message.startsWith("ThisIsMyList")){
						System.out.println(message);
					}
					
						else  {
						String [] arr = message.split(",") ; 
						int portnumber = Integer.parseInt(arr[0]) ; 
						String msg = "" ; 
						for (int i = 1; i < arr.length; i++) {
							msg+=arr[i] ; 
						}
						
						ClientEvent e = new ClientEvent (portnumber , msg , ClientEventType.MESSAGE) ; 
						if (listener!=null) { 
							listener.OnClientEvent(e); 
						}
					}
					
				}
				}
				 
				catch (Exception e ) { 
					e.printStackTrace();
				}
				
			}
		}).start () ; 
		new Thread (new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)) ; 
				while (true) {
				try {
					while(!bf.ready()) ;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				try {
					ps.println(bf.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}) .start();
	}
public static boolean startWith (String s , String s1){ 
	if (s1.length() < s.length()) 
		return false ; 
	return s.equalsIgnoreCase(s1.substring(0, s.length())) ; 
}
	public ClientListener getListener() {
		return listener;
	}

	public void setListener(ClientListener listener) {
		this.listener = listener;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}