package base.neighborhood;

import base.Matrix;
import base.Path;
import base.IndexedPath;
import base.utils.Utils;

public class TwoOptNeighbourhood extends Neighbourhood {
	private int beg;
	private int end;

	public TwoOptNeighbourhood(Matrix matrix, Path path) {
		super(matrix, path);
		this.beg = 0;
		this.end = this.beg + 1;
	}

	@Override
	public IndexedPath getNext() {
		IndexedPath ip = new IndexedPath(this.path, this.beg, this.end, NType.INVERT);
		if (this.matrix.isSymmetric) {
			ip.setLength(getSymmetricLength());
		} else {
			Path newPath = new Path(this.path.vertices.clone());
			Utils.invert(newPath.vertices, this.beg, this.end);
			ip.setLength(this.matrix.objectiveFunction(newPath));
			ip.setPath(newPath);
		}
		this.end++;
		if (this.end >= this.path.size) {
			this.beg++;
			this.end = this.beg + 1;
			if (this.beg >= this.path.size - 1) {
				this.hasNext = false;
			}
		}
		return ip;
	}

	private int getSymmetricLength() {
		int nextLength = this.baseLength;
		if (this.beg == 0 && this.end == this.path.size - 1) {
			return nextLength;
		}
		nextLength -= this.matrix.distances[this.path.vertices[Math.floorMod(this.beg - 1, this.path.size)] - 1][this.path.vertices[this.beg] - 1];
		nextLength -= this.matrix.distances[this.path.vertices[this.end] - 1][this.path.vertices[Math.floorMod(this.end + 1, this.path.size)] - 1];
		nextLength += this.matrix.distances[this.path.vertices[Math.floorMod(this.beg - 1, this.path.size)] - 1][this.path.vertices[this.end] - 1];
		nextLength += this.matrix.distances[this.path.vertices[this.beg] - 1][this.path.vertices[Math.floorMod(this.end + 1, this.path.size)] - 1];
		return nextLength;
	}
}
