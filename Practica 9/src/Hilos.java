import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * Taller de Sistemas Operativos Avanzados - 2013B
 * Simental Magaña Marcos Eleno Joaquín.
 * Sección D04 - Tarea 9
 */
public class Hilos extends JTextArea implements Runnable{

	private static final int WIDTH_TEXT_AREA = 20, HEIGHT_TEXT_AREA = 10;
	private static final long serialVersionUID = 1L;
	private long timeToWait;
	private Window parent;
	private String name;
	private int count;
	
	public Hilos(String name, long timeToWait, Window parent){
		super(HEIGHT_TEXT_AREA, WIDTH_TEXT_AREA);;
		this.timeToWait = timeToWait;
		this.name = name;
		this.parent = parent;
		count = 0;
		new Thread(this,"hilo 1").start();
	}
	
	public void run(){
		while(true){
			try{
				Thread.sleep(timeToWait);
				parent.canPrint();
				count += 1; 
				setText(getText()+name+":  "+count+"\n");
				
			}
			catch (InterruptedException e) {}
		}
	}
	
	public static void main(String[] args) {
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
		Window w;
		w = new Window();
		w.setVisible(true);
		w.runThread();
	}

}

class Window extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final int WIDTH_TEXT_AREA = 20, HEIGHT_TEXT_AREA = 10;
	private static final long TIME_1 = 1000L, TIME_2 = 2000L;
	private int count;
	private Hilos areaThread;
	private JTextArea textArea;
	private JButton button;
	private boolean printFlag = true;
	
	public Window(){
		super("TSOA - Tarea 9: Hilos.java");
		JPanel buttonPanel, textAreaPanel;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		areaThread = new Hilos("Hilo 1",TIME_1, this);
		areaThread.setSize(WIDTH_TEXT_AREA,HEIGHT_TEXT_AREA);
		areaThread.setEditable(false);
		textArea= new JTextArea(HEIGHT_TEXT_AREA, WIDTH_TEXT_AREA);
		textArea.setSize(WIDTH_TEXT_AREA,HEIGHT_TEXT_AREA);
		textArea.setEditable(false);
		button = new JButton("Dormir hilo");
		buttonPanel = new JPanel();
		textAreaPanel = new JPanel();
		textAreaPanel.add(new JScrollPane(areaThread,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					  					  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		textAreaPanel.add(new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			 	  						  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		buttonPanel.add(button);
		button.addActionListener(this);
		add(textAreaPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		count = 0;
		pack();
	}
	
	public synchronized void changeFlag(){
		printFlag = !printFlag;
		if(printFlag){
			notify();
		}
	}
	
	public void runThread(){
		printTextArea("Hilo 2");
	}
	
	public synchronized void canPrint(){
		if(!printFlag){
			try{
				wait();
			} 
			catch (InterruptedException e) {}
		}
	}
	
	public void printTextArea(String text){
		while(true){
			try {
				Thread.sleep(TIME_2);
				count+=1;
				textArea.setText(textArea.getText()+text+":  "+count+"\n");
			} 
			catch (InterruptedException e) {}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		changeFlag();
	}
	
}
