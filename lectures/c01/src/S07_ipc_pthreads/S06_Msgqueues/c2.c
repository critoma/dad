    #include <stdio.h>
    #include <stdlib.h>
    #include <errno.h>
    #include <sys/types.h>
    #include <sys/ipc.h>
    #include <sys/msg.h>

    struct my_msgbuf {
        long mtype;
        char mtext[44];
    };

    int main(void)
    {
        struct my_msgbuf buf;
        int msqid;
        key_t key;
	  pid_t pid;

        if ((key = ftok("c1.c", 'B')) == -1) {  
            perror("ftok");
            exit(1);
        }

        if ((msqid = msgget(key, 0644)) == -1) {
            perror("msgget");
            exit(1);
        }
    	  int i = 0;
	  pid = fork();
	  if(!pid) {//parinte
         //for(;;) { 
		for(i = 0; i <=9; i++) {
             if (msgrcv(msqid, (struct msgbuf *)&buf, sizeof(buf), i, 0) == -1) {
                perror("msgrcv");
                exit(1);
             }
             printf("PARENT %s \n", buf.mtext);
		}
         //}
	  } else {//copil
         //for(;;) { 
		for(i = 10; i <=19; i++) {
             if (msgrcv(msqid, (struct msgbuf *)&buf, sizeof(buf), i, 0) == -1) {
                perror("msgrcv");
                exit(1);
             }
             printf("CHILD: %s \n", buf.mtext);
		}
	   //}
        }
        return 0;
    }
