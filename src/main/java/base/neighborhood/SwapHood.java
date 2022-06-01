package base.neighborhood;

import base.IndexedPath;
import base.Matrix;
import base.Path;
import base.utils.Utils;

import java.util.Arrays;

public class SwapHood extends Neighbourhood {
    private int first;
    private int second;

    /*public SwapHood(Path path) {
        super(path);
        this.first = 0;
        this.second = this.first + 1;
    }*/

    public SwapHood(Matrix matrix, Path path) {
        super(matrix, path);
        this.first = 0;
        this.second = this.first + 1;
    }

    @Override
    public IndexedPath getNext() {
        IndexedPath ip = new IndexedPath(this.path, this.first, this.second, NType.SWAP);
        ip.setLength(getThisLength());
        this.second++;
        if (this.second >= path.size) {
            this.first++;
            this.second = this.first + 1;
            if (this.first >= path.size - 1) {
                this.hasNext = false;
            }
        }
        return ip;
    }

    @Override
    public boolean hasNext() {
        return super.hasNext();
    }

    private int getThisLength() {
        int nextLength = this.baseLength;
        nextLength -= this.matrix.distances[this.path.vertices[Math.floorMod(this.first - 1, this.path.size)] - 1][this.path.vertices[this.first] - 1];
        nextLength -= this.matrix.distances[this.path.vertices[this.first] - 1][this.path.vertices[Math.floorMod(this.first + 1, this.path.size)] - 1];
        if (this.first + 1 != this.second) {
            nextLength -= this.matrix.distances[this.path.vertices[Math.floorMod(this.second - 1, this.path.size)] - 1][this.path.vertices[this.second] - 1];
        }
        if (Math.floorMod(this.second + 1, this.path.size) != this.first) {
            nextLength -= this.matrix.distances[this.path.vertices[this.second] - 1][this.path.vertices[Math.floorMod(this.second + 1, this.path.size)] - 1];
        }
        if (Math.floorMod(this.first - 1, this.path.size) != this.second) {
            nextLength += this.matrix.distances[this.path.vertices[Math.floorMod(this.first - 1, this.path.size)] - 1][this.path.vertices[this.second] - 1];
        } else {
            nextLength += this.matrix.distances[this.path.vertices[this.first] - 1][this.path.vertices[this.second] - 1];
        }
        if (this.first + 1 != this.second) {
            nextLength += this.matrix.distances[this.path.vertices[this.second] - 1][this.path.vertices[Math.floorMod(this.first + 1, this.path.size)] - 1];
            nextLength += this.matrix.distances[this.path.vertices[Math.floorMod(this.second - 1, this.path.size)] - 1][this.path.vertices[this.first] - 1];
        } else {
            nextLength += this.matrix.distances[this.path.vertices[this.second] - 1][this.path.vertices[this.first] - 1];
        }
        if (Math.floorMod(this.second + 1, this.path.size) != this.first) {
            nextLength += this.matrix.distances[this.path.vertices[this.first] - 1][this.path.vertices[Math.floorMod(this.second + 1, this.path.size)] - 1];
        }
        return nextLength;
    }
}
