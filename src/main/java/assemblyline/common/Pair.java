package assemblyline.common;

public class Pair {
    private final Object left;
    private final Object right;

    public Pair(Object left, Object right) {
        this.left = left;
        this.right = right;
    }

    public Object getKey() {
        return this.left;
    }

    public Object getValue() {
        return this.right;
    }

    public int hashCode() {
        return this.left.hashCode() ^ this.right.hashCode();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair pairo = (Pair)o;
        return this.left.equals(pairo.getKey()) && this.right.equals(pairo.getValue());
    }
}

