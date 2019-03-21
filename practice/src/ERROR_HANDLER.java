/*
Description: The ERROR_HANDLER subsystem handles errors that we can encounter during the runtime of the system. The error thrown from the system will be trapped in this subsystem and an appropriate error number will be generated .In case of an error 
the system dumps the memory upto and terminates itself. 
 */




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//ERROR_HANDLER  which catches all the errors in the system 
public class ERROR_HANDLER extends SYSTEM {
	
	 public static int Error_Number;
	 public static String message = "";

	 // The ERROR method will take the parameter of an error number
	 public static void ERROR(int err) {
		
	  try (BufferedWriter b = new BufferedWriter(new FileWriter(outfile, true))) {
	 
	    switch (err) {
	     
	    case 101:
	    	message = "Input File not found";
	    	outputerror(Job_Id,System_Clock,IO_Clock,message);
	    	SYSTEM.EXIT();
	      
	      break;
	     case 102:
	    	 	message = "Invalid loader Format:Job Lines should not exceed 16 hex digits";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	      break;
	     case 103:
	    	 	message = "Invalid loader Format:Invalid Hex code in input";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	      
	      break;
	     case 104:
	    	 	message = "Memory Range Fault";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	      
	      break;
	     case 105:
	    	 	message = "Length of the Input Job doesn't match the specified size";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	      break;  

	     case 107:
	    	 	message = "Invalid Trace Flag";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	      break; 
	     case 1:
	    	 	message = "The length of one of the words is not equal to 4";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	     
	      break;
	     case 2:
	    	 message = "PC value exceeds the length of the Program";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	      break;
	     case 3:
	    	 message = "Only Integer values are Allowed as Inputs";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	     
	      break;
	     case 4:
	    	 	message = "Input value exceeds the range";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	     
	      break;
	     
	     case 5:
	    	 	message = "Invalid opcode";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	      
	      break;
	     case 6:
	    	 	message = "Suspected Infinite Loop";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT();
	      break;
	  
	     case 8:
	    	 	message = "Illegal Instruction Trap ";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT(); 
	      break;
	     case 9:
	    	 	message = "Illegal Instruction Trap:Stack Overflow ";
		    	outputerror(Job_Id,System_Clock,IO_Clock,message);
		    	SYSTEM.EXIT(); 
	      break;
	    
	    
	   }
	  } catch (IOException e) {
	   e.printStackTrace();
	  }

	 }
	}


