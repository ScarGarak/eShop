package shop.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import shop.local.domain.ShopVerwaltung;
import shop.local.domain.exceptions.KundeExistiertNichtException;
import shop.local.ui.gui.components.JAccountButton;
import shop.local.ui.gui.components.JWarenkorbButton;
import shop.local.ui.gui.table.ArtikelTableModel;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;

@SuppressWarnings("serial")
public class KundeSucheGUI extends JFrame {

	private ShopVerwaltung shop;
	private Kunde kunde;
	
	private JButton accountButton;
	private JTextField searchField;
	private JButton searchButton;
	private JButton warenkorbButton;
	private JTable artikelTable;
	
	public KundeSucheGUI() throws IOException {
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
		setSize(new Dimension(700, 500));
		setLayout(new BorderLayout());

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// Header
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));
		accountButton = new JAccountButton(kunde.getName());
		headerPanel.add(accountButton);	
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
		searchPanel.add(new JLabel("Suche "));
		searchField = new JTextField();
		searchField.setToolTipText("Bitte geben Sie hier einen Suchbegriff ein.");
		searchPanel.add(searchField);
		searchButton = new JButton("Los");
		searchButton.addActionListener(new SearchListener());
		searchPanel.add(searchButton);
		headerPanel.add(searchPanel);
		JPanel warenkorbPanel = new JPanel();
		warenkorbPanel.setLayout(new BoxLayout(warenkorbPanel, BoxLayout.PAGE_AXIS));
		warenkorbButton = new JWarenkorbButton(0);
		warenkorbButton.addActionListener(new WarenkorbListener());
		warenkorbPanel.add(warenkorbButton);
		warenkorbPanel.add(new JLabel("Warenkorb"));
		headerPanel.add(warenkorbPanel);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		
		// Table
		artikelTable = new JTable(new ArtikelTableModel(shop.gibAlleArtikelSortiertNachBezeichnung()));
		artikelTable.setShowGrid(true);
		artikelTable.setGridColor(Color.LIGHT_GRAY);
		JScrollPane scrollPane = new JScrollPane(artikelTable);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());
		
		add(headerPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void updateTable(List<Artikel> artikel) {
		ArtikelTableModel atm = (ArtikelTableModel) artikelTable.getModel();
		atm.updateDataVector(artikel);
	}
	
	class SearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(searchButton)) {
				updateTable(shop.sucheArtikel(searchField.getText()));
			}
		}
	}
	
	class WarenkorbListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(warenkorbButton)) {
				try {
					new KundeWarenkorbGUI(kunde);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dispose();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new KundeSucheGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
