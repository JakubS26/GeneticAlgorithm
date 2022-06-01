package base;

public class Path {
    public int size;
    public int[] vertices;

    public Path(int size) {
        this.size = size;
        this.vertices = new int[size];
    }

    public Path(int[] tab) {
        this.size = tab.length;
        this.vertices = tab;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(this.vertices[i] + " -> ");
        }
        System.out.println(this.vertices[0]);
    }
}
