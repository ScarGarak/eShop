/**
 * 
 */
package shop.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import shop.local.domain.ShopVerwaltung;
import shop.local.ui.gui.kundengui.KundeGUI;
import shop.local.ui.gui.mitarbeitergui.MitarbeiterGUI;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Person;
import shop.local.valueobjects.PersonTyp;

/**
 * @author Mort
 *
 */

@SuppressWarnings("serial")
public class LogInGUI extends JFrame implements ActionListener, KeyListener, MouseListener {
	
	/**
	 * 
	 */
	
	private ShopVerwaltung shop;
	private Person p;
	private int key;

//	login/register objects
	private JButton logInButton = new JButton("LogIn");
	private JTextField usernameField = new JTextField();
	private JTextField passwordField = new JPasswordField();
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Passwort");
	private JLabel userError = new JLabel("Bitte Usernamen eingeben");
	private JLabel passwdError = new JLabel("Bitte Passwort eingeben");
	private JLabel forgetLabel = new JLabel("<html><u>Login vergessen?</u></html>");
	private JLabel registerLabel = new JLabel("<html><u>Registrieren</u></html>");
	
//	layout objects
	private JPanel borderPanNorth = new JPanel();
	private JPanel borderPanSouth = new JPanel();
	private JPanel borderPanEast = new JPanel();
	private JPanel borderPanWest = new JPanel();
	private JPanel borderPanNN = new JPanel();
	private JPanel borderPanSW = new JPanel();
	private JPanel borderPanEE = new JPanel();
	private JPanel borderPanWW = new JPanel();
	private JPanel contentPanel = new JPanel();
	private JPanel labelPanel = new JPanel();
	private JPanel labelPanelLeft = new JPanel();
	private JPanel labelPanelRight = new JPanel();
	
//	register/forget objects
	private JButton registerButton = new JButton();
	private JLabel nameLabel = new JLabel();
	private JLabel streetLabel = new JLabel();
	private JLabel zipcodeLabel = new JLabel();
	private JLabel townLabel = new JLabel();
	private JTextField nameField = new JTextField();
	private JTextField streetField = new JTextField();
	private JTextField zipcodeField = new JTextField();
	private JTextField townField = new JTextField();
	
//	forget objects
	private JButton renewButton = new JButton();
	
	public LogInGUI() throws IOException {
		super("eShop - LogIn");
		shop = new ShopVerwaltung();
		initialize();
//		initialize2();
	}
	
	
//	private void initialize2() {
//		setTitle("LogIn");
//		setResizable(false);
//		setSize(new Dimension(700, 500));
//	}
	
	private void initialize() {
		setTitle("LogIn");
		setResizable(false);
		setSize(new Dimension(700, 500));
		
		setLayout(new BorderLayout(220,150));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		add(borderPanNorth, BorderLayout.NORTH);
		borderPanNorth.setLayout(new BorderLayout());
		borderPanNorth.add(borderPanNN, BorderLayout.CENTER);
		add(borderPanSouth, BorderLayout.SOUTH);
		borderPanSouth.setLayout(new BorderLayout());
		borderPanSouth.add(borderPanSW, BorderLayout.WEST);
		borderPanSW.add(registerLabel);
		add(borderPanEast, BorderLayout.EAST);
		borderPanEast.setLayout(new BorderLayout());
		borderPanEast.add(borderPanEE, BorderLayout.CENTER);
		add(borderPanWest, BorderLayout.WEST);
		borderPanWest.setLayout(new BorderLayout());
		borderPanWest.add(borderPanWW, BorderLayout.CENTER);
		add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new GridLayout(5,1));
		contentPanel.add(usernameLabel);
		contentPanel.add(usernameField);
		contentPanel.add(passwordLabel);
		contentPanel.add(passwordField);
		contentPanel.add(labelPanel);
		
		labelPanel.setLayout(new GridLayout(1,2));
		labelPanel.add(labelPanelLeft);
		labelPanelLeft.setLayout(new BorderLayout());
		labelPanelLeft.add(forgetLabel, BorderLayout.WEST);
		labelPanel.add(labelPanelRight);
		labelPanelRight.setLayout(new BorderLayout());
		labelPanelRight.add(logInButton, BorderLayout.CENTER);
				
		logInButton.addActionListener(this);
		usernameField.addKeyListener(this);
		passwordField.addKeyListener(this);
		forgetLabel.addMouseListener(this);
		registerLabel.addMouseListener(this);
		
//		forgetLabel.setForeground(Color.BLUE);
//		registerLabel.setForeground(Color.BLUE);
		
	}
	
	// Methoden zur verarbeitung und prüfung der Nutzer eingaben
	
	private void anmeldeVorgang() {
		logIn();
		
		if (p != null) {
			if (p.getPersonTyp().equals(PersonTyp.Mitarbeiter)) {
				try {
//					dispose();
					this.setVisible(false);
					new MitarbeiterGUI((Mitarbeiter)p, shop);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} else
				try {
//					dispose();
					this.setVisible(false);
					new KundeGUI((Kunde)p, shop);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	private void logIn() {
		if (!usernameField.getText().equals("")) {
			String username = usernameField.getText();
			if (!passwordField.getText().equals("")) {
				String password = passwordField.getText();
				p = shop.pruefeLogin(username, password);
				if (p == null) {
//					pop up mit Hinweis aus falsche eingabe
//					einfärben der Textfelder, Fokus auf usernameField
//					tf.setText
				}
			} else {
//				Error Label einfügen
				passwordField.setBackground(new Color(250,240,230));
				passwordField.requestFocus();
			}
		} else {
			if (passwordField.getText().equals("")) {
				passwordField.setBackground(new Color(250,240,230));
//				Error Label einfügen
//				usernameField.setText("Usernamen eingeben!");
				usernameField.setBackground(new Color(250,240,230));
				usernameField.requestFocus();
			} else {
//				Error Label einfügen
//				usernameField.setText("Usernamen eingeben!");
				usernameField.setBackground(new Color(250,240,230));
				usernameField.requestFocus();
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent mr) {
		// TODO Auto-generated method stub
		Component comp = mr.getComponent();
		System.out.println("Component pos: " + comp);
		if (comp.equals(forgetLabel)) {
//			einblenden anderer Komponenten
			forgetLabel.setForeground(Color.BLUE);
		}
		if (comp.equals(registerLabel)) {
//			einblenden anderer Komponenten
			registerLabel.setForeground(Color.BLUE);
		}
	}
	
	public void keyPressed(KeyEvent kp) {
		key = kp.getKeyCode();
		System.out.println("key in pressed: " + key);
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped (KeyEvent ke) {
		if (usernameField.getText().equals("Usernamen eingeben!")) {
			usernameField.setText("");
		}
		if (passwordField.getText().equals("Passwort eingeben!")) {
			passwordField.setText("");
		}
		// in arbeit
		if (ke.getSource() == usernameField) {
			if (key == KeyEvent.VK_ENTER) {
//				if um zu gucken ob gucken ob feld leer oder "usernamen eingeben" enthält?
				passwordField.requestFocus();
			}
		} if (ke.getSource() == passwordField) {
			if (key == KeyEvent.VK_ENTER) {
//				if um zu gucken ob gucken ob feld leer oder "password eingeben" enthält?
				anmeldeVorgang();
			}
		}
	}
	
	public void actionPerformed(ActionEvent b) {
		anmeldeVorgang();		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				try {
					JFrame frame = new LogInGUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
        }); // Erzeugt einen neuen Thread, der eine Instanz von LogInGUI erzeugt
	}

}
