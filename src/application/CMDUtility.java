package application;

import java.lang.Math.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
/*
 * Hilfsklasse die Nebenberechnungen führt und Nützliche Daten Besitzt
 */
public abstract class CMDUtility {
	
	private static HashMap fRegisters = new HashMap(); // "Simuliert" Speicheradressen
	private static boolean isProgrammEnded = false;
	private static HashMap bank0Saife = new HashMap();
	private static HashMap bank0 = new HashMap();
	private static HashMap bank0Us = new HashMap();
	private static HashMap bank1Saife = new HashMap();
	private static HashMap bank1 = new HashMap();
	private static HashMap bank1Us = new HashMap();
	
	private static int[][] bank0Default = {
			{2,0},
			{3,0b00011000},
			{10,0},
			{11,0}//+RBIF;
	};
	
	private static int[][] bank1Default = {
			{0x81,0xFF},
			{0x82,0},
			{0x83,0b00011000},//die 2 einser sind variabel, + Z+DC+C
			{0x85,0b11111},
			{0x86,0xFF},
			{0x88,0},//+WRERR
			{0x8A,0},
			{0x8B,0},//+RBIF
	};	
	private static int[][] uPosBank0= { //speicher: die adresse und die anzahls der u's von rechts
			{1,8},
			{3,4},
			{4,8},
			{5,5},
			{6,8},
			{8,8},
			{9,8},
			{10,1}
			
	};
	private static int[][] uPosBank1= { //speicher: die adresse und die anzahls der u's von rechts
			{0x83,3},
			{0x84,8},
			{0x8B,1}
			
	};
	
	
	private static int iWDT = 0;
	private static int WRegister = 0;
	

	public static void setWRegister(int wRegister) {
		WRegister = wRegister;
	}

	private static int carryFlag =0;
	private static int digitalCarryFlag =0;
	private static int zRegister =0;
	private static int programmCounterLine = 0; //enthält die Line, die gerade ausgeführt wird
	private static Queue<Integer> returnAdress = new LinkedList<Integer>(); //Stack der return adressen speichert (bsp für CALL)
	private static final int HIGHEST_VAL =  0b11111111111111;
	private static final int BITMASK_FOR_F = 0b1111111; //f ist immer in den ersten 7 bit (von rechts) gespeichert
	private static final int BITMASK_FOR_D = 0b10000000;
	private static final int BITMASK_FOR_B = 0b1110000000;
	private static final int BITMASK_FOR_K_NORMAL = 0b11111111; //ACHTUNG: Nicht für "CALL" und "GO TO" benutzen
	private static final int BITMASK_FOR_K_SPECIAL = 0b11111111111;//für "CALL" und "GO TO"
	
	public static int get_HIGHEST_VAL() {
		return HIGHEST_VAL;
	}
	

	
	
	public static CMD recognizeCMD(int intCMD) {
		
		
		for (CMD cmd : CMD.values()){
			//System.out.println("test auf: "+cmd.getName());


		    if(cmd.isMnemonic(intCMD)) {
		    	

		    	int i=0;
		    	return cmd;
	    		
		    	
		    }
		}
		
		return null;
		
	}
	
