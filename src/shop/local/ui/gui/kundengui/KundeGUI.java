package shop.local.ui.gui.kundengui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shop.local.domain.ShopVerwaltung;
import shop.local.domain.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;
import shop.local.domain.exceptions.ArtikelBestandIstZuKleinException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.KundeExistiertNichtException;
import shop.local.domain.exceptions.WarenkorbIstLeerException;
import shop.local.ui.gui.LogInGUI;
import shop.local.ui.gui.components.JAccountButton;
import shop.local.ui.gui.components.JImagePanel;
import shop.local.ui.gui.components.JWarenkorbButton;
import shop.local.ui.gui.kundengui.table.ArtikelTableCellRenderer;
import shop.local.ui.gui.kundengui.table.ArtikelTableModel;
import shop.local.ui.gui.kundengui.table.WarenkorbArtikelTableCellRenderer;
import shop.local.ui.gui.kundengui.table.WarenkorbArtikelTableModel;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Massengutartikel;
import shop.local.valueobjects.Rechnung;
import shop.local.valueobjects.WarenkorbArtikel;

@SuppressWarnings("serial")
public class KundeGUI extends JFrame {

	private ShopVerwaltung shop;
	private Kunde kunde;
	
	private JPanel headerPanel;
	private JButton accountButton;
	private JButton logoutButton;
	private JPanel accountPanel;
	private JTextField searchField;
	private JButton searchButton;
	private JPanel warenkorbPanel;
	private JButton warenkorbButton;
	private JPanel kaufenLeerenPanel;
	private JButton kaufenButton;
	private JButton leerenButton;
	private JPanel tablePanel;
	private JTable searchTable;
	private JScrollPane searchScrollPane;
	private JTable warenkorbTable;
	private JScrollPane warenkorbScrollPane;
	private JLabel artikelanzahl;
	private JLabel gesamtpreis;
	private JPanel tableFooterPanel;
	private JTextArea rechnung;
	private JPanel rechnungPanel; 
	private JPanel detailsPanel;
	private JPanel bildPanel;
	private JPanel infoPanel;
	private JLabel bezeichnung;
	private JTextArea details;
	private JPanel auswahlPanel;
	private JComboBox menge;
	private JPanel mengePanel;
	private JComboBox stueckzahl;
	private JPanel stueckzahlPanel;
	private JTextArea errorMessage;
	private JButton inDenWarenkorbButton;
	private JButton entfernenButton;
	
	public KundeGUI(Kunde kunde, ShopVerwaltung shop) throws IOException {
		super("eShop - Kunde");
		this.shop = shop;
		this.kunde = kunde;
		
		initialize();
	}
	
	private void initialize() {
		setMinimumSize(new Dimension(600, 400));
		setSize(new Dimension(700, 500));
		setLayout(new BorderLayout());

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowCloser());
		
		createHeader();
		createTableSearch();
		createTableWarenkorb();
		createDetails();
		
