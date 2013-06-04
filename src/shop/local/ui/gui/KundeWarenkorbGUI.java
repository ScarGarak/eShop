package shop.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import shop.local.domain.ShopVerwaltung;
import shop.local.ui.gui.components.JAccountButton;
import shop.local.ui.gui.table.WarenkorbArtikelTableModel;
import shop.local.valueobjects.Kunde;

@SuppressWarnings("serial")
public class KundeWarenkorbGUI extends JFrame {

	private ShopVerwaltung shop;
	private Kunde kunde;
	
	private JButton accountButton;
	private JButton buyButton;
	private JTable warenkorbArtikelTable;
	
	public KundeWarenkorbGUI(Kunde kunde) throws IOException {
		super("eShop - Warenkorb");
		shop = new ShopVerwaltung();
		this.kunde = kunde;
		initialize();
	}
	
	private void initialize() {
		setSize(new Dimension(700, 500));
		setLayout(new BorderLayout());

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		// Header
		JPanel headerPanel = new JPanel();
		accountButton = new JAccountButton(kunde.getName());
		headerPanel.add(accountButton);	
		buyButton = new JButton("Kaufen");
		headerPanel.add(buyButton);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		
		// Table
		warenkorbArtikelTable = new JTable(new WarenkorbArtikelTableModel(kunde.getWarenkorbVerwaltung().getWarenkorb()));
		warenkorbArtikelTable.setShowGrid(true);
		warenkorbArtikelTable.setGridColor(Color.LIGHT_GRAY);
		JScrollPane scrollPane = new JScrollPane(warenkorbArtikelTable);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());
		
		// Footer
		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new GridLayout(1,2));
		footerPanel.add(new JLabel("Gesamtpreis: "));
		footerPanel.add(new JLabel("0,00 EUR"));
		
		add(headerPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(footerPanel, BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}
