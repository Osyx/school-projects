#include <pic32mx.h>
#include <stdint.h>

#define DISPLAY_VDD PORTFbits.RF6
#define DISPLAY_VBATT PORTFbits.RF5
#define DISPLAY_COMMAND_DATA PORTFbits.RF4
#define DISPLAY_RESET PORTGbits.RG9


#define DISPLAY_VDD_PORT PORTF
#define DISPLAY_VDD_MASK 0x40
#define DISPLAY_VBATT_PORT PORTF
#define DISPLAY_VBATT_MASK 0x20
#define DISPLAY_COMMAND_DATA_PORT PORTF
#define DISPLAY_COMMAND_DATA_MASK 0x10
#define DISPLAY_RESET_PORT PORTG
#define DISPLAY_RESET_MASK 0x200

// Declare all constants and the buffers (vectors).
extern char textbuffer[4][16];
extern char imgbuffer[4][128];
extern const uint8_t const font[];
extern const uint8_t const Start_start[];
extern const uint8_t const start_screen[];
extern const uint8_t const Start_settings[];
extern const uint8_t const ground[];
extern const uint8_t const ep2[];
extern const uint8_t const hat[];
extern const uint8_t const hole[];
extern const uint8_t const hat1[];
extern const uint8_t const died[];
extern const uint8_t const victory[];
extern const uint8_t const story[];
extern const uint8_t const flower[];
extern const uint8_t const jump[];
extern const uint8_t const settings_normal[];
extern const uint8_t const settings_ds[];
int display_settings = 0;
int score = 0;
int highscore = 0;
int btn_pressed = 0;
uint32_t random_seed = 0;
uint32_t random_seed2 = 0;

void hardware_init () {
  /* Set up peripheral bus clock */
	OSCCON &= ~0x180000;
	OSCCON |= 0x080000;

	/* Set up output pins */
	AD1PCFG = 0xFFFF;
	ODCE = 0x0;
	TRISECLR = 0xFF;
	PORTE = 0x0;

	/* Output pins for display signals */
	PORTF = 0xFFFF;
	PORTG = (1 << 9);
	ODCF = 0x0;
	ODCG = 0x0;
	TRISFCLR = 0x70;
	TRISGCLR = 0x200;

	/* Set up input pins */
	TRISDSET = (1 << 8);
	TRISFSET = (1 << 1);

	/* Set up SPI as master */
	SPI2CON = 0;
	SPI2BRG = 4;

	/* Clear SPIROV*/
	SPI2STATCLR &= ~0x40;
	/* Set CKP = 1, MSTEN = 1; */
        SPI2CON |= 0x60;

	/* Turn on SPI */
	SPI2CONSET = 0x8000;
}

#define ITOA_BUFSIZ ( 24 )
char * itoaconv( int num )
{
  register int i, sign;
  static char itoa_buffer[ ITOA_BUFSIZ ];
  static const char maxneg[] = "-2147483648";

  itoa_buffer[ ITOA_BUFSIZ - 1 ] = 0;   /* Insert the end-of-string marker. */
  sign = num;                           /* Save sign. */
  if( num < 0 && num - 1 > 0 )          /* Check for most negative integer */
  {
    for( i = 0; i < sizeof( maxneg ); i += 1 )
    itoa_buffer[ i + 1 ] = maxneg[ i ];
    i = 0;
  }
  else
  {
    if( num < 0 ) num = -num;           /* Make number positive. */
    i = ITOA_BUFSIZ - 2;                /* Location for first ASCII digit. */
    do {
      itoa_buffer[ i ] = num % 10 + '0';/* Insert next digit. */
      num = num / 10;                   /* Remove digit from number. */
      i -= 1;                           /* Move index to next empty position. */
    } while( num > 0 );
    if( sign < 0 )
    {
      itoa_buffer[ i ] = '-';
      i -= 1;
    }
  }
  /* Since the loop always sets the index i to the next empty position,
   * we must add 1 in order to return a pointer to the first occupied position. */
  return( &itoa_buffer[ i + 1 ] );
}

// Delay function which does its job but not efficently.
void delay(int cyc) {
	int i;
	for(i = cyc; i > 0; i--);
}

// Function to calculate x to the power of a.
int pow(x, a) {
	int i;
	int pow_val = 1;
	for(i = 0; i < a; i++) {
		pow_val *= x;
	}
	return pow_val;
}

