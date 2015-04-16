#include <stdio.h>
void mikado(int);
int main(void){
	int pooh = 2, bah = 5;
}

void mikado(int bah){
	int pooh = 10;
}

void interchange(int * u,int * v);
void interchange(int * u, int * v){
	int temp;
	temp = *u;
	*u = *v;
	*v = temp;
}