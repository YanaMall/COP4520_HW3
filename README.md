# COP4520_HW3
My implementation of homework assignment 3.

Problem 1
To compile this file all you input is 'javac gifts.java' and it should compile as normal. From there you input 'java gifts' where it should run the program and give output on the number of thank you letters written and the runtime.
CORRECTNESS
While I dont think my implementation is 100% correct, I tried to implement an executor this time to help with the threads (0and there is actual threads in action this time) and used a lazy method linked list with locks to help with node insertion.
EFFECIENCY
This code is currently running in the high 10~20 ms area. 

Problem 2
To compile this file all you input is 'javac temp.java' and it should compile as normal. From there you input 'java temp' where it should run the program and give output on the sensor readings in a 24 hour period.
CORRECTNESS
I once again used a executor but I'm not sure if the threads where actually used properly.
EFFECIENCY
This code is currently running in the 50~60 ms area and is giving actual output which leads me to believe it a better implementation than problem 1 at least. 
