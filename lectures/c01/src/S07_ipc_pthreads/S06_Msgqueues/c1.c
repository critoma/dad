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

        if ((key = ftok("c1.c", 'B')) == -1) {
            perror("ftok");
            exit(1);
        }

        if ((msqid = msgget(key, 0644 | IPC_CREAT)) == -1) {
            perror("msgget");
            exit(1);
        }
       pid_t pid;
	 int i;
       switch(pid=fork()) {
            case -1:
                perror("fork");  
                exit(1);         

            case 0:
                printf("\n CHILD:\n");
                printf("\n CHILD: Enter lines of text \n");
	           
		    for(i=0;i<=9;i++){
				printf("\n CHILD %d ",i);
				buf.mtype = i;
				gets(buf.mtext);
      	           	if (msgsnd(msqid, (struct msgbuf *)&buf, sizeof(buf), 0) == -1)
	                		perror("msgsnd");
	          }
		    exit(0);	
            default:
                printf("\n PARENT: \n");
		    printf("\n PARENT: Enter lines of text \n");
 
		    for(i=10;i<=19;i++){
				printf("\n PARENT %d ",i);
		            buf.mtype = i;
				gets(buf.mtext);
      	           	if (msgsnd(msqid, (struct msgbuf *)&buf, sizeof(buf), 0) == -1)
	                		perror("msgsnd");
	          } 
		    wait(NULL);
        }
        return 0;
    }