// Write the buffer vector to the display buffer.
uint8_t spi_send_recv(uint8_t data) {
	while(!(SPI2STAT & 0x08));
	SPI2BUF = data;
	while(!(SPI2STAT & 0x01));
	return SPI2BUF;
}

// Initialize the display
void display_init() {
	DISPLAY_COMMAND_DATA_PORT &= ~DISPLAY_COMMAND_DATA_MASK;
	delay(10);
	DISPLAY_VDD_PORT &= ~DISPLAY_VDD_MASK;
	delay(1000000);

	spi_send_recv(0xAE);
	DISPLAY_RESET_PORT &= ~DISPLAY_RESET_MASK;
	delay(10);
	DISPLAY_RESET_PORT |= DISPLAY_RESET_MASK;
	delay(10);

	spi_send_recv(0x8D);
	spi_send_recv(0x14);

	spi_send_recv(0xD9);
	spi_send_recv(0xF1);

	DISPLAY_VBATT_PORT &= ~DISPLAY_VBATT_MASK;
	delay(10000000);

	spi_send_recv(0xA1);
	spi_send_recv(0xC8);

	spi_send_recv(0xDA);
	spi_send_recv(0x20);

	spi_send_recv(0xAF);

	int i,j;
	for (i = 0; i < 32; i++) {
		for (j = 0; j < 128; j++) {
			imgbuffer[i][j] = 255;
		}
	}
}

static uint32_t rand(uint32_t x)
{
    x ^= x >> 11;
    x ^= x << 7 & 0x9D2C5680;
    x ^= x << 15 & 0xEFC60000;
    x ^= x >> 18;
    return x;
}

// Function to diplay a string of text on a certain y value of the screen
void display_string(int line, char *s) {
	int i;
	if(line < 0 || line >= 4)
		return;
	if(!s)
		return;

	for(i = 0; i < 16; i++)
		if(*s) {
			textbuffer[line][i] = *s;
			s++;
		} else
			textbuffer[line][i] = ' ';
}

// Function to add a data vector (constant) to the display buffer vector.
void add_img(double x, int y, int data_end, const uint8_t const *data) {
	int i, j;
	int z = 0;
	for (i = y; i < 4; i++) {
		for(j = x; j < 128; j++) {
			if (j <= 0) {
				z++;
			}
			else{
				if (z >= data_end)
					break;
				else {
					imgbuffer[i][j] = data[z];
					z++;
				}
			}
		}
	}
}

// Function to send the display buffer vector to the display buffer.
void display_img() {
	int i, j;

	for(i = 0; i < 4; i++) {
		DISPLAY_COMMAND_DATA_PORT &= ~DISPLAY_COMMAND_DATA_MASK;
		spi_send_recv(0x22);
		spi_send_recv(i);
		spi_send_recv(0x10);

		DISPLAY_COMMAND_DATA_PORT |= DISPLAY_COMMAND_DATA_MASK;

		for(j = 0; j < 128; j++)
			spi_send_recv(~imgbuffer[i][j]);
	}
}

void display_update() {
	int i, j, k;
	int c;
	for(i = 0; i < 4; i++) {
		DISPLAY_COMMAND_DATA_PORT &= ~DISPLAY_COMMAND_DATA_MASK;
		spi_send_recv(0x22);
		spi_send_recv(i);

		spi_send_recv(0x0);
		spi_send_recv(0x10);

		DISPLAY_COMMAND_DATA_PORT |= DISPLAY_COMMAND_DATA_MASK;

		for(j = 0; j < 16; j++) {
			c = textbuffer[i][j];
			if(c & 0x80)
				continue;

			for(k = 0; k < 8; k++)
				spi_send_recv(font[c*8 + k]);
		}
	}
}

// Function to convert a 4096 vector to a 512 vector which we can send to the display.
const uint8_t const* eightbin_conv (int bredd, const uint8_t const *data) {
	 uint8_t new_data[bredd * 4];
	 int i;
	 for (i = 0; i < bredd * 4; i++) {
		 new_data[i] = 0;
	 }
	 int bin, eightbit;
	 int onebit = 0;
	 int onebit_spot = 0;
	 for (eightbit = 0; eightbit < bredd * 4; eightbit++) {
			for (bin = 0; bin < bredd; bin++) {
				new_data[eightbit] += data[onebit] * pow(2, bin);
				onebit += bredd;
			}
			onebit_spot += 1;
			if (onebit_spot == bredd)
				onebit_spot = bredd * 8;
			if (onebit_spot == (bredd * 8) + bredd)
				onebit_spot = bredd * 8 * 2;
			if (onebit_spot == (bredd * 8 * 2) + bredd)
				onebit_spot = bredd * 8 * 3;
			onebit = onebit_spot;
	 }
	 return new_data;
}

