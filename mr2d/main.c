#include <pic32mx.h>
#include <stdint.h>

int main(void) {

	hardware_init();
	display_init();
	start();
	start_select();
	settings_select();
	opening();
	run_game();
	you_died();
	score_screen();
	main();

	return 0;
}
