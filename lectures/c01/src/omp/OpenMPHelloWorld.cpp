// g++ -o OpenMPHelloWorld -fopenmp OpenMPHelloWorld.cpp
// export OMP_NUM_THREADS=5
// ./OpenMPHelloWorld
#include <iostream>

int main()
{
    #pragma omp parallel 
    {
      std::cout << "hello world \n";
    }
    return 0;
}
