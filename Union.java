package sample;

public class Union {
    public static int find(int[] a, int id) {
        if(a[id]<0) {
            return a[id];
        }

        if(a[id] == id ) {
            return id;
        }
        else return find(a,a[id]);
    }
    public static void union(int[] a, int p, int q) { //a = array p = 1st id, q = 2nd id
        a[find(a,q)] = find(a,p);

    }

}
