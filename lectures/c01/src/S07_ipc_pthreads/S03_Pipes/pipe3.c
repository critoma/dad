#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int pfd[2];
int pid = 0;
int c;

int main(int argc, char** argv)
{
	pipe(pfd);
	
	pid = fork();
	
	if(pid) { //parinte
		close(pfd[0]);
		printf("\n Proces parinte - introduceti un sir: %d %d %d\n", pid, pfd[0], pfd[1]);
		
		while(read(0, (int*)&c, sizeof(char)) > 0) {
			if(write(pfd[1], (int*)&c, sizeof(char)) < 0) {
				perror("write - err");
				exit(1);
			}
		}
		
		close(pfd[1]);
		
		wait(NULL);
		printf("\n Parintele a primit exit de la copil");
		exit(0);
	} else { //copil
		close(pfd[1]);
		printf("\n proces fiu - preia datele %d %d %d\n", pid, pfd[0], pfd[1]);
		while(read(pfd[0], (int*)&c, sizeof(char)) > 0) {
			if(islower(c))
				printf("%c ", toupper(c));
			else
				printf("%c ", c);
		}
		close(pfd[0]);
		exit(0);
	}
}