	//"Benötigt" sind der Command (in Hex) und der Befehl(Bsp: ADDLW)
	public static void do_CMD(CMD cmd, int icmd) {

	    switch(cmd){ 
	    case MOVLW: 
	    	do_MOVLW(icmd);
	        break; 
	    case ANDLW: 
	    	do_ANDLW(icmd);
	        break; 
	    case IORLW: 
	    	do_IORLW(icmd); 
	        break; 
	    case SUBLW: 
	    	do_SUBLW(icmd);
	        break; 
	    case XORLW: 
	    	do_XORLW(icmd);
	        break; 
	    case ADDLW: 
	    	do_ADD(icmd);
	        break; 
	    case GOTO: 
	    	do_GO_TO(icmd);
	        break; 
	    case CALL: 
	    	do_CALL(icmd);
	        break; 
	    case RETURN: 
	    	do_RETURN();
	        break; 
	    case NOP: 
	    	do_NOP();
	        break;
	    case RETLW: 
	    	do_RETLW(icmd);
	        break;
	    case MOVWF: 
	    	do_MOVWF(icmd);
	        break;
	    case ADDWF: 
	    	do_ADDWF(icmd);
	        break;
	    case ANDWF: 
	    	do_ANDWF(icmd);
	        break;
	    case CLRF: 
	    	do_CLRF(icmd);
	        break;
	    case COMF: 
	    	do_COMF(icmd);
	        break;
	    case DECF: 
	    	do_DECF(icmd);
	        break;  
	    case INCF: 
	    	do_INCF(icmd);
	        break; 
	    case MOVF: 
	    	do_MOVF(icmd);
	        break; 
	    case IORWF: 
	    	do_IORWF(icmd);
	        break; 
	    case SUBWF: 
	    	do_SUBWF(icmd);
	    	break; 
	    case SWAPF: 
	    	do_SWAPF(icmd);
	    	break; 
	    case XORWF: 
	    	do_XORWF(icmd);
	        break; 
	    case CLRW: 
	    	do_CLRW();
	        break;
	    case RLF: 
	    	do_RLF(icmd);
	        break;
	    case RRF: 
	    	do_RRF(icmd);
	        break; 
	    case DECFSZ: 
	    	do_DECFSZ(icmd);
	        break;
	    case INCFSZ: 
	    	do_INCFSZ(icmd);
	        break;
	    case BSF: 
	    	do_BSF(icmd);
	        break;
	    case BCF: 
	    	do_BCF(icmd);
	        break;
	    case BTFSS: 
	    	do_BTFSS(icmd);
	        break;
	    case BTFSC: 
	    	do_BTFSC(icmd);
	        break;
	    default: 
	    	System.out.print(cmd.getName()+"wurde noch nicht implementiert");
	        break; 
	    } 
		
		
	}
	
