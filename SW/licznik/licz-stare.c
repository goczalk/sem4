#include "AT91SAM9263.h"
#include "AT91SAM9263-EK.h"

#define B(b) (1 << (b))

#define S7_S1_B B(28)
#define S7_S2_B B(30)

#define S7E_B B(20)
#define S7D_B B(21)
#define S7C_B B(22)
#define S7DP_B B(23)
#define S7B_B B(24)
#define S7A_B B(25)
#define S7G_B B(26)
#define S7F_B B(27)

#define S7_B (S7A_B | S7B_B | S7C_B | S7D_B | S7E_B | S7F_B | S7G_B | S7DP_B)

#define S7_segs 8


#define BTN1C (1 << 5)
#define BTN2C (1 << 4)
#define CLK_PCDE (1 << 4)

#define VC_LOOP 400

#define PERIOD 10000

const unsigned int S7_S_B[S7_segs] = {
    S7A_B,
    S7B_B,
    S7C_B,
    S7D_B,
    S7E_B,
    S7F_B,
    S7G_B,
    S7DP_B
};

const unsigned int S7_DGT_B[10] = {
  S7A_B | S7B_B | S7C_B | S7D_B | S7E_B | S7F_B,
  S7B_B | S7C_B,
  S7A_B | S7B_B | S7G_B | S7E_B | S7D_B,
  S7A_B | S7B_B | S7C_B | S7D_B | S7G_B,
  S7F_B | S7G_B | S7B_B | S7C_B,
  S7A_B | S7F_B | S7G_B | S7C_B | S7D_B,
  S7A_B | S7F_B | S7G_B | S7C_B | S7D_B | S7E_B,
  S7A_B | S7B_B | S7C_B,
  S7A_B | S7B_B | S7C_B | S7D_B | S7E_B | S7F_B | S7G_B,
  S7A_B | S7B_B | S7C_B | S7D_B | S7F_B | S7G_B,
};

#define PICNT_SHIFT 20

int checkPIT() {
  int i = 0;
  if(*AT91C_PITC_PISR & AT91C_PITC_PITS) {
    i = *AT91C_PITC_PIVR >> PICNT_SHIFT;
  }
  return i;
}

void decode2(unsigned int* s1, unsigned int* s2, int val) {
  int j = val % 10;
  val /= 10;
  int d = val % 10;
  *s2 = S7_DGT_B[j];
  *s1 = S7_DGT_B[d];
}

int main(void) {
  unsigned int seg1_B = 0, seg2_B = 0;
  int cnt = 0;
  int cloop = 0;
  int sh = 0;    
  decode2(&seg1_B, &seg2_B, cnt);
  *AT91C_PIOB_PER = S7_B | S7_S1_B | S7_S2_B;
  *AT91C_PIOB_OER = S7_B | S7_S1_B | S7_S2_B;
  *AT91C_PIOB_CODR = S7_B;
  *AT91C_PIOB_SODR = S7_S1_B | S7_S2_B;
  
  *AT91C_PITC_PIMR = AT91C_PITC_PITEN | PERIOD;
  
  while(1) {
    int pi = checkPIT();
    if(pi > 0) {
      if(sh) {
        *AT91C_PIOB_CODR = S7_S1_B | S7_B;
        *AT91C_PIOB_SODR = seg1_B | S7_S2_B;
        sh = 0;
      } else {
        *AT91C_PIOB_CODR = S7_S2_B | S7_B;
        *AT91C_PIOB_SODR = seg2_B | S7_S1_B;
        sh = 1;
      }
      if(++cloop > VC_LOOP) {
        cnt++;
        if(cnt < 0)
          cnt = 99;
        else if(cnt > 99)
          cnt = 0;
        cloop = 0;
        decode2(&seg1_B, &seg2_B, cnt);
      }
    }
  }
}

void dbgu_print_ascii(const char* string){}
