package shop.local.persitence;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;

public class ObjectStreamPersistenceManager implements PersistenceManager{
	
	private ObjectInputStream objectIn = null;
	private ObjectOutputStream objectOut = null;

	@Override
	public void openForReading(String datenquelle) throws IOException {
		objectIn = new ObjectInputStream(new FileInputStream(datenquelle));
	}

	@Override
	public void openForWriting(String datenquelle) throws IOException {
		objectOut = new ObjectOutputStream(new FileOutputStream(datenquelle));
	}

	@Override
	public boolean close() {
		if (objectOut != null){
			try {
				objectOut.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				return false;
			}
		}
		if (objectIn != null) {
			try {
				objectIn.close();
			} catch (IOException e) {
				e.printStackTrace();
				
				return false;
			}
		}
		
		return true;
	}

	@Override
	public Kunde ladeKunden() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean speichereKunden(Kunde k) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public Mitarbeiter ladeMitarbeiter() throws IOException {
		Mitarbeiter m = null;
		try {
			m = (Mitarbeiter) objectIn.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (EOFException e2){
			//Keine weiteren Daten mehr in der Datei
		}
		return m;
	}

	@Override
	public boolean speichereMitarbeiter(Mitarbeiter m) throws IOException {
		objectOut.writeObject(m);
		return true;
	}

}
