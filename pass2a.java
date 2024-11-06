package pass2;

public class pass2a{
	public static void main(String args[])throws Exception{
		String MOTtable[][] = {{"L","58","4","RX"},{"A","5A","4","RX"},{"ST","50","4","RX"}};
		String POTtable[][] = {{"START","P_START()"},{"DC","P_DC()"},{"DS","P_DS()"},{"END","P_END()"}};
		String SYMtable[][] = {{"01","JOHN","0","4"},{"02","FIVE","116","4"},{"03","FOUR","112","4"},{"04","RESULT","120","4"}};
		String LITtable[][];
		String ICtable[][] = {{"0","$01"},{"100","_"},{"100","L 1, $02"},{"104","A 1, $03"},{"108","ST 1, $04"},{"112","4"},{"116","5"},{"120","_"},{"124","_"}};
		String MCtable[][] = new String[50][50];
		String line;
		
		for(int i=0; i<ICtable.length; i++) {
			line = ICtable[i][1];
			String word[] = line.split(" ");
			MCtable[i][0] = ICtable[i][0];
			for(int j=0; j<word.length; j++) {
				for(int a=0; a<MOTtable.length; a++) {
					if(word[j].equalsIgnoreCase(MOTtable[a][0])) {
						line = line.replace(word[j], MOTtable[a][1]);
						break;
					}
				}
				for(int b=0; b<SYMtable.length; b++) {
					if(word[j].contains("$")) {
						String val = word[j].replace("$","");
						for(int c=0; c<SYMtable.length; c++) {
							if(SYMtable[c][0].equalsIgnoreCase(val)) {
								line = line.replace(word[j], SYMtable[c][2]);
							}
						}
					}
				}
			}
			MCtable[i][1] = line;
		}
		
		//print MC table
		System.out.println("\n\tMC TABLE");
		System.out.println("----------------");
		System.out.println("LC\tMC");
		System.out.println("----------------");
		for(int i=0; i<ICtable.length; i++)
			System.out.println(MCtable[i][0] + "\t" + MCtable[i][1]);
		System.out.println("----------------");
	}

}