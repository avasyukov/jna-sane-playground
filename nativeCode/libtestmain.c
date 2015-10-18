#include <stdio.h>
#include "libtest.h"

int main(){
	if( getGoodValue() == SANE_STATUS_GOOD )
		printf("Good value ok\n");
	if( getSomeError() == SANE_STATUS_INVAL )
		printf("Error reporting ok\n");
	return 0;
}
