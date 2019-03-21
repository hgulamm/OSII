/*
Description: The CPU subroutine is the central unit that processes the entire instructions.It will take the memory from the MEMORY subsroutine 
and finds out the type of instruction it belongs to. The CPU will process the instructions based on the Loader format. 
 */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import java.io.*;


//CPU performs all the arithmetic and logical operations
public class CPU extends SYSTEM {
	
public static String rs;
public static String rd;
public static String Op_Code;


public static String Input;

public static String[] Register = new String[8];
public static String rt;
public static int trace_flag =0;
public static String immd ;
public static String trace_reg = "  ";
public static String trace_EA = "  ";
public static String trace_reg_bef = "  ";
public static String trace_MemEA_bef = "    ";
public static String trace_reg_aft = "  ";
public static String trace_MemEA_aft = "    ";

public static Boolean flag=true;



//Parameterized CPU Constructor to take program counter and Trace switch as input values
public static void CPU(int X,int Y)
{	
	 try  {
		
		 
		 if(Y==1){
			 File f= new File(trace_file);
			 
			 
			 if(flag){
				 if(f.exists()){f.delete();}
				 BufferedWriter b1 = new BufferedWriter(new FileWriter(f,true));
				 b1.write("\t\t\t\t\t\t\t\t\t\t\t\tBEFORE EXECUTION\t\tAFTER EXECUTION\n");
				 b1.write("Clock"+"\t\t"+"PC"+"\t\t"+"Instruction"+"\t\t"+"R "+"\t\t"+"EA"+"\t\t"+"(R)"+"\t\t"+"(EA)"+"\t\t"+"(R)"+"\t\t"+"(EA)\n");   
				 b1.write("(Dec)"+"\t\t"+"(Hex)"+"\t\t"+"(Hex)"+"\t\t"+"(Hex)"+"\t"+"(Hex)"+"\t"+"(Hex)"+"\t"+"(Hex)"+"\t"+"(Hex)"+"\t"+"(Hex)\n");
				 b1.close();
				 flag=false;
			 }
		  }
		 } catch (IOException e) {
		
		  }  
	try
	{
		
	//Allocate the values to memory location 
		 while(PC<Start_Instruction)
		 { 
			 Instruction_Register=MEMORY.MEMORY("READ", PC, null);

				
			 MEMORY.MEMORY("ALLOC", PC, Instruction_Register );
			 System_Clock=System_Clock+1;

			
			 PC=PC+2;
			 
		 }
		 
		
	Instruction_Register=MEMORY.MEMORY("READ",PC,null);	
	

	//Condition for Suspected Infinite loop
	
	if(System_Clock>10000)
	{
		ERROR_HANDLER.ERROR(6);
	}
	//Incrementing the system clock
		System_Clock = System_Clock + 1;
		//Initialize the Opcode
		Op_Code= Instruction_Register.substring(0,4);
		switch(Op_Code)
		{
		case "0000": 
			ADD();
			
			break;
		case "0001":
			ADD_I();
			break;
		case "0010":
			SUB();
			break;
		case "0011":
			SUB_I();
		  break;
		case "1000":
			LOAD();
			break;
		case "1001":
			STORE();
			break;
		case "1010":
			MOVE();
			break;
		case "1011":
			MOVE_I();
			break;
		case "1101":
			SEQ();
			break;
		case "1110":
			SGT();
			break;
		case "1111":
			SNE();
			break;
		case "0111":
			BEQZ();
			break;
		case "1100":
			BNEZ();
			break;
		case "0100":
			TRAP();
			break;
		case "0101":
			LOCK();
			break;
		case "0110":
			UNLOCK();
			break;
		default:
			ERROR_HANDLER.ERROR(5);
	}
	}catch(NumberFormatException e){
		ERROR_HANDLER.ERROR(103);
	}
	
}
//Perform Unlock operation on memory location
private static void UNLOCK() {
	try {
		immd=Instruction_Register.substring(4,16);
		String rsbef=MEMORY.MEMORY("READ", Bin_to_Dec(immd), null);
		
		MEMORY.MEMORY("UNLOCK", Bin_to_Dec(immd), null);
		String rsaft=MEMORY.MEMORY("READ", Bin_to_Dec(immd), null);
	trace_reg=rs;
	trace_EA="";
	trace_MemEA_aft="";
	trace_MemEA_bef="";
	printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		
	}
}
//Perform lock operation on Memory location
private static void LOCK() {
	// TODO Auto-generated method stub
	try {
		immd=Instruction_Register.substring(4,16);
		String rsbef=MEMORY.MEMORY("READ", Bin_to_Dec(immd), null);
		
		MEMORY.MEMORY("LOCK", Bin_to_Dec(immd), null);
		String rsaft=MEMORY.MEMORY("READ", Bin_to_Dec(immd), null);
		trace_reg=rs;
		trace_EA="";
		trace_MemEA_aft="";
		trace_MemEA_bef="";
		printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
}
//Trap instruction
private static void TRAP() {
	try {
	// TODO Auto-generated method stub
rd=Instruction_Register.substring(4, 16);

if(Bin_to_Dec(rd)==2)
{
Scanner input=new Scanner(System.in);
System.out.println("Enter the input:");
int number=input.nextInt();
String rsbef=Register[1];
Register[1]=Dec_to_Bin_16_bit(number);
String rsaft=Register[1];
trace_reg=Dec_to_Bin_16_bit(1);
trace_EA="";
trace_MemEA_aft="";
trace_MemEA_bef="";
printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
System_Clock=System_Clock+10;
IO_Clock=IO_Clock+10;
}
else if(Bin_to_Dec(rd)==1)
{
	String rsbef=Register[1];
	Output=Register[Bin_to_Dec(rd)];
	String rsaft=Register[1];
	trace_reg=Dec_to_Bin_16_bit(1);
	trace_EA="";
	trace_MemEA_aft="";
	trace_MemEA_bef="";
	printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
System.out.println("Output value:"+Output+" HEX:"+Bin_to_Hex(Register[1]));
System_Clock=System_Clock+10;
IO_Clock=IO_Clock+10;
SYSTEM.output(Job_Id,System_Clock,IO_Clock,Output);
HLT();
}
else if(Bin_to_Dec(rd)==0)
{
	printtrace("","","","","","");
	HLT();
}
else {
	ERROR_HANDLER.ERROR(8);
}	
PC=PC+2;
CPU(PC,Trace_Flag);
	}
	catch (InputMismatchException ie) {
		// TODO Auto-generated catch block
		ERROR_HANDLER.ERROR(3);
	}
	catch (Exception e) {
		// TODO Auto-generated catch block
		
	}
}
//HALT operation returns the control to SYSTEM
public static void HLT()
{
SYSTEM.EXIT();
}
//Branch not equal to zero operation
private static void BNEZ() {
	// TODO Auto-generated method stub
try {	rd=Instruction_Register.substring(4, 7);
	rs=Instruction_Register.substring(7,10);	
		
		immd=Instruction_Register.substring(10,16);
		int label=twosComplement(immd);
		String rsbef=Register[Bin_to_Dec(rs)];
		String value=MEMORY.MEMORY("READ", Bin_to_Dec(Register[Bin_to_Dec(rs)]), null);
		if(Bin_to_Dec(value)!=0)
		{
			PC=PC+label;
			
		}
		String rsaft=Register[Bin_to_Dec(rs)];
		trace_reg=rs;
		trace_EA="";
		trace_MemEA_aft="";
		trace_MemEA_bef="";
		printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
	PC=PC+2;	
		
		CPU(PC,Trace_Flag);	
}
catch(StackOverflowError e) {
	ERROR_HANDLER.ERROR(6);
}
catch(Exception e)
{
	}
}
//Branch Equals Zero operation
private static void BEQZ() {
	try {
	// TODO Auto-generated method stub
rd=Instruction_Register.substring(4, 7);
rs=Instruction_Register.substring(7,10);	
	immd=Instruction_Register.substring(10,16);
	int label=twosComplement(immd);
	String rsbef=Register[Bin_to_Dec(rs)];
	String value=MEMORY.MEMORY("READ", Bin_to_Dec(Register[Bin_to_Dec(rs)]), null);
	if(Bin_to_Dec(value)==0)
	{
		PC=PC+label;
	}
	String rsaft=Register[Bin_to_Dec(rs)];
	trace_reg=rs;
	trace_EA="";
	trace_MemEA_aft="";
	trace_MemEA_bef="";
	printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
	PC=PC+2;
	CPU(PC,Trace_Flag);	
	}
	catch(StackOverflowError e) {
		ERROR_HANDLER.ERROR(6);
	}
	
	catch(Exception e) {}
}

//Set if not equal operation
private static void SNE() {
	try {
	// TODO Auto-generated method stub
rd=Instruction_Register.substring(4, 7);
	rs=Instruction_Register.substring(7,10);
	rt=Instruction_Register.substring(10,13);
	String rsbef=Register[Bin_to_Dec(rs)];
	String rdbef=Register[Bin_to_Dec(rd)];
	String rtbef=Register[Bin_to_Dec(rt)];;
	if(Bin_to_Dec(Register[Bin_to_Dec(rs)])!=Bin_to_Dec(Register[Bin_to_Dec(rt)]))
	{
		Register[Bin_to_Dec(rd)]="0000000000000001";
	}
	else {
		Register[Bin_to_Dec(rd)]="0000000000000000";
	}
		String rsaft=Register[Bin_to_Dec(rs)];
		String rdaft=Register[Bin_to_Dec(rd)];
		String rtaft=Register[Bin_to_Dec(rt)];
		trace_reg=rd;
		trace_EA="";
		trace_MemEA_aft="";
		trace_MemEA_bef="";
		printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
		trace_reg=rs;
		trace_EA="";
		trace_MemEA_aft="";
		trace_MemEA_bef="";
		printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
		trace_reg=rt;
		trace_EA="";
		trace_MemEA_aft="";
		trace_MemEA_bef="";
		printtrace(trace_reg,trace_EA,rtbef,trace_MemEA_bef,rtaft,trace_MemEA_aft);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		
	}
PC=PC+2;
	
	CPU(PC,Trace_Flag);	
}
//Set if greater than operation
private static void SGT() {
	// TODO Auto-generated method stub
	try {
rd=Instruction_Register.substring(4, 7);
	rs=Instruction_Register.substring(7,10);
	rt=Instruction_Register.substring(10,13);
	String rsbef=Register[Bin_to_Dec(rs)];
	String rdbef=Register[Bin_to_Dec(rd)];
	String rtbef=Register[Bin_to_Dec(rt)];
	if(Bin_to_Dec(Register[Bin_to_Dec(rs)])>Bin_to_Dec(Register[Bin_to_Dec(rt)]))
	{
		Register[Bin_to_Dec(rd)]="0000000000000001";
	}
	else {
		Register[Bin_to_Dec(rd)]="0000000000000000";
	}
	String rsaft=Register[Bin_to_Dec(rs)];
	String rdaft=Register[Bin_to_Dec(rd)];
	String rtaft=Register[Bin_to_Dec(rt)];
	trace_reg=rd;
	trace_EA="";
	trace_MemEA_aft="";
	trace_MemEA_bef="";
	printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
	trace_reg=rs;
	trace_EA="";
	trace_MemEA_aft="";
	trace_MemEA_bef="";
	printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
	trace_reg=rt;
	trace_EA="";
	trace_MemEA_aft="";
	trace_MemEA_bef="";
	printtrace(trace_reg,trace_EA,rtbef,trace_MemEA_bef,rtaft,trace_MemEA_aft);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		
	}
PC=PC+2;
	CPU(PC,Trace_Flag);	
}
//Set if equal operation
private static void SEQ() {
	// TODO Auto-generated method stub
	try {
rd=Instruction_Register.substring(4, 7);
	rs=Instruction_Register.substring(7,10);
	rt=Instruction_Register.substring(10,13);
	String rsbef=Register[Bin_to_Dec(rs)];
	String rdbef=Register[Bin_to_Dec(rd)];
	String rtbef=Register[Bin_to_Dec(rt)];
	if(Bin_to_Dec(Register[Bin_to_Dec(rs)])==Bin_to_Dec(Register[Bin_to_Dec(rt)]))
	{
		Register[Bin_to_Dec(rd)]="0000000000000001";
	}
	else {
		Register[Bin_to_Dec(rd)]="0000000000000000";
	}
	String rsaft=Register[Bin_to_Dec(rs)];
	String rdaft=Register[Bin_to_Dec(rd)];
	String rtaft=Register[Bin_to_Dec(rt)];
	trace_reg=rd;
	trace_EA="";
	trace_MemEA_aft="";
	trace_MemEA_bef="";
	printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
	trace_reg=rs;
	trace_EA="";
	trace_MemEA_aft="";
	trace_MemEA_bef="";
	printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
	trace_reg=rd;
	trace_EA="";
	trace_MemEA_aft="";
	trace_MemEA_bef="";
	printtrace(trace_reg,trace_EA,rtbef,trace_MemEA_bef,rtaft,trace_MemEA_aft);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		
	}
PC=PC+2;
	
	CPU(PC,Trace_Flag);	
}
//Move register operation
private static void MOVE() {
	try {
	// TODO Auto-generated method stub
rd=Instruction_Register.substring(4, 7);
trace_reg_bef=Register[Bin_to_Dec(rd)];
	rs=Instruction_Register.substring(7,10);
	rt=Instruction_Register.substring(10,13);
	String rsbef=Register[Bin_to_Dec(rs)];
	String rdbef=Register[Bin_to_Dec(rd)];
	
Register[Bin_to_Dec(rd)]=Register[Bin_to_Dec(rs)];
String rsaft=Register[Bin_to_Dec(rs)];
String rdaft=Register[Bin_to_Dec(rd)];
trace_reg=rd;
trace_EA="";
trace_MemEA_aft="";
trace_MemEA_bef="";
printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
trace_reg=rs;
trace_EA="";
trace_MemEA_aft="";
trace_MemEA_bef="";
printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		
	}
PC=PC+2;
	
	CPU(PC,Trace_Flag);	
}
//Store operation
private static void STORE() {
	// TODO Auto-generated method stub
	try {
rd=Instruction_Register.substring(4, 7);

int addr=twosComplement(Register[Bin_to_Dec(rd)]);
	rs=Instruction_Register.substring(7,10);
	immd=Instruction_Register.substring(10,16);
	int disp=twosComplement(immd);
	String rsbef=Register[Bin_to_Dec(rs)];
	String rdbef=Register[Bin_to_Dec(rd)];
String store=MEMORY.MEMORY("WRITE",addr+disp, Register[Bin_to_Dec(rs)]);
String rsaft=Register[Bin_to_Dec(rs)];
String rdaft=Register[Bin_to_Dec(rd)];
trace_reg=rd;
trace_EA=immd;
trace_MemEA_bef=MEMORY.MEMORY("READ",disp, null);
trace_MemEA_aft=MEMORY.MEMORY("READ",disp, null);
printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
trace_reg=rs;
trace_EA="";
trace_MemEA_aft="";
trace_MemEA_bef="";
printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		
	}
PC=PC+2;
	CPU(PC,Trace_Flag);
}
//Load operation
private static void LOAD() {
	// TODO Auto-generated method stub
try {
	rd=Instruction_Register.substring(4, 7);
	
	rs=Instruction_Register.substring(7,10);
	int addr=Bin_to_Dec(Register[Bin_to_Dec(rs)]);
	immd=Instruction_Register.substring(10,16);
	int disp=Bin_to_Dec(immd);
	String meabf=MEMORY.MEMORY("READ",disp, null);
	String bin=MEMORY.MEMORY("READ",addr+disp, null);
	int value=twosComplement(bin);
	String rsbef=Register[Bin_to_Dec(rs)];
	String rdbef=Register[Bin_to_Dec(rd)];
	Register[Bin_to_Dec(rd)]=Dec_to_Bin_16_bit(value);
	String rsaft=Register[Bin_to_Dec(rs)];
	String rdaft=Register[Bin_to_Dec(rd)];
	trace_reg=rd;
	trace_EA="";
	trace_MemEA_bef="";
	trace_MemEA_aft="";
	printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
	trace_reg=rs;
	trace_EA=Dec_to_Bin_16_bit(disp);
	trace_MemEA_aft=MEMORY.MEMORY("READ",disp, null);
	trace_MemEA_bef=meabf;
printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
PC=PC+2;
	CPU(PC,Trace_Flag);
}
catch(Exception e)
{}
}
//Subtract immediate operation
private static void SUB_I() {
	// TODO Auto-generated method stub
	try {	rd=Instruction_Register.substring(4, 7);
	int dest=Bin_to_Dec(rd);
	rs=Instruction_Register.substring(7,10);
	immd=Instruction_Register.substring(10,16);
String rsbef=Register[Bin_to_Dec(rs)];
String rdbef=Register[Bin_to_Dec(rd)];
int value=Bin_to_Dec(Register[Bin_to_Dec(rs)])-Bin_to_Dec(immd);
Register[Bin_to_Dec(rd)]=Dec_to_Bin_16_bit(value);
String rsaft=Register[Bin_to_Dec(rs)];
String rdaft=Register[Bin_to_Dec(rd)];
trace_reg=rd;
trace_EA="";
trace_MemEA_bef="";
trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
trace_reg=rs;
trace_EA="";
trace_MemEA_bef="";
trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
	} catch (Exception e) {
		// TODO Auto-generated catch block
	
	}
PC=PC+2;
	CPU(PC,Trace_Flag);
}
//Subtract operation
private static void SUB() {try {
	// TODO Auto-generated method stub
rd=Instruction_Register.substring(4, 7);
	rs=Instruction_Register.substring(7,10);
	rt=Instruction_Register.substring(10,13);
	String rsbef=Register[Bin_to_Dec(rs)];
	String rdbef=Register[Bin_to_Dec(rd)];
	String rtbef=Register[Bin_to_Dec(rt)];
int value=Bin_to_Dec(Register[Bin_to_Dec(rs)])-Bin_to_Dec(Register[Bin_to_Dec(rt)]);
Register[Bin_to_Dec(rd)]=Dec_to_Bin_16_bit(value);
String rsaft=Register[Bin_to_Dec(rs)];
String rdaft=Register[Bin_to_Dec(rd)];
String rtaft=Register[Bin_to_Dec(rt)];
trace_reg=rd;
trace_EA="";

trace_MemEA_bef="";

trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
trace_reg=rs;
trace_EA="";

trace_MemEA_bef="";

trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);
trace_reg=rt;
trace_EA="";

trace_MemEA_bef="";

trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rtbef,trace_MemEA_bef,rtaft,trace_MemEA_aft);
} catch (Exception e) {
	
	
}

PC=PC+2;

CPU(PC,Trace_Flag);	
}
//Add immediate operation
private static void ADD_I() {
	
try {	rd=Instruction_Register.substring(4, 7);
	int dest=Bin_to_Dec(rd);
	rs=Instruction_Register.substring(7,10);
	
	immd=Instruction_Register.substring(10,16);
	String rsbef=Register[Bin_to_Dec(rs)];
	String rdbef=Register[Bin_to_Dec(rd)];
int value=Bin_to_Dec(Register[Bin_to_Dec(rs)])+Bin_to_Dec(immd);
Register[Bin_to_Dec(rd)]=Dec_to_Bin_16_bit(value);

String rsaft=Register[Bin_to_Dec(rs)];
String rdaft=Register[Bin_to_Dec(rd)];
trace_reg=rd;
trace_EA="";

trace_MemEA_bef="";

trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
trace_reg=rs;
trace_EA="";

trace_MemEA_bef="";

trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);

	}
	catch(Exception e) {}