// Function to get the correct button that is pressed.
int getbtns (void) {
  int retur = (PORTD & 0x00e0) >> 5;
  return retur;
}

// Display the start screen.
void start() {
  add_img(0,0,512, start_screen);
  while(1) {
    display_img();
    if (getbtns() == 2 && !btn_pressed){
				btn_pressed = 1;
				break;
		}
  }
	btn_pressed = 0;
}

// Display the settings.
void settings_select() {
	if (display_settings == 1) {
		int wait = 0;
		int previous = 0;
		while(1) {
			//Enables your to change between the two difficulty settings
            if (wait < 1) {
				if (getbtns() == 1) {
					if (previous == 0) {
						add_img(0, 0, 512, settings_normal);
						previous = 1;
					}
					else {
						add_img(0, 0, 512, settings_ds);
						previous = 0;
					}
					wait = 700;
				}
				if (getbtns() == 2) {
					if (previous == 0) {
						add_img(0, 0, 512, settings_normal);
						previous = 1;
					}
					else {
						add_img(0, 0, 512, settings_ds);
						previous = 0;
					}
					wait = 700;
				}
				if (getbtns() == 4 && !btn_pressed){
						btn_pressed = 1;
						break;
				}
				display_img();
			}
			if (wait > 0)
				wait--;
			delay(10000);
		}
	}
	btn_pressed = 0;
}

// Display the menu.
void start_select() {
	int wait = 0;
  int previous = 0;
  while(1) {
		if (wait < 1) {
			random_seed2 += 11;
			random_seed += 1;
	    if (getbtns() == 1) {
	      if (previous == 0) {
	        add_img(0, 0, 512, Start_start);
	        previous = 1;
	      }
	      else {
	        add_img(0, 0, 512, Start_settings);
	        previous = 0;
	      }
				wait = 700;
	    }
	    if (getbtns() == 2) {
	      if (previous == 0) {
	        add_img(0, 0, 512, Start_start);
	        previous = 1;
	      }
	      else {
	        add_img(0, 0, 512, Start_settings);
	        previous = 0;
	      }
				wait = 700;
	    }
			if (getbtns() == 4 && !btn_pressed){
			  btn_pressed = 1;
				if (previous == 1) {
					display_settings = 0;
				}
				else {
					display_settings = 1;
				}
				break;
			}

	    display_img();
		}
		if (wait > 0)
			wait--;
		delay(10000);
  }
	btn_pressed = 0;
}

// Display Ep2 screen.
void opening() {
  add_img(0,0,512, ep2);
  while(1) {
    display_img();
		if (getbtns() == 2 && !btn_pressed){
		    btn_pressed = 1;
      	break;
		}
  }
	btn_pressed = 0;
}

// Display the story element
void opening2() {
  add_img(0,0,512, story);
  while(1) {
    display_img();
    if (getbtns() == 1 && !btn_pressed){
		    btn_pressed = 1;
      	break;
		}
  }
	btn_pressed = 0;
}

