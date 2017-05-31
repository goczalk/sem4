.global main
.arm

//dla diody prawej
.equ PIOC_PER, 0xFFFFF600 //PIO enable
.equ PIOC_OER, 0xFFFFF610 //output enable
.equ PIOC_ODR, 0xFFFFF614 //output disable
.equ PIOC_SODR, 0xFFFFF630 //set
.equ PIOC_CODR, 0xFFFFF634 //clear
.equ SET_1, 0x20000000 //bit 29 -> 2^29

//dioda lewa
.equ PIOB_PER, 0xFFFFF400 //PIO enable
.equ PIOB_OER, 0xFFFFF410 //output enable
.equ PIOB_SODR, 0xFFFFF430 //set
.equ PIOB_CODR, 0xFFFFF434 //clear
.equ SET_1L, 0x00000100 //bit 8 -> 2^8
.equ LICZNIK, 0x00040000 //1000


//PIO_PDSR - do sprawdzenia stanu guzika
.equ PIOC_PDSR, 0xFFFFF63C //Pin data status reg -> 1 jak niewcisniety?
.equ PIOC_PUER, 0xFFFFF664 //enable pull up

//przycisk L na bicie 5, R na 4
//#define BTN_L (1<<5) //jesli jest 1 to jest NIEwcisniety
.equ BTN_L, 0x00000020 //wczesniej bylo na 0x00000010 ??
.equ BTN_R, 0x00000010

.section .text
main:
	BL	led_left_enable
	BL	led_right_enable
	BL	buttons_enable

//	BL	loop zbedne -> i tak zacznie wykonywac loopa

loop:
	//MRYGAJ
	BL	led_on
	LDR	R0, =LICZNIK //licznik
	BL	delay_loop

	BL	led_off
	LDR	R0, =LICZNIK //licznik
	BL	delay_loop
	//KONIEC MRYGANIA

	//PRZYCISKI
	BL	button_left
	BL	button_right

	B	loop //BL z odlozeniem adresu na stos

led_left_enable:
	LDR	R1, =SET_1L
	LDR	R2, =PIOB_PER
	LDR	R3, =PIOB_OER
	STR	R1, [R2]
	STR	R1, [R3]
	BX	LR 		//wroc do poprzedniego w main

led_right_enable:
	LDR	R1, =SET_1
	LDR	R2, =PIOC_PER
	LDR	R3, =PIOB_OER
	STR	R1, [R2]
	STR	R1, [R3]
	BX	LR 		//wroc do poprzedniego w main

buttons_enable:
	LDR	R2, =PIOC_PER
	LDR	R3, =PIOC_PUER
	LDR	R4, =PIOC_ODR

	//enable left
	LDR	R1, =BTN_L

	STR	R1, [R2]
	STR	R1, [R3]
	STR	R1, [R4]

	//enable right
	LDR	R1, =BTN_R

	STR	R1, [R2]
	STR	R1, [R3]
	STR	R1, [R4]

	BX	LR 

//left
led_on:
	LDR	R1, =SET_1L
	LDR	R2, =PIOB_CODR
	STR	R1, [R2]
	BX	LR

//left
led_off:
	LDR	R1, =SET_1L
	LDR	R2, =PIOB_SODR
	STR	R1, [R2]
	BX	LR 

led_right_on:
	LDR	R1, =SET_1
	LDR	R2, =PIOC_CODR
	STR	R1, [R2]
	BX	LR

led_right_off:
	LDR	R1, =SET_1
	LDR	R2, =PIOC_SODR
	STR	R1, [R2]
	BX	LR 

button_left:
	LDR	R1, =BTN_L
	LDR	R6, =PIOC_PDSR
	
	//ustaw 0 na R7
	MOV	R7, #0
	//odczytaj status pinu i wpisz w rej R6

	//r11 to temp dla r6
	LDR	R11, [R6]

	//r2 w interrupt

	//dodaj interrupt i status
	ANDS	R7, R11, R1
	//jesli bedzie 1 wylacz
	BEQ	led_right_off
	BX	LR 

button_right:
	LDR	R1, =BTN_R
	LDR	R6, =PIOC_PDSR

	//ustaw 0 na R7
	MOV	R7, #0
	//odczytaj status pinu i wpisz w rej R6

	//r11 to temp dla pdsr
	LDR	R11, [R6]

	//dodaj interrupt i status
	ANDS	R7, R11, R1
	//jesli bedzie 1 wylacz
	BEQ	led_right_on
	BX	LR 

delay_loop:
	SUBS	R0, R0, #1
	BNE	delay_loop
	BX	LR

