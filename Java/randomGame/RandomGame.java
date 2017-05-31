/**
Napisz program losujacy liczbe z zakresu 0-100.
Nastepnie program pyta sie uzytkownika, co to za liczba.
Jezeli uzytkownik nie zgadl, dowiaduje sie czy wylosowana liczba jest wieksza czy mniejsza od podanej.
Jezeli zgadl, dowiaduje sie ile wykonal prob i jest pytany o ochote do dalszej gry. 
*/
import java.util.*;

class RandomGame {
	public static void main(String[] args) {
		System.out.println("Random Game!");

		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);

		System.out.println("Zgadnij liczbę od 0 do 100");
		int proby=1;

		while(true){
			Scanner sc = new Scanner(System.in);
			int guess = sc.nextInt();

			if ( guess == randomInt){
				System.out.println("Gratulacje! Zgadles! Miales " + proby + " prob.");

				//porownywanie stringow!
				boolean accurate = false;
				while(!accurate){
					System.out.println("Chcesz grac dalej? T/N");
					sc = new Scanner(System.in);
					String will = sc.nextLine();

					if ( will.equals("T")){
						randomInt = randomGenerator.nextInt(100);
						proby = 0;
						System.out.println("Zgadnij liczbę od 0 do 100");
						accurate = true;
					}
					else if( will.equals("N")){
						System.exit(1);
						accurate = true;
					}

				}

				
			}
			else if ( guess > randomInt){
				System.out.println("Niżej!");
			}
			else{
				System.out.println("Wyżej!");
			}
			proby++;
		}
	}
}
