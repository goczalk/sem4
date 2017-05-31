package phoneBook;

public class Firma extends Wpis {
    private String name;
    private Adres location;

	/*
    public Firma (String n, Adres l){
        name = n;
        location = l;
    };
	*/
	/*
    public Firma (String n, String city, String street, String buildNum, NrTelefoniczny num){
        name = n;
        location = new Adres(city,street,buildNum,num);
    };
	*/
    public Firma (String n, String city, String street, String buildNum, int k, int num){
        name = n;
        location = new Adres(city,street,buildNum,new NrTelefoniczny(k,num));
    };
    public Firma(Firma copy){
        name = copy.name;
        location = copy.location;
    };
	public Adres getAddress(){
        return location;
    };
    public String toString(){
        return String.format("%s, %s ", name, location);
    };
    public void opis(){
        System.out.println("Nazwa firmy: " + name);
        System.out.println("Adres: " + location);
    };
}