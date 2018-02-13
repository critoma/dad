#include <iostream>
#include <string>

// Required by for routine
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

using namespace std;

int globalVariable = 2;

int main()
{
   string sIdentifier;
   int    iStackVariable = 20;

   pid_t pID = fork();
   if (pID == 0)                // child
   {
      // Code only executed by child process

      sIdentifier = "Child Process: ";
      globalVariable++;
      iStackVariable++;
    }
    else if (pID < 0)            // failed to fork
    {
        cerr << "Failed to fork" << endl;
        return 1;
        //throw new Exception();
        //exit(1);
        // Throw exception
    }
    else                                   // parent
    {
      // Code only executed by parent process

      sIdentifier = "Parent Process:";
      //cout<<"global var="<<globalVariable<<endl;
    }

    // Code executed by both parent and child.
  
    cout << sIdentifier<<endl;
    cout << " Global variable: " << globalVariable;
    cout << " Stack variable: "  << iStackVariable << endl;
    return 0;
}
