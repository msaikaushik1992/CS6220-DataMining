import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

// uses RandomForest for classification
public class EnsembleDecisionTrees {

    public void evaluateForest(Instances train, Instances test) throws Exception {
        RandomForest r = getForest();

        //int featureCount = findNoOfFeaturesUsingCV(train);

        /* When the no.of features in random tree in no.of attributes /2.
        Best performance was observed. This is found from CV in above statement.
        But above statement is commented to speedup th execution time. Comment
        the below statement and un comment the above statement if you want to
        derive the same result using CV. But the CV will take lot of time to complete.
         */
        int featureCount = train.numAttributes()/2;
        r.setNumFeatures(featureCount);
        r.buildClassifier(train);

        System.out.println();
        System.out.println("---------------Training Statistics----------------");
        System.out.println();
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(r, train);
        System.out.println(eval.toMatrixString("Confusion Matrix - Random Forest"));
        System.out.println(eval.toClassDetailsString(""));
        System.out.println(eval.toSummaryString());

        System.out.println();
        System.out.println("---------------Testing Statistics----------------");
        System.out.println();
        Evaluation eval1 = new Evaluation(test);
        eval1.evaluateModel(r, test);
        System.out.println(eval1.toMatrixString("Confusion Matrix - Random Forest"));
        System.out.println(eval1.toClassDetailsString(""));
        System.out.println(eval1.toSummaryString());

    }

    // finds the number of features to be used in the forest using cross validation
    private int findNoOfFeaturesUsingCV(Instances data) throws Exception {
        int folds = 10;
        int bestCount = 0;

        // CV to find the number of features to use for each trees in Random Forest
        for(int i=2; i<=data.numAttributes(); i++) {
            double prevKappa = 0;
            double kappa = 0;
            for (int n = 0; n < folds; n++) {
                Instances train = data.trainCV(folds, n);
                Instances test = data.testCV(folds, n);
                RandomForest r = getForest();
                r.setNumFeatures(i);
                r.buildClassifier(train);
                Evaluation eval = new Evaluation(train);
                eval.evaluateModel(r, test);
                kappa += eval.kappa();
            }
            if(prevKappa < (kappa / folds)) {
                prevKappa = kappa / folds;
                bestCount = i;
            }
            else {
                break;
            }
        }

        return  bestCount;
    }

    private RandomForest getForest() {
        RandomForest r = new RandomForest();

        // default no.of trees is 100. Set it to 51 to reduce the processing time.
        r.setNumIterations(51);

        /* default is 100 percent. It might reuse the same set of data in all trees and
            produce the same result. Hence reduce the bag size to 75% of given data.
            This will lead to each bag having different set of data.
         */
        r.setBagSizePercent(75);
        return r;
    }

}
