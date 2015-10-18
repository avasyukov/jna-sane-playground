#include "libtest.h"

SANE_Status getGoodValue() {
	return SANE_STATUS_GOOD;
}

SANE_Status getSomeError() {
	return SANE_STATUS_INVAL;
}
