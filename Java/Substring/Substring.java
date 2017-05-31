/**
Napisz program losujacy liczbe z zakresu 0-100.
Nastepnie program pyta sie uzytkownika, co to za liczba.
Jezeli uzytkownik nie zgadl, dowiaduje sie czy wylosowana liczba jest wieksza czy mniejsza od podanej. Jezeli zgadl, dowiaduje sie ile wykonal prob i jest pytany o ochote do dalszej gry.  
*/

class RandomGame {
	public static void main(String[] args) {
		System.out.println("Random Game!");

		if (args.length != 3){
			System.out.println("Nieodpowiednia ilosc argumentow. Podaj 3.");
			System.exit(1);
		}

		String str = args[0];
		int a=0, b=0;
		try{
			a = Integer.parseInt(args[1]);
			b = Integer.parseInt(args[2]);
		    }catch(NumberFormatException e){
			System.out.println("Dwa ostatnie argumenty powinny byc liczbami.");
			System.exit(1);
		}

		if( a < 0){
			System.out.println("Pf. Wezme sobie 0.");
			a = 0;		
		}
		
		if ( b > str.length()-1){
			b = str.length()-1;
		}

		System.out.println(str.subSequence(a, b+1));
	}

