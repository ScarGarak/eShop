package shop.local.ui.gui.mitarbeitergui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shop.local.domain.ShopVerwaltung;
import shop.local.domain.exceptions.KundeExistiertNichtException;
import shop.local.domain.exceptions.MitarbeiterExistiertNichtException;
import shop.local.ui.gui.components.JAccountButton;
import shop.local.ui.gui.mitarbeitergui.table.ArtikelTableCellRenderer;
import shop.local.ui.gui.mitarbeitergui.table.ArtikelTableModel;
import shop.local.ui.gui.mitarbeitergui.table.KundenTableModel;
import shop.local.ui.gui.mitarbeitergui.table.MitarbeiterTableModel;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;

@SuppressWarnings("serial")
public class MitarbeiterGUI extends JFrame{
	
	private Mitarbeiter mitarbeiter;
	private ShopVerwaltung shop;
	
	private JTabbedPane tabbedPane;
	
	//Artikel Panel
	private JPanel artikelPanel;
	private ArtikelTableModel artikelTableModel;
	private ArtikelTableCellRenderer artikelTableCellRenderer;
	private JTable artikelTable;
	private JScrollPane artikelTableScrollPane;
	private JPanel artikelButtonsPanel;
	private JButton artikelHinzufuegen;
	private JButton artikelEntfernen;
	private JButton artikelBearbeiten;
	
	//Mitarbeiter Panel
	private JPanel mitarbeiterPanel;
	private JTable mitarbeiterTable;
	private MitarbeiterTableModel mitarbeiterTableModel;
	private JScrollPane mitarbeiterTableScrollPane;
	private JPanel mitarbeiterButtonsPanel;
	private JButton mitarbeiterHinzufuegen;
	private JButton mitarbeiterEntfernen;
	
	//Kunden Panel
	private JPanel kundenPanel;
	private JTable kundenTable;
	private KundenTableModel kundenTableModel;
	private JScrollPane kundenTableScrollPane;
	private JPanel kundenButtonsPanel;
	private JButton kundenEntfernen;
	
	//Header
	private JPanel headerPanel;
	private JButton accountButton;
	private JTextField searchField;
	private JButton searchButton;
	
	//Footer
	private JPanel footerPanel;
	
	public MitarbeiterGUI(Mitarbeiter mitarbeiter, ShopVerwaltung shop) throws IOException{
		super("eShop - Mitarbeiter");
		this.shop = shop;
		this.mitarbeiter = mitarbeiter;
		
		initialize();
	}
	
	private void initialize(){
		setMinimumSize(new Dimension(500, 300));
		setSize(new Dimension(700, 500));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		createHeader();
		createTabbedPane();
		createFooter();
		
		add(headerPanel, BorderLayout.NORTH);
		add(tabbedPane, BorderLayout.CENTER);
		add(footerPanel, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createHeader(){
		headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));
		accountButton = new JAccountButton(mitarbeiter.getName());
		headerPanel.add(accountButton);	
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
		searchPanel.add(new JLabel("Suche "));
		searchField = new JTextField();
		searchField.addActionListener(new SearchListener());
		searchPanel.add(searchField);
		searchButton = new JButton("Los");
		searchButton.addActionListener(new SearchListener());
		searchPanel.add(searchButton);
		headerPanel.add(searchPanel);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
	}
	
	private void createFooter(){
		footerPanel = new JPanel();
	}
	
	private void createTabbedPane(){
		tabbedPane = new JTabbedPane();
		
		createArtikelPanel();
		tabbedPane.addTab("Artikel", artikelPanel);

		createMitarbeiterPanel();
		tabbedPane.addTab("Mitarbeiter", mitarbeiterPanel);

		createKundenPanel();
		tabbedPane.addTab("Kunden", kundenPanel);

		JPanel panel4 = new JPanel();
		panel4.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Log", panel4);
		
		tabbedPane.addChangeListener(new TabListener());
	}
	
	private void createArtikelPanel(){
		artikelPanel = new JPanel();
		artikelPanel.setLayout(new BoxLayout(artikelPanel, BoxLayout.X_AXIS));
		
		/////////// Artikel Tabelle ///////////
		artikelTableModel = new ArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung());
		
		artikelTableCellRenderer = new ArtikelTableCellRenderer();
		
		artikelTable = new JTable(artikelTableModel);
		artikelTable.setShowGrid(true);
		artikelTable.setGridColor(Color.LIGHT_GRAY);
		
		artikelTable.setDefaultRenderer(Object.class, artikelTableCellRenderer);
		artikelTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		artikelTable.getTableHeader().setReorderingAllowed(false);
		
		artikelTableScrollPane = new JScrollPane(artikelTable);
		artikelTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		artikelTableScrollPane.setAlignmentY(TOP_ALIGNMENT);
		
		
		/////////// Artikel Buttons ///////////
		artikelButtonsPanel = new JPanel();
		artikelButtonsPanel.setLayout(new BoxLayout(artikelButtonsPanel, BoxLayout.Y_AXIS));
		
