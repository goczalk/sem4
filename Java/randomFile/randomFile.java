/**
Napisz program losujacy 1000 znakow i zapisz je do pliku,
a nastepnie odczytaj pliku i wypisz na ekran.
Utworz dwie rozne pary procedur zapisujaco/odczytujacych, raz korzystajac z pakietu java.io a drugi raz z pakietu java.nio.
Porownaj szybkosc zapisu i odczytu, wynik wypisz na ekranie. 

PROCEDURY
POROWNAJ
*/
import java.util.*;
import java.io.*; //Methods for Unbuffered Streams and Interoperable with java.io APIs
import java.nio.file.Files.*; //Methods for Channels and ByteBuffers
import java.nio.channels.*;
import java.nio.*;

class randomFile {
	public static void main(String[] args) {
		System.out.println("Random String to File!");

		Random randomGenerator = new Random();
		String str = new String();
		String filename = new String("random_string.txt");
		
		for(int i = 0; i <1000; i++){
			char randomChar = (char)(randomGenerator.nextInt(93) + 33);
			str += randomChar;
		}
		System.out.println(str);
		
		//IO
		//sam zamknie wiec finally niepotrzebne
		/*inaczej:
		BufferedReader br = new BufferedReader(new FileReader(path));
		    try {
			return br.readLine();
		    } finally {
			if (br != null) br.close();
		    }
		*/
		try(FileWriter out = new FileWriter(filename)) {
			out.write(str);
		} catch (IOException x) {
		    System.out.println("caught exception: " + x);
		}

		try(FileReader in = new FileReader(filename)) {
			char[] str2 = new char[1000];
			in.read(str2);
			System.out.print(str2);
		} catch (IOException x) {
		    System.out.println("caught exception: " + x);
		}

		//NIO
		try{
			RandomAccessFile aFile = new RandomAccessFile(filename, "rw");
			FileChannel inChannel = aFile.getChannel();
	
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			buf.put(str.getBytes());
			buf.flip();

			while(buf.hasRemaining()) {
			    inChannel.write(buf);
			}
			inChannel.close();
            		aFile.close();
		} catch (IOException x) {
		    	System.out.println("caught exception: " + x);
		}


/*
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
	*/}
}
