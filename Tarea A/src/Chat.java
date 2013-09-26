import java.awt.BorderLayout;
import java.awt.TrayIcon.MessageType;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea A
 */
public class Chat {
	private Window window;
	
	public Chat(){
		window = new Window();
	}
	
	public Window getWindow(){
		return window;
	}
	
	public static void main(String[] args) {
		Chat chat;
		chat = new Chat();
		chat.getWindow().setVisible(true);
	}
}

class Window extends JFrame{

	private static final long serialVersionUID = 1L;
	private static final int WIDTH_TEXT_AREA = 20, HEIGHT_TEXT_AREA = 15;
	private JTextField ipField, messageField;
	private JTextArea chatArea;
	private JButton sendButton;
	private JPanel panel;
	//private static final int WIDTH = 800, HEIGHT = 600;

	public Window(){
		super("TSOA - Tarea A: Chat");
		//setSize(WIDTH, HEIGHT);
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
}

class Receptor implements Runnable{

	@Override
	public void run() {
		
	}
	
}