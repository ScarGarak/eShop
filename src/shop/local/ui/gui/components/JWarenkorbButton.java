package shop.local.ui.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class JWarenkorbButton extends JButton {

	private BufferedImage image = null;
	private int artikelAnzahl = 0;
	
	public JWarenkorbButton(int artikelAnzahl) {
		super();
		this.artikelAnzahl = artikelAnzahl;
		try {                
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("shop/local/ui/gui/images/warenkorb.png");
			image = ImageIO.read(input);
	    } catch (IOException ex) {
	    	   
	    }
		setMinimumSize(new Dimension(70, 50));
		setPreferredSize(new Dimension(70, 50));
		setMaximumSize(new Dimension(70, 50));
		setOpaque(false);
	}
	
	@Override
	public void paint(Graphics g) {
		// Laenge und Breite ermitteln
		Dimension d = this.getSize();
		int width = (int) d.getWidth();
		int height = (int) d.getHeight();
		Font myFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(String.valueOf(artikelAnzahl));
		int textHeight = fm.getHeight();
		// Warenkorb zeichnen
		g.drawImage(image, width / 2 - (image.getWidth() + textWidth) / 2, height / 2 - image.getHeight() / 2, null);
		// Artikel Anzahl ausgeben
		g.setColor(Color.orange);
		g.setFont(myFont);
		g.drawString(String.valueOf(artikelAnzahl), (width / 2 - (image.getWidth() + textWidth) / 2) + image.getWidth(), height / 2 + textHeight / 2);	
	}
	
}
