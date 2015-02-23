import		java.	io.*;
import java.util.Properties;


public class	Main {
	public	static boolean flag;
	public static PrintStream stdout = System.out;
	public static void error(ParseException e)
	{
	String s=e.toString();
	int i= s.indexOf("line");
	int j=s.indexOf(",");
	String line= s.substring(i+4,j);
	System.err.print(line+".");
	 i= s.indexOf("column");
	 j=s.indexOf(".");
	String column= s.substring(i+7,j);
	System.err.print(column+": Syntax Error: expecting one of: " );
	 i= s.indexOf("one");
	String rem= s.substring(i+10,s.length());
	for(String retval:rem.split(" ...\n")){
         System.err.print(retval.trim()+ " ");}
	flag=true;
	System.err.println();	
	} 

	public static void main(String[] args) {
		Token		t;
		PrintStream	ops = null;
		InputStream	inputFile;
		 FieldNames 	F = new FieldNames("MiniJavaParserConstants");
		final MiniJavaParser parser;
		flag=false;		

		try
		{
			PrintStream	ps = new PrintStream("error.txt");
		//	System.setErr(ps);
		}
		catch(Exception e) {
			System.err.println("Problem with Error file: \"error.txt\" : \n" + e.toString());
		}
		try
		{
			ops = new PrintStream("tokens.txt");
			System.setOut (ops);
		}
		catch(Exception e) {
			System.err.println("Problem with tokens file: \"tokens.txt\" : \n" + e.toString());
		}



		try
		{
			inputFile = new FileInputStream(args[0]);
			Properties systemProperties = System.getProperties();
			String verboseOutput = System.getProperty("verbose");
                      
			if(verboseOutput==null)
			System.setOut(ops);
        		else 	
			System.setOut(stdout);
		 parser= new MiniJavaParser(inputFile);
       		 parser.Program();

		}
	
		catch         (ParseException e) {
		System.setOut (stdout);
		 System.out.print("Filename:"+args[0]);
	//	System.out.println("Error :\n" + e.toString()+"\n Myerror ");
		error(e); 

		}

		catch(IOException e) {
			System.err.println("File opening failed:");
		}

		System.setOut(stdout);
	
	if(!flag)
	System.out.println("filename=" + args[0] + "," + " errors=" + MiniJavaParser.totalErrors);
	}
	
}
