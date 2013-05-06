package shop.local.persitence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;

/**
 * @author Christof Ferreira Torres
 * 
 * Schnittstelle zur persistenten Speicherung von
 * Daten in serialisierte Dateien
 * @see shop.local.persistence.PersistenceManager
 */
public class ObjectPersistenceManager implements PersistenceManager {
	
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	
	public void openForReading(String datei) throws IOException {
		ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(datei)));
	}
	
	public void openForWriting(String datei) throws IOException {
		oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(datei)));
	}
	
	public boolean close() {
		if (oos != null)
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (ois != null) {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Methode zum Einlesen der Artikeldaten aus einer externen Datenquelle.
	 * 
	 * @return Artikel-Objekt, wenn einlesen erfolgreich, false null
	 * @throws ClassNotFoundException 
	 */
	public Artikel ladeArtikel() throws IOException, ClassNotFoundException {
		try {
			return (Artikel) ois.readObject();
		} catch (EOFException e) {
			return null;
		}
	}

	/**
	 * Methode zum schreiben der Artikeldaten in eine externe Datenquelle.
	 * 
	 * @param a Artikel-Objekt, das gespeichert werden soll
	 * @throws IOException
	 */
	public void speichereArtikel(Artikel a) throws IOException {
		oos.writeObject(a);
	}
	
	/**
	 * Methode zum Einlesen der Kundendaten aus einer externen Datenquelle.
	 * 
	 * @return Kunde-Objekt, wenn einlesen erfolgreich, false null
	 */
	public Kunde ladeKunden() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Methode zum schreiben der Kundendaten in eine externe Datenquelle.
	 * 
	 * @param k Kunde-Objekt, das gespeichert werden soll
	 * @throws IOException
	 */
	public void speichereKunden(Kunde k) throws IOException {
		// TODO Auto-generated method stub
	}

}
