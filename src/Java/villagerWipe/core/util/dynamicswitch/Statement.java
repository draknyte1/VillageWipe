package villagerWipe.core.util.dynamicswitch;

import villagerWipe.core.util.Utils;

public class Statement {

	public Statement(int loadedCount) {
		SwitchSize = loadedCount;
	}

	private Switcher switcher = new Switcher();
	private int ID = 0;
	private int SwitchSize = 0;
	private String Name;
	private Class Cl = null;

/*	public void addCase(final int IDx, final String Namex, final Class ClassName){
		
		switcher.addCaseCommand(IDx, new DynamicSwitchStatementCommand() {

			@Override
			public String execute(String S) {
				if (S.equals("class")){
					Utils.LOG_INFO("Adding "+Namex+" as selected Entity.");
					Cl = ClassName;
				}
				else if (S.equals("name")){
					//return null;
				}
				else if (S.equals("id")){
					//return null;
				}
				else {
					//return null;
				}
				return null;				
			}			

		});
	}*/

	public void addCase(final int IDx, final String Namex, final Class ClassName){
		switcher.addCaseCommand(IDx, new DynamicSwitchStatementCommand() {
			@Override
			public String execute(String S) {
				Utils.LOG_INFO("Scanning "+Namex+" as an Entity.");
				return ClassName.getName();
			}

			@Override
			public int checkID(String S) {
				return IDx;
			}

			@Override
			public String checkName(Integer integer) {
				return Namex;
			}
		});
	}

	private int checkSwitchStatements(String S){
		System.out.println("Checking statements");
		String Z = "";
		int localID = 0;
		int i = 1;
		while (!Z.toLowerCase().equals(S.toLowerCase()) && i <= SwitchSize){
			for (i=1; i<=SwitchSize; i++) {
				//Z = switcher.on(i, "name").toLowerCase();
				Z = switcher.checkName(i).toLowerCase();
				System.out.println("Found "+Z+" whilst searching for "+S);
				if (Z.equals(S)){
					System.out.println("Found the ID of the list entry for "+S+". It is ID:"+i);
					localID = i;
					break;
				}
			}
			if (Z.equals(S)){
				System.out.println("Lookup Entity "+Z+" found whilst searching for "+S);
				break;
			}
		}

		return localID;

	}

	public String executeSwitch(String S){
		System.out.println("Running Switch");
		int ENTITY_ID = checkSwitchStatements(S);
		return switcher.on(ENTITY_ID, S);
	}
}	



/* public void dynamicSwitch(int IDx, String Namex) {
addCase(IDx, Namex, Cl);

switcher.addCaseCommand(1, new DynamicSwitchStatementCommand() {
    @Override
    public void execute() {
        System.out.println("Command on {id: 1}");
    }
});
switcher.addCaseCommand(2, new DynamicSwitchStatementCommand() {
    @Override
    public void execute() {
        System.out.println("Command on {id: 2}");
    }
});
switcher.addCaseCommand(3, new DynamicSwitchStatementCommand() {
    @Override
    public void execute() {
        System.out.println("Command on {id: 3}");
    }
});
switcher.setDefaultCaseCommand(new DynamicSwitchStatementCommand() {
    @Override
    public void execute() {
        System.out.println("Command on {default}");
    }
});
}*/