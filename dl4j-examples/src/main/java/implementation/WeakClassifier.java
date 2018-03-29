package implementation;

import java.util.List;

 /**
 * 你可以根据自己的需要，继承这个类，实现自己的弱分类器，只需要实现两个方法即可
 * @author
 */
public abstract class WeakClassifier{
    public static final int RIGHT = 1;
    public static final int WRONG = 0;
    public double weight;//alpha
    public final double calculateErrorPositive(double[][] inputX, double[] inputY, int[] rightOrWrong){
        double errorTimes = 0;//预测错误的次数
        double pnum = 0;
        for(int i = 0; i < inputX.length; i++){
            if(inputY[i] == 1){
                pnum++;
                int res = predict(inputX[i], inputY[i]);
                if(res == WRONG){
                    errorTimes++;
                }
                rightOrWrong[i] = res;
            }
        }

        return errorTimes / pnum;//正样本错误率
    }

    public final double calculateErrorNegative(double[][] inputX, double[] inputY, int[] rightOrWrong){
        double errorTimes = 0;//预测错误的次数
        double nnum = 0;
        for(int i = 0; i < inputX.length; i++){
            if(inputY[i] == -1){
                nnum++;
                int res = predict(inputX[i], inputY[i]);
                if(res == WRONG){
                    errorTimes++;
                }
                rightOrWrong[i] = res;
            }

        }

        return errorTimes / nnum;//负样本错误率
    }

    public final double calculateError(double[][] inputX, double[] inputY, int[] rightOrWrong){
        double errorTimes = 0;//预测错误的次数
        for(int i = 0; i < inputX.length; i++){
            int res = predict(inputX[i], inputY[i]);
            if(res == WRONG){
                errorTimes++;
            }
            rightOrWrong[i] = res;
        }

        return errorTimes / inputY.length;//错误率
    }
    public final double calculateIR(double[][] inputX, double[] inputY, int[] rightOrWrong,List<double[]> irlist){
        double sumIr = 0;
        for(int i = 0; i < inputX.length; i++){
            if(inputY[i] > 0 && rightOrWrong[i] == WeakClassifier.RIGHT){
                sumIr += irlist.get(i)[0];
            }

            if(inputY[i] < 0 && rightOrWrong[i] == WeakClassifier.WRONG){
                sumIr += irlist.get(i)[0];
            }
        }

        return sumIr;
    }



    //预测正确返回RIGHT,错误返回WRONG
    public final int predict(double[] x, double y){
        double res = predict(x);

        //System.out.println(res);s

        if(res == y){
            return RIGHT;
        }else{
            return WRONG;
        }
    }

    public abstract double predict(double[] x);

    public abstract void train(double[][] inputX, double[] inputY, double[] weights);

}
