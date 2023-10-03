 //As a useless example, the following program creates, writes to, and reads from a pipe.

    #include <stdio.h>
    #include <stdlib.h>
    #include <errno.h>
    #include <unistd.h>
    
    int main()
    {
        int pfds[2];
        char buf[30];
        int errcode = 0;
    
        if (pipe(pfds) == -1) {
            perror("pipe");
            exit(1);
        }
    
        printf("writing to file descriptor #%d\n", pfds[1]);
        write(pfds[1], "test", 5);
        printf("reading from file descriptor #%d\n", pfds[0]);
        read(pfds[0], buf, 5);
        printf("read \"%s\"\n", buf);
        scanf("\n errcode = %d", &errcode);
        return errcode;
    }


