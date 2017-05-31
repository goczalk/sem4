#include "AT91SAM9263.h"        //tu definicja AT91C_PIOB_PER etc
#include "AT91SAM9263-EK.h"        

#define SHIFT(a) (1 << (a))
#define LEFT_DIG SHIFT(28)  //lewa cyfra jest na PB28
#define RIGHT_DIG SHIFT(30) //prawa cyfra jest na PB30

//shift na kreski wyswietlacza
#define S7E_SHIFT SHIFT(20)        //segment E
#define S7D_SHIFT SHIFT(21)        //segment D
#define S7C_SHIFT SHIFT(22)        //segment C
#define S7DP_SHIFT SHIFT(23)        //segment kropka(dot)
#define S7B_SHIFT SHIFT(24)        //segment B
#define S7A_SHIFT SHIFT(25)        //segment A
#define S7G_SHIFT SHIFT(26)        //segment G
#define S7F_SHIFT SHIFT(27)        //segment F

//wszystkie segmenty za jednym razem np. do enable
#define S7_SHIFT (S7A_SHIFT | S7B_SHIFT | S7C_SHIFT | S7D_SHIFT | S7E_SHIFT | S7F_SHIFT | S7G_SHIFT | S7DP_SHIFT)

//tablica z liczbami
const unsigned int S7_DGT_B[10] = {
  S7A_SHIFT | S7B_SHIFT | S7C_SHIFT | S7D_SHIFT | S7E_SHIFT | S7F_SHIFT,        //0
  S7B_SHIFT | S7C_SHIFT,                                        		//1
  S7A_SHIFT | S7B_SHIFT | S7G_SHIFT | S7E_SHIFT | S7D_SHIFT,               	//2
  S7A_SHIFT | S7B_SHIFT | S7C_SHIFT | S7D_SHIFT | S7G_SHIFT,                	//3
  S7F_SHIFT | S7G_SHIFT | S7B_SHIFT | S7C_SHIFT,                        	//4
  S7A_SHIFT | S7F_SHIFT | S7G_SHIFT | S7C_SHIFT | S7D_SHIFT,                	//5
  S7A_SHIFT | S7F_SHIFT | S7G_SHIFT | S7C_SHIFT | S7D_SHIFT | S7E_SHIFT,        //6
  S7A_SHIFT | S7B_SHIFT | S7C_SHIFT,                                		//7
  S7A_SHIFT | S7B_SHIFT | S7C_SHIFT | S7D_SHIFT | S7E_SHIFT | S7F_SHIFT | S7G_SHIFT,//8
  S7A_SHIFT | S7B_SHIFT | S7C_SHIFT | S7D_SHIFT | S7F_SHIFT | S7G_SHIFT,        //9
};

void for_loop(int i);

int main(void) {
  volatile unsigned int licznik = 0/*, delay = 0*/, i = 0;
  volatile long delay;
 
  *AT91C_PIOB_PER = S7_SHIFT | LEFT_DIG | RIGHT_DIG;        //enable
  *AT91C_PIOB_OER = S7_SHIFT | LEFT_DIG | RIGHT_DIG;        //output enable
  //*AT91C_PIOB_CODR = S7_SHIFT;                       	    //clear output
  //*AT91C_PIOB_SODR = LEFT_DIG | RIGHT_DIG;                //set output
  
  //dla kazdego segmentu mamy npn tranzystor wiec sie go zalacza stanem wysokim
  //wiec zapal SODR, zgas CODR

  //ogolnie dla COM1 i COM2 (lewy i prawy) na odwrot: zapal CODR, zgas SODR
  //(stan wysoki bo gdy chcemy on to musi byc stan niski -> ogolnie wyswietlacz)


  while(1){
	for(licznik = 0; licznik < 100; licznik++){
		//petla tylko spowolniajaca
		for(delay = 0; delay < 10000; delay++){

			//right off, left on
			*AT91C_PIOB_SODR = RIGHT_DIG;
			*AT91C_PIOB_CODR = LEFT_DIG;

			//all off
			*AT91C_PIOB_CODR = S7_SHIFT;

			//left num
			i = licznik/10;
			*AT91C_PIOB_SODR = S7_DGT_B[i];

			for_loop(25);

			//left off, right on
			*AT91C_PIOB_SODR = LEFT_DIG;
			*AT91C_PIOB_CODR = RIGHT_DIG;

			//all off
			*AT91C_PIOB_CODR = S7_SHIFT;

			//right num
			i = licznik%10;
			*AT91C_PIOB_SODR = S7_DGT_B[i];

			for_loop(25);
		}
	}

	//===============================================================
	//MRYGAJA RAZEM
	//zapal lewo i prawo wszystko
	/*	
	*AT91C_PIOB_CODR = LEFT_DIG;
	*AT91C_PIOB_CODR = RIGHT_DIG;
	*AT91C_PIOB_SODR = S7_SHIFT;
	for_loop();
	*/
	//zgas lewo i prawo wszystko
	//*AT91C_PIOB_CODR = S7_SHIFT;
	/*	
	*AT91C_PIOB_SODR = LEFT_DIG;
	*AT91C_PIOB_SODR = RIGHT_DIG;

	for_loop();
	*/
	//===============================================================	
	
	}
}

void for_loop(int i){	
	while(i>0){
		//zrob cokolwiek zeby nie optymalizowal	
		*AT91C_PIOB_CODR << 8;
	i--;}
}

void dbgu_print_ascii(const char* string){}
