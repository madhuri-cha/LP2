package pass2;

public class pass2c{
	public static void main(String args[])throws Exception{
		String MOTtable[][] = {{"L","58","4","RX"},{"A","5A","4","RX"},{"ST","50","4","RX"},{"SR","18","2","RR"}};
		String POTtable[][] = {{"START","P_START()"},{"USING","P_USING()"},{"LTORG","P_LTORG"},{"DC","P_DC()"},{"DS","P_DS()"},{"END","P_END()"}};
		String SYMtable[][] = {{"01","SAMPLE","0","4"},{"02","FOUR","119","4"},{"03","RESULT","123","4"},{"04","FIVE","115","4"}};
		String LITtable[][] = {{"01","='3'","104"},{"02","='5'","113"},{"03","='7'","114"}};
		String ICtable[][] = {{"0","$01"},{"100","_"},{"100","_"},{"100","L 1, $02"},{"104","A 1, #01"},{"105","ST 1, $03"},{"109","_"},{"109","$04"},{"113","A 2, #02"},{"114","A 2, #03"},{"115","5"},{"119","4"},{"123","_"}};
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
						String val1 = word[j].replace("$","");
						for(int c=0; c<SYMtable.length; c++) {
							if(SYMtable[c][0].equalsIgnoreCase(val1)) {
								line = line.replace(word[j], SYMtable[c][2]);
							}
						}
					}
				}
				for(int d=0; d<SYMtable.length; d++) {
					if(word[j].contains("#")) {
						String val2 = word[j].replace("#","");
						for(int e=0; e<LITtable.length; e++) {
							if(LITtable[e][0].equalsIgnoreCase(val2)) {
								line = line.replace(word[j], LITtable[e][2]);
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