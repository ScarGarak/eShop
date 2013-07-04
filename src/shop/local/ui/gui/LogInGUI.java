/**
 * 
 */
package shop.local.ui.gui;

import static java.awt.GridBagConstraints.CENTER;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.UsernameExistiertBereitsException;
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
	
	//	GridBagLayout Variablen
	private int gridx, gridy, gridwidth, gridheight, fill, anchor, ipadx, ipady;
    private double weightx, weighty;
    private Insets insets;
    
    private int panBreite;
    private int ixr;
    private int ixl;
	
//	frame objekte
       	 
	private JPanel frameHeader = new JPanel() {
		@Override
		public void paint(Graphics g) {
//		    Dimension d = this.getPreferredSize();
		    String header = new String("eShop");
		    int fontSize = 110;
		    Font f = new Font("Verdana", Font.PLAIN, fontSize);
		    FontMetrics fm   = g.getFontMetrics(f);
		    java.awt.geom.Rectangle2D rect = fm.getStringBounds(header, g);
		    int stringWidth = (int)(rect.getWidth());
		    int stringHeight = (int)(rect.getHeight());
		    int panWidth = frameHeader.getWidth();
		    int panHeight = frameHeader.getHeight();
		    int x = (panWidth  - stringWidth)  / 2;
		    int y = (panHeight - stringHeight) / 2  + fm.getAscent();

		    g.setFont(f);
		    g.setColor(Color.BLACK);
		    g.drawString(header, x, y);
		  }
	};

	private JPanel linksPan = new JPanel();
	private JPanel mittePan = new JPanel();
	private JPanel mitteUnten = new JPanel();
	private JPanel rechtsPan = new JPanel();
	private JPanel untenPan = new JPanel();
	
//	login objekte hinzufügen
	private JButton logInButton = new JButton("LogIn");
	private JTextField usernameField = new JTextField();
	private JTextField passwordField = new JPasswordField();
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Passwort");
	private JLabel forgetLabel = new JLabel("<html><u>Login vergessen?</u></html>");
	
//	links objekte hinzufügen
	private JLabel links = new JLabel("");
	
//	rechts objekte hinzufügen
	private JLabel rechts = new JLabel("");
	private JLabel rechts2 = new JLabel("");
	private JLabel rechts3 = new JLabel("");
	private JLabel rechts4 = new JLabel("");
	private JLabel rechts5 = new JLabel("");
	private JLabel usernameError = new JLabel("<html><font color=#FF0000>Bitte Usernamen eingeben</font></html>");
	private JLabel passwordError = new JLabel("<html><font color=#FF0000>Bitte Passwort eingeben</font></html>");
	
//	unten objekte hinzufügen
	private JLabel registerLabel = new JLabel("<html><u>Registrieren</u></html>");
	
//	register objekte
	private JLabel backLab = new JLabel("<html><u>zur\u00fcck</u></html>");
	private JLabel regLab = new JLabel("<html><u>Registrieren</u></html>");
	private JLabel changeLab = new JLabel("<html><u>\u00e4ndern</u></html>");
	private JTextField nameField = new JTextField("");
	private JTextField streetField = new JTextField("");
	private JTextField zipField = new JTextField("");
	private JTextField cityField = new JTextField("");
	private JTextField pwField = new JTextField("");
	private JTextField wpwField = new JTextField("");
	
