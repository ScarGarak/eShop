package shop.local.ui.gui.mitarbeitergui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import shop.local.domain.ShopVerwaltung;
import shop.local.domain.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.KundeExistiertNichtException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertNichtException;
import shop.local.domain.exceptions.UsernameExistiertBereitsException;
import shop.local.ui.gui.components.JAccountButton;
import shop.local.ui.gui.mitarbeitergui.table.ArtikelTableCellRenderer;
import shop.local.ui.gui.mitarbeitergui.table.ArtikelTableModel;
import shop.local.ui.gui.mitarbeitergui.table.KundenTableModel;
import shop.local.ui.gui.mitarbeitergui.table.MitarbeiterTableModel;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Massengutartikel;
import shop.local.valueobjects.Mitarbeiter;

@SuppressWarnings("serial")
public class MitarbeiterGUI extends JFrame{
	
	public static double MINDESTLOHN = 1800;
	
	private Mitarbeiter mitarbeiter;
	private ShopVerwaltung shop;
	
	private JTabbedPane tabbedPane;
	
	//////////// Artikel Panel ////////////
	private JPanel artikelPanel;
	private ArtikelTableModel artikelTableModel;
	private ArtikelTableCellRenderer artikelTableCellRenderer;
	private JTable artikelTable;
	private JPanel artikelButtonsPanel;
	private JButton artikelHinzufuegen;
	private JButton artikelEntfernen;
	private JButton artikelEinlagern;
	private JButton artikelBearbeiten;
	private JButton artikelAuslagern;
		//Artikel Footer
	private JPanel artikelFooterWrapper;
	private JLabel errorMsg;
	private JPanel artikelFooterPanel;
	
		//Artikel Eingabe Feld Komponenten
	private JTextField artikelNummerInput;
	private JTextField artikelBezeichnungInput;
	private JTextField artikelPreisInput;
	private JTextField artikelPackungsGroesseInput;
	private JTextField artikelBestandInput;
	
		//Artikel verschiedene Eingabefelder
	private JPanel artikelHinzufuegenEingabeFeld;
	private JPanel artikelBearbeitenEingabeFeld;
	private JPanel artikelEinlagernEingabeFeld;
	private JPanel artikelAuslagernEingabeFeld;
	
		//Verschieden Buttons Panel
	private JPanel artikelHinzufuegenButtonsPanel;
	private JPanel artikelBearbeitenButtonsPanel;
	private JPanel artikelEinlagernButtonsPanel;
	private JPanel artikelAuslagernButtonsPanel;
	
	
	//////////// Mitarbeiter Panel ////////////
	private JPanel mitarbeiterPanel;
	private JTable mitarbeiterTable;
	private MitarbeiterTableModel mitarbeiterTableModel;
//	private JScrollPane mitarbeiterTableScrollPane;
	private JPanel mitarbeiterButtonsPanel;
	private JButton mitarbeiterHinzufuegen;
	private JButton mitarbeiterBearbeiten;
	private JButton mitarbeiterEntfernen;
	
		//Artikel Eingabe Feld Komponenten
	private JTextField mitarbeiterIDInput;
	private JTextField mitarbeiterUsernameInput;
	private JTextField mitarbeiterNameInput;
	private JTextField mitarbeiterGehaltInput;
	
		//Mitarbeiter Footer
	private JPanel mitarbeiterFooterWrapper;
	private JPanel mitarbeiterFooterPanel;
	
		//Mitarbeiter verschiedene Eingabefelder
	private JPanel mitarbeiterHinzufuegenEingabeFeld;
	private JPanel mitarbeiterBearbeitenEingabeFeld;
	
		//Verschieden Buttons Panel
	private JPanel mitarbeiterHinzufuegenButtonsPanel;
	private JPanel mitarbeiterBearbeitenButtonsPanel;
	
	//////////// Kunden Panel ////////////
	private JPanel kundenPanel;
	private JTable kundenTable;
	private KundenTableModel kundenTableModel;
	private JPanel kundenButtonsPanel;
	private JButton kundenEntfernen;
	private JButton kundenBlockieren;
	
		//Mitarbeiter Footer
	private JPanel kundenFooterWrapper;
	
	//////////// Log Panel ////////////
	private JPanel logPanel;
	private JTextArea logTextArea;
	private JScrollPane logScrollPane;
	
	//////////// Header ////////////
	private JPanel headerPanel;
	private JButton accountButton;
	private JTextField searchField;
	private JButton searchButton;
	private JButton logoutButton;
	
	
	public MitarbeiterGUI(Mitarbeiter mitarbeiter, ShopVerwaltung shop) throws IOException{
		super("eShop - Mitarbeiter");
		this.shop = shop;
		this.mitarbeiter = mitarbeiter;
		
		initialize();
	}
	
	private void initialize() throws IOException{
		setMinimumSize(new Dimension(700, 500));
		setSize(new Dimension(700, 500));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		createHeader();
		createTabbedPane();
		
		add(headerPanel, BorderLayout.NORTH);
		add(tabbedPane, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createHeader(){
		accountButton = new JAccountButton(mitarbeiter.getName());
		logoutButton = new JButton("Abmelden");
		logoutButton.addActionListener(new logoutListener());
		JPanel accountPanel = new JPanel();
		accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.PAGE_AXIS));
		accountPanel.add(accountButton);
		accountPanel.add(logoutButton);
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
		searchPanel.add(new JLabel("Suche "));
		searchField = new JTextField();
		searchField.setToolTipText("Bitte geben Sie hier einen Suchbegriff ein.");
		searchField.addActionListener(new SearchListener());
		searchPanel.add(searchField);
		searchButton = new JButton("Los");
		searchButton.addActionListener(new SearchListener());
		searchPanel.add(searchButton);
		headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.add(accountPanel, BorderLayout.WEST);
		headerPanel.add(searchPanel, BorderLayout.CENTER);
	}
	
	private void createTabbedPane() throws IOException{
		tabbedPane = new JTabbedPane();
		
		createArtikelPanel();
		tabbedPane.addTab("Artikel", artikelPanel);

		createMitarbeiterPanel();
		tabbedPane.addTab("Mitarbeiter", mitarbeiterPanel);

		createKundenPanel();
		tabbedPane.addTab("Kunden", kundenPanel);

		createLogPanel();
		tabbedPane.addTab("Log", logPanel);
		
		tabbedPane.addChangeListener(new TabListener());
	}
	
	//////////////////////  Artikel Panels  //////////////////////
	
