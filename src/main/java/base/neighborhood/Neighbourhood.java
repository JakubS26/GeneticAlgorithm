package base.neighborhood;

import base.IndexedPath;
import base.Matrix;
import base.Path;

public class Neighbourhood {
    protected Path path;
    protected boolean hasNext;
    protected Matrix matrix;
    protected int baseLength;

    /*public Neighbourhood(Path path) {
        this.path = path;
        this.hasNext = true;
    }*/
    public Neighbourhood(Matrix matrix, Path path) {
        this.matrix = matrix;
        this.path = path;
        this.baseLength = this.matrix.objectiveFunction(path);
        this.hasNext = true;
    }

    public IndexedPath getNext() {
        return null;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public Path getPath() {
        return this.path;
    }

    public int getBaseLength() {
        return this.baseLength;
    }
}
