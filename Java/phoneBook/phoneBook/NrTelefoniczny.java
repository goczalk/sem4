package phoneBook;

public class NrTelefoniczny implements Comparable<NrTelefoniczny> {
    Integer nrkierunkowy;
    Integer nrTelefonu;
    
    public NrTelefoniczny(){
        nrkierunkowy = 0;
        nrTelefonu = 0;
    }
    public NrTelefoniczny(Integer k, Integer t){
        nrkierunkowy = k;
        nrTelefonu = t;
    }	
    public NrTelefoniczny(NrTelefoniczny nr){
        nrkierunkowy = nr.nrkierunkowy;
        nrTelefonu = nr.nrTelefonu;
    }
    
	/*
	public int getNrkierunkowy(){
        return nrkierunkowy;
    };
    public long getNrTelefonu(){
        return nrTelefonu;
    };
	*/
	
	//0 equal, 1 not equal
    public int compareTo(NrTelefoniczny nr){
		boolean nrkSame= this.nrkierunkowy.equals(nr.nrkierunkowy); //ta sama klasa, ma dostep do pól
        boolean nrtSame = this.nrTelefonu.equals(nr.nrTelefonu);
        return ((nrkSame == true) && (nrtSame == true)) ? 0 : 1;
    };
	
	public String toString(){
        return String.format("+%d %d", nrkierunkowy, nrTelefonu);
    };
}