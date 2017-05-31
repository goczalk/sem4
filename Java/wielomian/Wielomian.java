class Wielomian {
	public static void main(String[] args) {
		System.out.println("Wielomian!");

		int[] array = new int[3];

		if (args.length > 3){
			System.out.println("Za duzo argumentow");
			System.exit(1);
		}

		for(int i=0; i < args.length; i++){
			//a sprawdzenie czy wprowadzamy stringa zamiast inta??
			int a = Integer.parseInt(args[i]);
			array[i] = a;
			System.out.println(array[i]);
		}

		if(array[0] == 0){
			System.out.println("Wielomian nie jest kwadratowy. Nie policze ci pierwiastkow");
			System.exit(0);			
		}
		
		double delta = Math.pow(array[1], 2) - 4 * array[0] * array[2];
		
		if(delta < 0){
			System.out.println("Wielomian nie ma pierwiastków.");
			System.exit(0);		
		}
		
		double x1 = (array[1] + Math.sqrt(delta)) / 4 * array[0];  
		System.out.println("Pierwiastek wielomianu to:" + x1);
		if(delta > 0){
			double x2 = (array[1] - Math.sqrt(delta)) / 4 * array[0];
			System.out.println("Pierwiastek wielomianu to:" + x2);	
		}

	}
}
/*
		for(String arg: args){
		
			int a = Integer.parseInt(arg);
			System.out.println("argument" + a);
			suma += a;
		}

		System.out.println("suma:" + suma);
	}
}
*/
