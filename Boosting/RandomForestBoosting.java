import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.RandomTree;
import weka.core.AttributeStats;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.SpreadSubsample;


public class RandomForestBoosting {
  

    public static void evaluate(Instances train, Instances test) throws Exception {
        Classifier[] forest = train(train, 151);
        // evaluate and print results on training set
        classifyInstances(train, forest);
        // evaluate and print results on training set
        classifyInstances(test, forest);
    }

    private static Classifier[] train(Instances train, int treesCount) throws Exception {
        Classifier[] classifiers = new Classifier[treesCount];
        
        for(int i=1; i<=treesCount; i++) {
            SpreadSubsample s = new SpreadSubsample();
            AttributeStats status = train.attributeStats(train.classIndex());
            s.setMaxCount(status.nominalCounts[1]/3);
            s.setRandomSeed(i);
            s.setInputFormat(train);
            
            // Subsample the data for each bin
            Instances sample = Filter.useFilter(train, s);
            // Random Tree with half of the features 
            RandomTree tree = new RandomTree();
            tree.setKValue(train.numAttributes()/2);
            tree.setSeed(treesCount-i);
            
            // Boosting applied to the tree
            AdaBoostM1 adaBoost = new AdaBoostM1();
            adaBoost.setNumIterations(100);
            adaBoost.setClassifier(tree);
            adaBoost.buildClassifier(sample);
            
            classifiers[i-1] = adaBoost;
        }
        return classifiers;
    }

    private static void classifyInstances(Instances data, Classifier[] classifiers) throws Exception {   
    	// Predict class using majority vote
    	Vote majorityVote = new Vote();
    	majorityVote.setClassifiers(classifiers);
    	Evaluation eval = new Evaluation(data);
    	eval.evaluateModel(majorityVote ,data);

        System.out.println(eval.toSummaryString());
        System.out.println(eval.toClassDetailsString());
        System.out.println(eval.toMatrixString());
        
    }
}
