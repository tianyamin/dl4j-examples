package implementation;

class ThreadHoldWeakClassifier extends WeakClassifier{         //阀值分类器
    public double mThreadHold;
    public double mBias;
    private static final int maxBias = 20;
    private static final int minBias = -maxBias;
    private static double lastThreadHold = -10000;
    private static double lastBias = -10000;
    @Override
    public double predict(double[] x) {
        if(mThreadHold * x[0] + mBias >= 0){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return "bias=" + mBias + " threadHold=" + mThreadHold;
    }

    @Override
    public void train(double[][] inputX, double[] inputY, double[] weights) {
        double max = 0, min = 0;
        double step = 0.1;
        // find max min
        for(int i = 0; i < inputX.length; i++){
            double val = inputX[i][0];
            if(val > max){
                max = val;
            }
            if(val < min){
                min = val;
            }
        }
        //test threadHold  测试阀值
        if(lastThreadHold == -10000){
            lastThreadHold = min + step;
            lastBias = minBias;
        }else{
            if(lastBias == -10000){
                lastBias = minBias;
            }else{
                lastBias += 0.1;
                if(lastBias > maxBias){
                    lastBias = minBias;
                    lastThreadHold += step;
                }
            }

        }
        mThreadHold = lastThreadHold;
        mBias = lastBias;
        //System.out.println("bias = " + mBias + " threashHold = " + mThreadHold);
    }

}
