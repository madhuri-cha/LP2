package pass1;

import java.io.*;

public class Pass1 {
	public static void main(String[] args) throws Exception {
		FileReader FR = new FileReader("D:\\Aadi\\projects\\java\\pass\\sourcecode3.txt"); // Add the file path of your
																							// source code
		LineNumberReader BR = new LineNumberReader(FR);
		String line = null;

		String MOTtable[][] = new String[10][4];
		String POTtable[][] = new String[10][2];
		String SYMtable[][] = new String[10][4];
		String LITtable[][] = new String[10][4];
		String PLtable[] = new String[10];
		String IC[] = new String[50];
		int LC[] = new int[50];
		int MOTptr = 0, POTptr = 0, SYMptr = 0, LITptr = 0, PTptr = 0, LCptr = 0, LCvalue = 0;
		String minemonic[][] = { { "L", "58", "4", "RX", "0" }, { "A", "5A", "4", "RX", "0" },
				{ "ST", "50", "4", "RX", "0" }, { "SR", "18", "2", "RR", "0" }, { "MOVER", "04", "1", "IS", "0" },
				{ "PRINT", "10", "1", "IS", "0" }, { "ADD", "01", "1", "IS", "0" }, { "SUB", "02", "1", "IS", "0" },
				{ "COMP", "06", "1", "IS", "0" }, { "MULT", "03", "1", "IS", "0" }, { "MOVEM", "05", "1", "IS", "0" } };

		// print Source code
		System.out.println("\tSOURCE CODE");
		while ((line = BR.readLine()) != null) {
			System.out.println(line);
			String[] word = line.split(" ");
			for (int i = 0; i < word.length; i++) {
				for (int a = 0; a < minemonic.length; a++) {
					if (minemonic[a][0].equals(word[i]) && minemonic[a][4].equalsIgnoreCase("0")) {
						MOTtable[MOTptr][0] = word[i];
						MOTtable[MOTptr][1] = minemonic[a][1];
						MOTtable[MOTptr][2] = minemonic[a][2];
						MOTtable[MOTptr][3] = minemonic[a][3];
						minemonic[a][4] = "1";
						MOTptr++;
					}
				}
				if (word[i].equalsIgnoreCase("START") || word[i].equalsIgnoreCase("END")
						|| word[i].equalsIgnoreCase("DS") || word[i].equalsIgnoreCase("DC")
						|| word[i].equalsIgnoreCase("USING") || word[i].equalsIgnoreCase("ORIGIN")
						|| word[i].equalsIgnoreCase("LTORG")) {
					int flag = 0;
					if (word[i].equalsIgnoreCase("START") || word[i].equalsIgnoreCase("ORIGIN")) {
						LCvalue = Integer.parseInt(line.replace(word[i], "").replace(" ", ""));
					}
					for (int b = 0; b <= POTptr; b++) {
						if (word[i].equalsIgnoreCase(POTtable[b][0])) {
							flag = 1;
							break;
						}
					}
					if (flag != 1) {
						POTtable[POTptr][0] = word[i];
						POTtable[POTptr][1] = "P_" + word[i] + "()";
						POTptr++;
						if (!word[i].equalsIgnoreCase("DC") && !word[i].equalsIgnoreCase("DS")) {
							LC[LCptr] = LCvalue;
							IC[LCptr] = "_";
							LCptr++;
						}
					}
				} else if (word[i].contains("='")) {
					LITtable[LITptr][0] = 0 + Integer.toString(LITptr + 1);
					LITtable[LITptr][1] = word[i];
					LITtable[LITptr][2] = Integer.toString(LCvalue);
					LITtable[LITptr][3] = word[i - 1].replace("REG,", "");
					LC[LCptr] = LCvalue;
					IC[LCptr] = line.replace(word[i], "#" + LITtable[LITptr][0]).replace("REG", "");
					LITptr++;
					LCptr++;
					LCvalue += 1;
				} else if (!word[i].contains("'") && !word[i].contains(",") && !word[i].matches(".*[0-9]")
						&& !word[i].contains("EQU") && !word[i].contains("1F")) {
					int flag = 0;
					for (int d1 = 0; d1 <= POTptr; d1++) {
						if (word[i].equalsIgnoreCase(POTtable[d1][0])) {
							flag = 1;
							break;
						}
					}
					for (int d2 = 0; d2 < minemonic.length; d2++) {
						if (word[i].equals(minemonic[d2][0])) {
							flag = 1;
							break;
						}
					}
					for (int d3 = 0; d3 <= SYMptr; d3++) {
						if (word[i].equalsIgnoreCase(SYMtable[d3][1])) {
							SYMtable[d3][2] = Integer.toString(LCvalue);
							flag = 1;
							break;
						}
					}
					if (flag != 1) {
						if (word[i].contains(":")) {
							word[i] = word[i].replace(":", "");
						}
						SYMtable[SYMptr][0] = 0 + Integer.toString(SYMptr + 1);
						SYMtable[SYMptr][1] = word[i];
						SYMtable[SYMptr][2] = Integer.toString(LCvalue);
						SYMtable[SYMptr][3] = minemonic[i][2];
						LC[LCptr] = LCvalue;
						IC[LCptr] = line.replace(word[i], "$" + SYMtable[SYMptr][0]).replace("REG", "");
						SYMptr++;
						LCptr++;
						LCvalue += Integer.parseInt(minemonic[i][2]);
					}
				} else if (word[i].contains("F'")) {
					IC[LCptr] = word[i].replace("F", "").replace("'", "");
					LC[LCptr] = LCvalue;
					LCptr++;
					LCvalue += Integer.parseInt(minemonic[i][2]);
				}
				if (word[i].matches(".*[A-Z]") && word[i].length() == 1) {
					for (int e = 0; e <= LITptr - 1; e++) {
						if (word[i].equalsIgnoreCase(LITtable[e][3])) {
							PLtable[PTptr] = "#" + LITtable[e][0];
							LITtable[e][3] = " ";
							PTptr++;
							break;
						}
					}
				}
			}
		}

		// print POT table
		System.out.println("\n\t POT TABLE");
		System.out.println("------------------");
		System.out.println("Pseudo\nCode\tAddress");
		System.out.println("------------------");
		for (int i = 0; i < POTptr; i++)
			System.out.println(POTtable[i][0] + "\t" + POTtable[i][1]);
		System.out.println("------------------");

		// print MOT table
		System.out.println("\n\t MOT TABLE");
		System.out.println("------------------------------");
		System.out.println("Mine-\nmonic\tHexcode\tLength\tFormat");
		System.out.println("------------------------------");
		for (int i = 0; i < MOTptr; i++)
			System.out.println(MOTtable[i][0] + "\t" + MOTtable[i][1] + "\t" + MOTtable[i][2] + "\t" + MOTtable[i][3]);
		System.out.println("------------------------------");

		// print Symbol table
		System.out.println("\n\t SYMBOL TABLE");
		System.out.println("------------------------------");
		System.out.println("OPcode\tName\tAddress\tlength");
		System.out.println("------------------------------");
		for (int i = 0; i < SYMptr; i++)
			System.out.println(SYMtable[i][0] + "\t" + SYMtable[i][1] + "\t" + SYMtable[i][2] + "\t" + SYMtable[i][3]);
		System.out.println("------------------------------");

		// print Literal table
		System.out.println("\n\t LITERAL TABLE");
		System.out.println("------------------------");
		System.out.println("OPcode\tName\tAddress");
		System.out.println("------------------------");
		for (int i = 0; i < LITptr; i++)
			System.out.println(LITtable[i][0] + "\t" + LITtable[i][1] + "\t" + LITtable[i][2]);
		System.out.println("------------------------");

		// print Pool Table
		System.out.println("\nPOOL TABLE");
		System.out.println("----");
		for (int i = 0; i < PTptr; i++)
			System.out.print(PLtable[i] + "\n");
		System.out.println("\n----");

		// print LC
		System.out.println("\n\tIC TABLE");
		System.out.println("-----------------------");
		System.out.println("LC\tIC");
		System.out.println("-----------------------");
		for (int i = 0; i < LCptr; i++)
			System.out.print(LC[i] + "\t" + IC[i] + "\n");
		System.out.println("\n-----------------------");
	}
}