//	forget objekte
	private JTextField opwField = new JTextField();
	
	public LogInGUI() throws IOException {
		super("eShop - LogIn");
		shop = new ShopVerwaltung();
		initialize();
	}
	
	private void initialize() {
		setTitle("LogIn");
		setResizable(false);
		setSize(new Dimension(700, 500));
		
		setLayout(new GridBagLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
//		int xy = ;
//		System.out.println(xy);
		
		zeichneLogin();
		
//		hinzufügen der events
		logInButton.addActionListener(this);
		usernameField.addKeyListener(this);
		passwordField.addKeyListener(this);
		forgetLabel.addMouseListener(this);
		registerLabel.addMouseListener(this);
		regLab.addMouseListener(this);
		backLab.addMouseListener(this);
		changeLab.addMouseListener(this);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addGB(Component component, int gridx, int gridy, int gridwidth, int gridheight,
            int fill, double weightx, double weighty, int anchor, Insets insets,
            int ipadx, int ipady) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.anchor = anchor;
        constraints.insets = insets;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        add(component, constraints);
    }
	
	private void addGB(Component component, int gridx, int gridy, int width, int ipadx, int ipady) {
        addGB(component, gridx, gridy, width, 1, GridBagConstraints.BOTH, 0.0, 0.0, CENTER, new Insets(5, 5, 5, 5), ipadx, ipady);
    }
	
	private void loginVergessen(){
		Kunde k = null;
		String name = nameField.getText();
		String strasse = streetField.getText();
		String zipStr = zipField.getText();
		String stadt = cityField.getText();
		
		String passwort = pwField.getText();
		String passwortWiederholung = wpwField.getText();
		
		try{
			int zip = Integer.parseInt(zipStr);
			k = shop.loginVergessen(name, strasse, zip, stadt);
		} catch (NumberFormatException nfe){
			//TODO
			System.err.println("Bitte geben Sie fŸr die Postleitzahl nur Ziffern ein!");
		}
		
		if(k != null){
			usernameField.setText(k.getUsername());
			if(passwort.equals(passwortWiederholung)){
				k.setPasswort(passwort);
				zeichneLogin();
			}else{
				//TODO
				System.err.println("Das Passwort und die Passwort Wiederholung m/u00fcssen gleich sein!");
			}
		}else{
			//TODO
			System.err.println("Kein Account mit diesen Angaben gefunden!");
		}
		
	}
	
	private void registrierung() {
//		prüfe welche id die letzte war dann +1
		
//		String strGebDat = gebDatField.getText();
//		SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
//		try {
//			Date dateGebDat = sdfToDate.parse(strGebDat);
//			System.out.println("" + dateGebDat);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		String username = usernameField.getText();
		String passwort = pwField.getText();
		String wPasswort = wpwField.getText();
		String name = nameField.getText();
		String strasse = streetField.getText();
		String strPlz = zipField.getText();
		int plz = Integer.parseInt(strPlz);
		String wohnort = cityField.getText();
		try {
			shop.fuegeKundenHinzu(username, passwort, name, strasse, plz, wohnort);
			System.out.println("Kunde wurde hinzugefügt!");
			zeichneLogin();
			try {
				shop.schreibeKunden();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (KundeExistiertBereitsException e) {
			System.err.println(e.getMessage());
		} catch (UsernameExistiertBereitsException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void zeichneIO() {
		rechtsPan.add(rechts);
		rechtsPan.add(rechts2);
		rechtsPan.add(rechts3);
		rechtsPan.add(rechts4);
		rechtsPan.add(rechts5);
	}
	
	private void zeichneError() {
		rechtsPan.add(rechts);
		rechtsPan.add(usernameError);
		rechtsPan.add(rechts3);
		rechtsPan.add(passwordError);
		rechtsPan.add(rechts5);
	}
	
	private void zeichneErrorPw() {
		rechtsPan.add(rechts);
		rechtsPan.add(rechts2);
		rechtsPan.add(rechts3);
		rechtsPan.add(passwordError);
		rechtsPan.add(rechts5);
	}
	
	private void zeichneErrorUn() {
		rechtsPan.add(rechts);
		rechtsPan.add(usernameError);
		rechtsPan.add(rechts3);
		rechtsPan.add(rechts4);
		rechtsPan.add(rechts5);
	}
	
	private void zeichneLogin() {
//		berechnung von ipadx in abhängigkeit zum Label
		panBreite = 1;
		resizePan();
		
//		hinzufügen der objekte zum frame
		addGB(frameHeader, gridx = 1, gridy = 1, gridwidth = 3, ipadx = 400, ipady = 150);
		addGB(linksPan, gridx = 1, gridy = 2, gridwidth = 1, ipadx = 150 - ixl, ipady = 0);
		addGB(mittePan, gridx = 2, gridy = 2, gridwidth = 1, ipadx = 0, ipady = 0);
		addGB(rechtsPan, gridx = 3, gridy = 2, gridwidth = 1, ipadx = 150 - ixr, ipady = 0);
		addGB(untenPan, gridx = 1, gridy = 3, gridwidth = 3, ipadx = 300, ipady = 0);
		
		linksPan.removeAll();
		mittePan.removeAll();
		rechtsPan.removeAll();
		untenPan.removeAll();
		
//		nur zum testen
		linksPan.add(links);
		linksPan.repaint();
		rechtsPan.setLayout(new GridLayout(5, 1));
		zeichneIO();
		rechtsPan.repaint();
		untenPan.add(registerLabel);
		untenPan.repaint();
		
//		füge mittePan objekte hinzu
		mittePan.setLayout(new GridLayout(5, 1, 0, 2));
		mittePan.add(usernameLabel);
		mittePan.add(usernameField);
		usernameField.setEnabled(true);
		usernameField.requestFocus();
		mittePan.add(passwordLabel);
		mittePan.add(passwordField);
		passwordField.setText("");
		mittePan.add(mitteUnten);
		
//		füge mitteUnten objekte hinzu
		mitteUnten.setLayout(new GridLayout(1, 2, 10, 0));
		mitteUnten.add(forgetLabel);
		mitteUnten.add(logInButton);
		
		linksPan.validate();
		mittePan.validate();
		rechtsPan.validate();
		untenPan.validate();
	}
	
	private void zeichneForget() {
		
		panBreite = 2;
		resizePan();
		usernameField.setBackground(Color.WHITE);
		usernameField.setText("");
		
		JLabel nameLab = new JLabel("Name");
		JLabel streetLab = new JLabel("Strasse/HNr.");
		JLabel zipLab = new JLabel("Postleitzahl/zip");
		JLabel cityLab = new JLabel("Stadt");
		JLabel wpwLab = new JLabel("Passwort wiederholen");
		
		JLabel opwLab =new JLabel("altes Passwort");
		
		addGB(linksPan, gridx = 1, gridy = 2, gridwidth = 1, ipadx = 100 - ixl, ipady = 20);
		addGB(mittePan, gridx = 2, gridy = 2, gridwidth = 1, ipadx = 100, ipady = 20);
		addGB(rechtsPan, gridx = 3, gridy = 2, gridwidth = 1, ipadx = 100 - ixr, ipady = 20);
		
		linksPan.removeAll();
		linksPan.setLayout(new GridLayout(6, 1));
		linksPan.add(nameLab);
		linksPan.add(nameField);
		nameField.requestFocus();
		linksPan.add(streetLab);
		linksPan.add(streetField);
		linksPan.add(new JLabel(""));
		linksPan.add(new JLabel(""));
		linksPan.repaint();
		linksPan.validate();
		
		mittePan.removeAll();
		mittePan.setLayout(new GridLayout(6, 1));
		mittePan.add(zipLab);
		mittePan.add(zipField);
		mittePan.add(cityLab);
		mittePan.add(cityField);
		mittePan.add(usernameLabel);
		mittePan.add(usernameField);
		usernameField.setEnabled(false);
		mittePan.repaint();
		mittePan.validate();
		
		rechtsPan.removeAll();
		rechtsPan.setLayout(new GridLayout(6, 1));
//		rechtsPan.add(opwLab);
//		rechtsPan.add(opwField);
		rechtsPan.add(passwordLabel);
		rechtsPan.add(pwField);
		rechtsPan.add(wpwLab);
		rechtsPan.add(wpwField);
		rechtsPan.add(rechts3);
		rechtsPan.add(rechts4);
		rechtsPan.repaint();
		rechtsPan.validate();
		
		untenPan.removeAll();
		untenPan.add(backLab);
		untenPan.add(changeLab);
		untenPan.repaint();
		untenPan.validate();
		
	}
	
	private void resizePan() {
		switch (panBreite) {
		case 1:
			ixr = (int) rechts2.getPreferredSize().getWidth();
			ixl = (int) links.getPreferredSize().getWidth();
			break;
		case 2:
			ixr = (int) usernameField.getPreferredSize().getWidth();
			ixl = (int) usernameField.getPreferredSize().getWidth();
			break;
		}
	}

	private void zeichneRegister() {
		
		panBreite = 2;
		resizePan();
		usernameField.setBackground(Color.WHITE);
		usernameField.setText("");
		pwField.setEnabled(true);
		wpwField.setEnabled(true);
		
		JLabel nameLab = new JLabel("Name");
		JLabel streetLab = new JLabel("Strasse/HNr.");
		JLabel zipLab = new JLabel("Postleitzahl/zip");
		JLabel cityLab = new JLabel("Stadt");
		JLabel wpwLab = new JLabel("Passwort wiederholen");
		
		addGB(linksPan, gridx = 1, gridy = 2, gridwidth = 1, ipadx = 100 - ixl, ipady = 20);
		addGB(mittePan, gridx = 2, gridy = 2, gridwidth = 1, ipadx = 100, ipady = 20);
		addGB(rechtsPan, gridx = 3, gridy = 2, gridwidth = 1, ipadx = 100 - ixr, ipady = 20);
		
		linksPan.removeAll();
		linksPan.setLayout(new GridLayout(6, 1));
		linksPan.add(nameLab);
		linksPan.add(nameField);
		nameField.requestFocus();
		linksPan.add(streetLab);
		linksPan.add(streetField);
		linksPan.add(new JLabel(""));
		linksPan.add(new JLabel(""));
		linksPan.repaint();
		linksPan.validate();
		
		mittePan.removeAll();
		mittePan.setLayout(new GridLayout(6, 1));
		mittePan.add(zipLab);
		mittePan.add(zipField);
		mittePan.add(cityLab);
		mittePan.add(cityField);
		mittePan.add(usernameLabel);
		mittePan.add(usernameField);
		mittePan.repaint();
		mittePan.validate();
		
		rechtsPan.removeAll();
		rechtsPan.setLayout(new GridLayout(6, 1));
		rechtsPan.add(passwordLabel);
		rechtsPan.add(pwField);
		rechtsPan.add(wpwLab);
		rechtsPan.add(wpwField);
		rechtsPan.add(rechts3);
		rechtsPan.add(rechts4);
		rechtsPan.repaint();
		rechtsPan.validate();
		
		untenPan.removeAll();
		untenPan.add(backLab);
		untenPan.add(regLab);
		untenPan.repaint();
		untenPan.validate();
		
	}
	
	private void anmeldeVorgang() {
		try {
			logIn();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	private void logIn() throws IOException {
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
				passwordField.setBackground(new Color(250,240,230));
				passwordField.requestFocus();
				usernameField.setBackground(Color.WHITE);
//				usernameField.repaint();
//				usernameField.validate();
				rechtsPan.removeAll();
				zeichneErrorPw();
				rechtsPan.repaint();
				rechtsPan.validate();
				System.out.println("epw");
			}
		} else {
			if (passwordField.getText().equals("")) {
				passwordField.setBackground(new Color(250,240,230));
				usernameField.setBackground(new Color(250,240,230));
				usernameField.requestFocus();
				rechtsPan.removeAll();
				zeichneError();
				rechtsPan.repaint();
				rechtsPan.validate();
				System.out.println("e");
			} else {
				usernameField.setBackground(new Color(250,240,230));
				usernameField.requestFocus();
				passwordField.setBackground(Color.WHITE);
//				passwordField.repaint();
//				passwordField.validate();
				rechtsPan.removeAll();
				zeichneErrorUn();
				rechtsPan.repaint();
				rechtsPan.validate();
				System.out.println("eun");
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
		if (comp.equals(forgetLabel)) {
			zeichneForget();
		}
		if (comp.equals(registerLabel)) {
			System.out.println(registerLabel);
			zeichneRegister();
		}
		if (comp.equals(regLab)) {
			registrierung();
		}
		if (comp.equals(backLab)) {
			zeichneLogin();
		}
		if (comp.equals(changeLab)) {
			loginVergessen();
		}
	}
	
	public void keyPressed(KeyEvent kp) {
		key = kp.getKeyCode();
//		System.out.println("key in pressed: " + key);
//		System.out.println(usernameField.getWidth());
//		System.out.println(usernameField.getHeight());
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped (KeyEvent ke) {
//		if (usernameField.getText().equals("Usernamen eingeben!")) {
//			usernameField.setText("");
//		}
//		if (passwordField.getText().equals("Passwort eingeben!")) {
//			passwordField.setText("");
//		}
		// in arbeit
		if (ke.getSource() == usernameField) {
			if (key == KeyEvent.VK_ENTER) {
				passwordField.requestFocus();
			}
		} if (ke.getSource() == passwordField) {
			if (key == KeyEvent.VK_ENTER) {
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
					//frame.setLocationRelativeTo(null);
					//frame.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
        }); // Erzeugt einen neuen Thread, der eine Instanz von LogInGUI erzeugt
	}

}
