/*
Description:The Loader will be responsible for loading each user program into Main memory.
Each user program is loaded into main memory from location 0.A User program has to be converted from 
HEX to BINARY.A String Buffer is created that can load the Binary data and in turn load the data into Main Memory. 
 */


import java.io.*;
import java.lang.*;
import java.math.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;


//LOADER class that loads the data from the File System
public class LOADER extends SYSTEM {
 
	//ArrayList to store the each line of the input job
	public static ArrayList<String> lines = new ArrayList<String>();
	// ArrayList to store the hexadecimal format of numbers
	public static ArrayList<String> HD = new ArrayList<String>();
	//ArrayList to store the Binary format of numbers
	public static ArrayList<String> BIN = new ArrayList<String>();
	
	//Default LOADER Constructor to Initialize variables used in the system
	LOADER()
	{
		 
		 String each_line = null;

	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(FileName);

	           
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((each_line = bufferedReader.readLine()) != null) {
	                
	            	if(each_line.length()>16)
	            	{
	            		ERROR_HANDLER.ERROR(102);
	            	}
	                lines.add(each_line);       
	            }   
	            
	            String first_line = lines.get(0);
	            String[] First_line_values = first_line.split("\\s+");
	            Job_Id = 1;
	     
	            Program_length = Hex_to_Dec(First_line_values[0]);
	            PC = 0;
	            Start_Instruction=Hex_to_Dec(First_line_values[1]);
	            if(Start_Instruction>Program_length)
	            {
	            	ERROR_HANDLER.ERROR(2);
	            }
	            
	            
	            
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	        ERROR_HANDLER.ERROR(101);
	        }
	        catch(IOException ex) {
	        	ERROR_HANDLER.ERROR(101);
	        }
	        catch(IndexOutOfBoundsException ex)
	        {
	        	
	        }
	    }
	
	//Loading the hexadecimal elements into the HexaDecimal Arraylist
	public void HexLoad()
	{
		String one_line="";
		for(int line_index=1;line_index<lines.size()-1;line_index++)
		{
		 one_line=one_line + lines.get(line_index);
		}
		int lines_size=lines.size();
		
		Trace_Flag = Hex_to_Dec(lines.get(lines_size-1));
		
        if(Trace_Flag!=1&&Trace_Flag!=0)
        {
        	ERROR_HANDLER.ERROR(107);
        }
		int each_word=0;
		
		while(each_word<one_line.length()-1)
		{
			if(one_line.length()%4!=0)
			{
				ERROR_HANDLER.ERROR(1);
			}
			HD.add(one_line.substring(each_word,Math.min(each_word+4,one_line.length())));
			each_word = each_word+4;
			
			
		}
		
		if(HD.size()!=Program_length)
		{
			
			ERROR_HANDLER.ERROR(105);
			
		}
		
		for(int i=0;i<HD.size();i++)
		{
			if(!HD.get(i).matches("-?[0-9a-fA-F]+"))
			{
				ERROR_HANDLER.ERROR(103);
			}
			
			String first_word = Hex_to_Bin_8_bit(HD.get(i).substring(0, 2));
			String second_word = Hex_to_Bin_8_bit(HD.get(i).substring(2,4));
			BIN.add(first_word+second_word);
			
		}	
	}
	
		 
	}
	
	

	
	
	
	
	
	

