import weka.core.AttributeStats;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

import java.util.Random;

/**
 * Main function for data
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Instances data = PreProcessor.preprocess();
        System.out.println("----------For the Given Data Set----------");
        classStatistics(data);

        // shuffle data
        Instances randData = new Instances(data);
        randData.randomize(new Random(1));


        // split 80% for training and rest for testing
        int trainSize = (int) Math.round(randData.numInstances() * 0.8);
        int testSize = randData.numInstances() - trainSize;
        Instances train = new Instances(randData, 0, trainSize);
        Instances test = new Instances(randData, trainSize, testSize);

        // resample the data to increase the number of instances of 1
        Resample sample = new Resample();
        sample.setRandomSeed(0);
        sample.setInputFormat(train);
        sample.setBiasToUniformClass(0.8);
        train = Filter.useFilter(train, sample);

        System.out.println();
        System.out.println("----------Resampled training split----------");
        classStatistics(train);

        System.out.println();
        System.out.println("----------Testing split----------");
        classStatistics(test);

        EnsembleDecisionTrees forest = new EnsembleDecisionTrees();

        // check how random forests works for the given data
        forest.evaluateForest(train, test);
    }

    // prints the statistics no. of instances with 0 class and 1 class
    public static void classStatistics(Instances data) {
        AttributeStats status = data.attributeStats(data.classIndex());
        System.out.println("Instances with 0 class = " + status.nominalCounts[0]);
        System.out.println("Instances with 1 class = " + status.nominalCounts[1]);
    }
}

