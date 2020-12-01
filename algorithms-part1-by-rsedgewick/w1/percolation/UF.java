public class UF {
    private final int[] id;
    private int count;

    public UF(int n) {
        id = new int[n];
        count = n;
        for(int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        if(p >= id.length) {
            return -1;
        }
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);

        System.out.println("pId: " + pId);
        System.out.println("qId: " + qId);

        if(pId == qId) {
            return;
        }

        for(int i = 0; i < id.length; i++) {
            if(id[i] == pId) {
                id[i] = qId;
            }
        }

        count--;
    }

    public void print() {
        for(int i = 0; i < id.length; i++) {
            System.out.print(id[i]);
        }
        System.out.print("\n");
        System.out.println("--------------");
    }

    public static void main(String[] args) {
        UF uf = new UF(10);
        uf.print();

        uf.union(4, 3);
        uf.print();

        uf.union(4, 5);
        uf.print();

        uf.union(6, 5);
        uf.print();

        uf.union(9, 6);
        uf.print();

        System.out.println(uf.find(9));
        System.out.println("CC num " + uf.count());

    }
}
