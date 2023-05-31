package application;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;  
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import application.CMD;
import application.CMDUtility;

import java.awt.BorderLayout;
import java.awt.Dimension;


public class Main {
	//"Wandelt Command Zeile in Befehl um"
	static CMD tempCMD;
    static HashMap<Integer, CMD> line_to_CMD = new HashMap<Integer, CMD>();
    
    static HashMap<Integer, Integer> line_to_Full_CMD = new HashMap<Integer, Integer>();
			
	public static void main(String[] args) {    
 		Scanner scanner = new Scanner(System.in);
        
        try {
        	System.out.println("AND "+Integer.toHexString(0x25 & 0x36)); 
        	String fabiansPath = "C:\\Users\\fabia\\PIC_GUI\\PIC_UI\\src\\main\\TestProg_PicSim_20210420\\TPicSim1.LST";
        	String NidisPath = "/Users/nidhi/Desktop/Coding/UniWorkspace/Java/Gitrepo/PIcSImulator/PicSimulator/src/main/TestProg_PicSim_20210420/TPicSim5.LST";
        	
        	String activePath = fabiansPath;
        	
        	// Öffnen Sie die .LST-Datei zum Lesen
        	BufferedReader reader = new BufferedReader(new FileReader(activePath));
            
        	 // Liest die erste Zeile der Datei
        	String line = reader.readLine(); 
                               
            List<String> input = new ArrayList<String>();                                             
            
            input = readLinesFromFile(activePath);
            createAndShowGUI(input);
            
            getCommands(input);          
            processCommands(line_to_CMD,line_to_Full_CMD);
            
           
            reader.close(); //Schließen Sie die Datei
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
    }
	
	private static List<String> readLinesFromFile(String filePath) throws IOException {
	    List<String> lines = new ArrayList<>();
	    BufferedReader reader = new BufferedReader(new FileReader(filePath));

	    String line;
	    while ((line = reader.readLine()) != null) {
	        lines.add(line);
	    }

	    reader.close();
	    return lines;
	}
	private static void processCommands(Map<Integer, CMD> lineToCMD, Map<Integer, Integer> lineToFullCMD) {
	    while (!CMDUtility.isProgramEnded()) {
	        int i = CMDUtility.getProgrammCounterLine();
	        CMD execCMD = lineToCMD.get(i);

	        // Weitere Verarbeitung des Befehls hier...

	        CMDUtility.do_CMD(execCMD, lineToFullCMD.get(CMDUtility.getProgrammCounterLine()));
	    }
	}
	private static void getCommands(List<String> input) {
		for(String k : input) {
			if((k.charAt(0)+"").equals(" ") ) {
	    		
	    	}else {
	    		
	    		int temp_dec_CMD = Integer.parseInt(k.split(" ")[1],16);
	    		//System.out.println("line: "+line.split(" ")[1]);
	    		tempCMD = CMDUtility.recognizeCMD(temp_dec_CMD);
	    		int tempCMDLine = Integer.parseInt(k.split(" ")[0],16);
	    		line_to_Full_CMD.put(tempCMDLine, temp_dec_CMD);
	    		line_to_CMD.put(tempCMDLine, tempCMD);
	    		
	    	}
		}
		
	}
	private static void createAndShowGUI(List<String>input) {
	    JTextArea textArea = new JTextArea();
	    for(String k:input) {
	    	textArea.append(k + "\n");
	    }
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    JFrame frame = new JFrame("Program Output");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    frame.setPreferredSize(new Dimension(800, 600));
	    frame.pack();
	    frame.setVisible(true);
	}

	
}