	private void createArtikelPanel(){
		artikelPanel = new JPanel(new BorderLayout());
		
		JPanel artikelNorthPanel = new JPanel();
		artikelNorthPanel.setLayout(new BoxLayout(artikelNorthPanel, BoxLayout.X_AXIS));
		
		/////////// Artikel Tabelle ///////////
		artikelTableModel = new ArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
		
		artikelTableCellRenderer = new ArtikelTableCellRenderer();
		
		artikelTable = new JTable(artikelTableModel);
		artikelTable.setShowGrid(true);
		artikelTable.setGridColor(Color.LIGHT_GRAY);
		
		artikelTable.setDefaultRenderer(Object.class, artikelTableCellRenderer);
		artikelTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		artikelTable.getTableHeader().setReorderingAllowed(false);
		artikelTable.setAutoCreateRowSorter(true);
		artikelTable.getSelectionModel().addListSelectionListener(new ArtikelSelectionListener());
		setTableCellAlignment(artikelTableCellRenderer, artikelTable, JLabel.LEFT);
		
		JScrollPane artikelTableScrollPane = new JScrollPane(artikelTable);
		artikelTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		artikelTableScrollPane.setAlignmentY(TOP_ALIGNMENT);
		
		
		/////////// Artikel Buttons ///////////
		artikelButtonsPanel = new JPanel();
		artikelButtonsPanel.setLayout(new BoxLayout(artikelButtonsPanel, BoxLayout.Y_AXIS));
		
		artikelHinzufuegen = new JButton("Hinzufügen");
		artikelHinzufuegen.addActionListener(new ActionListener() {
			////////// Artikel hinzufuegen //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				// Säubere zuerst alle nötigen Komponenten
				clearErrorMsg();
				clearEingabeFelder();
				artikelFooterPanel.removeAll();
				
				clearArtikelTableSelection();
				
				// Bilde das Panel zum Hinzufuegen eines Artikels neu. Dies ist notwendig, da die Eingabefelder auch beim
				// Bearbeiten benutzt werden. Somit gehen wir sicher, dass sich die Eingabefelder jetzt auch im richtigen
				// Panel befinden.
				rebuildArtikelHinzufuegenEingabeFeld();
				
				// Fuege jetzt noch die entsprechenden Panels zum Artikelfooter hinzu
				artikelFooterPanel.add(Box.createGlue());
				artikelFooterPanel.add(artikelHinzufuegenEingabeFeld);
				artikelFooterPanel.add(Box.createRigidArea(new Dimension(50, 50)));
				artikelFooterPanel.add(artikelHinzufuegenButtonsPanel);
				artikelFooterPanel.add(Box.createGlue());
				
				// Setze das Artikelfoote in den Wrapper
				artikelFooterWrapper.add(artikelFooterPanel);
				artikelFooterWrapper.setVisible(true);
			}
		});
		
		artikelEntfernen =   new JButton(" Entfernen  ");
		artikelEntfernen.addActionListener(new ActionListener() {
			////////// Artikel entfernen //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrorMsg();
				artikelFooterWrapper.removeAll();
				
				int row = artikelTable.getSelectedRow();
				
				if(row != -1){
					Artikel a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					
					try {
						int choice = JOptionPane.showConfirmDialog(new JFrame(),
								"Sind Sie sicher, dass Sie das Artikel '"+a.getBezeichnung()+"'\n"
								+"mit der ID "+a.getArtikelnummer()+" löschen möchten?", "Sicher?!",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						
						if(choice == 0){
							shop.entferneArtikel(mitarbeiter, a.getArtikelnummer());
							updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
							clearArtikelTableSelection();
						}
						artikelFooterWrapper.setVisible(false);
					} catch (ArtikelExistiertNichtException e1) {
						setErrorMsg("Das Artikel existiert nicht!", artikelFooterWrapper);
						artikelFooterWrapper.setVisible(true);
					}
				}else{
					setErrorMsg("Keine Zeile ausgewählt!", artikelFooterWrapper);
					artikelFooterWrapper.setVisible(true);
				}
			}
		});
		artikelEntfernen.setEnabled(false);
		
