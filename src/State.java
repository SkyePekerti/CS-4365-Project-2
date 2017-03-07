import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by TerrencePark on 3/6/17.
 */
public class State {

    ArrayList<Variable> vars;
    ArrayList<Constraint> cons;
    HashMap<Character, Integer> selected;
    ArrayList<Variable> solvedVars;
    boolean useCEP;
    int iteration;

    public State() {
        this(new ArrayList<>(), new ArrayList<>(), false);
    }

    public State(ArrayList<Variable> vars, ArrayList<Constraint> cons, boolean useCEP) {
        this.vars = vars;
        this.cons = cons;
        this.useCEP = useCEP;
        selected = new HashMap<>();
        solvedVars = new ArrayList<>();
        iteration = 1;
    }

    public State copyOf() {
        State copy = new State();
        for (Variable v : vars) {
            copy.vars.add(v.copyOf());
        }
        copy.cons = cons;
        copy.selected.putAll(selected);
        copy.useCEP = useCEP;
        return copy;
    }

    public boolean isSolved() {
        for (Constraint c : cons) {
            if (!selected.containsKey(c.var1) || !selected.containsKey(c.var2)) {
                return false;
            }
            int val1 = selected.get(c.var1);
            int val2 = selected.get(c.var2);
            if (!c.valid(val1, val2)) {
                return false;
            }
        }
        return true;
    }

    public void selectNextVar() {
        Collections.sort(vars);
    }

    public int[] getOrderedVals() {
        Variable nextVar = vars.get(0);
        if (useCEP) {
            int[][] valPairs = new int[nextVar.values.size()][2];
            for (int i = 0; i < valPairs.length; i++) {
                valPairs[i][0] = nextVar.values.get(i);
                valPairs[i][1] = getAffectedValues(nextVar, valPairs[i][0]);
            }
            int[] orderedVals = new int[nextVar.values.size()];
            for (int i = 0; i < valPairs.length; i++) {
                orderedVals[i] = valPairs[i][0];
            }
            return orderedVals;
        } else {
            int[] orderedVals = new int[nextVar.values.size()];
            for (int i = 0; i < orderedVals.length; i++) {
                orderedVals[i] = nextVar.values.get(i);
            }
            return orderedVals;
        }
    }

    private int getAffectedValues(Variable nextVar, int nextVal) {
        int numAffected = 0;
        for (Constraint c : cons) {
            if (c.var1 == nextVar.var) {
                for (Variable v : vars) {
                    if (c.var2 == v.var) {
                        for (int val : v.values) {
                            if (c.valid(nextVal, val)) {
                                numAffected++;
                            }
                        }
                        break;
                    }
                }
            } else if (c.var2 == nextVar.var) {
                for (Variable v : vars) {
                    if (c.var1 == v.var) {
                        for (int val : v.values) {
                            if (c.valid(val, nextVal)) {
                                numAffected++;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return numAffected;
    }

    public void setVar(int val) {
        //add val to selected and vars.get(0) to solvedVars
        //apply affected values (similar to getAffectedValues but actually remove values)
    }

    public boolean consistent() {
        for (Constraint c : cons) {
            int val1 = selected.get(c.var1);
            int val2 = selected.get(c.var2);
            if (!c.valid(val1, val2)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(iteration).append(". ");
        boolean first = true;
        for (Variable v : solvedVars) {
            if (!first) {
                sb.append(", ");
                first = false;
            }
            sb.append(v.var).append("=").append(selected.get(v.var));
        }
        sb.append("  ");
        if (isSolved()) {
            sb.append("solution");
        } else {
            sb.append("failure");
        }
        return sb.toString();
    }
}
