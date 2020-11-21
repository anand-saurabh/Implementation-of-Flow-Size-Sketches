Project 3: Report

I have created a total of 4 classes for this project:
1) "Main" class
2) "CountMin" class - contains the implementation CountMin.
 
Important methods:
record: records the sizes of all flows.
3) "CounterSketch" class - contains the implementation of Counter Sketch.

Important methods:
record: records the sizes of all flows.
4) "ActiveCounter" class - contains the implementation of the Active Counter.

Important methods:
increaseProb: increases the value of active counter probabilistically.

The "Main" class has the "main" function which is the entry point of the program. This main function gives you a list of options and asks from the user for the input.

The list of options displayed are:

Enter 1: Count Min 2: Counter Sketch 3: Active Counter 4: exit

Based on the user input, it invokes the implementation methods of CountMin, Counter Sketch,  ActiveCounter  classes respectively.

 Steps to run the program:

1)	copy all the .java code files in one folder/directory. Also, copy the “project3input.txt” file (present in the submission folder) in the same folder/directory where the source code is present.

2)	compile all the .java code files using this command on the command line: javac *.java

3)	run the command "java Main" on the command line to invoke the "main" method 

of the “Main class”. A list of options will be displayed as:


Enter 1: Count Min 2: Counter Sketch 3: Active Counter 4: exit

4)	select one of the options (1, 2, or 3) from the above options according to which sketch code you want to run by entering a number (1,2 or 3) on the command line and press enter. Select 4 if you want to exit the program.

5)	The code will run for the type of the sketch selected and the output will be printed on the console/terminal and also output files will be created.

6)	After the console output is generated, the main menu will appear again with the same options as shown in step 3 above so that we can run the program again and again if required.

Currently, all the three implementation of sketches (count min, counter sketch, active counter) is called from the "main" java function in the "Main" class. Each of these implementations take the default values of the parameters given in the project requirements file.

Output of Program:
For each of sketch implementation (count min, counter sketch, active counter) the outputs are printed on the console.
Also, the output files for different sketches will be created in the current directory every time we run the code for different sketches by selecting options (1, 2 or 3) from the menu. The file names of different output files are: 
1) CountMin.txt - output file for Count Min. 
2) CounterSketch.txt - output file for Counter Sketch. 
3) ActiveCounter.txt - output file for Active Counter.

One output file with names as specified above for each sketch is also submitted along with the code files.