		artikelEinlagern =  new JButton("  Einlagern  ");
		artikelEinlagern.addActionListener(new ActionListener() {
			////////// Artikel einlagern //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrorMsg();
				clearEingabeFelder();
				artikelFooterPanel.removeAll();
				
				int row = artikelTable.getSelectedRow();
				
				if(row != -1){
//					Artikel a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					
					rebuildEinlagernEingabeFeld();
					
					// Fuege jetzt noch die entsprechenden Panels zum Artikelfooter hinzu
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.add(artikelEinlagernEingabeFeld);
					artikelFooterPanel.add(Box.createRigidArea(new Dimension(75, 50)));
					artikelFooterPanel.add(artikelEinlagernButtonsPanel);
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.revalidate();
					artikelFooterPanel.repaint();
					
					// Setze das Artikelfooter in den Wrapper
					artikelFooterWrapper.add(artikelFooterPanel);
					artikelFooterWrapper.setVisible(true);
				}else{
					setErrorMsg("Keine Zeile ausgewählt!", artikelFooterWrapper);
				}
				
			}
		});
		artikelEinlagern.setEnabled(false);
		
		artikelBearbeiten = new JButton(" Bearbeiten ");
		artikelBearbeiten.addActionListener(new ActionListener() {
			////////// Bearbeiten //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				// Säubere zuerst alle nötigen Komponenten
				clearErrorMsg();
				clearEingabeFelder();
				artikelFooterPanel.removeAll();
				
				int row = artikelTable.getSelectedRow();
				
				if(row != -1){
					Artikel a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					// Bilde das Panel zum Bearbeiten eines Artikels neu. Dies ist notwendig, da die Eingabefelder auch beim
					// Hinzufuegen benutzt werden. Somit gehen wir sicher, dass sich die Eingabefelder jetzt auch im richtigen
					// Panel befinden.Gleichzeitig werden die Felder initialisiert
					rebuildArtikelBearbeitenEingabeFeld(a);
					
					// Fuege jetzt noch die entsprechenden Panels zum Artikelfooter hinzu
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.add(artikelBearbeitenEingabeFeld);
					artikelFooterPanel.add(Box.createRigidArea(new Dimension(50, 50)));
					artikelFooterPanel.add(artikelBearbeitenButtonsPanel);
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.revalidate();
					artikelFooterPanel.repaint();
					
					// Setze das Artikelfooter in den Wrapper
					artikelFooterWrapper.add(artikelFooterPanel);
					artikelFooterWrapper.setVisible(true);
				}else{
					setErrorMsg("Keine Zeile ausgewählt!", artikelFooterWrapper);
				}
			}
		});
		artikelBearbeiten.setEnabled(false);
		
		artikelAuslagern = new JButton(" Auslagern  ");
		artikelAuslagern.addActionListener(new ActionListener() {
			////////// Artikel auslagern //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrorMsg();
				clearEingabeFelder();
				artikelFooterPanel.removeAll();
				
				int row = artikelTable.getSelectedRow();
				
				if(row != -1){
//					Artikel a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					
					rebuildAuslagernEingabeFeld();
					
					// Fuege jetzt noch die entsprechenden Panels zum Artikelfooter hinzu
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.add(artikelAuslagernEingabeFeld);
					artikelFooterPanel.add(Box.createRigidArea(new Dimension(75, 50)));
					artikelFooterPanel.add(artikelAuslagernButtonsPanel);
					artikelFooterPanel.add(Box.createGlue());
					artikelFooterPanel.revalidate();
					
					// Setze das Artikelfooter in den Wrapper
					artikelFooterWrapper.add(artikelFooterPanel);
					artikelFooterWrapper.setVisible(true);
				}else{
					setErrorMsg("Keine Zeile ausgewählt!", artikelFooterWrapper);
				}
			}
		});
		artikelAuslagern.setEnabled(false);
		
		
		artikelButtonsPanel.add(artikelHinzufuegen);
		artikelButtonsPanel.add(artikelBearbeiten);
		artikelButtonsPanel.add(artikelEinlagern);
		artikelButtonsPanel.add(artikelAuslagern);
		artikelButtonsPanel.add(artikelEntfernen);
		artikelButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		
		artikelNorthPanel.add(artikelTableScrollPane);
		artikelNorthPanel.add(artikelButtonsPanel);
		
		createArtikelFooterWrapper();
		
		artikelPanel.add(artikelNorthPanel, BorderLayout.CENTER);
		artikelPanel.add(artikelFooterWrapper, BorderLayout.SOUTH);
	}
	
	private void createArtikelFooterWrapper(){
		artikelFooterWrapper = new JPanel();
		artikelFooterWrapper.setLayout(new BoxLayout(artikelFooterWrapper, BoxLayout.Y_AXIS));
		
		errorMsg = new JLabel("");
		errorMsg.setForeground(Color.RED);
		errorMsg.setAlignmentX(LEFT_ALIGNMENT);
		
		createArtikelFooterPanel();

//		artikelFooterWrapper.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		artikelFooterWrapper.setVisible(false);
	}
	
	private void createArtikelFooterPanel(){
		artikelFooterPanel = new JPanel();
		artikelFooterPanel.setLayout(new BoxLayout(artikelFooterPanel, BoxLayout.X_AXIS));
		artikelFooterPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		createArtikelEingabeFeldKomponenten();
		rebuildArtikelHinzufuegenEingabeFeld();
		
		createArtikelHinzufuegenButtonsPanel();
		createArtikelBearbeitenButtonsPanel();
		createArtikelEinlagernButtonsPanel();
		createArtikelAuslagernButtonsPanel();
	}

	private void createArtikelEingabeFeldKomponenten(){
		artikelNummerInput = new JTextField(10);
		artikelBezeichnungInput = new JTextField(30);
		artikelPreisInput = new JTextField(10);
		artikelPackungsGroesseInput = new JTextField(10);
		artikelBestandInput = new JTextField(10);
	}
	
		//Hinzufuegen
	private void createArtikelHinzufuegenButtonsPanel(){
		artikelHinzufuegenButtonsPanel = new JPanel();
		artikelHinzufuegenButtonsPanel.setLayout(new BoxLayout(artikelHinzufuegenButtonsPanel, BoxLayout.Y_AXIS));

		JButton bestaetigen = new JButton(" Hinzufügen ");
		bestaetigen.addActionListener(new ActionListener() {
			///////////// Artikel Hinzufuegen /////////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;

				try{
					int artikelnummer = Integer.parseInt(artikelNummerInput.getText());
					String bezeichnung = artikelBezeichnungInput.getText();
					double preis = Double.parseDouble(artikelPreisInput.getText());
					int bestand = Integer.parseInt(artikelBestandInput.getText());
					if(bestand < 0){
						// Wird zum Catch Block mit NumberFormatException weitergeleitet
						// weil -1 kein gültiger Wert ist
						throw new NumberFormatException();
					}
					if(artikelPackungsGroesseInput.getText().equals("") || artikelPackungsGroesseInput.getText().equals("1")){
						shop.fuegeArtikelEin(mitarbeiter, artikelnummer, bezeichnung, preis, bestand);
						updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
					}else{
						int packungsgroesse = Integer.parseInt(artikelPackungsGroesseInput.getText());
						shop.fuegeMassengutartikelEin(mitarbeiter, artikelnummer, bezeichnung, preis, packungsgroesse, bestand);
						updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
					}

					success = true;
				}catch (NumberFormatException nfe){
					setErrorMsg("Bitte fügen Sie richtige Werte ein!", artikelFooterWrapper);
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException ex) {
					setErrorMsg("Bestand ist keine Vielfache der Packungsgröße!", artikelFooterWrapper);
				} catch (ArtikelExistiertBereitsException ex) {
					setErrorMsg("Die angegebene ID existiert bereits!", artikelFooterWrapper);
				}


				if(success){
					artikelFooterWrapper.setVisible(false);
					artikelFooterWrapper.remove(artikelFooterPanel);

					// Reset von allen Input Felden
					clearEingabeFelder();
					clearErrorMsg();
				}
			}
		});

		JButton abbrechen = new JButton(" Abbrechen  ");
		abbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				artikelFooterWrapper.setVisible(false);
				artikelFooterWrapper.remove(artikelFooterPanel);

				// Resetten
				clearErrorMsg();
				clearEingabeFelder();
			}
		});

		artikelHinzufuegenButtonsPanel.add(bestaetigen);
		artikelHinzufuegenButtonsPanel.add(abbrechen);
	}
	
	private void rebuildArtikelHinzufuegenEingabeFeld(){
		artikelHinzufuegenEingabeFeld = new JPanel(new GridLayout(0, 4));
		artikelHinzufuegenEingabeFeld.setPreferredSize(new Dimension(500, 90));
		artikelHinzufuegenEingabeFeld.setMaximumSize(new Dimension(500, 90));
		artikelHinzufuegenEingabeFeld.setMinimumSize(new Dimension(500, 90));
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Artikelnummer:"));
		artikelHinzufuegenEingabeFeld.add(artikelNummerInput);
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Bezeichnung:"));
		artikelHinzufuegenEingabeFeld.add(artikelBezeichnungInput);
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Preis:"));
		artikelHinzufuegenEingabeFeld.add(artikelPreisInput);
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Packungsgröße:"));
		artikelHinzufuegenEingabeFeld.add(artikelPackungsGroesseInput);
		
		artikelHinzufuegenEingabeFeld.add(new JLabel("   Bestand:"));
		artikelHinzufuegenEingabeFeld.add(artikelBestandInput);
	}
	
		//Bearbeiten
	private void createArtikelBearbeitenButtonsPanel(){
		artikelBearbeitenButtonsPanel = new JPanel();
		artikelBearbeitenButtonsPanel.setLayout(new BoxLayout(artikelBearbeitenButtonsPanel, BoxLayout.Y_AXIS));
		
		JButton bestaetigen = new JButton(" Speichern ");
		bestaetigen.addActionListener(new ActionListener() {
			//////// Speichern ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				Artikel a = null;
				String bezeichnung = "";
				double preis = 0;
				
				int row = artikelTable.getSelectedRow();
				if(row != -1){
					a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					try{
						bezeichnung = artikelBezeichnungInput.getText();
						preis = Double.parseDouble(artikelPreisInput.getText());
						if(bezeichnung.equals("")){
							setErrorMsg("Die Bezeichnung muss einen gültigen Namen haben!", artikelFooterWrapper);
						}else{
							success = true;
						}
					} catch (NumberFormatException nfe){
						setErrorMsg("Geben Sie bitte richtege Werte ein!", artikelFooterWrapper);
					}
				}else{
					setErrorMsg("Ups... Keine Zeile ausgewählt!", artikelFooterWrapper);
				}
				
				if(a == null){
					setErrorMsg("Interner Fehler: 'Kein Artikel gefunden' !", artikelFooterWrapper);
				}else if(success){
					a.setBezeichnung(bezeichnung);
					a.setPreis(preis);
					
					updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
					
					artikelFooterWrapper.setVisible(false);
					// Resetten
					clearErrorMsg();
					clearEingabeFelder();
				}
				
			}
		});
		
		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
			//////// Abbrechen ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				artikelFooterWrapper.setVisible(false);
				artikelFooterWrapper.remove(artikelFooterPanel);

				// Resetten
				clearErrorMsg();
				clearEingabeFelder();
			}
		});
		
		artikelBearbeitenButtonsPanel.add(bestaetigen);
		artikelBearbeitenButtonsPanel.add(abbrechen);
	}
	
	private void rebuildArtikelBearbeitenEingabeFeld(Artikel a){
		artikelBearbeitenEingabeFeld = new JPanel(new GridLayout(0, 2));
		artikelBearbeitenEingabeFeld.setPreferredSize(new Dimension(250, 80));
		artikelBearbeitenEingabeFeld.setMaximumSize(new Dimension(250, 100));
		artikelBearbeitenEingabeFeld.setMinimumSize(new Dimension(250, 70));
		
		artikelBearbeitenEingabeFeld.add(new JLabel("   Bezeichnung:"));
		artikelBearbeitenEingabeFeld.add(artikelBezeichnungInput);
		artikelBezeichnungInput.setText(""+a.getBezeichnung());
		
		artikelBearbeitenEingabeFeld.add(new JLabel("   Preis:"));
		artikelBearbeitenEingabeFeld.add(artikelPreisInput);
		artikelPreisInput.setText(a.getPreis()+"");
		
		if(a instanceof Massengutartikel){
			artikelBearbeitenEingabeFeld.add(new JLabel("   Packungsgröße:"));
			artikelBearbeitenEingabeFeld.add(artikelPackungsGroesseInput);
			artikelPackungsGroesseInput.setText(((Massengutartikel) a).getPackungsgroesse()+"");
		}
	}
	
		//Einlagern
	private void createArtikelEinlagernButtonsPanel(){
		artikelEinlagernButtonsPanel = new JPanel();
		artikelEinlagernButtonsPanel.setLayout(new BoxLayout(artikelEinlagernButtonsPanel, BoxLayout.Y_AXIS));
		
		JButton bestaetigen = new JButton(" Einlagern ");
		bestaetigen.addActionListener(new ActionListener() {
			//////// Einlagern ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				int row = artikelTable.getSelectedRow();
				Artikel a = null;
				int anzahl = 0;
				
				if(row != -1){
					a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					try{
						anzahl = Integer.parseInt(artikelBestandInput.getText());
						if(anzahl < 0){
							setErrorMsg("Sie können nur eine positive Zahl eingeben!", artikelFooterWrapper);
						}else{
							success = true;
						}
					} catch (NumberFormatException nfe){
						setErrorMsg("Geben Sie einen gültigen Wert ein!", artikelFooterWrapper);
					}
				}else{
					setErrorMsg("Ups... Keine Zeile ausgewählt!", artikelFooterWrapper);
				}
				
				if(a == null){
					setErrorMsg("Interner Fehler: 'Kein Artikel gefunden' !", artikelFooterWrapper);
				}else if(success){
					try {
						a.setBestand(a.getBestand()+anzahl);
					} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e1) {
						setErrorMsg("Die Anzahl ist keine Vielfache der Packungsgroesse!", artikelFooterWrapper);
						return;
					}
					
					updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
					
					artikelFooterWrapper.setVisible(false);
					// Resetten
					clearErrorMsg();
					clearEingabeFelder();
				}
			}
		});
		
		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
			//////// Abbrechen ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				artikelFooterWrapper.setVisible(false);
				artikelFooterWrapper.remove(artikelFooterPanel);

				// Resetten
				clearErrorMsg();
				clearEingabeFelder();
			}
		});
		
		artikelEinlagernButtonsPanel.add(bestaetigen);
		artikelEinlagernButtonsPanel.add(abbrechen);
	}
	
	private void rebuildEinlagernEingabeFeld(){
		artikelEinlagernEingabeFeld = new JPanel(new GridLayout(0, 2));
		artikelEinlagernEingabeFeld.setPreferredSize(new Dimension(150, 20));
		artikelEinlagernEingabeFeld.setMaximumSize(new Dimension(150, 40));
		artikelEinlagernEingabeFeld.setMinimumSize(new Dimension(150, 15));
		
		artikelEinlagernEingabeFeld.add(new JLabel("   Anzahl:"));
		artikelEinlagernEingabeFeld.add(artikelBestandInput);
	}
	
		//Auslagern
	private void createArtikelAuslagernButtonsPanel(){
		artikelAuslagernButtonsPanel = new JPanel();
		artikelAuslagernButtonsPanel.setLayout(new BoxLayout(artikelAuslagernButtonsPanel, BoxLayout.Y_AXIS));
		
		JButton bestaetigen = new JButton(" Auslagern");
		bestaetigen.addActionListener(new ActionListener() {
			//////// Auslagern ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				int row = artikelTable.getSelectedRow();
				Artikel a = null;
				int anzahl = 0;
				
				if(row != -1){
					a = artikelTableModel.getArtikel(artikelTable.convertRowIndexToModel(row));
					try{
						anzahl = Integer.parseInt(artikelBestandInput.getText());
						if(anzahl < 0){
							setErrorMsg("Sie können nur eine positive Zahl eingeben!", artikelFooterWrapper);
						}else if(anzahl > a.getBestand()){
							setErrorMsg("Sie können nicht soviel auslagern!", artikelFooterWrapper);
						}else{
							success = true;
						}
					} catch (NumberFormatException nfe){
						setErrorMsg("Geben Sie einen gültigen Wert ein!", artikelFooterWrapper);
					}
				}else{
					setErrorMsg("Ups... Keine Zeile ausgewählt!", artikelFooterWrapper);
				}
				
				if(a == null){
					setErrorMsg("Interner Fehler: 'Kein Artikel gefunden' !", artikelFooterWrapper);
				}else if(success){
					try {
						a.setBestand(a.getBestand()-anzahl);
					} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e1) {
						setErrorMsg("Die Anzahl ist keine Vielfache der Packungsgroesse!", artikelFooterWrapper);
						return;
					}
					
					updateArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
					
					artikelFooterWrapper.setVisible(false);
					// Resetten
					clearErrorMsg();
					clearEingabeFelder();
				}
			}
		});
		
		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
			//////// Abbrechen ////////
			@Override
			public void actionPerformed(ActionEvent e) {
				artikelFooterWrapper.setVisible(false);
				artikelFooterWrapper.remove(artikelFooterPanel);

				// Resetten
				clearErrorMsg();
				clearEingabeFelder();
			}
		});
		
		artikelAuslagernButtonsPanel.add(bestaetigen);
		artikelAuslagernButtonsPanel.add(abbrechen);
	}
	
	private void rebuildAuslagernEingabeFeld(){
		artikelAuslagernEingabeFeld = new JPanel(new GridLayout(0, 2));
		artikelAuslagernEingabeFeld.setPreferredSize(new Dimension(150, 20));
		artikelAuslagernEingabeFeld.setMaximumSize(new Dimension(150, 40));
		artikelAuslagernEingabeFeld.setMinimumSize(new Dimension(150, 15));
		
		artikelAuslagernEingabeFeld.add(new JLabel("   Anzahl:"));
		artikelAuslagernEingabeFeld.add(artikelBestandInput);
	}
	
	
	//Artikel Helper Methoden
	private void clearEingabeFelder(){
		artikelNummerInput.setText("");
		artikelBezeichnungInput.setText("");
		artikelPreisInput.setText("");
		artikelPackungsGroesseInput.setText("");
		artikelBestandInput.setText("");
	}
	
	private void clearArtikelTableSelection(){
		artikelTable.clearSelection();
		
		artikelBearbeiten.setEnabled(false);
		artikelEinlagern.setEnabled(false);
		artikelAuslagern.setEnabled(false);
		artikelEntfernen.setEnabled(false);
	}
	
	private void updateArtikelTableModel(List<Artikel> artikelListe){
		artikelTableModel = new ArtikelTableModel(artikelListe);
		artikelTable.setModel(artikelTableModel);
		artikelTableModel.fireTableDataChanged();
	}
	
	
	//////////////////////  Mitarbeiter Panels  //////////////////////
	
	private void createMitarbeiterPanel(){
		mitarbeiterPanel = new JPanel(new BorderLayout());
		
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		
		mitarbeiterTableModel = new MitarbeiterTableModel(shop.gibAlleMitarbeiter());
		
		mitarbeiterTable = new JTable(mitarbeiterTableModel);
		mitarbeiterTable.setShowGrid(true);
		mitarbeiterTable.setGridColor(Color.LIGHT_GRAY);
		
		mitarbeiterTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mitarbeiterTable.getTableHeader().setReorderingAllowed(false);
		mitarbeiterTable.setAutoCreateRowSorter(true);
		mitarbeiterTable.getSelectionModel().addListSelectionListener(new MitarbeiterSelectionListener());
		setTableCellAlignment(new DefaultTableCellRenderer(), mitarbeiterTable, JLabel.LEFT);
		
		JScrollPane mitarbeiterTableScrollPane = new JScrollPane(mitarbeiterTable);
		mitarbeiterTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		mitarbeiterTableScrollPane.setAlignmentY(TOP_ALIGNMENT);
		
		/////////// Mitarbeiter Buttons ///////////
		mitarbeiterButtonsPanel = new JPanel();
		mitarbeiterButtonsPanel.setLayout(new BoxLayout(mitarbeiterButtonsPanel, BoxLayout.Y_AXIS));
		
		mitarbeiterHinzufuegen = new JButton("Hinzufügen");
		mitarbeiterHinzufuegen.addActionListener(new ActionListener() {
			////////// Mitarbeiter hinzufuegen //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				// Setze alles zurueck
				clearErrorMsg();
				clearMitarbeiterEingabeFelder();
				mitarbeiterFooterPanel.removeAll();
				
				clearMitarbeiterTableSelection();
				
				rebuildMitarbeiterHinzufuegenEingabeFeld();

				mitarbeiterFooterPanel.add(Box.createGlue());
				mitarbeiterFooterPanel.add(mitarbeiterHinzufuegenEingabeFeld);
				mitarbeiterFooterPanel.add(Box.createRigidArea(new Dimension(50,50)));
				mitarbeiterFooterPanel.add(mitarbeiterHinzufuegenButtonsPanel);
				mitarbeiterFooterPanel.add(Box.createGlue());
				mitarbeiterFooterPanel.revalidate();
				
				mitarbeiterFooterWrapper.add(mitarbeiterFooterPanel);
				mitarbeiterFooterWrapper.setVisible(true);
			}
		});
		
		mitarbeiterBearbeiten = new JButton(" Bearbeiten ");
		mitarbeiterBearbeiten.addActionListener(new ActionListener() {
			////////// Mitarbeiter bearbeiten //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				mitarbeiterFooterWrapper.setVisible(false);
				// Setze alles zurueck
				clearErrorMsg();
				clearMitarbeiterEingabeFelder();
				mitarbeiterFooterPanel.removeAll();
				
				int row = mitarbeiterTable.getSelectedRow();
				if(row != -1){
					Mitarbeiter m = mitarbeiterTableModel.getMitarbeiter(mitarbeiterTable.convertRowIndexToModel(row));
					
					rebuildMitarbeiterBearbeitenEingabeFeld(m);

					mitarbeiterFooterPanel.add(Box.createGlue());
					mitarbeiterFooterPanel.add(mitarbeiterBearbeitenEingabeFeld);
					mitarbeiterFooterPanel.add(Box.createRigidArea(new Dimension(50,50)));
					mitarbeiterFooterPanel.add(mitarbeiterBearbeitenButtonsPanel);
					mitarbeiterFooterPanel.add(Box.createGlue());
					mitarbeiterFooterPanel.revalidate();

					mitarbeiterFooterWrapper.add(mitarbeiterFooterPanel);
					mitarbeiterFooterWrapper.setVisible(true);
				}else{
					setErrorMsg("Keine Zeile ausgewählt!", mitarbeiterFooterWrapper);
					mitarbeiterFooterWrapper.setVisible(true);
				}
			}
		});
		mitarbeiterBearbeiten.setEnabled(false);
		
		
		mitarbeiterEntfernen =   new JButton("  Entfernen ");
		mitarbeiterEntfernen.addActionListener(new ActionListener() {
			////////// Mitarbeiter entfernen //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				mitarbeiterFooterWrapper.setVisible(false);
				clearErrorMsg();
				mitarbeiterFooterPanel.removeAll();
				
				int row = mitarbeiterTable.getSelectedRow();
				
				if(row != -1){
					Mitarbeiter m = mitarbeiterTableModel.getMitarbeiter(mitarbeiterTable.convertRowIndexToModel(row));
					
					if(!mitarbeiter.equals(m)){
						int choice = JOptionPane.showConfirmDialog(new JFrame(), "Sind Sie sicher, dass Sie Mitarbeiter '" + m.getName() + "'\n"
								+"(ID: "+m.getId()+") löschen möchten?", "Sicher?!", JOptionPane.YES_NO_OPTION, 
								JOptionPane.WARNING_MESSAGE);

						if(choice == 0){
							shop.mitarbeiterLoeschen(m);
							updateMitarbeiterTableModel(shop.gibAlleMitarbeiter());
							clearMitarbeiterTableSelection();
						}
					}else{
						setErrorMsg("Sie können sich nicht selbst löschen! Bitte gehen Sie dafür in Ihre Account Einstellungen!", mitarbeiterFooterWrapper);
						mitarbeiterFooterWrapper.setVisible(true);
					}
				}else{
					setErrorMsg("Keine Zeile ausgewählt!", mitarbeiterFooterWrapper);
					mitarbeiterFooterWrapper.setVisible(true);
				}
			}
		});
		mitarbeiterEntfernen.setEnabled(false);
		
		mitarbeiterButtonsPanel.add(mitarbeiterHinzufuegen);
		mitarbeiterButtonsPanel.add(mitarbeiterBearbeiten);
		mitarbeiterButtonsPanel.add(mitarbeiterEntfernen);
		mitarbeiterButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		
		north.add(mitarbeiterTableScrollPane);
		north.add(mitarbeiterButtonsPanel);
		
		createMitarbeiterFooterWrapper();
		
		mitarbeiterPanel.add(north, BorderLayout.CENTER);
		mitarbeiterPanel.add(mitarbeiterFooterWrapper, BorderLayout.SOUTH);
	}
	
	private void createMitarbeiterFooterWrapper(){
		mitarbeiterFooterWrapper = new JPanel();
		mitarbeiterFooterWrapper.setLayout(new BoxLayout(mitarbeiterFooterWrapper, BoxLayout.Y_AXIS));
		
		createMitarbeiterFooterPanel();

//		mitarbeiterFooterWrapper.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		mitarbeiterFooterWrapper.setVisible(false);
	}
	
	private void createMitarbeiterFooterPanel(){
		mitarbeiterFooterPanel = new JPanel();
		mitarbeiterFooterPanel.setLayout(new BoxLayout(mitarbeiterFooterPanel, BoxLayout.X_AXIS));
		mitarbeiterFooterPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		createMitarbeiterEingabeFeldKomponenten();
		rebuildArtikelHinzufuegenEingabeFeld();
		
		createMitarbeiterHinzufuegenButtonsPanel();
		createMitarbeiterBearbeitenButtonsPanel();
	}
	
	private void createMitarbeiterEingabeFeldKomponenten(){
		mitarbeiterIDInput = new JTextField(10);
		mitarbeiterUsernameInput = new JTextField(10);
		mitarbeiterNameInput = new JTextField(10);
		mitarbeiterGehaltInput = new JTextField(10);
	}
	
	
		//Hinzufuegen
	private void rebuildMitarbeiterHinzufuegenEingabeFeld(){
		mitarbeiterHinzufuegenEingabeFeld = new JPanel(new GridLayout(0, 4));
		mitarbeiterHinzufuegenEingabeFeld.setPreferredSize(new Dimension(500, 60));
		mitarbeiterHinzufuegenEingabeFeld.setMaximumSize(new Dimension(500, 70));
		mitarbeiterHinzufuegenEingabeFeld.setMinimumSize(new Dimension(500, 40));
		
		mitarbeiterHinzufuegenEingabeFeld.add(new JLabel("   ID Nummer:"));
		mitarbeiterHinzufuegenEingabeFeld.add(mitarbeiterIDInput);
		
		mitarbeiterHinzufuegenEingabeFeld.add(new JLabel("   Username:"));
		mitarbeiterHinzufuegenEingabeFeld.add(mitarbeiterUsernameInput);
		
		mitarbeiterHinzufuegenEingabeFeld.add(new JLabel("   Name:"));
		mitarbeiterHinzufuegenEingabeFeld.add(mitarbeiterNameInput);
		
		mitarbeiterHinzufuegenEingabeFeld.add(new JLabel("   Gehalt:"));
		mitarbeiterHinzufuegenEingabeFeld.add(mitarbeiterGehaltInput);
	}
	
	private void createMitarbeiterHinzufuegenButtonsPanel(){
		mitarbeiterHinzufuegenButtonsPanel = new JPanel();
		mitarbeiterHinzufuegenButtonsPanel.setLayout(new BoxLayout(mitarbeiterHinzufuegenButtonsPanel, BoxLayout.Y_AXIS));

		JButton bestaetigen = new JButton(" Hinzufügen ");
		bestaetigen.addActionListener(new ActionListener() {
			///////////// Mitarbeiter Hinzufuegen /////////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				
				try{
					int ID = Integer.parseInt(mitarbeiterIDInput.getText());
					String username = mitarbeiterUsernameInput.getText();
					String name = mitarbeiterNameInput.getText();
					double gehalt = 0;

					if(!mitarbeiterGehaltInput.getText().equals("")){
						gehalt = Double.parseDouble(mitarbeiterGehaltInput.getText());
					}

					if(username.equals("") || name.equals("")){
						setErrorMsg("Sie müssen 'Username' und 'Name' angeben!", mitarbeiterFooterWrapper);
					}else{
						shop.fuegeMitarbeiterHinzu(ID, username, "123" , name);
						shop.sucheMitarbeiter(ID).setGehalt(gehalt);
						success = true;
					}
				} catch (NumberFormatException nfe){
					setErrorMsg("Bitte geben Sie nur gültige Werte an!", mitarbeiterFooterWrapper);
				} catch (MitarbeiterExistiertBereitsException ex) {
					setErrorMsg("Es existiert bereits ein Mitarbeiter mit dieser ID!", mitarbeiterFooterWrapper);
				} catch (UsernameExistiertBereitsException ex) {
					setErrorMsg("Dieser Username ist bereits vergeben!", mitarbeiterFooterWrapper);
				} catch (MitarbeiterExistiertNichtException ex) {
					setErrorMsg("Interner Fehler: 'Mitarbeiter existiert nicht: Gehalt wurde nicht gesetzt!'", mitarbeiterFooterWrapper);
				}
				
				if(success){
					updateMitarbeiterTableModel(shop.gibAlleMitarbeiter());
					
					//Setze alles zurück
					clearErrorMsg();
					clearMitarbeiterEingabeFelder();
					mitarbeiterFooterPanel.removeAll();
					mitarbeiterFooterWrapper.setVisible(false);
				}
			}
		});

		JButton abbrechen = new JButton(" Abbrechen  ");
		abbrechen.addActionListener(new ActionListener() {
			///////////// Abbrechen ///////////// 
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrorMsg();
				clearMitarbeiterEingabeFelder();
				mitarbeiterFooterPanel.removeAll();
				mitarbeiterFooterWrapper.setVisible(false);
			}
		});

		mitarbeiterHinzufuegenButtonsPanel.add(bestaetigen);
		mitarbeiterHinzufuegenButtonsPanel.add(abbrechen);
	}
	
		//Bearbeiten
	private void rebuildMitarbeiterBearbeitenEingabeFeld(Mitarbeiter m){
		mitarbeiterBearbeitenEingabeFeld = new JPanel(new GridLayout(0, 2));
		mitarbeiterBearbeitenEingabeFeld.setPreferredSize(new Dimension(200, 30));
		mitarbeiterBearbeitenEingabeFeld.setMaximumSize(new Dimension(200, 35));
		mitarbeiterBearbeitenEingabeFeld.setMinimumSize(new Dimension(200, 20));
		
		mitarbeiterBearbeitenEingabeFeld.add(new JLabel("   Gehalt:"));
		mitarbeiterBearbeitenEingabeFeld.add(mitarbeiterGehaltInput);
		mitarbeiterGehaltInput.setText(m.getGehalt()+"");
	}
	
	private void createMitarbeiterBearbeitenButtonsPanel(){
		mitarbeiterBearbeitenButtonsPanel = new JPanel();
		mitarbeiterBearbeitenButtonsPanel = new JPanel();
		mitarbeiterBearbeitenButtonsPanel.setLayout(new BoxLayout(mitarbeiterBearbeitenButtonsPanel, BoxLayout.Y_AXIS));

		JButton bestaetigen = new JButton(" Speichern ");
		bestaetigen.addActionListener(new ActionListener() {
			///////////// Mitarbeiter Bearbeiten /////////////
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				
				int row = mitarbeiterTable.getSelectedRow();
				
				if(row != -1){
					Mitarbeiter m = mitarbeiterTableModel.getMitarbeiter(mitarbeiterTable.convertRowIndexToModel(row));
					double gehalt = 0;
					try{
						gehalt = Double.parseDouble(mitarbeiterGehaltInput.getText());
						
						if(gehalt < 0){
							setErrorMsg("Sie können keinen negativen Wert eingeben!", mitarbeiterFooterWrapper);
						}else if(gehalt < MINDESTLOHN){
							setErrorMsg(String.format("Der Mindestlohn für Mitarbeiter beträgt: %.2f "+ Currency.getInstance(Locale.GERMANY), MINDESTLOHN) , mitarbeiterFooterWrapper);
						}else{
							m.setGehalt(gehalt);
							success = true;
						}
					} catch (NumberFormatException nfe){
						setErrorMsg("Bitte geben Sie einen gültigen Wert an!", mitarbeiterFooterWrapper);
					}
				}else{
					setErrorMsg("Keine Zeile ausgewählt!", mitarbeiterFooterWrapper);
				}
				
				if(success){
					clearErrorMsg();
					clearMitarbeiterEingabeFelder();
					mitarbeiterFooterPanel.removeAll();
					mitarbeiterFooterWrapper.setVisible(false);
				}
			}
		});

		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
			///////////// Abbrechen /////////////
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrorMsg();
				clearMitarbeiterEingabeFelder();
				mitarbeiterFooterPanel.removeAll();
				mitarbeiterFooterWrapper.setVisible(false);
			}
		});

		mitarbeiterBearbeitenButtonsPanel.add(bestaetigen);
		mitarbeiterBearbeitenButtonsPanel.add(abbrechen);
	}
	
	//Mitarbeiter Helper Methoden
	private void clearMitarbeiterEingabeFelder(){
		mitarbeiterIDInput.setText("");
		mitarbeiterUsernameInput.setText("");
		mitarbeiterNameInput.setText("");
		mitarbeiterGehaltInput.setText("");
	}

	private void clearMitarbeiterTableSelection(){
		mitarbeiterTable.clearSelection();

		mitarbeiterBearbeiten.setEnabled(false);
		mitarbeiterEntfernen.setEnabled(false);
	}

	private void updateMitarbeiterTableModel(List<Mitarbeiter> mitarbeiterListe){
		mitarbeiterTableModel = new MitarbeiterTableModel(mitarbeiterListe);
		mitarbeiterTable.setModel(mitarbeiterTableModel);
		mitarbeiterTableModel.fireTableDataChanged();
	}
	
	//////////////////////  Kunden Panels  //////////////////////
	
	private void createKundenPanel(){
		kundenPanel = new JPanel(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		
		kundenTableModel = new KundenTableModel(shop.gibAlleKunden());
		
		kundenTable = new JTable(kundenTableModel);
		kundenTable.setShowGrid(true);
		kundenTable.setGridColor(Color.LIGHT_GRAY);
		
		kundenTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		kundenTable.getTableHeader().setReorderingAllowed(false);
		kundenTable.setAutoCreateRowSorter(true);
		kundenTable.getSelectionModel().addListSelectionListener(new KundenSelectionListener());
		setTableCellAlignment(new DefaultTableCellRenderer(), kundenTable, JLabel.LEFT);
		
		JScrollPane kundenTableScrollPane = new JScrollPane(kundenTable);
		kundenTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		kundenTableScrollPane.setAlignmentY(TOP_ALIGNMENT);
		
		/////////// Kunden Buttons ///////////
		kundenButtonsPanel = new JPanel();
		kundenButtonsPanel.setLayout(new BoxLayout(kundenButtonsPanel, BoxLayout.Y_AXIS));
		
		kundenEntfernen =  new JButton(" Entfernen");
		kundenEntfernen.addActionListener(new ActionListener() {
			////////// Kunde entfernen //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				kundenFooterWrapper.setVisible(false);
				clearErrorMsg();
				
				int row = kundenTable.getSelectedRow();
				
				if(row != -1){
					Kunde k = kundenTableModel.getKunde(kundenTable.convertRowIndexToModel(row));
					
					int choice = JOptionPane.showConfirmDialog(new JFrame(), "Sind Sie sicher, dass Sie Kunde '"+k.getName()+"'\n"
							+ "(ID: "+k.getId()+") löschen möchten!", "Sicher?!",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
					if(choice == 0){
						shop.kundenLoeschen(k);
						updateKundenTableModel(shop.gibAlleKunden());
						clearKundenTableSelection();
					}
					
				}else{
					setErrorMsg("Keine Zeile ausgewählt!", kundenFooterWrapper);
					kundenFooterWrapper.setVisible(true);
				}
			}
		});
		kundenEntfernen.setEnabled(false);
		
		kundenBlockieren = new JButton("Blockieren");
		kundenBlockieren.addActionListener(new ActionListener() {
			////////// Kunde blockieren //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				kundenFooterWrapper.setVisible(false);
				clearErrorMsg();
				
				int row = kundenTable.getSelectedRow();
				
				if(row != -1){
					Kunde k = kundenTableModel.getKunde(kundenTable.convertRowIndexToModel(row));
//					k.setBlocked(!k.getBlocked());
					System.out.println("Den Kunde den Sie blockieren möchten ist:");
					System.out.println(k.toString());
				}else{
					setErrorMsg("Keine Zeile ausgewählt!", kundenFooterWrapper);
					kundenFooterWrapper.setVisible(true);
				}
			}
		});
		kundenBlockieren.setEnabled(false);
		
		kundenButtonsPanel.add(kundenEntfernen);
		kundenButtonsPanel.add(kundenBlockieren);
		kundenButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		
		centerPanel.add(kundenTableScrollPane);
		centerPanel.add(kundenButtonsPanel);
		
		createKundenFooterWrapper();
		
		kundenPanel.add(centerPanel, BorderLayout.CENTER);
		kundenPanel.add(kundenFooterWrapper, BorderLayout.SOUTH);
	}

	private void createKundenFooterWrapper(){
		kundenFooterWrapper = new JPanel();
		kundenFooterWrapper.setLayout(new BoxLayout(kundenFooterWrapper, BoxLayout.Y_AXIS));
		
//		kundenFooterWrapper.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		kundenFooterWrapper.setVisible(false);
	}
	
	//Kunden Helper Methoden
	
	private void updateKundenTableModel(List<Kunde> kundenListe){
		kundenTableModel = new KundenTableModel(kundenListe);
		kundenTable.setModel(kundenTableModel);
		kundenTableModel.fireTableDataChanged();
	}
	
	private void clearKundenTableSelection(){
		kundenTable.clearSelection();

		kundenEntfernen.setEnabled(false);
		kundenBlockieren.setEnabled(false);
	}
	
	//////////////////////  Log Panels  //////////////////////
	
	private void createLogPanel() throws IOException{
		logPanel = new JPanel(new BorderLayout());
		
		logTextArea = new JTextArea();
		logTextArea.setText(shop.gibLogDatei());
		logTextArea.setEditable(false);
		
		logScrollPane = new JScrollPane(logTextArea);
		
		
		logPanel.add(logScrollPane, BorderLayout.CENTER);
	}
	
	////////////////////// Listener //////////////////////
	
	class logoutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(logoutButton)) {
				try {
//					p = null;
					dispose();
					shop.logoutGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	class SearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(searchButton) || ae.getSource().equals(searchField)) {
				int index = tabbedPane.getSelectedIndex();
				if(index != -1){
					switch(index){
					case 0: updateArtikelTableModel(shop.sucheArtikel(searchField.getText()));
							break;
					case 1: mitarbeiterFooterWrapper.setVisible(false);
							clearErrorMsg();
							try{
								int id = Integer.parseInt(searchField.getText());
								List<Mitarbeiter> m = new Vector<Mitarbeiter>();
								m.add(shop.sucheMitarbeiter(id));
								
								updateMitarbeiterTableModel(m);
							}catch (NumberFormatException e){
								if(searchField.getText().equals("")){
									updateMitarbeiterTableModel(shop.gibAlleMitarbeiter());
								}else{
									setErrorMsg("Bitte geben Sie eine ID Nummer an!", mitarbeiterFooterWrapper);
									mitarbeiterFooterWrapper.setVisible(true);
								}
							} catch (MitarbeiterExistiertNichtException e) {
								setErrorMsg("Ein Mitarbeiter mit der angegebenen ID existiert nicht!", mitarbeiterFooterWrapper);
								mitarbeiterFooterWrapper.setVisible(true);
							}
							break;
					case 2: kundenFooterWrapper.setVisible(false);
							try{
								int id = Integer.parseInt(searchField.getText());
								List<Kunde> k = new Vector<Kunde>();
								k.add(shop.sucheKunde(id));
								updateKundenTableModel(k);
							}catch (NumberFormatException e){
								if(searchField.getText().equals("")){
									updateKundenTableModel(shop.gibAlleKunden());
								}else{
									setErrorMsg("Bitte geben Sie eine ID Nummer an!", kundenFooterWrapper);
									kundenFooterWrapper.setVisible(true);
								}
							} catch (KundeExistiertNichtException e) {
								setErrorMsg("Ein Kunde mit der angegebenen ID existiert nicht!", kundenFooterWrapper);
								kundenFooterWrapper.setVisible(true);
							}
							break;
					case 3: //System.out.println("Log Tab!");
							break;
					default:System.err.println("Interner Fehler: 'Anzahl der Tabs überschritten'!");
					}
				}
				
			}
		}
	}
	
	
	class TabListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			int index = tabbedPane.getSelectedIndex();
			
			switch(index){
			case 0: searchField.setEnabled(true);
					searchButton.setEnabled(true);
					
					// Alles von den anderen Tabs zurücksetzen
					clearMitarbeiterTableSelection();
					break;
					
			case 1: searchField.setEnabled(true);
					searchButton.setEnabled(true);
					
					// Alles von den anderen Tabs zurücksetzen
					clearArtikelTableSelection();
					artikelFooterWrapper.setVisible(false);
					break;
					
			case 2: searchField.setEnabled(true);
					searchButton.setEnabled(true);
					
					// Alles von den anderen Tabs zurücksetzen
					clearArtikelTableSelection();
					clearMitarbeiterTableSelection();
					artikelFooterWrapper.setVisible(false);
					break;
					
			case 3: searchField.setEnabled(false);
					searchButton.setEnabled(false);
					
					// Alles von den anderen Tabs zurücksetzen
					clearArtikelTableSelection();
					clearMitarbeiterTableSelection();
					artikelFooterWrapper.setVisible(false);
					break;
					
			default:System.err.println("Zu viele Tabs!"); 
					break;
			}
			
		}
		
	}
	
	
	class ArtikelSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				artikelFooterWrapper.setVisible(false);
				artikelEinlagern.setEnabled(true);
				artikelBearbeiten.setEnabled(true);
				artikelAuslagern.setEnabled(true);
				artikelEntfernen.setEnabled(true);
			}
		}
		
	}
	
	class MitarbeiterSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				mitarbeiterFooterWrapper.setVisible(false);
				mitarbeiterBearbeiten.setEnabled(true);
				mitarbeiterEntfernen.setEnabled(true);
			}
		}
		
	}

	class KundenSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				kundenFooterWrapper.setVisible(false);
				kundenEntfernen.setEnabled(true);
				kundenBlockieren.setEnabled(true);
			}
		}
		
	}

	////////////////////// Helper Methods //////////////////////
	
	private void setTableCellAlignment(DefaultTableCellRenderer renderer, JTable table, int alignment) {
		renderer.setHorizontalAlignment(alignment);
		for (int i=0; i < table.getColumnCount();i++){
			table.setDefaultRenderer(table.getColumnClass(i),renderer);
		}
		
		table.updateUI();
	} 
	
	private void setErrorMsg(String text, JPanel p){
		errorMsg.setText("   "+text);
		if(errorMsg.getParent() == null)
			p.add(errorMsg);
		p.revalidate();
	}
	
	private void clearErrorMsg(){
		if(errorMsg.getParent() != null){
			JPanel p = (JPanel)errorMsg.getParent();
			p.remove(errorMsg);
			p.revalidate();
		}
	}
	
	public static void main(String[] args){
		try {
			ShopVerwaltung shop = new ShopVerwaltung();
			Mitarbeiter m = shop.sucheMitarbeiter(1);
			new MitarbeiterGUI(m, shop);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MitarbeiterExistiertNichtException e) {
			e.printStackTrace();
		}
	}
}
