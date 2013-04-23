package shop.local.domain;

import java.util.Comparator;
import shop.local.valueobjects.Artikel;

public class SortierungNachArtikelnummer implements Comparator<Artikel> {

    public int compare(Artikel artikel1, Artikel artikel2) {
		return artikel1.getArtikelnummer() - artikel2.getArtikelnummer();
	}

}
