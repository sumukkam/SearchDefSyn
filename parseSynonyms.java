import java.io.*;
import java.util.*;
import java.io.File;
import java.lang.*;
import java.lang.Double;
import java.lang.String;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.lang.Integer;


public class parseSynonyms {
	static String outputDirStr;
	static String paramsXmlFile;
	static String taxonomyFilePath;
	static int numOfThreads;
	static int numOfFiles;
	static int beginning = 0;
	static int end = 0;
	static StringBuffer dataInFile = new StringBuffer();
    static String fileLocation;    

	public static void main( String [] args ) {
		outputDirStr = "";
		paramsXmlFile = "";
		taxonomyFilePath = "";
		numOfThreads = 12;
		numOfFiles = 0;
		
        // Change this depending on where you want to put the synonyms.txt file
        fileLocation = "/Users/Sushanth/Desktop/ProgrammingProjects/SearchDefSyn/synonyms.txt";
        
		File definitionsSourceFile = new File( fileLocation );
		readFile(definitionsSourceFile);
		String data = dataInFile.toString();

        data = removeDataBetweenTags(dataInFile);
			
		clearDataInFile();
		dataInFile.append("\r\n\r\n").append(data);
        

        data = formatSynonyms(dataInFile);
 

        clearDataInFile();
		dataInFile.append("\r\nSynonyms:").append(data).append("\r\n");


        if(dataInFile.length() < 22) {
		    clearDataInFile();            
            dataInFile.append("Synonyms:\r\nNo synonyms were found for this word.\r\n").append("\r\n");
        }
        else {
		    clearDataInFile();
            dataInFile.append("Synonyms:").append(data).append("\r\n");
        }

        
		writeToFile();
	}




    public static String removeDataBetweenTags(StringBuffer data) {
        String dataStr;
        int beginning = 0;
        int ending = 0;

        while(data.toString().contains("<")) {
            beginning = data.indexOf("<");
            ending = data.indexOf(">");
            data.delete(beginning, ending+1);
        }

        dataStr = data.toString();

        int count = 0;
        while(dataStr.contains("\r\n\r\n\r\n")) {
            dataStr = dataStr.replaceAll("\r\n\r\n\r\n", "\r\n\r\n");
        }

        return dataStr;
    }




    public static String formatSynonyms(StringBuffer data) {
        String dataStr = data.toString();
        StringBuffer dataStrBuf = new StringBuffer();
        int maxSize = 0;


        while(dataStr.contains("\r\n\r\n")) {
            dataStr = dataStr.replaceFirst("\r\n\r\n", "\r\n");
        }
        
        String [] dataArray = dataStr.split("\r\n");

        for(int x = 0; x < dataArray.length; x++) {
            if(dataArray[x].length() > maxSize) {
                maxSize = dataArray[x].length();
            }
        }

        dataStrBuf.append("\r\n");
        for(int x = 1; x < dataArray.length; x++) {
            dataStrBuf.append(dataArray[x]);
            for(int count = (maxSize-(dataArray[x].length()))+4; count >= 0; count--) {
                dataStrBuf.append(" ");
            }
            if(x%4 == 0) {
                dataStrBuf.append("\r\n");
            }
        }

        return dataStrBuf.toString();
    }




	public static boolean readFile( File definitionsSourceFile ) {
        clearDataInFile();
		Scanner fileToRead = null;
		try {
			fileToRead = new Scanner( definitionsSourceFile );
			String line;
			while( ((line = fileToRead.nextLine()) != null) &&
                    (fileToRead.hasNextLine()) ) {
				dataInFile.append(line);
				dataInFile.append("\r\n");
			}
		} catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            fileToRead.close();
            return true;
        }
	}




	public static void writeToFile() {
		try {
			BufferedWriter bufwriter = new BufferedWriter( new FileWriter(fileLocation) );
			bufwriter.write(dataInFile.toString());
			bufwriter.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	



	public static void clearDataInFile() {
		int numOfChars = dataInFile.length();
		dataInFile = dataInFile.delete( 0, numOfChars );
	}
	
}

