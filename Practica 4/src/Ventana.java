import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea 4
 */
public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 800, HEIGHT = 600;
	private TextArea textArea;
	private TextField textField;
	private JButton button;
	
	public Ventana(){
		super("Life is too short to run proprietary software...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLayout( new BorderLayout());
		textField = new TextField();
		textArea = new TextArea("Write some text here...");
		button = new JButton("I'm feeling ducky");
		add(textField, BorderLayout.NORTH);
		add(textArea, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
		
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