// Run the actual game
void run_game() {
  int j_time = 0;
	int char_y = 2;
	int j_wait = 0;
	int die = 0;
	int object = rand(random_seed) & 1;
	int object2 = rand(random_seed) & 1;
	double object_x = 140;
	double object2_x = 140;
	double object3_x = 140;
	score = 0;

	// Game loop
	while(1) {
		random_seed += 1;
		random_seed2 += 11;
		// Puts the player on the ground again after jump is finished.
		if (j_time == 40) {
			char_y = 2;
			j_time = 0;
		}
        //Player dies and death animation is initialized
		if(((object == 0 && char_y == 2 && (object3_x < 57) && (object3_x > 49)) || (object == 1 && char_y == 2 && (object3_x < 65) && (object3_x > 63))) || ((char_y == 2 && (object_x < 61) && (object_x > 53)) || (char_y == 2 && (object2_x < 65) && (object2_x > 63)))) {
				char_y = 3;
				die = 700;
		}

		add_img(0, 0, 512, ground);
        //Generating obstacles for the player to surpass
		if (object == 0) {
			add_img(object_x, 3, 16, hole);
		}
		if (object == 1) {
			add_img(object2_x, 2, 4, flower);
		}
		if (object2 == 1) {
			if (object == 1) {
				object3_x = object_x + 35;
				add_img(object3_x, 3, 16, hole);
			}
			else{
				object3_x = object2_x + 35;
				add_img(object3_x, 2, 4, flower);
			}
		}

        //Generates character on ground
		if (j_time == 0) {
				add_img(62, char_y, 4, eightbin_conv(8, hat1));
		}
        //Generates character in jump animation
		else{
			add_img(62, char_y, 4, eightbin_conv(8, jump));
		}
		delay(100000);
		if (j_wait == 0) {

			// Jump and starts counter for when jump is available again
			if (getbtns() == 1) {
					char_y = 1;
					j_wait = 55;
			}
		}
		display_img();
        //Increasing counter to determine how long the players has been in the air
		if (char_y == 1) {
				j_time++;
		}
		//Decreasing counter to determine when the player is able to jump again
        if (j_wait > 0) {
				j_wait--;
		}
        //Moves the obstacles towards the player
		if (object == 1) {
			object2_x -= 0.8;
		}
		if (object == 0) {
			object_x -= 0.8;
		}
		if (object2 == 1) {
			if (object == 1)
				object_x -= 0.8;
			else
				object2_x -= 0.8;
		}

        //Add points to the player's score and generate new obstacles
		if (object2 != 1) {
			if (object_x < -18) {
				score += 1;
				object = rand(random_seed) & 1;
				object2 = rand(random_seed2) & 1;
				object_x = 140;
			}

			if (object2_x < -18) {
				score += 1;
				object = rand(random_seed) & 1;
				object2 = rand(random_seed2) & 1;
				object2_x = 140;
			}
		}
		else {
			if (object3_x < -18) {
				score += 1;
				object = rand(random_seed) & 1;
				object2 = rand(random_seed2) & 1;
				object_x = 140;
				object2_x = 140;
				object3_x = 140;
			}
		}

		if (char_y == 3) {
			while (die > -200){

                //Generates background for death animation
                add_img(0, 0, 512, ground);

				if (object == 0) {
					add_img(object_x - 4, 3, 16, hole);
				}
				if (object == 1) {
					add_img(object2_x - 4, 2, 4, flower);
				}
				if (object2 == 1) {
					if (object == 1) {
						object3_x = object_x + 35;
						add_img(object3_x - 4, 3, 16, hole);
					}
					else{
						object3_x = object2_x + 35;
						add_img(object3_x - 4, 2, 4, flower);
					}
				}
                //Death animation
				if (die > 600)
					add_img(62 + 1, 1, 4, eightbin_conv(8, hat1));

				if (die > 500 && die <= 600)
					add_img(62 + 2, 0, 4, eightbin_conv(8, hat1));

				if (die > 400 && die <= 500)
					add_img(62 + 3, 1, 4, eightbin_conv(8, hat1));

				if (die > 300 && die <= 400)
					add_img(62 + 4, 2, 4, eightbin_conv(8, hat1));

				if (die < 301 && die >= 200)
					add_img(62 + 5, 3, 4, eightbin_conv(8, hat1));
				display_img();
				die--;
			}
				//Saves your score as the highscore if you did better
                if (score > highscore)
					highscore = score;
				break;
		}
	}
}

// Display the You Died screen.
void you_died() {
  add_img(0,0,512, died);
	display_img();
  while(1) {
    if (getbtns() == 1)
      break;
  }
}
//Displays the score of your recent game and the all time high score
void score_screen() {
	display_string(0, "Your score:");
	display_string(1, itoaconv(score));
	display_string(2, "Highscore:");
	display_string(3, itoaconv(highscore));
	display_update();
	while(1) {
    if (getbtns() == 4)
      break;
  }
}

// Display the End screen.
void victorious() {
  add_img(0,0,512, victory);
  while(1) {
    display_img();
    if (getbtns() == 1)
      break;
  }
  delay(1000000);
}
