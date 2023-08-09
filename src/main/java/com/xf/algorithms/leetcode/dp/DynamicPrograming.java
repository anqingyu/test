package com.xf.algorithms.leetcode.dp;

import java.util.Arrays;
import java.util.Scanner;

/**
 *   动态规划（英语：Dynamic programming，DP）是一种在数学、计算机科学和经济学中使用的，通过把原问题分解为相对简单的子问题的方式求解复杂问题的方法。
 * 动态规划常常适用于有重叠子问题和最优子结构性质的问题，动态规划方法所耗时间往往远少于朴素解法。
 */
public class DynamicPrograming {

    /**
     * 1049. 最后一块石头的重量 II:
     * 有一堆石头，用整数数组 stones 表示。其中 stones[i] 表示第 i 块石头的重量。
     *
     * 每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
     *
     * 如果 x == y，那么两块石头都会被完全粉碎；
     * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
     * 最后，最多只会剩下一块 石头。返回此石头 最小的可能重量 。如果没有石头剩下，就返回 0。
     *
     * @param stones
     * @return
     */
    public static int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int weight : stones) {
            sum += weight;
        }
        int m = sum / 2;
        boolean[] dp = new boolean[m + 1];
        dp[0] = true;
        for (int weight : stones) {
            for (int j = m; j >= weight; --j) {
                dp[j] = dp[j] || dp[j - weight];
            }
        }
        for (int j = m;; --j) {
            if (dp[j]) {
                return sum - 2 * j;
            }
        }
    }

    public static void main(String[] args) {
        // 最后一块石头的重量 II
        int[] stones = new int[]{2,7,4,1,8,1};
        System.out.println(lastStoneWeightII(stones));
        int[] stones2 = new int[]{31,26,33,21,40};
        System.out.println(lastStoneWeightII(stones2));

        test();
    }

    /**
     * 01背包
     */
    public static void test(){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();//n件物品
        int m = scan.nextInt();//背包最大容量
        int[] W = new int[n+1];//储存物品所占空间
        int[] V = new int[n+1];//物品价值

        /*
        用i表示装前i件物品，j表示背包容量
         */
        int dp[][] = new int[n+1][m+1];//状态转移

        for(int i = 1 ; i<=n ; i++){
            W[i] = scan.nextInt();
            V[i] = scan.nextInt();
        }

        for(int i = 1 ; i<=n ; i++){//从选择前1件物品遍历
            for(int j = 1 ; j<=m ; j++){//从容量为1时遍历
                /*
                 因为可能出现第i件物品容量要大于背包容量的问题,此时背包装不下第i件物品
                 装不下就是不选,直接继承上一个状态
                 */
                if(j-W[i]>=0){
                    dp[i][j] = Math.max(dp[i-1][j-W[i]]+V[i],dp[i-1][j]);
                }else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
//        调试结果是否与推测相同
        for(int i = 0 ; i<=n ; i++){
            System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println(dp[n][m]);
    }

}
