#include <iostream>
#include <vector>
#include <omp.h>
using namespace std;

#include <time.h>

clock_t start, stop;

int main(int argc, char** argv)
{
	int nr_elements = 10000000, i = 0, nrThreads = 2;
	vector<int> v1(nr_elements, 1), v2(nr_elements, 2);
	vector<int> result(nr_elements, 0);

	//nrThreads = atoi(argv[1]);

	start = clock();
	for(i = 0; i < nr_elements; i++)
	{
		result[i] = v1[i] + v2[i];
	}
	stop = clock();
	
	cout<<"It took "<<(stop-start)<<" clicks ("<<(((float)(stop-start))/CLOCKS_PER_SEC)<<" seconds)."<<endl;

	nrThreads = 32;
        //nrThreads = 2;
	//a worksharing for construct to add vectors:
	omp_set_num_threads(nrThreads);

	start = clock();
	#pragma omp parallel for
	for(i = 0; i < nr_elements; i++)
	{
		result[i] = v1[i] + v2[i];
	}
	stop = clock();
	
	cout<<"It took "<<(stop-start)<<" clicks ("<<(((float)(stop-start))/CLOCKS_PER_SEC)<<" seconds)."<<endl;

	for(int j = 0; j < 20; j++)
		cout<<" , "<<result[j];

	return 0;
}