		add(headerPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(detailsPanel, BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createHeader() {
		accountButton = new JAccountButton(kunde.getName());
		logoutButton = new JButton("Abmelden");
		logoutButton.addActionListener(new logoutListener());
		accountPanel = new JPanel();
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
		warenkorbButton = new JWarenkorbButton(0);
		warenkorbButton.addActionListener(new WarenkorbListener());
		kaufenButton = new JButton("Kaufen");
		kaufenButton.addActionListener(new KaufenListener());
		leerenButton = new JButton("Leeren");
		leerenButton.addActionListener(new LeerenListener());
		kaufenLeerenPanel = new JPanel();
		kaufenLeerenPanel.setLayout(new BoxLayout(kaufenLeerenPanel, BoxLayout.PAGE_AXIS));
		kaufenLeerenPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		kaufenLeerenPanel.add(new JLabel());
		kaufenLeerenPanel.add(kaufenButton);
		kaufenLeerenPanel.add(leerenButton);
		kaufenLeerenPanel.add(new JLabel());
		warenkorbPanel = new JPanel();
		warenkorbPanel.setLayout(new BorderLayout());
		warenkorbPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		warenkorbPanel.add(warenkorbButton, BorderLayout.NORTH);
		headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.add(accountPanel, BorderLayout.WEST);
		headerPanel.add(searchPanel, BorderLayout.CENTER);
		headerPanel.add(warenkorbPanel, BorderLayout.EAST);
	}
	
	private void createTableSearch() {
		searchTable = new JTable(new ArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung()));
		searchTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchTable.getSelectionModel().addListSelectionListener(new SelectionDetailListener());
		searchTable.setDefaultRenderer(Object.class, new ArtikelTableCellRenderer());
		searchTable.setAutoCreateRowSorter(true);
		searchTable.setShowGrid(true);
		searchTable.setGridColor(Color.LIGHT_GRAY);
		searchTable.getTableHeader().setReorderingAllowed(false);
		searchScrollPane = new JScrollPane(searchTable);
		searchScrollPane.setBorder(BorderFactory.createEtchedBorder());
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(searchScrollPane, BorderLayout.CENTER);
	}
	
	private void createTableWarenkorb() {
		warenkorbTable = new JTable(new WarenkorbArtikelTableModel(kunde.getWarenkorbVerwaltung().getWarenkorb()));
		warenkorbTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		warenkorbTable.getSelectionModel().addListSelectionListener(new SelectionDetailListener());
		warenkorbTable.setDefaultRenderer(Object.class, new WarenkorbArtikelTableCellRenderer());
		warenkorbTable.setAutoCreateRowSorter(true);
		warenkorbTable.setShowGrid(true);
		warenkorbTable.setGridColor(Color.LIGHT_GRAY);
		warenkorbTable.getTableHeader().setReorderingAllowed(false);
		warenkorbScrollPane = new JScrollPane(warenkorbTable);
		warenkorbScrollPane.setBorder(BorderFactory.createEtchedBorder());
		artikelanzahl = new JLabel("Ihr Warenkorb ist leer.");
		artikelanzahl.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
		gesamtpreis = new JLabel(String.format("Gesamtpreis: %.2f ", kunde.getWarenkorbVerwaltung().getGesamtpreis()) + Currency.getInstance(Locale.GERMANY));
		gesamtpreis.setHorizontalAlignment(JLabel.RIGHT);
		gesamtpreis.setFont(new Font("Arial", Font.BOLD, 14));
		gesamtpreis.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
		tableFooterPanel = new JPanel();
		tableFooterPanel.setLayout(new BorderLayout());
		tableFooterPanel.add(artikelanzahl, BorderLayout.WEST);
		tableFooterPanel.add(gesamtpreis, BorderLayout.EAST);
		rechnung = new JTextArea();
		rechnung.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0), BorderFactory.createTitledBorder("Rechnung")));
		rechnung.setEditable(false);
		rechnung.setOpaque(false);
		rechnungPanel = new JPanel();
		rechnungPanel.setLayout(new BorderLayout());
		rechnungPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		rechnungPanel.add(new JLabel("Vielen Dank für ihren Einkauf und besuchen Sie uns bald wieder!"), BorderLayout.NORTH);
		rechnungPanel.add(rechnung, BorderLayout.CENTER);
	}
	
	private void createDetails() {
		bildPanel = new JImagePanel(null);
		bezeichnung = new JLabel();
		bezeichnung.setFont(new Font("Arial", Font.BOLD, 16));
		bezeichnung.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
		details = new JTextArea();
		details.setFont(new Font("Arial", Font.PLAIN, 12));
		details.setEditable(false);
		details.setOpaque(false);
		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
		infoPanel.add(bezeichnung, BorderLayout.NORTH);
		infoPanel.add(details, BorderLayout.CENTER);
		errorMessage = new JTextArea();
		errorMessage.setFont(new Font("Arial", Font.PLAIN, 12));
		errorMessage.setForeground(Color.RED);
		errorMessage.setLineWrap(true);
		errorMessage.setWrapStyleWord(true);
		errorMessage.setEditable(false);
		errorMessage.setOpaque(false);
		errorMessage.setPreferredSize(new Dimension(150, 50));
		menge = new JComboBox();
		mengePanel = new JPanel();
		mengePanel.setLayout(new GridLayout(1,2));
		mengePanel.add(new JLabel("  Menge:"));
		mengePanel.add(menge);
		JTextArea stueckzahlLabel = new JTextArea(" Stückzahl ändern:");
		stueckzahlLabel.setEditable(false);
		stueckzahlLabel.setLineWrap(true);
		stueckzahlLabel.setOpaque(false);
		stueckzahlLabel.setPreferredSize(new Dimension(60, 20));
		stueckzahl = new JComboBox();
		stueckzahl.addItemListener(new StueckzahlListener());
		stueckzahlPanel = new JPanel();
		stueckzahlPanel.setLayout(new GridLayout(1,2));
		stueckzahlPanel.add(stueckzahlLabel);
		stueckzahlPanel.add(stueckzahl);
		inDenWarenkorbButton = new JButton("In den Warenkorb");
		inDenWarenkorbButton.addActionListener(new InDenWarenkorbListener());
		entfernenButton = new JButton("Artikel entfernen");
		entfernenButton.addActionListener(new EntfernenListener());
		auswahlPanel = new JPanel();
		auswahlPanel.setLayout(new BorderLayout());
		auswahlPanel.setBorder(BorderFactory.createEmptyBorder(20, 8, 20, 8));
		auswahlPanel.add(errorMessage, BorderLayout.NORTH);
		auswahlPanel.add(mengePanel, BorderLayout.CENTER);
		auswahlPanel.add(inDenWarenkorbButton, BorderLayout.SOUTH);
		detailsPanel = new JPanel();
		detailsPanel.setLayout(new BorderLayout());
	}
	
	private void updateSearchTable(List<Artikel> artikel) {
		ArtikelTableModel atm = (ArtikelTableModel) searchTable.getModel();
		atm.updateDataVector(artikel);
	}
	
	private void updateArtikelMenge(Artikel a) {
		menge.removeAllItems();
		for (int i = 1; i <= a.getBestand(); i++) {
			if (a instanceof Massengutartikel) {
				if (i % ((Massengutartikel) a).getPackungsgroesse() == 0) {
					menge.addItem(i);
				}
			} else {
				menge.addItem(i);
			}
		}
	}
	
	private void updateWarenkorbArtikelStueckzahl(WarenkorbArtikel wa) {
		stueckzahl.removeAllItems();
		for (int i = 0, j = 1; j <= wa.getArtikel().getBestand() + wa.getStueckzahl(); j++) {
			if (wa.getArtikel() instanceof Massengutartikel) {
				if (j % ((Massengutartikel) wa.getArtikel()).getPackungsgroesse() == 0) {
					stueckzahl.insertItemAt(j, i);
					i++;
				}
			} else {
				stueckzahl.insertItemAt(j, i);
				i++;
			}
		}
		stueckzahl.getModel().setSelectedItem(wa.getStueckzahl());
	}
	
	private void updateArtikelanzahl() {
		((JWarenkorbButton) warenkorbButton).setArtikelanzahl(kunde.getWarenkorbVerwaltung().getWarenkorb().size());
		if (((JWarenkorbButton) warenkorbButton).getArtikelanzahl() == 0) {
			artikelanzahl.setText("Ihr Warenkorb ist leer.");
		} else 
		if (((JWarenkorbButton) warenkorbButton).getArtikelanzahl() == 1) {
			artikelanzahl.setText("Es befindet sich " + ((JWarenkorbButton) warenkorbButton).getArtikelanzahl() + " Artikel in ihrem Warenkorb.");
		} else {
			artikelanzahl.setText("Es befinden sich " + ((JWarenkorbButton) warenkorbButton).getArtikelanzahl() + " Artikel in ihrem Warenkorb.");
		}
	}
	
	private void updateGesamtpreis() {
		gesamtpreis.setText(String.format("Gesamtpreis: %.2f ", kunde.getWarenkorbVerwaltung().getGesamtpreis()) + Currency.getInstance(Locale.GERMANY));
	}
	
	class logoutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(logoutButton)) {
				try {
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
				warenkorbPanel.remove(kaufenLeerenPanel);
				warenkorbPanel.add(warenkorbButton, BorderLayout.NORTH);
				headerPanel.validate();
				headerPanel.repaint();
				tablePanel.remove(warenkorbScrollPane);
				tablePanel.remove(tableFooterPanel);
				tablePanel.remove(rechnungPanel);
				warenkorbTable.clearSelection();
				tablePanel.add(searchScrollPane, BorderLayout.CENTER);
				updateSearchTable(shop.sucheArtikel(searchField.getText()));
				searchTable.clearSelection();
				tablePanel.setBorder(null);
				tablePanel.validate();
				tablePanel.repaint();
				validate();
				repaint();
			}
		}
	}
	
	class WarenkorbListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(warenkorbButton)) {
				warenkorbPanel.remove(warenkorbButton);
				warenkorbPanel.add(kaufenLeerenPanel, BorderLayout.CENTER);
				headerPanel.validate();
				headerPanel.repaint();
				tablePanel.remove(searchScrollPane);
				searchTable.clearSelection();
				tablePanel.add(warenkorbScrollPane, BorderLayout.CENTER);
				warenkorbTable.clearSelection();
				updateArtikelanzahl();
				updateGesamtpreis();
				tablePanel.add(tableFooterPanel, BorderLayout.SOUTH);
				tablePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
				tablePanel.validate();
				tablePanel.repaint();
			}
		}
	}
	
	class KaufenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(kaufenButton)) {
				try {
					Rechnung r = shop.kaufen(kunde);
					rechnung.setText(r.toString());
					updateArtikelanzahl();
					warenkorbPanel.remove(kaufenLeerenPanel);
					warenkorbTable.clearSelection();
					tablePanel.remove(warenkorbScrollPane);
					tablePanel.remove(tableFooterPanel);
					tablePanel.add(rechnungPanel, BorderLayout.CENTER);
					tablePanel.setBorder(null);
					tablePanel.validate();
					tablePanel.repaint();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WarenkorbIstLeerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	class LeerenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(leerenButton)) {
				try {
					shop.leeren(kunde);
					updateArtikelanzahl();
					updateGesamtpreis();
					warenkorbTable.clearSelection();
					tablePanel.validate();
					tablePanel.repaint();
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	class SelectionDetailListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent lse) {
			int index = -1;
			if (lse.getSource().equals(searchTable.getSelectionModel()) && searchTable.getSelectedRow() != -1) {
				index = searchTable.convertRowIndexToModel(searchTable.getSelectedRow());
				ArtikelTableModel atm = (ArtikelTableModel) searchTable.getModel();
				Artikel a = atm.getRowValue(index);
				((JImagePanel) bildPanel).setImagePath("images/" + a.getArtikelnummer() + ".jpg");
				bezeichnung.setText(a.getBezeichnung());
				details.setText("");
				details.append("Preis: " + String.format("%.2f ", a.getPreis()) + Currency.getInstance(Locale.GERMANY) + "\n");
				details.append("Bestand: " + a.getBestand() + "\n");
				auswahlPanel.remove(stueckzahlPanel);
				auswahlPanel.add(mengePanel);
				updateArtikelMenge(a);
				auswahlPanel.remove(entfernenButton);
				auswahlPanel.add(inDenWarenkorbButton, BorderLayout.SOUTH);
			} else 
			if (lse.getSource().equals(warenkorbTable.getSelectionModel()) && warenkorbTable.getSelectedRow() != -1) {
				index = warenkorbTable.convertRowIndexToModel(warenkorbTable.getSelectedRow());	
				WarenkorbArtikelTableModel watm = (WarenkorbArtikelTableModel) warenkorbTable.getModel();
				WarenkorbArtikel wa = watm.getRowValue(index);
				((JImagePanel) bildPanel).setImagePath("images/" + wa.getArtikel().getArtikelnummer() + ".jpg");
				bezeichnung.setText(wa.getArtikel().getBezeichnung());
				details.setText("");
				details.append("Stückzahl: " + wa.getStueckzahl() + "\n");
				details.append("Preis: " + String.format("%.2f ", wa.getArtikel().getPreis()) + Currency.getInstance(Locale.GERMANY) + "\n");
				auswahlPanel.remove(mengePanel);
				auswahlPanel.add(stueckzahlPanel);
				updateWarenkorbArtikelStueckzahl(wa);
				auswahlPanel.remove(inDenWarenkorbButton);
				auswahlPanel.add(entfernenButton, BorderLayout.SOUTH);
			}
			if (index != -1) {
				detailsPanel.add(bildPanel, BorderLayout.WEST);
				detailsPanel.add(infoPanel, BorderLayout.CENTER);
				detailsPanel.add(auswahlPanel, BorderLayout.EAST);
				detailsPanel.validate();
				detailsPanel.repaint();
			} else {
				detailsPanel.remove(bildPanel);
				detailsPanel.remove(infoPanel);
				detailsPanel.remove(auswahlPanel);
				detailsPanel.validate();
				detailsPanel.repaint();
			}
			errorMessage.setText("");
		}
	}
	
	class InDenWarenkorbListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(inDenWarenkorbButton)) {
				ArtikelTableModel atm = (ArtikelTableModel) searchTable.getModel();
				Artikel a = atm.getRowValue(searchTable.convertRowIndexToModel(searchTable.getSelectedRow()));
				try {
					shop.inDenWarenkorbLegen(kunde, a, (Integer) menge.getItemAt(menge.getSelectedIndex()));
					updateArtikelanzahl();
					tablePanel.validate();
					tablePanel.repaint();
					updateArtikelMenge(a);
				} catch (NullPointerException e) {
					errorMessage.setText("Bitte wählen Sie unten eine gültige Menge aus.");
				} catch (ArtikelBestandIstZuKleinException e) {
					errorMessage.setText("Der Bestand dieses Artikels ist zu klein oder leer.");
				} catch (ArtikelExistiertNichtException e) {
					errorMessage.setText("Dieser Artikel existiert nicht.");
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
					errorMessage.setText("Der gewählte Menge ist keine Vielfache der Packungsgröße dieses Artikels.");
				}
			}
		}
	}
	
	class EntfernenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(entfernenButton)) {
				WarenkorbArtikelTableModel watm = (WarenkorbArtikelTableModel) warenkorbTable.getModel();
				WarenkorbArtikel wa = watm.getRowValue(warenkorbTable.convertRowIndexToModel(warenkorbTable.getSelectedRow()));
				try {
					shop.ausDemWarenkorbHerausnehmen(kunde, wa.getArtikel());
					updateArtikelanzahl();
					updateGesamtpreis();
					warenkorbTable.clearSelection();
					tablePanel.validate();
					tablePanel.repaint();
				} catch (ArtikelExistiertNichtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	class StueckzahlListener implements ItemListener {
		@Override
	    public void itemStateChanged(ItemEvent event) {
	       if (event.getStateChange() == ItemEvent.SELECTED) {
				WarenkorbArtikelTableModel watm = (WarenkorbArtikelTableModel) warenkorbTable.getModel();
				WarenkorbArtikel wa = watm.getRowValue(warenkorbTable.convertRowIndexToModel(warenkorbTable.getSelectedRow()));
				try {
					if (stueckzahl.getSelectedIndex() != -1) {
						kunde.getWarenkorbVerwaltung().stueckzahlAendern(wa, (Integer) stueckzahl.getItemAt(stueckzahl.getSelectedIndex()));
						updateGesamtpreis();
						tablePanel.validate();
						tablePanel.repaint();
						details.setText("");
						details.append("Stückzahl: " + wa.getStueckzahl() + "\n");
						details.append("Preis: " + String.format("%.2f ", wa.getArtikel().getPreis()) + Currency.getInstance(Locale.GERMANY) + "\n");
						detailsPanel.validate();
						detailsPanel.repaint();
					}
				} catch (ArtikelBestandIstZuKleinException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArtikelExistiertNichtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	class WindowCloser extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent we) {
			Window w = we.getWindow();
			if (((JWarenkorbButton) warenkorbButton).getArtikelanzahl() == 0) {
				w.setVisible(false);
				w.dispose();
				System.exit(0);
			} else {
				if (JOptionPane.showConfirmDialog(null,
					"Sind Sie sich sicher dass Sie die Anwendung beenden wollen?\n" +
					"Sie werden alle Artikel die sich in ihrem Warenkorb befinden verlieren.", "Anwendung beenden",
	                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {		
					try {
						shop.leeren(kunde);
					} catch (ArtikelBestandIstKeineVielfacheDerPackungsgroesseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					w.setVisible(false);
					w.dispose();
					System.exit(0);
				} else {
					w.setVisible(true);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			ShopVerwaltung shop = new ShopVerwaltung();
			new KundeGUI(shop.sucheKunde(1), shop);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KundeExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
