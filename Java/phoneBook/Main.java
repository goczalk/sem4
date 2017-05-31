/*
  Napisz klase NrTelefoniczny, posiadajaca 2 pola: nrkierunkowy i nrTelefonu i implementujaca
  interfejs Comparable. Nastepnie utworz abstrakcyjna klase Wpis a nastepnie dziedziczace z
  niej klasy Osoba i Firma. Klasa Wpis ma abstrakcyjna metode opis, ktora opisuje dany wpis.
  Byc moze ma rowniez inne metody abstrakcyjne lub nie w miare potrzeb. Klasa Osoba ma zawierac
  informacje o imieniu, nazwisku, adresie i (w tym nrTelefonu). Klasa Firma ma miec nazwe i
  adres( w tym NrTelefonu).
  
  Utworz kilka obiektow klasy Osoba i kilka obiektow klasy Firma i
  umiesc je w kontenerze TreeMap, poslugujac sie jako kluczem numerem telefonicznym. Nastepnie
  wypisz utworzona w ten sposob ksiazke telefoniczna za pomoca iteratora.
 */


import java.util.*;
import phoneBook.*;

public class Main {
	
	public static void main(String[] args) {

        Osoba os1 = new Osoba("Klaudia","Goczał","Łódź","Piotrkowska","1", 48, 123456789);
        Osoba os2 = new Osoba("Piotr", "Nowak", "Poznań", "Leśna", "8", 48, 987654321);
        Osoba os3 = new Osoba("Jan", "Kowalski", "Warszawa", "Rajdowa", "70a", 48, 123987456);

        Firma firma1 = new Firma("Subway", "Warszawa", "Aleje Jerozolimskie", "11", 48, 111222333);
        Firma firma2 = new Firma("KFC", "Łódź", "Aleje Prawiejerozolimskie", "12", 48, 444555666);
        Firma firma3 = new Firma("McDonald", "Poznań", "Aleje Niejerozolimskie", "13", 48, 777888999);

        os1.opis();
        os2.opis();
        os3.opis();
        firma1.opis();
        firma2.opis();
        firma3.opis();

        TreeMap<NrTelefoniczny,Wpis> phoneBook = new TreeMap<NrTelefoniczny,Wpis>();
			phoneBook.put(os1.getAddress().getPhoneNumber(), os1);
			phoneBook.put(os2.getAddress().getPhoneNumber(), os2);
			phoneBook.put(os3.getAddress().getPhoneNumber(), os3);
			phoneBook.put(firma1.getAddress().getPhoneNumber(), firma1);
			phoneBook.put(firma2.getAddress().getPhoneNumber(), firma2);
			phoneBook.put(firma3.getAddress().getPhoneNumber(), firma3);

        Set set = phoneBook.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            Map.Entry buff = (Map.Entry)it.next();
            System.out.print(buff.getKey() + " : ");
            System.out.println(buff.getValue());
        }
    };
    
}