//dla diody prawej
#define PIOC_PER (volatile unsigned int*)0xFFFFF600 //PIO enable
#define PIOC_OER (volatile unsigned int*)0xFFFFF610 //output enable
#define PIOC_SODR (volatile unsigned int*)0xFFFFF630 //set
#define PIOC_CODR (volatile unsigned int*)0xFFFFF634 //clear
#define SET_1 (1<<29)

//dioda lewa
#define PIOB_PER (volatile unsigned int*)0xFFFFF400 //PIO enable
#define PIOB_OER (volatile unsigned int*)0xFFFFF410 //output enable
#define PIOB_SODR (volatile unsigned int*)0xFFFFF430 //set
#define PIOB_CODR (volatile unsigned int*)0xFFFFF434 //clear
#define SET_1L (1<<8)

//PIO_PDSR - do sprawdzenia stanu guzika
//PIO_PUER - enable pull up
#define PIOC_PDSR (volatile unsigned int*)0xFFFFF63C
#define PIOC_PUER (volatile unsigned int*)0xFFFFF664

//przycisk L na bicie 5, R na 4
#define BTN_L (1<<5) //jesli jest 1 to jest NIEwcisniety
//#define BTN_L 0x00000010
#define BTN_R (1<<4)

void for_loop(void){
	volatile unsigned int i = 1000000;	
	while(i>0){i--;}
}

void btn(int b1, int b2){
	if(b1 == 0){
		*PIOC_CODR = SET_1;
	}
	else if (b2 == 0){
		*PIOC_SODR = SET_1;
	}
}

//adres <-> wskaznik!
int main(){
	//mozna tu ustawic reszte bitow na 0 jako dobra praktyke
	*PIOC_OER |= SET_1;
	*PIOC_PER |= SET_1 | BTN_L | BTN_R;
	*PIOC_PUER |= BTN_L | BTN_R;
	
	*PIOB_OER |= SET_1L;
	*PIOB_PER |= SET_1L;
	volatile unsigned int bL, bR;

	while(1){
		
		/*mryganie*/
		*PIOB_CODR |= SET_1L;
		for_loop();
		
		*PIOB_SODR |= SET_1L;
		// *PIOC_SODR = SET_1 [to samo bo wpisanie 0 nic nie zmienia]
		for_loop();

		bL = *(PIOC_PDSR) & BTN_L;
		bR = *(PIOC_PDSR) & BTN_R;
		//10000 <- jedynka na 5 miejscu! to 16!
		btn(bL, bR);
/*
		if(bL == 16) {*(PIOC_SODR) |= SET_1;}
		//else if (bL == 0) {*PIOB_SODR |= SET_1;}
		else {*(PIOC_CODR) |= SET_1;}
*/
	}
}

void dbgu_print_ascii(const char* string){}
