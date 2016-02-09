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

	public void addCase(int IDx, final String Namex, final Class ClassName){
		switcher.addCaseCommand(IDx, new DynamicSwitchStatementCommand() {
			@Override
			public void execute() {
				Utils.LOG_INFO("Adding "+Namex+" to Loaded Entity list.");
				Cl = ClassName;
			}
		});
	}

	public void executeSwitch(){
		for (int i = 1; i <= SwitchSize; i++) {
			switcher.on(i);
		}
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