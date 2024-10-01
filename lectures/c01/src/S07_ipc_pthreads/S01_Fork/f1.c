#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int sv = 3;

int main()
{
        pid_t pid;
        int rv;
        int x = 1;
        int* px = (int*)malloc(4);
        *px = 9;

        switch(pid=fork()) {
            case -1:
                perror("fork");  /* something went wrong */
                exit(1);         /* parent exits */

            case 0:
                printf(" CHILD: This is the child process!\n");
                sv--;
                (*px)++;
                printf(" CHILD: x = %d, *px = %d, &px = %p, px = %p, sv = %d \n", (++x), (*px), &px, px, sv); // same addr because mem virt
                printf(" CHILD: My PID is %d\n", getpid());
                printf(" CHILD: My parent's PID is %d\n", getppid());
                printf(" CHILD: Enter my exit status (make it small): ");
                scanf(" %d", &rv);
                printf(" CHILD: I'm outta here!\n");
                free(px);
                exit(rv);

            default:
                printf("PARENT: This is the parent process!\n");
                sv++;
                (*px)--;
                printf("PARENT: x = %d, *px = %d, &px = %p, px = %p, sv = %d \n", (--x), (*px), &px, px, sv);
                printf("PARENT: My PID is %d\n", getpid());
                printf("PARENT: My child's PID is %d\n", pid);
                printf("PARENT: I'm now waiting for my child to exit()...\n");
                wait(&rv);
                printf("PARENT: My child's exit status is: %d\n", WEXITSTATUS(rv));
                printf("PARENT: I'm outta here!\n");
                free(px);
        }
}
