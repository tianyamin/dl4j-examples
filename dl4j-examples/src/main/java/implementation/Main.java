package implementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaboost的一个实现框架，你可以根据自己的需要，训练自己的弱分类器
 *
 */
public class Main {
    /***
     *
     dsaadsadsadsadsadsadsa
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int epoch = 110000;//训练次数
        //初始化训练样本,最后一列为标签
        FileReader fr = new FileReader(new File("/home/mm/www/2/input.txt"));
        BufferedReader bfr = new BufferedReader(fr);
        String linestr = null;
        boolean flag = true;

        List<double[]> trainData = new ArrayList<double[]>();

        ///的撒阿斯顿的撒
        List<double[]> trainRate = new ArrayList<double[]>();
        List<double[]> testData = new ArrayList<double[]>();
        List<double[]> testRate = new ArrayList<double[]>();
        while((linestr = bfr.readLine()) != null){
            if(linestr.startsWith("test")){
                flag = false;
                continue;
            }

            //dsasasadsa你的撒的 的撒的撒
            String[] s = linestr.split(",");


            double[] darr = new double[2];

            darr[0] = Double.valueOf(s[1]);
            darr[1] = Double.valueOf(s[2]);

            double[] rarr = new double[1];
            rarr[0] = Double.valueOf(s[3]);

            if(flag){
                //读入输入数据
                trainData.add(darr);
                trainRate.add(rarr);
            }else{
                //读入测试数据
                testData.add(darr);

                testRate.add(rarr);
            }

        }
        bfr.close();
        final int trainRows = trainData.size();
        final int testRows = testData.size();
        double[][] X = new double[trainRows][trainData.get(0).length];
        double[][] testInput = new double[testRows][testData.get(0).length];
        for(int i = 0; i < trainRows; i++){
            X[i][0] = trainData.get(i)[0];
            X[i][1] = trainData.get(i)[1];

        }
        for(int i = 0; i < testRows; i++){
            testInput[i][0] = testData.get(i)[0];
            testInput[i][1] = testData.get(i)[1];
        }
        trainData.clear();
        testData.clear();


        if(testInput == null || testInput.length == 0){
            new RuntimeException("no input data, please check !");
        }
        final int cols = testInput[0].length - 1;
        double testX[][] = new double[testInput.length][cols];
        double testY[] = new double[testInput.length];
        for(int i = 0; i < testInput.length; i++){
            for(int j = 0; j < testInput[i].length; j++){
                if(j < testInput[i].length -1){
                    testX[i][j] = testInput[i][j];
                }else{
                    testY[i] = testInput[i][j];
                }
            }

        }


        Adaboost adaboost = new Adaboost(X);
        adaboost.trainWeakClassifiers(epoch, trainRate);
        double testErrorTimes = 0;
        double total = 0;
        double testErrorTimes1 = 0;
        double total1 = 0;
        double ir = 0;
        double ir1 = 0;
        for(int i = 0; i < testX.length; i++){
            double res = adaboost.predict(testX[i]);
            /******************正做*******************/
            if(testY[i] > 0 && res >= 0){
                ir += testRate.get(i)[0];
                System.out.println("正做:" + testRate.get(i)[0]);
                total++;
            }

            if(testY[i] < 0 && res >= 0){
                ir += testRate.get(i)[0];
                System.out.println("正做:" + testRate.get(i)[0]);
                testErrorTimes++;
                total++;
            }
            /******************反做*******************/
            if(testY[i] > 0 && res < 0){
                ir1 += testRate.get(i)[0];
                System.out.println("反做:" + testRate.get(i)[0]);
                testErrorTimes1++;
                total1++;
            }

            if(testY[i] < 0 && res < 0){
                ir1 += testRate.get(i)[0];
                System.out.println("反做:" + testRate.get(i)[0]);
                total1++;
            }

            System.out.println("在测试数据上的IR=" + ir + "    IR1=" + ir1);
            System.out.println();
        }
        System.out.println();
        System.out.println("在测试数据上的IR=" + ir + " error=" + (testErrorTimes/total*100));
        System.out.println("在测试数据上的IR1=" + ir1 + " error=" + (testErrorTimes1/total1*100));



    }
}