PC=PC+2;

CPU(PC,Trace_Flag);
}
//Add operation
private static void ADD() {
	
try {	rd=Instruction_Register.substring(4, 7);
	
	rs=Instruction_Register.substring(7,10);
	
	rt=Instruction_Register.substring(10,13);
	String rtbef=Register[Bin_to_Dec(rt)];
String rsbef=Register[Bin_to_Dec(rs)];
String rdbef=Register[Bin_to_Dec(rd)];
int value=Bin_to_Dec(Register[Bin_to_Dec(rs)])+Bin_to_Dec(Register[Bin_to_Dec(rt)]);
Register[Bin_to_Dec(rd)]=Dec_to_Bin_16_bit(value);
String rsaft=Register[Bin_to_Dec(rs)];
String rdaft=Register[Bin_to_Dec(rd)];
String rtaft=Register[Bin_to_Dec(rt)];
trace_reg=rd;
trace_EA="";

trace_MemEA_bef="";

trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
trace_reg=rs;
trace_EA="";

trace_MemEA_bef="";

trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rsbef,trace_MemEA_bef,rsaft,trace_MemEA_aft);

trace_reg=rt;
trace_EA="";

trace_MemEA_bef="";

trace_MemEA_aft="";
printtrace(trace_reg,trace_EA,rtbef,trace_MemEA_bef,rtaft,trace_MemEA_aft);
}
catch (Exception e) {
	// TODO: handle exception
	}

