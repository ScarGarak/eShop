package shop.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shop.local.domain.ShopVerwaltung;
import shop.local.domain.exceptions.ArtikelBestandIstKeineVielfacheDerPackungsgroesseException;
import shop.local.domain.exceptions.ArtikelBestandIstZuKleinException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.KundeExistiertNichtException;
import shop.local.ui.gui.components.JAccountButton;
import shop.local.ui.gui.components.JImagePanel;
import shop.local.ui.gui.components.JWarenkorbButton;
import shop.local.ui.gui.table.ArtikelTableModel;
import shop.local.ui.gui.table.WarenkorbArtikelTableModel;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Massengutartikel;
import shop.local.valueobjects.WarenkorbArtikel;

@SuppressWarnings("serial")
public class KundeGUI extends JFrame {

	private ShopVerwaltung shop;
	private Kunde kunde;
	
	private JPanel headerPanel;
	private JButton accountButton;
	private JTextField searchField;
	private JButton searchButton;
	private JPanel warenkorbPanel;
	private JButton warenkorbButton;
	private JButton kaufenButton;
	private JPanel tablePanel;
	private JTable searchTable;
	private JScrollPane searchScrollPane;
	private JTable warenkorbTable;
	private JScrollPane warenkorbScrollPane;
	private JLabel gesamtpreis;
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
	
