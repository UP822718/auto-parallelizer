# Auto Parallelizer

This is an automatic parallelization tool to translate serial C/C++ code to parallel code using OpenMP directives.
It is mainly based on task parallelization and it also supports `for` parallelization of reduction operations.

## Usage

Run the jar file and set the required arguments. 
Task parallelization is included by default and other options can be set as follow:

```
-i, -input <arg>      Input file
-o, -output <arg>     Output file
-r, -reduction        Enable the for parallelization of reduction operations 
-h, -help             Print this message
```

## Example

### Serial code
```cpp
#include <iostream>

using namespace std;

int main () {
    int n = 10, sum1 = 0, sum2 = 0;
    for (int i = 0; i < n; ++i)
        sum1 += i;
    for (int i = 0; i < n; ++i)
        sum2 += 2 * i;
    cout << sum1 + sum2 << '\n';
}
```
### Run the parallelizer
`java -jar AutoParallelizer.jar -reduction -input input.cpp`

### Parallelized code
```cpp
#include <iostream>
using namespace std;
int main () {
    int n = 10, sum1 = 0, sum2 = 0;
    #pragma omp parallel sections
    {
        #pragma omp section
        {
            #pragma omp parallel for reduction(+:sum2)
            for (int i = 0; i < n; ++i)
                sum2 += 2 * i;
        }
        #pragma omp section
        {
            #pragma omp parallel for reduction(+:sum1)
            for (int i = 0; i < n; ++i)
                sum1 += i;
        }
    }
    cout << sum1 + sum2 << '\n';
}
```