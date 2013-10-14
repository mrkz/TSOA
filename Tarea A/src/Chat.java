import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;


/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea A
 */
public class Chat {
	private Window window;
	private static final int PORT = 8081;
	
	public Chat(){
		window = new Window(PORT);
	}
	
	public Window getWindow(){
		return window;
	}
	
	public static void main(String[] args) {
		Chat chat;
		
		/* para el look n feel del S.O.*/
		try{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		/* hasta aquí el look n feel */
		
		chat = new Chat();
		chat.getWindow().setVisible(true);
	}
}

class Window extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final int WIDTH_TEXT_AREA = 20, HEIGHT_TEXT_AREA = 15;
	private JTextField ipField, messageField;
	private JTextArea chatArea;
	private JButton sendButton;
	private JPanel panel;
	private Receiver receiver;
	private Transmitter transmitter;

	public Window(int port){
		super("TSOA - Tarea A: Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		receiver = new Receiver(this, port);
		new Thread(receiver,"receptor").start();
		transmitter = new Transmitter(port);
		setLayout(new BorderLayout());
		ipField = new JTextField();
		panel = new JPanel();
		panel.add(new JLabel("IP:"));
		ipField = new JTextField(17);
		panel.add(ipField);
		chatArea = new JTextArea(WIDTH_TEXT_AREA, HEIGHT_TEXT_AREA);
		chatArea.setEditable(false);
		messageField = new JTextField(20);
		sendButton = new JButton("Enviar");
		sendButton.addActionListener(this);
		add(panel, BorderLayout.NORTH);
		add(new JScrollPane(chatArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				  			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
		panel = new JPanel();
		panel.add(new JLabel("Mensaje: "));
		panel.add(messageField);
		panel.add(sendButton);
		add(panel, BorderLayout.SOUTH);
		pack();
	}
	
	public void showMessage(String message){
		chatArea.setText(chatArea.getText()+message+"\n");
	}

	@Override
	public void actionPerformed(ActionEvent source) {
		if(source.getActionCommand() == "Enviar"){
			String ip = ipField.getText();
			String messageToSend = messageField.getText();
			transmitter.sendMessage(ip, messageToSend);
			messageField.setText("");
		}
	}
}

class Receiver implements Runnable{
	
	private Window parent;
	private DatagramSocket socket;
	private DatagramPacket dp;
	private byte[] buffer;
	private static final int  SIZE = 1024;
	
	public Receiver(Window parent, int port){
		buffer = new byte[SIZE];
		this.parent = parent;
		dp=new DatagramPacket(buffer,buffer.length);
		try {
			socket=new DatagramSocket(port);
		}
		catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void waitMessage(){
		while(true){
			try {
				socket.receive(dp);
				parent.showMessage(formatMessage(dp));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		waitMessage();
	}
	
	private String formatMessage(DatagramPacket incoming){
		String formattedString = ""; 
		formattedString+="IP emisora: "+incoming.getAddress().getHostAddress()+": "+
						 new String(incoming.getData(),0,incoming.getLength());
		
		return formattedString;
	}
	
}

class Transmitter {
	
	private DatagramSocket socket;
	private int port;
	
	public Transmitter(int port){
		this.port = port;
		try {
			socket = new DatagramSocket();
		}
		catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String ip, String messageToSend){
		byte [] message = messageToSend.getBytes();
		DatagramPacket packet;
		try {
			packet = new DatagramPacket(message, message.length, InetAddress.getByName(ip), port);
			socket.send(packet);
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	
}