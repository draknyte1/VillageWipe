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

	public void addCase(final int IDx, final String Namex, final Class ClassName){
		switcher.addCaseCommand(IDx, new DynamicSwitchStatementCommand() {
			@Override
			public void execute() {
				Utils.LOG_INFO("Adding "+Namex+" as selected Entity.");
				Cl = ClassName;
			}

			@Override
			public String checkName() {
				return Namex;				
			}

			@Override
			public int checkID() {
				return IDx;
			}
		});
	}

	private int checkSwitchStatements(String S){
		System.out.println("Checking statements");
		String Z = "";
		int localID = 0;
		while (!Z.equals(S)){
			for (int i = 1; i <= SwitchSize; i++) {
				Z = switcher.checkName(i);
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

	public void executeSwitch(String S){
		System.out.println("Running Switch");
		int ENTITY_ID = checkSwitchStatements(S);
		switcher.on(ENTITY_ID);
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