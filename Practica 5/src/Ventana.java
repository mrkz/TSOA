import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea 4
 */
public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 800, HEIGHT = 600;
	private JTextArea textArea;
	private JTextField textField;
	private Listener listener;
	private JButton button;
	private JPanel buttonPanel;
	public static final String [] buttons = {"send","another action","Last one"}; 
	
	public Ventana(){
		super("Life is too short to run proprietary software...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		listener = new Listener(this);
		buttonPanel = new JPanel();
		setLayout( new BorderLayout());
		textField = new JTextField("Write some text here...");
		textArea = new JTextArea();
		for(int i = 0 ; i < buttons.length; i++){
			button = new JButton(buttons[i]);
			button.addActionListener(listener);
			buttonPanel.add(button);
			
		}
		add(textField, BorderLayout.NORTH);
		add(textArea, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		
	}
	
	public void pushMessage(){
		textArea.setText(textArea.getText()+textField.getText()+"\n");
	}
	
	public void clearTextArea(){
		textArea.setText("");
	}
	
	public void changeDefaultTextField(JButton button){
		button.setText("Text Changed :D");
	}
	
	public static void main(String[] args) {
		
		/*para el look n feel del S.O.*/
		try{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)	{
		 	e.printStackTrace();
		}
		/* hasta aquí el look n feel */
		
		Ventana window = new Ventana();
		window.setVisible(true);
	}

}


class Listener implements ActionListener{
	
	private Ventana father;
	public Listener(Ventana window){
		father = window;
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		String  command = event.getActionCommand();
		switch(command){
		case "send":
			father.pushMessage();
			break;
		case "another action":
			father.clearTextArea();
			break;
		case "Last one": case "Text Changed :D":
			father.changeDefaultTextField((JButton) event.getSource());
			break;
		default:
			System.out.println("Error: evento "+command+" Desconocido...");
			System.exit(1);
			break;
		}
		
	}
	
}
