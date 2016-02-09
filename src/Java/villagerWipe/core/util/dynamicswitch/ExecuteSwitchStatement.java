package villagerWipe.core.util.dynamicswitch;

import java.util.HashMap;
import java.util.Map;

public class ExecuteSwitchStatement implements DynamicSwitchStatementCommand{

	@Override	public String execute(String S) {return null;}
	@Override	public int checkID(String S) {return 0;}
	@Override	public String checkName(Integer integer) {return null;}


}

class Switcher {

	private Map<Integer, DynamicSwitchStatementCommand> caseCommands;

	private DynamicSwitchStatementCommand defaultCommand;

	private DynamicSwitchStatementCommand getCaseCommandByCaseId(Integer caseId) {
		if (caseCommands.containsKey(caseId)) {
			return caseCommands.get(caseId);
		} else {
			return defaultCommand;
		}
	}

	public Switcher() {
		caseCommands = new HashMap<Integer, DynamicSwitchStatementCommand>();

		setDefaultCaseCommand(new ExecuteSwitchStatement());
	}

	public void addCaseCommand(Integer caseId, DynamicSwitchStatementCommand caseCommand) {
		caseCommands.put(caseId, caseCommand);
	}

	public void setDefaultCaseCommand(DynamicSwitchStatementCommand defaultCommand) {
		if (defaultCommand != null) {
			this.defaultCommand = defaultCommand;
		}
	}

	public String on(Integer caseId, String S) {
		DynamicSwitchStatementCommand command = getCaseCommandByCaseId(caseId);
		return command.execute(S);
	}

	public String checkID(String caseId) {
		DynamicSwitchStatementCommand command = getCaseCommandByCaseId(Integer.getInteger(caseId));
		int S = command.checkID(caseId);
		String T = String.valueOf(S);
		return T;
	}
	
	public String checkName(int caseId) {
		DynamicSwitchStatementCommand command = getCaseCommandByCaseId(caseId);
		String X = command.checkName(caseId);
		return X;
	}
}