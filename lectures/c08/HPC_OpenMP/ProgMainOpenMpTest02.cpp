#include <stdlib.h>
#include <stdio.h>
#include "omp.h"

int main()
{
	int nthreads, tid;
	omp_set_num_threads(3);
	int i;

	#pragma omp parallel private(tid) shared(i)
	{
		tid = omp_get_thread_num();
		printf("Hello world from (%d)\n", tid);

		#pragma omp for
		for(i = 0; i <= 4; i++)
		{
			printf(" Iteration %d by (%d) \n", i, tid);
		}
	} //all threads join master thread and terminate

	return 0;
}

