package base;

import base.heuristics.NearestNH;
import base.heuristics.Paths;
import base.heuristics.TwoOptAccH;
import base.neighborhood.NType;
import base.reader.InstanceReader;
import base.reader.ProblemInstance;
import base.tabu.TabuSearch;

import java.io.FileNotFoundException;




public class MainProcessor {
    private ProblemInstance instance;
    private Matrix matrix;
    private HType hType;
    private NType primaryN;
    private NType secondaryN;
    private int optimalLength;
    private int iterations;
    private int tabuLength;
    private double promisingThreshold;
    private int maxNoImp;
    private int maxNoImpJump;
    private int maxMem;

    private enum HType {
        NEAREST_N,
        TWO_OPT
    }
    public MainProcessor() {
        this.hType = HType.TWO_OPT;
        this.primaryN = NType.INVERT;
        this.secondaryN = NType.INSERT;
        this.iterations = 10000;
        this.tabuLength = 100;
        this.promisingThreshold = 0.995;
        this.maxNoImp = 200;
        this.maxNoImpJump = 15;
        this.maxMem = 100;
    }

    public int processCommand(String[] command) {
        if (command.length == 0) {
            return 0;
        }
        switch(command[0]) {
            case "load" -> {
                boolean result = loadInstance(command);
                System.out.println(isOk(result));
            }
            case "set" -> {
                boolean result = setParameter(command);
                System.out.println(isOk(result));
            }
            case "run" -> {
                boolean result = run(command);
                System.out.println(isOk(result));
            }
            case "check" -> {
                System.out.println(configAsString());
            }
            case "help" -> {
                System.out.println(helpAsString());
            }
            case "quit" -> {
                return 0;
            }
            default -> {
                System.out.println("Command unknown");
            }
        }
        return 1;
    }
    private String isOk(boolean param) {
        if (param) {
            return "ok";
        } else {
            return "error";
        }
    }
    private boolean isReady() {
        return this.matrix != null && this.hType != null && this.primaryN != null;
    }
    private String helpAsString() {
        return """
                load:
                \tload [path]
                \tload random [tsp/atsp/euclid] [<int>]
                set:
                \tset optimal [<int>]
                \tset start [near/2opt]
                \tset hood1 [invert/swap/insert]
                \tset hood2 [invert/swap/insert]
                \tset iter [<int>]
                \tset tabu [<int>]
                \tset pthreshold [<double>]
                \tset max-no-imp [<int>]
                \tset max-no-imp-jump [<int>]
                \tset max-mem [<int>]
                run
                check
                help
                quit""";
    }
    private String configAsString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Matrix: ");
        if (this.matrix == null) {
            builder.append("null");
            return builder.toString();
        }
        builder.append("size ").append(this.matrix.size);
        if (this.instance != null) {
            builder.append("\n").append("Instance: ").append(this.instance.getName());
        }
        builder.append("\n").append("Start path heuristic: ").append(this.hType);
        builder.append("\n").append("Primary Neighbourhood type: ").append(this.primaryN);
        builder.append("\n").append("Secondary Neighbourhood type: ").append(this.primaryN);
        builder.append("\n").append("Optimal path length: ").append(this.optimalLength);
        builder.append("\n").append("Max number of iterations: ").append(this.iterations);
        builder.append("\n").append("Tabu length: ").append(this.tabuLength);
        builder.append("\n").append("Promising Threshold: ").append(this.promisingThreshold);
        builder.append("\n").append("Max Iter w/o improvement: ").append(this.maxNoImp);
        builder.append("\n").append("Max Jump w/o improvement: ").append(this.maxNoImpJump);
        builder.append("\n").append("Max Mem Size: ").append(this.maxMem);
        return builder.toString();
    }
    private boolean loadInstance(String[] command) {
        if (command.length < 2) {
            return false;
        }
        if ("random".equals(command[1])) {
            return loadRandom(command);
        }
        try {
            this.instance = new ProblemInstance(command[1]);
            this.matrix = InstanceReader.read(this.instance);
            return true;
        } catch (IllegalArgumentException | FileNotFoundException e) {
            return false;
        }

    }
    private boolean loadRandom(String[] command) {
        if (command.length < 4) {
            return false;
        }
        int size;
        try {
            size = Integer.parseInt(command[3]);
        } catch (NumberFormatException e) {
            return false;
        }
        switch(command[2]) {
            case "tsp" -> {
                this.matrix = Matrices.randomSymmetricTSP(size);
                this.instance = null;
                return true;
            }
            case "atsp" -> {
                this.matrix = Matrices.randomATSP(size);
                this.instance = null;
                return true;
            }
            case "euclid" -> {
                this.matrix = Matrices.randomEUC_2D(size);
                this.instance = null;
                return true;
            }
            default -> {
                return false;
            }
        }
    }
    private boolean setParameter(String[] command) {
        if (command.length < 2) {
            return false;
        }
        switch (command[1]) {
            case "optimal" -> {
                return setOptimal(command);
            }
            case "start" -> {
                return setHeuristic(command);
            }
            case "hood1" -> {
                return setPNeighbourhood(command);
            }
            case "hood2" -> {
                return setSNeighbourhood(command);
            }
            case "iter" -> {
                return setIterations(command);
            }
            case "tabu" -> {
                return setTabuLength(command);
            }
            case "pthreshold" -> {
                return setPromisingThreshold(command);
            }
            case "max-no-imp" -> {
                return setMaxNoImp(command);
            }
            case "max-no-imp-jump" -> {
                return setMaxNoImpJump(command);
            }
            case "max-mem" -> {
                return setMaxMem(command);
            }
            default -> {
                return false;
            }
        }
    }

    private boolean setMaxMem(String[] command) {
        if (command.length < 3) {
            return false;
        }
        try {
            this.maxMem = Integer.parseInt(command[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean setMaxNoImpJump(String[] command) {
        if (command.length < 3) {
            return false;
        }
        try {
            this.maxNoImpJump = Integer.parseInt(command[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean setMaxNoImp(String[] command) {
        if (command.length < 3) {
            return false;
        }
        try {
            this.maxNoImp = Integer.parseInt(command[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean setTabuLength(String[] command) {
        if (command.length < 3) {
            return false;
        }
        try {
            this.tabuLength = Integer.parseInt(command[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean setIterations(String[] command) {
        if (command.length < 3) {
            return false;
        }
        try {
            this.iterations = Integer.parseInt(command[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean setPromisingThreshold(String[] command) {
        if (command.length < 3) {
            return false;
        }
        try {
            this.promisingThreshold = Double.parseDouble(command[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean setOptimal(String[] command) {
        if (command.length < 3) {
            return false;
        }
        try {
            this.optimalLength = Integer.parseInt(command[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean setHeuristic(String[] command) {
        if (command.length < 3) {
            return false;
        }
        switch (command[2]) {
            case "near" -> {
                this.hType = HType.NEAREST_N;
                return true;
            }
            case "2opt" -> {
                this.hType = HType.TWO_OPT;
                return true;
            }
            default -> {
                return false;
            }
        }
    }
    private boolean setPNeighbourhood(String[] command) {
        if (command.length < 3) {
            return false;
        }
        switch (command[2]) {
            case "invert" -> {
                this.primaryN = NType.INVERT;
                return true;
            }
            case "swap" -> {
                this.primaryN = NType.SWAP;
                return true;
            }
            case "insert" -> {
                this.primaryN = NType.INSERT;
                return true;
            }
            default -> {
                return false;
            }
        }
    }
    private boolean setSNeighbourhood(String[] command) {
        if (command.length < 3) {
            return false;
        }
        switch (command[2]) {
            case "invert" -> {
                this.secondaryN = NType.INVERT;
                return true;
            }
            case "swap" -> {
                this.secondaryN = NType.SWAP;
                return true;
            }
            case "insert" -> {
                this.secondaryN = NType.INSERT;
                return true;
            }
            default -> {
                return false;
            }
        }
    }
    private boolean run(String[] command) {
        if (!isReady()) {
            return false;
        }
        Path startPath = getStartPath(this.hType, this.matrix);
        TabuSearch tabuSearch = new TabuSearch(this.matrix, this.tabuLength, this.iterations,
                this.promisingThreshold, this.maxNoImp, this.maxNoImpJump, this.maxMem, this.primaryN, this.secondaryN);
        long time = System.currentTimeMillis();
        Path tabuPath = tabuSearch.solve(startPath);
        time = System.currentTimeMillis() - time;
        System.out.println("distance initial path: " + this.matrix.objectiveFunction(startPath));
        System.out.println("distance tabu path: " + this.matrix.objectiveFunction(tabuPath));
        System.out.println("time: " + time);
        if (this.optimalLength != 0) {
            System.out.println("PRD: " + this.matrix.PRD(tabuPath, this.optimalLength));
        }
        if (this.matrix instanceof MatrixEuclid) {
            PathVisualFrame frame = new PathVisualFrame((MatrixEuclid) this.matrix, startPath, "Initial");
            PathVisualFrame frame1 = new PathVisualFrame((MatrixEuclid) this.matrix, tabuPath, "TabuSearch");
        }
        return true;
    }
    private Path getStartPath(HType mode, Matrix matrix) {
        switch (mode) {
            case TWO_OPT -> {
                Path path = new NearestNH(matrix).solve(Paths.ascPath(matrix));
                return new TwoOptAccH(matrix).solve(path);
            }
            case NEAREST_N -> {
                return new NearestNH(matrix).solve(Paths.ascPath(matrix));
            }
            default -> {
                return null;
            }
        }
    }

}
