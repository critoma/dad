#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

int main()
{
        int pfds[2];
        char bufread[6];

        pipe(pfds);

        if (!fork()) {
            printf(" CHILD: writing to the pipe\n");
            write(pfds[1], "test", 5);
            printf(" CHILD: exiting\n");
            exit(0);
        } else {
            printf("PARENT: reading from pipe\n");
            read(pfds[0], bufread, 5);
            printf("PARENT: read \"%s\"\n", bufread);
            wait(NULL);
        }
}
