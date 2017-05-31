package phoneBook;

public class Adres {
    private String city;
    private String street;
    private String buildingNumber;
    private NrTelefoniczny phoneNumber;
	
    public Adres(String c, String s, String n, NrTelefoniczny num){
        city = c;
        street = s;
        buildingNumber = n;
        phoneNumber = num;
    };
	/*
    public Adres(String c, String s, String n, int k, int num){
        city = c;
        street = s;
        buildingNumber = n;
        phoneNumber = new NrTelefoniczny(k,num);
    };
	*/
    public Adres(Adres copy){
        city = copy.city;
        street = copy.street;
        buildingNumber = copy.buildingNumber;
        phoneNumber = copy.phoneNumber;
    };
    public NrTelefoniczny getPhoneNumber(){
        return phoneNumber;
    };
	public String toString(){
        return String.format("%s, %s %s, %s", city, street, buildingNumber, phoneNumber);
    };
}