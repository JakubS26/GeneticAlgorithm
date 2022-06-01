package base.reader;

import base.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

public class ProblemInstance extends File {
    private String type;
    private String edgeWeight;
    private String format;
    private int dimension;

    public ProblemInstance(String pathname) throws FileNotFoundException, NumberFormatException {
        super(pathname);
        setAttributes();
    }

    public ProblemInstance(String parent, String child) throws FileNotFoundException, NumberFormatException {
        super(parent, child);
        setAttributes();
    }

    public ProblemInstance(File parent, String child) throws FileNotFoundException, NumberFormatException {
        super(parent, child);
        setAttributes();
    }

    public ProblemInstance(URI uri) throws FileNotFoundException, NumberFormatException {
        super(uri);
        setAttributes();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEdgeWeight() {
        return edgeWeight;
    }

    public void setEdgeWeight(String edgeWeight) {
        this.edgeWeight = edgeWeight;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    private void setAttributes() throws FileNotFoundException, NumberFormatException {
        this.setType(Utils.findKey(this, "TYPE"));
        this.setDimension(Integer.parseInt(Utils.findKey(this, "DIMENSION")));
        this.setEdgeWeight(Utils.findKey(this, "EDGE_WEIGHT_TYPE"));
        if (getEdgeWeight().startsWith("EXPLICIT")) {
            this.setFormat(Utils.findKey(this, "EDGE_WEIGHT_FORMAT"));
        }
    }
}
