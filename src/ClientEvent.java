
public class ClientEvent {
private int ClientPort ;
private String msg ; 
private ClientEventType clientEventType ;
public ClientEvent(String msg,  ClientEventType clientEventType) {
	this.msg = msg;
	this.clientEventType = clientEventType;
} 
public ClientEvent(int clientPort, String msg, ClientEventType clientEventType) {
	ClientPort = clientPort;
	this.msg = msg;
	this.clientEventType = clientEventType;
} 
public ClientEvent( ClientEventType clientEventType) {
	// TODO Auto-generated constructor stub
	this.clientEventType = clientEventType ;
}
public int getClientPort() {
	return ClientPort;
}
public void setClientPort(int clientPort) {
	ClientPort = clientPort;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public ClientEventType getClientEventType() {
	return clientEventType;
}
public void setClientEventType(ClientEventType clientEventType) {
	this.clientEventType = clientEventType;
}


}
