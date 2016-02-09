package villagerWipe.core.util.dynamicswitch;

import java.util.HashMap;
import java.util.Map;

public class ExecuteSwitchStatement implements DynamicSwitchStatementCommand{
	@Override public void execute() {}
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

    public void on(Integer caseId) {
    	DynamicSwitchStatementCommand command = getCaseCommandByCaseId(caseId);

        command.execute();
    }
}