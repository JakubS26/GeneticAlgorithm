package base;

public class Path {
    public int size;
    public int[] vertices;

    public static final int lengthNotSet = -1;
    private int pathLen;

    public Path(int size) {
        this.size = size;
        this.vertices = new int[size];
        this.pathLen = Path.lengthNotSet;
    }

    public Path(int[] tab) {
        this.size = tab.length;
        this.vertices = tab;
        this.pathLen = Path.lengthNotSet;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(this.vertices[i] + " -> ");
        }
        System.out.println(this.vertices[0]);
    }

    public void setPathLen(int pathLen) {
        this.pathLen = pathLen;
    }

    public int getPathLen() {
        return pathLen;
    }
}