		artikelHinzufuegen = new JButton("    Hinzufügen    ");
		artikelEntfernen =   new JButton("     Entfernen      ");
		artikelBearbeiten =  new JButton("Bestand erhöhen");
		artikelBearbeiten.addActionListener(new ActionListener() {
			////////// Bestand erhöhen //////////
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = artikelTable.getSelectedRow();
				
				if(row != -1){
					Artikel a = artikelTableModel.getArtikel(row);
					System.out.println(a.toString());
				}else{
					System.err.println("Keine Zeile ausgewählt!");
				}
			}
		});
		
		artikelButtonsPanel.add(artikelHinzufuegen);
		artikelButtonsPanel.add(artikelEntfernen);
		artikelButtonsPanel.add(artikelBearbeiten);
		artikelButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		
		
		
		artikelPanel.add(artikelTableScrollPane);
		artikelPanel.add(artikelButtonsPanel);
	}
	
	private void createMitarbeiterPanel(){
		mitarbeiterPanel = new JPanel();
		mitarbeiterPanel.setLayout(new BoxLayout(mitarbeiterPanel, BoxLayout.X_AXIS));
		
		mitarbeiterTableModel = new MitarbeiterTableModel(shop.gibAlleMitarbeiter());
		
		mitarbeiterTable = new JTable(mitarbeiterTableModel);
		mitarbeiterTable.setShowGrid(true);
		mitarbeiterTable.setGridColor(Color.BLACK);
		
		mitarbeiterTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mitarbeiterTable.getTableHeader().setReorderingAllowed(false);
		
		mitarbeiterTableScrollPane = new JScrollPane(mitarbeiterTable);
		mitarbeiterTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		mitarbeiterTableScrollPane.setAlignmentY(TOP_ALIGNMENT);
		
		/////////// Mitarbeiter Buttons ///////////
		mitarbeiterButtonsPanel = new JPanel();
		mitarbeiterButtonsPanel.setLayout(new BoxLayout(mitarbeiterButtonsPanel, BoxLayout.Y_AXIS));
		
		mitarbeiterHinzufuegen = new JButton("Hinzufügen ");
		mitarbeiterEntfernen =   new JButton("  Entfernen  ");
		
		mitarbeiterButtonsPanel.add(mitarbeiterHinzufuegen);
		mitarbeiterButtonsPanel.add(mitarbeiterEntfernen);
		mitarbeiterButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		
		mitarbeiterPanel.add(mitarbeiterTableScrollPane);
		mitarbeiterPanel.add(mitarbeiterButtonsPanel);
	}
	
	private void createKundenPanel(){
		kundenPanel = new JPanel();
		kundenPanel.setLayout(new BoxLayout(kundenPanel, BoxLayout.X_AXIS));
		
		kundenTableModel = new KundenTableModel(shop.gibAlleKunden());
		
		kundenTable = new JTable(kundenTableModel);
		kundenTable.setShowGrid(true);
		kundenTable.setGridColor(Color.BLACK);
		
		kundenTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		kundenTable.getTableHeader().setReorderingAllowed(false);
		
		kundenTableScrollPane = new JScrollPane(kundenTable);
		kundenTableScrollPane.setBorder(BorderFactory.createEtchedBorder());
		kundenTableScrollPane.setAlignmentY(TOP_ALIGNMENT);
		
		/////////// Kunden Buttons ///////////
		kundenButtonsPanel = new JPanel();
		kundenButtonsPanel.setLayout(new BoxLayout(kundenButtonsPanel, BoxLayout.Y_AXIS));
		
		kundenEntfernen =   new JButton("Entfernen");
		
		kundenButtonsPanel.add(kundenEntfernen);
		kundenButtonsPanel.setAlignmentY(TOP_ALIGNMENT);
		
		kundenPanel.add(kundenTableScrollPane);
		kundenPanel.add(kundenButtonsPanel);
	}
	
	class SearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(searchButton) || ae.getSource().equals(searchField)) {
				int index = tabbedPane.getSelectedIndex();
				if(index != -1){
					switch(index){
					case 0: artikelTable.setModel(new ArtikelTableModel(shop.sucheArtikel(searchField.getText())));
							artikelTableScrollPane.setViewportView(artikelTable);
							break;
					case 1: try{
								int id = Integer.parseInt(searchField.getText());
								List<Mitarbeiter> m = new Vector<Mitarbeiter>();
								m.add(shop.sucheMitarbeiter(id));
								mitarbeiterTable.setModel(new MitarbeiterTableModel(m));
								mitarbeiterTableScrollPane.setViewportView(mitarbeiterTable);
							}catch (NumberFormatException e){
								if(searchField.getText().equals("")){
									mitarbeiterTable.setModel(new MitarbeiterTableModel(shop.gibAlleMitarbeiter()));
									mitarbeiterTableScrollPane.setViewportView(mitarbeiterTable);
								}else{
									System.err.println("Bitte geben Sie eine ID Nummer ein!");
								}
							} catch (MitarbeiterExistiertNichtException e) {
								System.err.println(e.getMessage());
							}
							break;
					case 2: try{
								int id = Integer.parseInt(searchField.getText());
								List<Kunde> k = new Vector<Kunde>();
								k.add(shop.sucheKunde(id));
								kundenTable.setModel(new KundenTableModel(k));
								kundenTableScrollPane.setViewportView(kundenTable);
							}catch (NumberFormatException e){
								if(searchField.getText().equals("")){
									kundenTable.setModel(new KundenTableModel(shop.gibAlleKunden()));
									kundenTableScrollPane.setViewportView(kundenTable);
								}else{
									System.err.println("Bitte geben Sie eine ID Nummer ein!");
								}
							} catch (KundeExistiertNichtException e) {
								System.err.println(e.getMessage());
							}
							break;
					case 3: System.out.println("Log Tab!");
							break;
					default:System.out.println("Nix!");
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
					break;
			case 1: searchField.setEnabled(true);
					searchButton.setEnabled(true);
					break;
			case 2: searchField.setEnabled(true);
					searchButton.setEnabled(true);
					break;
			case 3: searchField.setEnabled(false);
					searchButton.setEnabled(false);
					break;
			default: break;
			}
			
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
