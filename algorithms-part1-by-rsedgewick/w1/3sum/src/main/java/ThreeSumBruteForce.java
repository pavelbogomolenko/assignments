package main.java;

public class ThreeSumBruteForce {
    public int count(int[] a, int sum) {
        int sumCount = 0;
        int N = a.length;
        for(int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j ++) {
                for(int k = j + 1; k < N; k ++) {
                    if(a[i] + a[j] + a[k] == sum) {
                        sumCount++;
                    }
                }
            }
        }
        return sumCount;
    }
}
