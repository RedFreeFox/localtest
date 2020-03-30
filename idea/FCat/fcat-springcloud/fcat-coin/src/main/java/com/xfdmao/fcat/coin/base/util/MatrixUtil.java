package com.xfdmao.fcat.coin.base.util;

import com.xfdmao.fcat.coin.base.entity.Matrix;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cissa on 2019/7/27.
 */
public class MatrixUtil {
    /**
     * 将二维数组转换为java的List对象
     * @param mns
     * @return
     */
    public static List<Matrix> dealMatrix(double[][] mns){
        List<Matrix> matrixList = new ArrayList<>();
        for(int i=0;i<mns.length;i++)
        {
            for(int j=0;j<mns[0].length;j++)
            {
                double incomeRate = mns[i][j];
                 Matrix matrix = new Matrix();
                 matrix.setN(j+1);
                 matrix.setM(i+1);
                 matrix.setValue(incomeRate);
                 matrixList.add(matrix);
            }
        }
        Collections.sort(matrixList, new Comparator<Matrix>() {
            @Override
            public int compare(Matrix o1, Matrix o2) {
                if(o1.getValue()-o2.getValue()>0){
                    return -1;
                }else if(o1.getValue()-o2.getValue()==0){
                    return 0;
                }else{
                    return 1;
                }
            }
        });
        for(int i=0;i<matrixList.size();i++){
            Matrix matrix = matrixList.get(i);
            matrix.setIndex(i+1);
        }
        return matrixList;
    }

    /**
     * 输出二维数组
     * @param mns
     */
    public static void print(double[][] mns) {
        System.out.print(String.format("%-5s\t",""));
        for(int i=0;i<mns.length;i++){
            System.out.print(String.format("%-5d\t",i+1));
        }
        System.out.println();
        for(int i=0;i<mns.length;i++)
        {
            for(int j=0;j<mns[0].length;j++)
            {
                double value = mns[i][j];
                String show = new BigDecimal(value).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_DOWN).toString();
                if(show.equals("0.00")){
                    show = "";
                }
                if(j==0) System.out.print(String.format("%-5d\t",i+1));
                System.out.print(String.format("%-5s\t",show));
            }
            System.out.println();
        }
    }
    /**
     *获取矩阵中最大的值
     * @param mns
     * @return
     */
    public static Matrix queryMaxMatrix(double[][] mns) {
        Matrix matrix = new Matrix();
        double maxIncomeRate = 0;
        int m = 0;
        int n = 0;
        for(int i=0;i<mns.length;i++)
        {
            for(int j=0;j<mns[0].length;j++)
            {
                double incomeRate = mns[i][j];
                if(incomeRate>maxIncomeRate){
                    maxIncomeRate = incomeRate;
                    m = i;
                    n = j;
                }
            }
        }
        Map<String,Integer> result = new HashMap<>();
        result.put("m",m);
        result.put("n",n);
        matrix.setM(m);
        matrix.setN(n);
        matrix.setValue(maxIncomeRate);
        return matrix;
    }

    public static void dealTop20(boolean[][] mns, List<Matrix> matrixList) {
        for(int i=0;i<mns.length;i++)
        {
            for(int j=0;j<mns[0].length;j++)
            {
                mns[i][j] = false;
            }
        }
        for(int i=0;i<20;i++){
            Matrix matrix = matrixList.get(i);
            mns[matrix.getM()-1][matrix.getN()-1] = true;
        }
    }
}
