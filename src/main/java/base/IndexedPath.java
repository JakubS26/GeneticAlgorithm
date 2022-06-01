package base;

import base.neighborhood.NType;
import base.utils.Utils;

import java.util.Arrays;

public class IndexedPath {
	
	private Path path;
	public int first;
	public int second;
	private int length;
	private NType type;
	private boolean pathSet;

	/*public IndexedPath(Path path, int first, int second) {
		
		this.path = path;
		this.first = first;
		this.second = second;
		this.length = -1;

	}*/

	public IndexedPath(Path path, int first, int second, NType type) {

		this.path = path;
		this.first = first;
		this.second = second;
		this.type = type;
		this.length = -1;

		this.pathSet = false;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return this.length;
	}

	public void setPath(Path path) {
		this.path = path;
		this.pathSet = true;
	}

	public Path getPath() {
		if (this.pathSet) {
			return this.path;
		}
		switch (this.type) {
			case INVERT -> {
				int[] nPath = Arrays.copyOf(this.path.vertices, this.path.size);
				Utils.invert(nPath, this.first, this.second);
				this.path = new Path(nPath);
				this.pathSet = true;
			}
			case SWAP -> {
				int[] nPath = Arrays.copyOf(this.path.vertices, this.path.size);
				Utils.swap(nPath, this.first, this.second);
				this.path = new Path(nPath);
				this.pathSet = true;
			}
			case INSERT -> {
				int[] nPath = Arrays.copyOf(this.path.vertices, this.path.size);
				Utils.insert(nPath, this.first, this.second);
				this.path = new Path(nPath);
				this.pathSet = true;
			}
		}
		return this.path;
	}

}
