/*
Description: The MEMORY subroutine is the main memory that consists of 1024 bytes (locations 0 to 1023)
A byte is the basic addressing unit. Each byte is 8 bits long. The Memory subroutine imports memory from the LOADER
through buffer and send or receive the data from CPU subroutine.
 */

import java.io.*;
import java.lang.*;
import java.math.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;



//Main Memory Class
public class MEMORY extends SYSTEM 
{
	
	public static String Z;
	//MEMORY Constructor
	MEMORY()
	{
		
	}
	//Main Memory with size 1024 bits
	public static String[] MEM = new String[1024];
	static int sizep=Program_length*2;
	public static String[] MEMO = new String[sizep];
	
	
	public static String[] Buffer = new String[sizep];
	public static Boolean[] lock = new Boolean[1024];
	public static int BIN_INDEX =0;
	public static int REDUCED_BIN_INDEX =0;
	public static int MEM_INDEX =0;
	public static int REDUCED_MEM_INDEX =0;

	//Load the elements from loader into the buffer
	public void Buffer_Loading()
	{
		try
		{
	int size=LOADER.BIN.size();
	int ind=0;
	
	while(size>0)
	{
		
		
		Buffer[BIN_INDEX] = LOADER.BIN.get(ind);
		
		ind++;
		BIN_INDEX=BIN_INDEX+2;
		
		size--;
		
	}
	
	for(int i=0;i<Buffer.length;i=i+2)
	{
		MEM[MEM_INDEX]=Buffer[i];
		lock[MEM_INDEX]=false;
		MEM_INDEX++;
		
		
	}
	
	for(int i=0;i<Program_length;i++)
	{
		MEMO[REDUCED_BIN_INDEX]=MEM[i].substring(0, 8);
		REDUCED_BIN_INDEX++;
		MEMO[REDUCED_BIN_INDEX]=MEM[i].substring(8,16);
		
		REDUCED_BIN_INDEX++;
		
	}
	
		 
		
		}
		catch(IndexOutOfBoundsException ex)
        {
			ERROR_HANDLER.ERROR(104);
        }

	}
	//MEMORY method to read, write,allocate, lock and unlock in memory
	public static String MEMORY(String X,int Y,String Z)
	{
		if(X.equals("READ"))
		{
			
			Z = MEMORY.Buffer[Y];
			
		}
		
		else if(X.equals("WRITE"))
		{
			
			if(MEMORY.lock[Y]!=true)
				MEMORY.MEM[Y]=Z;
			
			
		}
		else if(X.equals("ALLOC"))
			
		{	MEMORY.MEM[Y]=Z;
		}
else if(X.equals("LOCK"))
			
		{	
		MEMORY.lock[Y]=true;
	
		}
else if(X.equals("UNLOCK"))
{
	MEMORY.lock[Y]=false;
	}
		
		return Z;
	}
	
	//Prints the dump of the contents in memory
	public static void dump()
	{
		for(int i=0;i<Program_length;i++)
	{
		MEMO[REDUCED_MEM_INDEX]=MEM[i].substring(0, 8);
		REDUCED_MEM_INDEX++;
		MEMO[REDUCED_MEM_INDEX]=MEM[i].substring(8,16);
		
		REDUCED_MEM_INDEX++;
		
	}
		
		try {
			
			BufferedWriter b = new BufferedWriter(new FileWriter(dumpfile));
		  for(int j=0;j<sizep&&j<64;j++) {
			   
			  if(j%8==0)
					b.write("\n"+j+"\t");
			  String out=Bin_to_Hex(MEMO[j]);
		   b.write("\t"+out+"\t");
		  
			
			
			
			}
		   b.close();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		
		
	}
	
}




