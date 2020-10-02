package client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import event.ChoiceExploitForge;
import event.ChoiceFace;
import event.Protocol;
import io.socket.client.IO;
import io.socket.client.Socket;

 
// TODO: Auto-generated Javadoc
/**
 * The Class Client handles the client socket.
 */
public class Client implements Runnable {
	
	/**  Name of the AI which manage client choices. */
	private String AIname;
	
	/**  Integer variable used to create equivalent quantity of AI+ and AIrnd. */
	static int nbAI = 0;
	
	/**  The socket that contains the Client. */
	private Socket socket;
	
	/**  The Artificial Intelligence Manager, that's responsible of AI logic's *. */
	private AI AImanager;
	
	/**  Boolean that tell whether the client is connected to the server or not *. */
	Boolean connected;

	/** Lock is for synchronization. */
	private Lock lock = new ReentrantLock();
	
	/** Condition is for synchronization too, works with a lock, allows to wait/notify between locks. */
	private Condition condition = this.lock.newCondition();
	
	/**
	 * Instantiates a new client.
	 */
	public Client() {
		if(nbAI <= 1) {
			this.AImanager = new AIManagerPlus();
			AIname = "_AIPlus";
			nbAI++;
		}else {
			this.AImanager = new AIRandom();
			AIname = "_AIRandom";
		}
		
		this.connected = false;
	}
	
	/**
	 * Define what's running in the the Client Thread,
	 * Configure the client socket,
	 * Subscribe the client socket to game events,
	 * The thread waits until socket disconnect from the server.  
	 */
	@Override
	public void run() {
		try {
			
			 IO.Options opts = new IO.Options();
		       // opts.transports = new String[] { WebSocket.NAME };// to active websocket protocol
		        opts.reconnection = false;
		        opts.forceNew = true;
		        opts.query = "version="+this.AIname.toString();
			this.socket = IO.socket("http://127.0.0.1:11000", opts);
			
			this.socket.on(Socket.EVENT_CONNECT, objects -> { 
				connected = true;
				lock.lock();
				condition.signal();
				lock.unlock();
			});
			
			this.socket.on(Socket.EVENT_DISCONNECT, objects -> {
				connected = false;
				lock.lock();
				condition.signal();
				lock.unlock();
			});
			
			this.initEvent(socket);
			this.socket.connect();
			
			while(!this.socket.connected()) {
				this.lock.lock();
				
				try {
					this.condition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				this.lock.unlock();
			}
			
			synchronized(this) {
				this.notify();
			}
			
			while(this.socket.connected()) {
				this.lock.lock();
				
				try {
					this.condition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				this.lock.unlock();
			}
				
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Runtime.getRuntime().exit(-1);
		}
	}
	
	/**
	 * Initialize the client socket subscriptions to game events. 
	 *
	 * @param s the client socket
	 */
	public void initEvent(Socket s) {
		s.on(Protocol.CHOICE_FORGE.name(), objects -> { choiceForge(objects[0].toString()); });
		s.on(Protocol.CHOICE_FACE.name(), objects -> { choiceFace(objects[0].toString());});
	}

	/**
	 *  Handle the CHOICE_FACE game event,
	 * Deserialize the JSON string to instance of ChoiceFace,
	 * Execute AI choice,
	 * Sends the choice by serializing choiceFace back.
	 *
	 * @param s the s
	 */
	private void choiceFace(String s) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ChoiceFace ch = mapper.readValue(s, ChoiceFace.class);
			this.AImanager.choiceFace(ch);
			this.socket.emit(Protocol.CHOICE_FACE.name(), mapper.writeValueAsString(ch));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Handle the CHOICE_FORGE game event,
	 * Deserialize the JSON string to instance of ChoiceForge,
	 * Execute AI choice,
	 * Sends the choice by serializing choiceForge back.
	 * 
	 * @param s the JSON of ChoiceForge object sent by the Server
	 */
	public void choiceForge(String s) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ChoiceExploitForge ch = mapper.readValue(s, ChoiceExploitForge.class);
			this.AImanager.ChoiceForge(ch);
			this.socket.emit(Protocol.CHOICE_FORGE.name(), mapper.writeValueAsString(ch));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