PC=PC+2;

CPU(PC,Trace_Flag);

}

public static void MOVE_I() {
	// TODO Move Immediate opreation
	try {
		
	rd=Instruction_Register.substring(4, 7);
	rs=Instruction_Register.substring(7,10);
	
	immd=Instruction_Register.substring(10,16);

	
	int dest=Bin_to_Dec(rd);
	
	String rdbef=Register[Bin_to_Dec(rd)];
	Register[dest]=immd;
	
	String rdaft=Register[Bin_to_Dec(rd)];
	
	
	trace_EA="";
	
	trace_MemEA_bef="";
	
	trace_MemEA_aft="";
	
	
	printtrace(rd,trace_EA,rdbef,trace_MemEA_bef,rdaft,trace_MemEA_aft);
	
	PC=PC+2;
	
	CPU(PC,Trace_Flag);
	
	}
	catch(Exception e) {}
}
//Print the tracefile
public static void printtrace(String trace_reg,String trace_EA, String trace_reg_bef,String trace_MemEA_bef,String trace_reg_aft,String trace_MemEA_aft) throws IOException{
	try {
		
	
	
	if (Trace_Flag==1){
		File f1 = new File(trace_file);
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(f1,true));
	//Conidtion to skip the tracefile in gaps of 0-10,20-30 virtual timeline
		if(System_Clock>=max)
		{
			
			if(System_Clock>=max+10)
			{
				
			min=min+10;
			max=max+20;
			}
		}
	else if(System_Clock<max){
		if(trace_reg_bef==null)
			trace_reg_bef="";
		if(trace_reg_aft==null)
			trace_reg_aft="";
		if(trace_MemEA_bef==null)
			trace_MemEA_bef="";
		if(trace_MemEA_aft==null)
			trace_MemEA_aft="";
		bw1.write(System_Clock+"\t\t\t"+Dec_to_Hex(PC)+"\t\t\t"+Bin_to_Hex(Instruction_Register)+"\t\t\t"+Bin_to_Hex(trace_reg)+"\t\t"+Bin_to_Hex(trace_EA)+"\t\t"+Bin_to_Hex(trace_reg_bef)+ "\t\t"+Bin_to_Hex(trace_MemEA_bef)+"\t\t"+ Bin_to_Hex(trace_reg_aft)+"\t\t"+Bin_to_Hex(trace_MemEA_aft)+"\t\t"+"\n");
		bw1.close();
	}
	}
	
	}
	catch(Exception e)
	{
		
	}
}

}



