package base.neighborhood;

import base.IndexedPath;
import base.Matrix;
import base.Path;

public class InsertHood extends Neighbourhood {
    private int source;
    private int dest;

    /*public InsertHood(Path path) {
        super(path);
        this.source = 0;
        this.dest = this.source + 1;
    }*/

    public InsertHood(Matrix matrix, Path path) {
        super(matrix, path);
        this.source = 0;
        this.dest = this.source + 1;
    }

    @Override
    public IndexedPath getNext() {
        IndexedPath ip = new IndexedPath(this.path, this.source, this.dest, NType.INSERT);
        ip.setLength(getThisLength());
        this.dest++;
        if (this.dest == this.source) {
            this.dest++;
        }
        if (this.dest >= path.size) {
            this.source++;
            this.dest = 0;
            if (this.source >= path.size) {
                this.hasNext = false;
            }
        }
        return ip;
    }

    private int getThisLength() {
        int nextLength = this.baseLength;
        if ((this.source == 0 && this.dest == this.path.size - 1) || (this.dest == 0 && this.source == this.path.size - 1)) {
            return nextLength;
        }
        nextLength -= this.matrix.distances[this.path.vertices[Math.floorMod(this.source - 1, this.path.size)] - 1][this.path.vertices[this.source] - 1];
        nextLength -= this.matrix.distances[this.path.vertices[this.source] - 1][this.path.vertices[Math.floorMod(this.source + 1, this.path.size)] - 1];
        if (this.source <= this.dest) {
            nextLength -= this.matrix.distances[this.path.vertices[this.dest] - 1][this.path.vertices[Math.floorMod(this.dest + 1, this.path.size)] - 1];
            nextLength += this.matrix.distances[this.path.vertices[this.dest] - 1][this.path.vertices[this.source] - 1];
            nextLength += this.matrix.distances[this.path.vertices[this.source] - 1][this.path.vertices[Math.floorMod(this.dest + 1, this.path.size)] - 1];
        } else {
            nextLength -= this.matrix.distances[this.path.vertices[Math.floorMod(this.dest - 1, this.path.size)] - 1][this.path.vertices[this.dest] - 1];
            nextLength += this.matrix.distances[this.path.vertices[this.source] - 1][this.path.vertices[this.dest] - 1];
            nextLength += this.matrix.distances[this.path.vertices[Math.floorMod(this.dest - 1, this.path.size)] - 1][this.path.vertices[this.source] - 1];
        }
        nextLength += this.matrix.distances[this.path.vertices[Math.floorMod(this.source - 1, this.path.size)] - 1][this.path.vertices[Math.floorMod(this.source + 1, this.path.size)] - 1];
        return nextLength;
    }

}
