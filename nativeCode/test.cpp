#include <cstdlib>
#include <stdio.h>
#include <sane/sane.h>

int main() {
    sane_init(NULL, NULL);

    const SANE_Device*** ppp = (const SANE_Device***)malloc(sizeof(SANE_Device**));
    int bla = sane_get_devices(ppp, true);
    printf("Res = %d\n",  bla);

    for (int i = 0; (*ppp)[i] != NULL; ++i)
    {
        printf("%s %s %s %s\n", (*ppp)[i]->name, (*ppp)[i]->vendor, (*ppp)[i]->model, (*ppp)[i]->type);
    }
    free(ppp);
    sane_exit();
    return 0;
}
