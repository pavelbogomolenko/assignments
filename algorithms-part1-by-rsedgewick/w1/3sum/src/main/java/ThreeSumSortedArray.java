package main.java;

public class ThreeSumSortedArray {
    public int count(int[] sortedArray, int sum) {
        int sumCount = 0;
        int N = sortedArray.length;
        for(int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j ++) {
                int twoSum = sortedArray[i] + sortedArray[j];
                int thirdNumber = sum - twoSum;

                int hi = (N - j + 1) - 1;
                int lo = j + 1;
                while(lo <= hi) {
                    int mid = lo + (hi - lo) / 2;
                    if(thirdNumber < sortedArray[mid]) {
                        hi = mid - 1;
                    } else if(thirdNumber > sortedArray[mid]) {
                        lo = mid + 1;
                    } else {
                        sumCount++;
                        break;
                    }
                }
            }
        }
        return sumCount;
    }
}
