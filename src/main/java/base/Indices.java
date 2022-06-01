package base;

public class Indices {
	
	public int beg;
	public int end;
	
	public Indices(int beg, int end) {
		this.beg = beg;
		this.end = end;
	}

	public Indices(Indices indices) {
		this.beg = indices.beg;
		this.end = indices.end;
	}
	
}
