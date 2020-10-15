# OS-Simulator

An Operating System simulator developed through the use of Java methods. A Netbeans compiler is used to run the program.

The simulator begins from booting the Shell, where critical variables and function parameters that can be used in further processing are initialized. Then it involves applying four separate process scheduling algorithms to represent a real operating system working framework in process scheduling. So it comes to the scheduling portion of the OS after booting, and here it asks the user for total number of processes along with their arrival and burst time for each process divided by space on which the scheduling algorithms will be simulated.

The simulator contains a Memory Management Unit.

The work of the MMU can be divided into three major categories:
1.Hardware memory management which supervises and regulates the use of RAM (random access memory) and cache memory by the processor.
2.OS (operating system) memory control, guaranteeing the availability of sufficient memory space for each running program's objects and data structures at all times;
3.Program resource management, which allocates the necessary resources for each particular application and then recycles free-up resource when the operation is done.

Four scheduling algorithms were used in the implementation: First Come First Serve, Shortest Job First, Highest Response Ratio Next and Shortest Time Remaining First.

The program contains a class named as multiprocessing which implements the thread and most of the
functionality is being implemented in the run function of the multiprocessing class. Then the functionality is called using the start functionality of the object of the multiprocessing class. 

The functionality of synchronization is being implemented using the semaphore object in java as the object named ‘MUTEX’, which is an object of the semaphore class. It's purpose is to implement synchronization and avoid the code from the race condition so that the output can be produced accurately. Moreover, the synchronized functionality of Java has also been used to ensure concurrency and synchronization.
