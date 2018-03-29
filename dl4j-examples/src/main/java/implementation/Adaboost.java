package implementation;

import java.util.ArrayList;
import java.util.List;

public class Adaboost{
    private double[][] mInputX = null;//样本
    private double[] mInputY = null;//样本标签
    private double[] mWeights = null;//样本权重
    private int mSampleNum = -1;
    private List<WeakClassifier> mWeakClassifierSet = new ArrayList<WeakClassifier>();


    public Adaboost(){}

    public Adaboost(double[][] X, double[] Y){
        setInput(X, Y);//构造函数，初始化训练样本，和标签1，-1
    }


    public Adaboost(double[][] input){
       if(input == null || input.length == 0){
            new RuntimeException("no input data,please check !");
        }
        final int cols = input[0].length - 1;
        double[][] X = new double[input.length][cols];
        double[] Y = new double[input.length];
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[i].length; j++){
                if(j < input[i].length -1){
                    X[i][j] = input[i][j];
                }else{
                    Y[i] = input[i][j];
                }
            }

        }
        setInput(X, Y);//构造函数，初始化训练样本，和标签1，-1
    }
    public void setInput(double[][] X, double[] Y){
        if(X == null || Y == null){
            throw new RuntimeException(
                "input X or input Y can not be null, please check!");
        }

        if(X.length != Y.length){
            throw new RuntimeException(
                "input X or input Y belongs to different dimension, please check!");
        }

        mInputX = X;
        mInputY = Y;
        mSampleNum = mInputX.length;
        mWeights = new double[mSampleNum];
    }

    private void initWeights(){
        for(int i = 0; i < mSampleNum; i++){
            mWeights[i] = 1.0 / mSampleNum;//初始化权重。
        }
    }

    public double predict(double[] x){//按照adaboost方法组合多个弱分类器
        double res = 0;
        if(mWeakClassifierSet.size() == 0){
            throw new RuntimeException(
                "no weak classifiers !!");
        }
        for(int i = 0; i < mWeakClassifierSet.size(); i++){
            res += mWeakClassifierSet.get(i).weight *
                mWeakClassifierSet.get(i).predict(x);
        }
        return res;
    }

    private void updateWeights(int[] rightOrWrong, double alpha){
        //更新样本权重，被分错的样本总是具有很大的权重，读者可自行根据权重来特殊训练这些容易被分错的样本
        double Z = 0;
        for(int i = 0; i < rightOrWrong.length; i++){
            if(rightOrWrong[i] == WeakClassifier.RIGHT){
                mWeights[i] *= Math.exp(-alpha);
            }else if(rightOrWrong[i] == WeakClassifier.WRONG){
                mWeights[i] *= Math.exp(alpha);
            }else{
                throw new RuntimeException(
                    "unknown right or wrong flag, please check!");
            }

            Z += mWeights[i];
        }

        //权重归一化
        for(int i = 0; i < rightOrWrong.length; i++){
            mWeights[i] /= Z;
        }
    }



    //这个方法是核心，也就是寻找合格的弱分类器，并保存在一个List中
    public void trainWeakClassifiers(int epoch,List<double[]> irlist){
        if(epoch <= 1){
            throw new RuntimeException(
                "training epoch must be greater than 1, please check!");
        }

        System.out.println("start training......");

        initWeights();//初始化样本权重

        for(int i = 0; i < epoch; i++){
            WeakClassifier weakClassifier = new ThreadHoldWeakClassifier();
            weakClassifier.train(mInputX, mInputY, mWeights);
            int[] rightOrWrong = new int[mSampleNum];// 1 right, 0 wrong
            double errorP = weakClassifier.calculateErrorPositive(mInputX, mInputY,rightOrWrong);//计算正样本错误率
            double errorN = weakClassifier.calculateErrorNegative(mInputX, mInputY,rightOrWrong);//计算负样本错误率
            double error = weakClassifier.calculateError(mInputX, mInputY,rightOrWrong);//计算总体错误率
            double sumIr = weakClassifier.calculateIR(mInputX, mInputY,rightOrWrong,irlist);//计算利润率
            //System.out.println("perror = " + errorP + " nerror = " + errorN + " error = " + error);
            if(errorP > 0.5 || errorN > 0.5){
                continue;//不满足错误率的分类器就抛弃
            }

            //if(sumIr <=0){
            // continue;
            //}
            //System.out.println("perror = " + errorP + " nerror = " + errorN);

            double alpha = Math.log((1 - error) / error) / 2;
            weakClassifier.weight = alpha;//保存弱分类器的权重
            updateWeights(rightOrWrong, alpha);
            mWeakClassifierSet.add(weakClassifier);
            System.out.println("epoch " + i +
                " got one weak classifier, heh " + weakClassifier.toString() + " error=" + error + " ir=" + sumIr);
        }
        System.out.println("train finish!!  " + mWeakClassifierSet.size() + " weak classifier(s) was trained !!");
        //     for(int i = 0; i < mWeakClassifierSet.size(); i++){
        //          ThreadHoldWeakClassifier twc = (ThreadHoldWeakClassifier) mWeakClassifierSet.get(i);
        //          //System.out.println("thread hold = " + twc.mThreadHold);
        //      }
    }
}
