#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main()
{
        int pfds[2];
	int rv = 0;

        pipe(pfds);

        if (!fork()) {
            close(1);//close(stdout);       /* close normal stdout */
            dup(pfds[1]);   /* make stdout same as pfds[1] */
            close(pfds[0]); /* we don't need this */
            execlp("ls", "ls", NULL);
	    rv = 0;
	    exit(rv);
        } else {
            close(0);//close(stdin);       /* close normal stdin */
            dup(pfds[0]);   /* make stdin same as pfds[0] */
            close(pfds[1]); /* we don't need this */
	    wait(&rv);
            execlp("wc", "wc", "-l", NULL);
	    exit(0);
        }
}
