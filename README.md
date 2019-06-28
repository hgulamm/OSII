# Operating System
The goal of the project is to simulate a simple batch processing system. At the very basic level the system is designed to find the Hexadecimal value after instruction from Loader format. The approach to the problem is the given Hexadecimal loader format is loaded into the system and each byte is converted to binary format. Later the CPU reads the instructions and executes the operations based on the opcode. Once the Instruction set is specified, the CPU will take care of the execution steps in order to display the necessary output. The entire memory that has been used in the system is inherited from the MEMORY.

INPUT FORMAT:
Assembly language: Program length: 9 Start instruction: 4 
0 start:	alloc 5	//store 5 in location 0 
2	alloc 0	//store 0 in location 2 
4 main:	movei r3,0	//move value 0 to Register r3 
6	load r2,(r3)	//load value of r3 t0 r2 
8	trap 2 //Get input value from stdin 
10	add r4,r2,r1	//Add r4=r1+r2 
12	store 2(r3),r4 //store value of r4 to location 2 
14	move r1,r4 //move contents of r4 to r1 
16	trap 1	//Output the value to console 
Assembly Language: 
Loader Format 
Input: 009 004 
00050000B60084C0 
400208889702A300 
4001 001

OUTPUT FORMAT: 3

The output displayed in console: The value -2 is added to 5
