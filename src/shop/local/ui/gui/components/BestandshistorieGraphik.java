package shop.local.ui.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import shop.local.valueobjects.Artikel;

@SuppressWarnings("serial")
public class BestandshistorieGraphik extends JPanel{
	
	private static final int ABSTANDZUMRAND = 40;
	private static final int MARKIERUNGSLAENGE = 6;		// Vorzugshalber sollte diese Zahl gerade sein
	private static final int PFEILXLAENGE = 10;
	private static final int PFEILYLAENGE = 5;
	private int[] yWerte;
	private Artikel artikel;
	
	public BestandshistorieGraphik(int[] yWerte, Artikel artikel){
		this.yWerte = yWerte;
		this.artikel = artikel;

		super.setBackground(Color.WHITE);
		super.setBorder(BorderFactory.createEtchedBorder());
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2D = (Graphics2D)g;
		int[] yWerteKopie = yWerte.clone();
		
		// Setze den Titel
		String titel = "Bestandshistorie - "+artikel.getBezeichnung();
		FontMetrics fm = g.getFontMetrics();
		g.setFont(g.getFont().deriveFont(Font.BOLD));
		int textWidth = fm.stringWidth(titel);
		int textHeight = fm.getHeight();
		g2D.drawString(titel, getWidth() / 2 - textWidth / 2, ABSTANDZUMRAND-textHeight);
		
		
		//Maximalen Wert der Bestandshistorie finden
		int max = 0;
		for(int i = 0; i < yWerteKopie.length; i++){
			if(yWerteKopie[i] > max)
				max = yWerteKopie[i];
		}
		
		// Y-Axe Unterteilungs-Interval:
		int yAxeInterval = 1;
		if(max > 20 && max <= 50){
			yAxeInterval = 5;
		}else if(max > 50 && max <= 100){
			yAxeInterval = 10;
		}else if(max > 100 && max <= 500){
			yAxeInterval = 20;
		}else if(max > 500){
			yAxeInterval = 100;
		}
		
		
		int xAxeLength = (getWidth()-2*ABSTANDZUMRAND);
		Double xAxePixelProPunkt = (double)xAxeLength/(yWerteKopie.length+(yWerteKopie.length/10));
		int yAxeLength = (getHeight()-2*ABSTANDZUMRAND);
		Double yAxePixelProPunkt = (double)yAxeLength/(max+yAxeInterval);
		
		// Die Graphik Axen
			//X-Axe
		g2D.drawLine(ABSTANDZUMRAND, getHeight() - ABSTANDZUMRAND, ABSTANDZUMRAND + xAxeLength, getHeight()-ABSTANDZUMRAND);
			//Y-Axes
		g2D.drawLine(ABSTANDZUMRAND, getHeight() - ABSTANDZUMRAND, ABSTANDZUMRAND, getHeight() - ABSTANDZUMRAND - yAxeLength);
		
		// Pfeile der Axen
			//X-Axe
		int[] xPointsXAxe = {(ABSTANDZUMRAND + xAxeLength) - PFEILXLAENGE, ABSTANDZUMRAND + xAxeLength, (ABSTANDZUMRAND + xAxeLength) - PFEILXLAENGE};
		int[] yPointsXAxe = {getHeight()-ABSTANDZUMRAND - PFEILYLAENGE, getHeight() - ABSTANDZUMRAND, getHeight() - ABSTANDZUMRAND + PFEILYLAENGE};
		g2D.drawPolyline(xPointsXAxe, yPointsXAxe, 3);
			//Y-Axe
		int[] xPointsYAxe = {ABSTANDZUMRAND - PFEILYLAENGE, ABSTANDZUMRAND, ABSTANDZUMRAND +  PFEILYLAENGE};
		int[] yPointsYAxe = {ABSTANDZUMRAND + PFEILXLAENGE, ABSTANDZUMRAND, ABSTANDZUMRAND + PFEILXLAENGE};
		g2D.drawPolyline(xPointsYAxe, yPointsYAxe, 3);
		
		// Namen der Axen
		g.setFont(g.getFont().deriveFont(Font.PLAIN));
			// X-Axe
		titel = "Tag";
		textWidth = fm.stringWidth(titel);
		g2D.drawString(titel, (ABSTANDZUMRAND + xAxeLength) - (textWidth / 2), (getHeight() - ABSTANDZUMRAND) + textHeight + 5);
			// Y-Axe
		titel = "Bestand";
		textWidth = fm.stringWidth(titel);
		g2D.drawString(titel, ABSTANDZUMRAND - (textWidth / 2), ABSTANDZUMRAND - textHeight + 10);
		
		// Markierungen X-Axe
		for(int i = 0; i < yWerteKopie.length; i++){
			int x0 = ABSTANDZUMRAND + (int)((i+1)*xAxePixelProPunkt);
			int y0 = getHeight() - ABSTANDZUMRAND + (MARKIERUNGSLAENGE/2);
			int x1 = x0;
			int y1 = y0-MARKIERUNGSLAENGE;
			g2D.drawLine(x0, y0, x1, y1);
			//Wert schreiben
			titel = (i+1)+"";
			textWidth = fm.stringWidth(titel);
			if(getWidth() <= 350){
				g2D.setFont(g2D.getFont().deriveFont((float)6.0));
			}else{
				g2D.setFont(g2D.getFont().deriveFont((float)9.0));
			}
			g2D.drawString(titel, x0 - (textWidth/2) , (getHeight() - ABSTANDZUMRAND) + 20);
		}
		
		// Markierungen Y-Axe
		for(int i = 0; i < max+yAxeInterval; i++){
			if(yAxeInterval == 1 || (i != 0 && i % yAxeInterval == 0)){
				int x0 = ABSTANDZUMRAND - (MARKIERUNGSLAENGE/2);
				int y0 = getHeight() - ABSTANDZUMRAND - (int)((i+1)*yAxePixelProPunkt);
				int x1 = x0 + MARKIERUNGSLAENGE;
				int y1 = y0;
				g2D.drawLine(x0, y0, x1, y1);
				//Wert schreiben
				if(yAxeInterval == 1){
					titel = (i+1)+"";
				}else{
					titel = i+"";
				}
				textWidth = fm.stringWidth(titel);
				if(getHeight() <= 350){
					g2D.setFont(g2D.getFont().deriveFont((float)9.0));
				}else{
					g2D.setFont(g2D.getFont().deriveFont((float)12.0));
				}
				g2D.drawString(titel, ABSTANDZUMRAND - textWidth - 10, y0 + (textHeight/2));
			}
			
			if(yAxeInterval == 1 && i == max-1){
				i++;
			}
		}
		
		// Werte der Y-Axe anpassen:
		for(int i = 0; i < yWerteKopie.length; i++){
			Double dWert = (getHeight()-ABSTANDZUMRAND)-(yWerteKopie[i]*yAxePixelProPunkt);
			yWerteKopie[i] = dWert.intValue();
		}
		
		//Werte der X-Axe:
		int[] xWerte = new int[yWerteKopie.length];
		for(int i = 0; i < xWerte.length; i++){
			Double dWert = (i*xAxePixelProPunkt) + ABSTANDZUMRAND;
			xWerte[i] = dWert.intValue();
		}
		
		// Graphik zeichnen
		g2D.setColor(Color.ORANGE);
		g2D.drawPolyline(xWerte, yWerteKopie, yWerteKopie.length);
	}
}
