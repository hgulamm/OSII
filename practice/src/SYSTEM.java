/*	
 * Name:Hiba Gulam Mohideen
 * CWID: A20110836
 * Course Number : CS 5323
 * Operating Systems Phase 1
 */

/*Description:
The SYSTEM class will be the driver for this simulation.The SYSTEM is to 
contain four subroutines : LOADER, CPU, MEMORY, ERROR_HANDLER. All the variables declared
in the SYSTEM are static and can be accessed all through the simulation.The objects for every 
subroutine are created and called in the SYSTEM.
*/

import java.io.*;
import java.lang.*;
import java.math.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


//System class which acts as the driver to the operating System
public class SYSTEM {
	public static int Job_Id;
	
	public static int Start_Instruction;
	public static int Trace_Flag;
	public static int PC;
	public static int Program_length;
	public static String Instruction_Register;
	public static String FileName;
	public static int System_Clock = 0;
	public static int min=0;
	public static int max=10;
	public static int IO_Clock =0;
	public static String Output;
	public static String outfile = "output.txt";
	public static String dumpfile = "dump.txt";
	public static String trace_file = "tracefile.txt";

	
	//Method to convert Hexadecimal number to Decimal number
	public static int Hex_to_Dec(String hex)
	{
		int decimal = Integer.parseInt(hex, 16);
		return decimal;
	}
	//Method to convert Hexadecimal number to Binary Number
	public static String Hex_to_Bin(String hex)
	{
	int decimal = Integer.parseInt(hex,16);    
	String binary = Integer.toBinaryString(decimal);
	//Append 0's at the beginning if the length of the string is less than 16
	while(binary.length()<16)
	{
		binary = "0"+binary;
	}
	return binary;
	}
	//Method to convert Hexadecimal number to 8 bit Binary Number
	public static String Hex_to_Bin_8_bit(String hex)
	{
	int decimal = Integer.parseInt(hex,16);    
	String binary = Integer.toBinaryString(decimal);
	//Pad 0's to make the length of the string to 8
	while(binary.length()<8)
	{
		binary = "0"+binary;
	}
	return binary;
	}
	
	////Method to convert Decimal number to binary number
	public static String Dec_to_Bin_16_bit(int Dec)
	{
		String binary = Integer.toBinaryString(Dec);
		//Trim the left most elements if the length of the string is greater than 16
		if(binary.length()>16)
		{
			binary = binary.substring((binary.length()-16),binary.length());
		}
		//Append 0's at the beginning if the length of the string is less than 16
		while(binary.length()<16)
		{
			binary = "0"+binary;
		}
		return binary;
		}

	//Method to convert binary to decimal number
	public static int Bin_to_Dec(String bin)
	{
		int decimalValue = Integer.parseInt(bin, 2);
		return decimalValue;
	}
	//Method to convert decimal to hxadecimal
	public static String Dec_to_Hex(int dec)
	{
		String hex_value = Integer.toHexString(dec);
		while(hex_value.length()<2)
		{
			hex_value = "0"+hex_value;
		}
		return hex_value;
	}
	//Method to convert Binary to Hexadecimal
	public static String Bin_to_Hex(String bin)
	{
		if(bin.equals(null))
		{
			return null;
		}
		if(bin.equals(""))
		{
			return "";
		}
		int decimal = Integer.parseInt(bin,2);
		String hexStr = Integer.toString(decimal,16);
		while(hexStr.length()<4)
		{
			hexStr = "0"+hexStr;
		}
		return hexStr;
		
	}
	
	//Method to convert the two's complement binary number to negative number 
	public static int twosComplement(String binaryInt) {
	    
	    if (binaryInt.charAt(0) == '1') {
	        
	        String invertedInt = invertbits(binaryInt);
	        int decimalValue = Integer.parseInt(invertedInt, 2);
	       
	        decimalValue = (decimalValue + 1) * -1;
	       
	        return decimalValue;
	    } else {
	           
	        return Integer.parseInt(binaryInt, 2);
	    }
	}
//Invert the bits if one's complement occurs
	public static String invertbits(String binaryInt) {
	    String result = binaryInt;
	    result = result.replace("0", " ");
	    result = result.replace("1", "0"); 
	    result = result.replace(" ", "1");
	    return result;
	}
	
	//Method to print Output into a file
	public static void output(int jobid, int sysclock,int ioclock,String output) {
		MEMORY.dump();
		  try (BufferedWriter b = new BufferedWriter(new FileWriter(outfile))) {
		   b.write("Cumulative Job Identification number :"+jobid+"(DEC)");
		   b.newLine();
		   b.write("Nature of termination : Normal");
		   b.newLine();
		 //  b.write("Output of the Job : " +output + "(BINARY)");
		   //b.newLine();
		   String sclock;
		   sclock = Integer.toHexString(sysclock);
		   b.write("Clock value at termination: " + sclock + " (HEX)virtual time units");
		   b.newLine();
		   b.write("Run time for the job");
		   b.newLine();
		   b.write("Execution time: " + (sysclock-ioclock) + "(DECIMAL)virtual time units");
		   b.newLine();
		   b.write("Input/output time: " + ioclock + "(DECIMAL)virtual time units");
		   b.newLine();
		   b.close();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
	
	//Method to print error or a warning into an Output file
	 public static void outputerror(int jobid,int sysclock, int ioclock,String error) {
		
		  try (BufferedWriter b = new BufferedWriter(new FileWriter(outfile))) {
		   b.write("Cumulative Job Identification number :"+jobid+"(DEC)");
		   b.newLine();
		   b.write("Nature of termination : Abnormal");
		   b.newLine();
		   b.write(error);
		   b.newLine();
		   String sclock;
		   sclock = Integer.toHexString(sysclock);
		   b.write("Clock value at termination: " + sclock + " (HEX)virtual time units");
		   b.newLine();
		   b.write("Run time for the job");
		   b.newLine();
		   b.write("Execution time: " + (sysclock-ioclock )+ " (DECIMAL)virtual time units");
		   b.newLine();
		   b.write("Input/output time: " + ioclock + " (DECIMAL)virtual time units");
		   b.newLine();
		   b.close();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		catch (NumberFormatException e) {
		   e.printStackTrace();
		  }

		 }
	//Main Method
	public static void main(String[] args) {
		
        FileName = args[0];
        LOADER loader = new LOADER();
		// TODO Auto-generated method stub
		loader.HexLoad();
		MEMORY memory = new MEMORY();
		memory.Buffer_Loading();
		
		CPU cpu = new CPU();
		CPU.CPU(PC,Trace_Flag);
		
		
	}
	public static void EXIT()
	{
		System.exit(0);
	}
}