	public static void do_BTFSS(int icmd) {
		int enc_attr = getFVar(icmd);
		int bVal = getBVar(icmd);
		
		if(((int)Math.pow(2, bVal) & getFRegister(enc_attr)) == 0) {
			
			
			//sol = getFRegister(enc_attr)+(int)Math.pow(2, bVal);
		}else {
			do_NOP();
			//sol = getFRegister(enc_attr);
		}
		printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_BTFSC(int icmd) {
		int enc_attr = getFVar(icmd);
		int bVal = getBVar(icmd);
		
		if(((int)Math.pow(2, bVal) & getFRegister(enc_attr)) == 0) {
			
			do_NOP();
			//sol = getFRegister(enc_attr)+(int)Math.pow(2, bVal);
		}else {
			//sol = getFRegister(enc_attr);
		}
		printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_BSF(int icmd) {
		int enc_attr = getFVar(icmd);
		int bVal = getBVar(icmd);
		int sol;
		
		if(((int)Math.pow(2, bVal) & getFRegister(enc_attr)) == 0) {
			sol = getFRegister(enc_attr)+(int)Math.pow(2, bVal);
		}else {
			sol = getFRegister(enc_attr);
		}
		setFRegister(enc_attr,sol);
		printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	//muss noch getestet werden
	public static void do_BCF(int icmd) {
		int enc_attr = getFVar(icmd);
		int bVal = getBVar(icmd); //getBVar();
		int sol;
		
		if(((int)Math.pow(2, bVal) & getFRegister(enc_attr)) == 0) {
			sol = getFRegister(enc_attr);
		}else {
			sol = getFRegister(enc_attr)-(int)Math.pow(2, bVal);
		}
		setFRegister(enc_attr,sol);
		printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_INCFSZ(int icmd) {
		int enc_attr = getFVar(icmd);
		int sol = getFRegister(enc_attr);
		if(sol != 0xFF) {
			sol = sol+1;
		}
		if(sol == 0xFF ) {
			do_NOP();
		}else {
			
		}

		if(getDVar(icmd)) {
        	
        	setFRegister(enc_attr,sol);
        	
        }else {

        	WRegister = sol;
        }
		
		printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_DECFSZ(int icmd) {
		int enc_attr = getFVar(icmd);
		int sol = getFRegister(enc_attr);
		if(getFRegister(enc_attr) >0) {
			sol = sol-1;
		}
		if(sol == 0 ) {
			do_NOP();
		}else {
			
		}

		if(getDVar(icmd)) {
        	
        	setFRegister(enc_attr,sol);
        	
        }else {

        	WRegister = sol;
        }
		
		printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_RRF(int icmd) {
		int enc_attr = getFVar(icmd);
		int sol = getFRegister(enc_attr);
		int tempCFlag;
		if(sol % 2 == 1) {
			tempCFlag = 1;
			sol = sol-1;
		}else {
			tempCFlag = 0;
		}
		sol = (sol/2) + carryFlag* 0x80;//8 = 2*2*2
		carryFlag = tempCFlag;
		
		if(getDVar(icmd)) {
        	
        	setFRegister(enc_attr,sol);
        	
        }else {

        	WRegister = sol;
        }
		
		printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_RLF(int icmd) {
		 int enc_attr = getFVar(icmd);
		 int sol = ((getFRegister(enc_attr)*2)%0x100)+carryFlag;
		 if((getFRegister(enc_attr)*2)/(0x100) >=1 ) {
			 carryFlag =1;
		 }else {
			 carryFlag =0;
		 }
		 if(getDVar(icmd)) {
	        	
	        	setFRegister(enc_attr,sol);
	        	
	        }else {

	        	WRegister = sol;
	        }
		 	printForDebug(enc_attr);
	        updateProgrammCounter();
	}
	
	public static void do_CLRW() {
		WRegister =0;
        zRegister =1;
        
		System.out.println("W = "+Integer.toHexString(WRegister));  
		updateProgrammCounter();
	}
	public static void do_XORWF(int icmd) {
		 int enc_attr = getFVar(icmd);
		 int sol = getFRegister(enc_attr) ^ WRegister;
	        if(getDVar(icmd)) {
	        	
	        	setFRegister(enc_attr,sol);
	        	
	        }else {

	        	WRegister = sol;
	        }
	        if(sol ==0) {
	        	zRegister =1;
	        }
	        
	        printForDebug(enc_attr);
	        updateProgrammCounter();
	}
	
	public static void printForDebug( int FAdress) {
        System.out.println("W = "+Integer.toHexString(WRegister));  
        System.out.println("F = "+FAdress); 
        System.out.println("F Val = "+Integer.toHexString(getFRegister(FAdress))); 
        System.out.println("C = "+carryFlag);
        System.out.println("DC = "+digitalCarryFlag);
        System.out.println("Z Register: "+ zRegister);
        
	}
	public static void printForDebug() {
        System.out.println("W = "+Integer.toHexString(WRegister));  
        System.out.println("C = "+carryFlag);
        System.out.println("DC = "+digitalCarryFlag);
        System.out.println("Z Register: "+ zRegister);
        
	}
	public static void do_MOVF(int icmd) {
        int enc_attr = getFVar(icmd);
        
        
        if(getDVar(icmd)) {
        	//EIG UNNÖTIG
        	setFRegister(enc_attr,getFRegister(enc_attr));
        }else {
        	WRegister = getFRegister(enc_attr);
        }
        if(getFRegister(enc_attr) ==0) {
        	zRegister =1;
        }
        
        printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_INCF(int icmd) {
        int enc_attr = getFVar(icmd);
        int sol;
        if(getFRegister(enc_attr) ==0xFF) {
        	sol = 0;
        }else {
            sol = getFRegister(enc_attr)+1;
        }
        if(sol ==0) {
        	zRegister =1;
        }
        
        if(getDVar(icmd)) {
        	setFRegister(enc_attr,sol);
        }else {
        	WRegister = sol;
        }
        printForDebug(enc_attr);
        updateProgrammCounter();
	}
	public static void do_IORWF(int icmd) {
		int enc_attr = getFVar(icmd);
		int sol = getFRegister(enc_attr) | WRegister;
		if(getDVar(icmd)) {
			setFRegister(enc_attr,sol);
			
			
		}else {
			WRegister = sol;
		}
		if(sol ==0) {
        	zRegister =1;
        }
		printForDebug(enc_attr);
        updateProgrammCounter();
	}
	public static void do_SUBWF(int icmd) {
        System.out.println("W = "+Integer.toHexString(WRegister));  
		int enc_attr = getFVar(icmd);
		
		set_C_and_DC_Addition(getFRegister(enc_attr) ,h_do_2Complement(WRegister));
		
		int sol = (getFRegister(enc_attr) + h_do_2Complement(WRegister))%0x100;
		if(getDVar(icmd)) {
			setFRegister(enc_attr,sol);
		}else {
			WRegister = sol;
		}
		printForDebug(enc_attr);
        updateProgrammCounter(); 
	}
	public static int h_do_2Complement(int num) {
		return getComplement(num)+1;
	}
	
	
	public static int getFirst7Bits(int num) {
		return num & 0b1111111;
	}
	
	public static void do_SWAPF(int icmd) {
		int enc_attr = getFVar(icmd);
		int sol = h_do_SWAPNUBBLES(getFRegister(enc_attr));
		if(getDVar(icmd)) {
			setFRegister(enc_attr,sol);
			
		}else {
			WRegister = sol;
			
		}
		printForDebug(enc_attr);
        updateProgrammCounter();
	}
	public static int h_do_SWAPNUBBLES(int num) {
		int bitmask1 = 0xF0;
		int bitmask2 = 0x0F;
		
		int part1 = num & bitmask1; //letzen 4 bits
		int part2 = num & bitmask2; //ersten 4 bits
		
		//anpassung der Positionen
		part1 = (part1)/(2*2*2*2);//bewegt die bits 4 nach rechts
		part2 = part2*2*2*2*2;
		return part1+part2;
	}
	
	
	public static void do_DECF(int icmd) {
        int enc_attr = getFVar(icmd);
        int sol;
        if(getFRegister(enc_attr) ==0) {
        	sol = 0xFF;
        }else {
            sol = getFRegister(enc_attr)-1;
        }
        if(sol ==0) {
        	zRegister =1;
        }
        
        
        if(getDVar(icmd)) {
        	setFRegister(enc_attr,sol);
        }else {
        	WRegister = sol;
        }
        
        printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_COMF(int icmd) {
        int enc_attr = getFVar(icmd);
        int sol = getComplement(getFRegister(enc_attr));
        System.out.println("SOLUTION:"+Integer.toHexString(sol) );
        if(getDVar(icmd)) {
        	setFRegister(enc_attr,sol);
        }else {
        	WRegister = sol;
        }
        if(sol ==0) {
        	zRegister =1;
        }
        
        printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_CLRF(int icmd) {
        int enc_attr = getFVar(icmd);
        setFRegister(enc_attr,0);
        zRegister =1;
        printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_ANDWF(int icmd) {
        int enc_attr = getFVar(icmd);
        int sol = WRegister & getFRegister(enc_attr);
        if(getDVar(icmd)) {
        	setFRegister(enc_attr,sol);
        }else {
        	WRegister = sol;
        }
        if(sol ==0) {
        	zRegister =1;
        }
        
        
        printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	
	public static void do_ADDWF(int icmd) {
        int enc_attr = getFVar(icmd);
        
        set_C_and_DC_Addition(WRegister,getFRegister(enc_attr));
        
        int sol = WRegister+getFRegister(enc_attr);
        
        if(getDVar(icmd)) {
        	setFRegister(enc_attr,sol);
        }else {
        	WRegister = sol;
        }
        
        printForDebug(enc_attr);
        updateProgrammCounter();
	}
	
	public static void do_MOVWF(int icmd) {
	
        int enc_attr = getFVar(icmd);
        setFRegister(enc_attr,WRegister);
        
        printForDebug(enc_attr);
        
        updateProgrammCounter();
	}
	
	public static void do_MOVLW(int icmd) {
        int enc_attr = getNKVar(icmd);
        WRegister = enc_attr;
        printForDebug() ;
        updateProgrammCounter();
	}
	
	public static void do_ANDLW(int icmd) {
		int enc_attr = getNKVar(icmd);
        WRegister = enc_attr & WRegister;
        printForDebug() ;
        if(WRegister ==0) {
        	zRegister =1;
        }
        updateProgrammCounter();
	}
	public static void do_IORLW(int icmd) {
		int enc_attr = getNKVar(icmd);
        WRegister = enc_attr | WRegister;
        if(WRegister ==0) {
        	zRegister =1;
        }
        printForDebug() ;
        updateProgrammCounter();
	}
	public static void do_SUBLW(int icmd) {
		int enc_attr = getNKVar(icmd);
		
		set_C_and_DC_Addition(enc_attr,h_do_2Complement(WRegister));
		
        WRegister = (enc_attr + h_do_2Complement(WRegister))%0x100;
        printForDebug() ;
        updateProgrammCounter();
	}
	public static void do_XORLW(int icmd) {
		int enc_attr = getNKVar(icmd);
        WRegister = enc_attr ^ WRegister;
        printForDebug() ;
        if(WRegister ==0) {
        	zRegister =1;
        }
        updateProgrammCounter();
	}
	public static void do_ADD(int icmd) {
		int enc_attr = getNKVar(icmd);
		set_C_and_DC_Addition(enc_attr,WRegister);
        WRegister = enc_attr + WRegister;
        printForDebug() ; 
        updateProgrammCounter();
	}
	
	public static void do_GO_TO(int icmd) {
        int enc_attr = BITMASK_FOR_K_SPECIAL & icmd;
        
        updateProgrammCounter(enc_attr);
	}
	public static void do_NOP() {
        updateProgrammCounter();
	}
	
	public static void do_RETLW(int icmd) {
        int enc_attr = getNKVar(icmd);
        WRegister = enc_attr;
        printForDebug() ;
		do_RETURN();
	}
	
	public static void do_CALL(int icmd) {
        int enc_attr = BITMASK_FOR_K_SPECIAL & icmd;
        
        returnAdress.add(programmCounterLine+1);
        updateProgrammCounter(enc_attr);
	}
	public static void do_RETURN() {
        updateProgrammCounter(returnAdress.poll());//nimmt das letzeingefügte element und löscht es aus der Queue
	}
	
	
	
private static int getFVar(int icmd) {

    	int attr = BITMASK_FOR_F & icmd;
    	return attr;
		
	}

private static int getNKVar(int icmd) {

	int attr = BITMASK_FOR_K_NORMAL & icmd;
	return attr;
	
}
private static int getBVar(int icmd) {

	int attr = BITMASK_FOR_B & icmd;
	
	return attr/(2*2*2*2*2*2*2);
	
}
//////
private static boolean getDVar(int icmd) {

	int attr = BITMASK_FOR_D & icmd;
	if(attr ==0) {
		return false;
	}else {
		return true;
	}
	
	
}

public static void updateProgrammCounter() {
	programmCounterLine++;
}

public static void updateProgrammCounter(int CMDAdress) {
	programmCounterLine = CMDAdress;
}
	
public static int getProgrammCounterLine() {
	return programmCounterLine;
}

public static boolean isProgramEnded() {
	return isProgrammEnded;
}

public static int getWRegister() {
	return WRegister;
}

public static int getFRegister(int adress) {
	if(fRegisters.containsKey(adress)) {
		return (int) fRegisters.get(adress);
	}else {
		return 0;
	}
}

public static void setFRegister(int adress,int value) {
	fRegisters.put(adress, value);
}

//18ms standart: 

public static int getComplement(int number) {
	return 0xFF ^ number;
}

public static void set_C_and_DC_Addition(int num1,int num2) {
	int sol = num1+num2;
	int firstNibbleMask = 0xF;
	if(sol>=0x100) {
		carryFlag =1;
	}else {
		carryFlag =0;
	}
	int nibbleNum1 = firstNibbleMask & num1;
	int nibbleNum2 = firstNibbleMask & num2;
	if(nibbleNum1+nibbleNum2 >= 0x10) {
		digitalCarryFlag = 1;
	}else {
		digitalCarryFlag = 0;
	}
}

public static void setBank0Init() {
	for(int[] defVal: bank0Default) {
		bank0.put(defVal[0], defVal[1]);
	}
}
public static void setBank1Init() {
	for(int[] defVal: bank1Default) {
		bank1.put(defVal[0], defVal[1]);
	}
}


public static void setBank0ForReset() {
	
	int bank0StartVal =0;
	int bank0EndVal =0xB;
	setUBank0();
	
	//U werte werden gespeichert
	for(int i = bank0StartVal; i<=bank0EndVal;i++) {
		if(bank0Us.containsKey(i)) {
			saifeUVar(i,(int)bank0Us.get(i),bank0Saife,bank0);
		}else {
			saifeUVar(i,0,bank0Saife,bank0);
		}
		
		
	}
	
	
	for(int[] defVal: bank0Default) {
		
		bank0.put(defVal[0], defVal[1]);
	}
	
	//u Werte werden geschrieben
	for(int i = bank0StartVal; i<=bank0EndVal;i++) {
		if(bank0.containsKey(i)) {
			bank0.put(i, (int)bank0.get(i) + (int)bank0Saife.get(i));
		}
	}
	
}

public static void setUBank0() {
	for(int i =0;i<uPosBank0.length;i++) {
		bank0Us.put(uPosBank0[i][0],setBitmaskall1(uPosBank0[i][1]));
	}
}

public static void setUBank1() {
	for(int i =0;i<uPosBank1.length;i++) {
		bank1Us.put(uPosBank1[i][0],setBitmaskall1(uPosBank1[i][1]));
	}
}


public static void saifeUVar(int adress, int bitmaskForU, HashMap bankSaife,HashMap bank  ) {
	bankSaife.put(adress, ((int)bank.get(adress) & bitmaskForU));
}



//TODO FABIAN:
public static void setBank1ForReset() {
	
	
	
	int bank1StartVal =80; //stimmt 81?
	int bank1EndVal =80 + 12;
	setUBank1();
	
	//U werte werden gespeichert
	for(int i = bank1StartVal; i<=bank1EndVal;i++) {
		if(bank1Us.containsKey(i)) {
			saifeUVar(i,(int)bank1Us.get(i),bank1Saife,bank1);
		}else {
			saifeUVar(i,0,bank1Saife,bank1);
		}
		
		
	}
	
	
	for(int[] defVal: bank1Default) {
		bank1.put(defVal[0], defVal[1]);
	
	}
	
	for(int i = bank1StartVal; i<=bank1EndVal;i++) {
		if(bank1.containsKey(i)) {
			bank1.put(i, (int)bank1.get(i) + (int)bank1Saife.get(i));
		}
	}
	
}

public static int setBitmaskall1(int numbOfOnes) {
	return (((int)Math.pow(2, numbOfOnes)-1));
}
	
	

 
}