    #include <stdio.h>
    #include <stdlib.h>
    #include <errno.h>
    #include <sys/types.h>
    #include <sys/ipc.h>
    #include <sys/msg.h>

    struct my_msgbuf {
        long mtype;
        char mtext[200];
    };

    int main(void)
    {
        struct my_msgbuf buf;
        int msqid;
        key_t key;

        if ((key = ftok("kirk.c", 'B')) == -1) {
            perror("ftok");
            exit(1);
        }

	//key = 755;
        if ((msqid = msgget(key, 0644 | IPC_CREAT)) == -1) {
            perror("msgget");
            exit(1);
        }
        
        printf("Enter lines of text, ^D to quit:\n");

        buf.mtype = 1; /* we don't really care in this case */
        while(gets(buf.mtext), !feof(stdin)) {
            if (msgsnd(msqid, (struct msgbuf *)&buf, sizeof(buf), 0) == -1)
                perror("msgsnd");
	    else buf.mtype++;
        }

        if (msgctl(msqid, IPC_RMID, NULL) == -1) {
            perror("msgctl");
            exit(1);
        }

        return 0;
    }
