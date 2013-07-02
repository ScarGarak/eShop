package shop.local.ui.gui.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import shop.local.valueobjects.Artikel;

@SuppressWarnings("serial")
public class BestandshistorieGraphik extends JPanel{
	
	private static final int ABSTANDZUMRAND = 40;
	private int[] yWerte;
	private Artikel artikel;
	
	public BestandshistorieGraphik(int[] yWerte, Artikel artikel){
		this.yWerte = yWerte;
		this.artikel = artikel;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		super.setBackground(Color.WHITE);
		super.setBorder(BorderFactory.createEtchedBorder());
		
		Graphics2D g2D = (Graphics2D)g;
		
		// Setze den Titel
		String titel = "Bestandshistorie - "+artikel.getBezeichnung();
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(titel);
		int textHeight = fm.getHeight();
		g2D.drawString(titel, getWidth() / 2 - textWidth / 2, ABSTANDZUMRAND-textHeight);
		
		
		//Maximalen Wert der Bestandshistorie finden
		int max = 0;
		for(int i = 0; i < yWerte.length; i++){
			if(yWerte[i] > max)
				max = yWerte[i];
		}
		
		max += max/10;
		
		int xAxeLength = (getWidth()-2*ABSTANDZUMRAND);
		Double xAxePixelProPunkt = (double)xAxeLength/(yWerte.length+(yWerte.length/10));
		int yAxeLength = (getHeight()-2*ABSTANDZUMRAND);
		Double yAxePixelProPunkt = (double)yAxeLength/max;
		
		// Die Graphik Axen
			//X-Axe
		g2D.drawLine(ABSTANDZUMRAND, getHeight()-ABSTANDZUMRAND, xAxeLength, getHeight()-ABSTANDZUMRAND);
			//Y-Axes
		g2D.drawLine(ABSTANDZUMRAND, getHeight()-ABSTANDZUMRAND, ABSTANDZUMRAND, ABSTANDZUMRAND);
		
		// Werte der Y-Axe anpassen:
		
		for(int i = 0; i < yWerte.length; i++){
			Double dWert = (getHeight()-ABSTANDZUMRAND)-(yWerte[i]*yAxePixelProPunkt);
			yWerte[i] = dWert.intValue();
		}
		
		//Werte der X-Axe:
		int[] xWerte = new int[yWerte.length];
		for(int i = 0; i < xWerte.length; i++){
			Double dWert = (i*xAxePixelProPunkt) + ABSTANDZUMRAND;
			xWerte[i] = dWert.intValue();
		}
		
		// Graphik zeichnen
		g2D.setColor(new Color(53, 126, 199));
		g2D.drawPolyline(xWerte, yWerte, yWerte.length);
	}
}
