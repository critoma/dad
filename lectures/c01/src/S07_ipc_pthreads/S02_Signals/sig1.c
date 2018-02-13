#include <unistd.h>
#include <signal.h>
#include <errno.h>
#include <sys/types.h>

void sighandler(int sig)
{
	execl("/bin/cat", "cat", "test.txt", NULL);	
}

int main()
{
	if(signal(SIGUSR2, sighandler) == SIG_ERR) {
		perror("err cu semnalizarea");
		return 1;
	}

	pause();
	return 0;
}
