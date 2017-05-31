/*
Napisz program proszacy o podanie 2 wektorow. Koniec wektora oznacza sie za pomoca wcisniecia klawisza enter.
Jezeli podany ciag nie jest liczba, jest ignorowany. Nastepnie nalezy sprobowac dodac wektory, jezeli sa rownej dlugosci.
Jezeli nie, sa, rzucany jest wlasny wyjatek WektoryRoznejDlugosciException, za pomoca ktorego mozna podac a nastepnie odczytac dlugosci tych wektorow.
Jezeli sa rownej dlugosci, wynik dodawania zapisywany jest do pliku. Jezeli nie sa rownej dlugosci, uzytkownik jest proszony o ponowne wprowadzenie tych wektorow.
*/

import java.util.*;
import vector.*	;

public class Main{
    public static void main(String[] args){
		boolean ifExit = false;
		
        while(!ifExit){
			Scanner sc = new Scanner(System.in);
			String line = "";

			VectorCustom vector1=null;
            VectorCustom vector2=null;
			VectorCustom vectorSum=null;
			
			 while(true){
                    try{
                        System.out.println("Provide first vector: ");
                        line = sc.nextLine();
						
						if(line.trim().equals("exit")){
							ifExit = true;
							break;
						}
						
                        vector1 = new VectorCustom(line);
                        break;
                    } catch(InvalidVectorDataException e){
                        //make silent
						//System.out.println("OMITTING1");
                    }
                }

			if(ifExit) break;
				
			while(true){
				try{
					System.out.println("Provide second vector: ");
					line = sc.nextLine();
					
					if(line.trim().equals("exit")){
						ifExit = true;
						break;	
					}
					
					vector2 = new VectorCustom(line);
					break;
				} catch(InvalidVectorDataException e){
					//make silent
					//System.out.println("OMITTING2");
				}
			}
			
			try{
				vectorSum = VectorCustom.add(vector1,vector2);
				System.out.println("Result of addition:");
				System.out.println(vectorSum);
				
				String filename = "vectorSum";
				vectorSum.writeToFile(filename);
				System.out.println("Saved into file: " + filename);
				
				break;

			} catch (VectorsOfDifferentSizesException e) { 
				// e.printStackTrace(System.out);
				System.err.println(e.getMessage());
				System.out.println("Lengths of vectors differ. Got: " + e.getVectorSizes()[0] + " and " + e.getVectorSizes()[1] + "."); 
			}
		}
    };
}