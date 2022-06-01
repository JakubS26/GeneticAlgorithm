package base.tabu;

import base.IndexedPath;
import base.Indices;

public class Tabu {
    private final int maxSize;
    private int index;
    private int size;
    private final boolean isSymmetric;
    private Indices[] tab;

    public Tabu(int maxSize, boolean isSymmetric) {
        this.maxSize = maxSize;
        this.isSymmetric = isSymmetric;
        this.index = 0;
        this.size = 0;
        this.tab = new Indices[maxSize];
    }

    public Tabu(Tabu tabu) {
        this.maxSize = tabu.maxSize;
        this.index = tabu.index;
        this.isSymmetric = tabu.isSymmetric;
        this.size = tabu.size;
        this.tab = new Indices[this.maxSize];
        for (int i = 0; i < tabu.size; i++) {
            this.tab[i] = new Indices(tabu.tab[i]);
        }
    }

    public void insert(Indices ind) {
        this.tab[index] = ind;
        if (this.size < this.maxSize) {
            this.size++;
        }
        this.index = (this.index + 1) % this.maxSize;
    }
    public boolean contains(IndexedPath indexedPath) {
        Indices ind = new Indices(indexedPath.first, indexedPath.second);
        return contains(ind);
    }
    public boolean contains(Indices ind) {
        if (isSymmetric) {
            for (int i = 0; i < this.size; i++) {
                if (equalsSymmetric(this.tab[i], ind)) {
                    return true;
                }
            }
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            if (equals(this.tab[i], ind)) {
                return true;
            }
        }
        return false;
    }

    public boolean equalsSymmetric(Indices a, Indices b) {
        if (a == null || b == null) {
            return false;
        }
        return (a.beg == b.beg && a.end == b.end) || (a.beg == b.end && a.end == b.beg);
    }

    public boolean equals(Indices a, Indices b) {
        if (a == null || b == null) {
            return false;
        }
        return (a.beg == b.beg && a.end == b.end);
    }

}