	public KundeGUI() throws IOException {
		super("eShop - Kunde");
		shop = new ShopVerwaltung();
		try {
			kunde = shop.sucheKunde(1);
		} catch (KundeExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}
	
	private void initialize() {
		setMinimumSize(new Dimension(500, 300));
		setSize(new Dimension(700, 500));
		setLayout(new BorderLayout());

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// Header
		headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));
		accountButton = new JAccountButton(kunde.getName());
		headerPanel.add(accountButton);	
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
		headerPanel.add(searchPanel);
		warenkorbPanel = new JPanel();
		warenkorbPanel.setLayout(new BoxLayout(warenkorbPanel, BoxLayout.PAGE_AXIS));
		warenkorbButton = new JWarenkorbButton(0);
		warenkorbButton.addActionListener(new WarenkorbListener());
		warenkorbPanel.add(warenkorbButton);
		warenkorbPanel.add(new JLabel("Warenkorb"));
		headerPanel.add(warenkorbPanel);
		kaufenButton = new JButton("Kaufen");
		headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		
		// Table Suche
		//TableRowSorter<ArtikelTableModel> artikelSorter = new TableRowSorter<ArtikelTableModel>();
		//artikelSorter.setModel(new ArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung()));
		searchTable = new JTable(new ArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung()));
		searchTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchTable.getSelectionModel().addListSelectionListener(new SelectionDetailListener());
		searchTable.setAutoCreateRowSorter(true);
		//searchTable.setRowSorter(artikelSorter);
		searchTable.setShowGrid(true);
		searchTable.setGridColor(Color.LIGHT_GRAY);
		searchTable.getTableHeader().setReorderingAllowed(false);
		searchScrollPane = new JScrollPane(searchTable);
		searchScrollPane.setBorder(BorderFactory.createEtchedBorder());
		
		// Table Warenkorb
		warenkorbTable = new JTable(new WarenkorbArtikelTableModel(kunde.getWarenkorbVerwaltung().getWarenkorb()));
		warenkorbTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		warenkorbTable.getSelectionModel().addListSelectionListener(new SelectionDetailListener());
		warenkorbTable.setShowGrid(true);
		warenkorbTable.setGridColor(Color.LIGHT_GRAY);
		warenkorbTable.getTableHeader().setReorderingAllowed(false);
		warenkorbScrollPane = new JScrollPane(warenkorbTable);
		warenkorbScrollPane.setBorder(BorderFactory.createEtchedBorder());
		gesamtpreis = new JLabel(String.format("Gesamtpreis: %.2f ", kunde.getWarenkorbVerwaltung().getGesamtpreis()) + Currency.getInstance(Locale.GERMANY));
		gesamtpreis.setHorizontalAlignment(JLabel.RIGHT);
		gesamtpreis.setFont(new Font("Arial", Font.BOLD, 14));
		gesamtpreis.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
		
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(searchScrollPane, BorderLayout.CENTER);
		
		// Artikel Details
		detailsPanel = new JPanel();
		detailsPanel.setLayout(new BorderLayout());
		
		bildPanel = new JImagePanel(null);
		
		infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
		infoPanel.setLayout(new BorderLayout());
		bezeichnung = new JLabel();
		bezeichnung.setFont(new Font("Arial", Font.BOLD, 16));
		infoPanel.add(bezeichnung, BorderLayout.NORTH);
		details = new JTextArea();
		details.setFont(new Font("Arial", Font.PLAIN, 12));
		details.setEditable(false);
		details.setOpaque(false);
		infoPanel.add(details, BorderLayout.CENTER);
		
		auswahlPanel = new JPanel();
		auswahlPanel.setBorder(BorderFactory.createEmptyBorder(20, 8, 20, 8));
		auswahlPanel.setLayout(new BorderLayout());
		errorMessage = new JTextArea();
		errorMessage.setFont(new Font("Arial", Font.PLAIN, 12));
		errorMessage.setForeground(Color.RED);
		errorMessage.setLineWrap(true);
		errorMessage.setWrapStyleWord(true);
		errorMessage.setEditable(false);
		errorMessage.setOpaque(false);
		errorMessage.setPreferredSize(new Dimension(150, 50));
		auswahlPanel.add(errorMessage, BorderLayout.NORTH);
			mengePanel = new JPanel();
			mengePanel.setLayout(new GridLayout(1,2));
			mengePanel.add(new JLabel("  Menge:"));
				menge = new JComboBox();
			mengePanel.add(menge);
			stueckzahlPanel = new JPanel();
			stueckzahlPanel.setLayout(new GridLayout(1,2));
			stueckzahlPanel.add(new JLabel("Stückzahl:"));
				stueckzahl = new JComboBox();
				stueckzahl.addItemListener(new StueckzahlListener());
			stueckzahlPanel.add(stueckzahl);
		auswahlPanel.add(mengePanel, BorderLayout.CENTER);
		inDenWarenkorbButton = new JButton("In den Warenkorb");
		inDenWarenkorbButton.addActionListener(new InDenWarenkorbListener());
		entfernenButton = new JButton("Entfernen");
		entfernenButton.addActionListener(new EntfernenListener());
		auswahlPanel.add(inDenWarenkorbButton, BorderLayout.SOUTH);
		add(headerPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(detailsPanel, BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void updateTable(List<Artikel> artikel) {
		ArtikelTableModel atm = (ArtikelTableModel) searchTable.getModel();
		atm.updateDataVector(artikel);
	}
	
	class SearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(searchButton) || ae.getSource().equals(searchField)) {
				headerPanel.remove(kaufenButton);
				headerPanel.add(warenkorbPanel);
				headerPanel.validate();
				headerPanel.repaint();
				tablePanel.remove(warenkorbScrollPane);
				warenkorbTable.clearSelection();
				tablePanel.remove(gesamtpreis);
				tablePanel.add(searchScrollPane, BorderLayout.CENTER);
				updateTable(shop.sucheArtikel(searchField.getText()));
				searchTable.clearSelection();
				tablePanel.validate();
				tablePanel.repaint();
			}
		}
	}
	
	class WarenkorbListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(warenkorbButton)) {
				headerPanel.remove(warenkorbPanel);
				headerPanel.add(kaufenButton);
				headerPanel.validate();
				headerPanel.repaint();
				tablePanel.remove(searchScrollPane);
				searchTable.clearSelection();
				tablePanel.add(warenkorbScrollPane, BorderLayout.CENTER);
				warenkorbTable.clearSelection();
				gesamtpreis.setText(String.format("Gesamtpreis: %.2f ", kunde.getWarenkorbVerwaltung().getGesamtpreis()) + Currency.getInstance(Locale.GERMANY));
				tablePanel.add(gesamtpreis, BorderLayout.SOUTH);
				tablePanel.validate();
				tablePanel.repaint();
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
				auswahlPanel.remove(entfernenButton);
				auswahlPanel.add(inDenWarenkorbButton, BorderLayout.SOUTH);
			} else 
			if (lse.getSource().equals(warenkorbTable.getSelectionModel()) && warenkorbTable.getSelectedRow() != -1) {
				index = warenkorbTable.getSelectedRow();	
				WarenkorbArtikelTableModel watm = (WarenkorbArtikelTableModel) warenkorbTable.getModel();
				WarenkorbArtikel wa = watm.getRowValue(index);
				((JImagePanel) bildPanel).setImagePath("images/" + wa.getArtikel().getArtikelnummer() + ".jpg");
				bezeichnung.setText(wa.getArtikel().getBezeichnung());
				details.setText("");
				details.append("Stückzahl: " + wa.getStueckzahl() + "\n");
				details.append("Preis: " + String.format("%.2f ", wa.getArtikel().getPreis()) + Currency.getInstance(Locale.GERMANY) + "\n");
				auswahlPanel.remove(mengePanel);
				auswahlPanel.add(stueckzahlPanel);
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
				Artikel a = atm.getRowValue(searchTable.getSelectedRow());
				try {
					shop.inDenWarenkorbLegen(kunde, a, (Integer) menge.getItemAt(menge.getSelectedIndex()));
					((JWarenkorbButton) warenkorbButton).setArtikelAnzahl(kunde.getWarenkorbVerwaltung().getWarenkorb().size());
					tablePanel.validate();
					tablePanel.repaint();
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
				WarenkorbArtikel wa = watm.getRowValue(warenkorbTable.getSelectedRow());
				try {
					shop.ausDemWarenkorbHerausnehmen(kunde, wa.getArtikel());
					((JWarenkorbButton) warenkorbButton).setArtikelAnzahl(kunde.getWarenkorbVerwaltung().getWarenkorb().size());
					gesamtpreis.setText(String.format("Gesamtpreis: %.2f ", kunde.getWarenkorbVerwaltung().getGesamtpreis()) + Currency.getInstance(Locale.GERMANY));
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
				WarenkorbArtikel wa = watm.getRowValue(warenkorbTable.getSelectedRow());
				try {
					if (stueckzahl.getSelectedIndex() != -1) {
						kunde.getWarenkorbVerwaltung().stueckzahlAendern(wa, (Integer) stueckzahl.getItemAt(stueckzahl.getSelectedIndex()));
						gesamtpreis.setText(String.format("Gesamtpreis: %.2f ", kunde.getWarenkorbVerwaltung().getGesamtpreis()) + Currency.getInstance(Locale.GERMANY));
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
	
	public static void main(String[] args) {
		try {
			new KundeGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
