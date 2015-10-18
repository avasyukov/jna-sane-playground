sanetest:
	gcc -o sanetest test.cpp -lsane
testlib:
	gcc -shared -o libtest.so libtest.c
testapp:
	gcc -L/home/anganar/git/other/jna-sane-playground/nativeCode/ -o testapp libtestmain.c -ltest
clean:
	rm -f sanetest libtest.so testapp
