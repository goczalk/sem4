package phoneBook;

public class Osoba extends Wpis {
    private String name;
    private String surname;
    private Adres address;

	/*
    public Osoba(String n, String s, Adres l){
        name = n;
        surname = s;
        address = l;
    };
	*/
	
	/*
    public Osoba(String n, String s, String city, String street, String buildNum, NrTelefoniczny num){
        name = n;
        surname = s;
        address = new Adres(city,street,buildNum,num);
    };
	*/
	
    public Osoba(String n, String s, String city, String street, String buildNum, int k, int num){
        name = n;
        surname = s;
        address = new Adres(city,street,buildNum,new NrTelefoniczny(k,num));
    };
    public Osoba(Osoba copy){
        name = copy.name;
        surname = copy.surname;
        address = copy.address;
    };
	public Adres getAddress(){
        return address;
    };
	public String toString(){
        return String.format("%s %s, %s", name, surname, address);
    };
    public void opis(){
        System.out.println("Nazwisko i imię: " + surname + " " + name);
        System.out.println("Adres: " + address);
    };
}