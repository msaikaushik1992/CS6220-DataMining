import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Random;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.AttributeStats;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RemoveUseless;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;


public class RFBoostingMain {
	public static void main(String[] args) throws Exception {
        
		// modify the class values to 1(re-admitted) and 0(not re-admitted)
        modifyClasses();

        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("processedicd9.csv"));
        Instances data = loader.getDataSet();
        
        // convert numeric attributes to nominal (discrete) attribute
        NumericToNominal nm = new NumericToNominal();
        nm.setInputFormat(data);
        nm.setAttributeIndicesArray(new int[]{6, 7, 8, 49});
        data = Filter.useFilter(data, nm);
        
        // remove unnecessary attributes
        Remove r = new Remove();
        r.setAttributeIndicesArray(new int[]{0, 1, 5, 10});
        r.setInputFormat(data);
        data = Filter.useFilter(data, r);
        
        // replace all the missing values with means and modes
        ReplaceMissingValues rm = new ReplaceMissingValues();
        rm.setInputFormat(data);
        data = Filter.useFilter(data, rm);
        
        // remove the attributes that may not affect the classification
        RemoveUseless ru = new RemoveUseless();
        ru.setInputFormat(data);
        ru.setMaximumVariancePercentageAllowed(75);
        data = Filter.useFilter(data, ru);
        //data = selectAttributes(data);

        // specify which attribute holds the classification value
        data.setClassIndex(data.numAttributes() - 1);
        
        //System.out.println(data.classIndex());

        // Sanity Check - no of instances per class in the data
        classStatistics(data);

        // check how random forests works for the given data
        evaluateForest(data);
        //evaluateSVM(data);
    }
    
    public static Instances selectAttributes(Instances data)
    {
    	AttributeSelection filter = new AttributeSelection();
    	Instances filteredIns = null;
    	// Evaluator
    	final CfsSubsetEval evaluator = new CfsSubsetEval();
    	evaluator.setMissingSeparate(true);
    	// Assign evaluator to filter
    	filter.setEvaluator(evaluator);
    	// Search strategy: best first (default values)
    	final GreedyStepwise search = new GreedyStepwise();
    	filter.setSearch(search);
    	// Apply filter
    	try {
    		filter.setInputFormat(data);

    		filteredIns = Filter.useFilter(data, filter);
    		//int[] indexes = filter.selectedAttributes();
            //System.out.println("Selected Features: "+Utils.arrayToString(indexes));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return filteredIns;

    }

	private static void modifyClasses() throws Exception {
        CSVReader reader = new CSVReader(new FileReader("data"+ File.separator +"diabetic_data.csv"), ',');
        List<String[]> csvBody = reader.readAll();
        boolean header = true;
        for (String[] r : csvBody) {
            if (header) {
                header = false;
                continue;
            }
           
            if (r[49].equals("<30")) {
                r[49] = "1";
            } else {
                r[49] = "0";
            }
        }
        converttoicd9(csvBody);
        CSVWriter writer = new CSVWriter(new FileWriter("processedicd9.csv"), ',');
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
        reader.close();
    }
    
    private static void converttoicd9(List<String[]> csv){
    	boolean header = true;
    	for (String[] r : csv) {
    		if (header) {
                header = false;
                continue;
            }
    		if(r[18].startsWith("?")){
    			
    		}
    		else if(r[18].startsWith("E") || r[18].startsWith("V")){
    			r[18] = "Other";
    		}
    		else{
    			// converting diag_1 column acc to icd9 codes
                if(Double.parseDouble(r[18]) < 140 && Double.parseDouble(r[18]) >= 1){
                	r[18] = "Neoplasms";
                }
                else if(Double.parseDouble(r[18]) < 240 && Double.parseDouble(r[18]) >= 140){
                	r[18] = "Neoplasms";
                }
                else if((Double.parseDouble(r[18]) < 280 && Double.parseDouble(r[18]) >= 240) && (!r[18].startsWith("250"))){
                	r[18] = "Neoplasms";
                }
                else if(r[18].startsWith("250")){
                	r[18] = "Diabetes";
                }
                else if(Double.parseDouble(r[18]) < 290 && Double.parseDouble(r[18]) >= 280){
                	r[18] = "Other";
                }
                else if(Double.parseDouble(r[18]) < 320 && Double.parseDouble(r[18]) >= 290){
                	r[18] = "Other";
                }
                else if(Double.parseDouble(r[18]) < 360 && Double.parseDouble(r[18]) >= 320){
                	r[18] = "Other";
                }
                else if(Double.parseDouble(r[18]) < 390 && Double.parseDouble(r[18]) >= 360){
                	r[18] = "Other";
                }
                else if((Double.parseDouble(r[18]) < 460 && Double.parseDouble(r[18]) >= 390) || Double.parseDouble(r[18]) == 785) {
                	r[18] = "Circulatory";
                }
                else if((Double.parseDouble(r[18]) < 520 && Double.parseDouble(r[18]) >= 460) || Double.parseDouble(r[18]) == 786) {
                	r[18] = "Circulatory";
                }
                else if((Double.parseDouble(r[18]) < 580 && Double.parseDouble(r[18]) >= 520) || Double.parseDouble(r[18]) == 787) {
                	r[18] = "Circulatory";
                }
                else if((Double.parseDouble(r[18]) < 630 && Double.parseDouble(r[18]) >= 580) || Double.parseDouble(r[18]) == 788) {
                	r[18] = "Genitourinary";
                }
                else if(Double.parseDouble(r[18]) < 680 && Double.parseDouble(r[18]) >= 630){
                	r[18] = "Other";
                }
                else if((Double.parseDouble(r[18]) < 710 && Double.parseDouble(r[18]) >= 680) || Double.parseDouble(r[18]) == 782) {
                	r[18] = "Neoplasms";
                }
                else if(Double.parseDouble(r[18]) < 740 && Double.parseDouble(r[18]) >= 710){
                	r[18] = "Musculoskeletal";
                }
                else if(Double.parseDouble(r[18]) < 760 && Double.parseDouble(r[18]) >= 740){
                	r[18] = "Other";
                }
                else if(Double.parseDouble(r[18]) == 780 || Double.parseDouble(r[18]) == 781 || Double.parseDouble(r[18]) == 783 || Double.parseDouble(r[18]) == 784 || Double.parseDouble(r[18]) == 789){
                	r[18] = "Neoplasms";
                }
                else if(Double.parseDouble(r[18]) < 800 && Double.parseDouble(r[18]) >= 790){
                	r[18] = "Neoplasms";
                }
                else if(Double.parseDouble(r[18]) < 1000 && Double.parseDouble(r[18]) >= 800){
                	r[18] = "Neoplasms";
                }
    		}
            // converting columns diag_2 according to their icd9 codes.
    		if(r[19].startsWith("?")){
    			
    		}
    		else if(r[19].startsWith("E") || r[19].startsWith("V")){
    			r[19] = "Other";
    		}
    		else{
                if(Double.parseDouble(r[19]) < 140 && Double.parseDouble(r[19]) >= 1){
                	r[19] = "Neoplasms";
                }
                else if(Double.parseDouble(r[19]) < 240 && Double.parseDouble(r[19]) >= 140){
                	r[19] = "Neoplasms";
                }
                else if((Double.parseDouble(r[19]) < 280 && Double.parseDouble(r[19]) >= 240) && (!r[19].startsWith("250"))){
                	r[19] = "Neoplasms";
                }
                else if(r[19].startsWith("250")){
                	r[19] = "Diabetes";
                }
                else if(Double.parseDouble(r[19]) < 290 && Double.parseDouble(r[19]) >= 280){
                	r[19] = "Other";
                }
                else if(r[19].startsWith("E")){
                	r[19] = "Other";
                }
                else if(r[19].startsWith("V")){
                	r[19] = "Other";
                }
                else if(Double.parseDouble(r[19]) < 320 && Double.parseDouble(r[19]) >= 290){
                	r[19] = "Other";
                }
                else if(Double.parseDouble(r[19]) < 360 && Double.parseDouble(r[19]) >= 320){
                	r[19] = "Other";
                }
                else if(Double.parseDouble(r[19]) < 390 && Double.parseDouble(r[19]) >= 360){
                	r[19] = "Other";
                }
                else if((Double.parseDouble(r[19]) < 460 && Double.parseDouble(r[19]) >= 390) || Double.parseDouble(r[19]) == 785) {
                	r[19] = "Circulatory";
                }
                else if((Double.parseDouble(r[19]) < 520 && Double.parseDouble(r[19]) >= 460) || Double.parseDouble(r[19]) == 786) {
                	r[19] = "Circulatory";
                }
                else if((Double.parseDouble(r[19]) < 580 && Double.parseDouble(r[19]) >= 520) || Double.parseDouble(r[19]) == 787) {
                	r[19] = "Circulatory";
                }
                else if((Double.parseDouble(r[19]) < 630 && Double.parseDouble(r[19]) >= 580) || Double.parseDouble(r[19]) == 788) {
                	r[19] = "Genitourinary";
                }
                else if(Double.parseDouble(r[19]) < 680 && Double.parseDouble(r[19]) >= 630){
                	r[19] = "Other";
                }
                else if((Double.parseDouble(r[19]) < 710 && Double.parseDouble(r[19]) >= 680) || Double.parseDouble(r[19]) == 782) {
                	r[19] = "Neoplasms";
                }
                else if(Double.parseDouble(r[19]) < 740 && Double.parseDouble(r[19]) >= 710){
                	r[19] = "Musculoskeletal";
                }
                else if(Double.parseDouble(r[19]) < 760 && Double.parseDouble(r[19]) >= 740){
                	r[19] = "Other";
                }
                else if(Double.parseDouble(r[19]) == 780 || Double.parseDouble(r[19]) == 781 || Double.parseDouble(r[19]) == 783 || Double.parseDouble(r[19]) == 784 || Double.parseDouble(r[19]) == 789){
                	r[19] = "Neoplasms";
                }
                else if(Double.parseDouble(r[19]) < 800 && Double.parseDouble(r[19]) >= 790){
                	r[19] = "Neoplasms";
                }
                else if(Double.parseDouble(r[19]) < 1000 && Double.parseDouble(r[19]) >= 800){
                	r[19] = "Neoplasms";
                } 
    		}
    		// converting diag_3 column acc to icd9 codes
    		if(r[20].startsWith("?")){
    			
    		}
    		else if(r[20].startsWith("E") || r[20].startsWith("V")){
    			r[20] = "Other";
    		}
    		else{
                if(Double.parseDouble(r[20]) < 140 && Double.parseDouble(r[20]) >= 1){
                	r[20] = "Neoplasms";
                }
                else if(Double.parseDouble(r[20]) < 240 && Double.parseDouble(r[20]) >= 140){
                	r[20] = "Neoplasms";
                }
                else if((Double.parseDouble(r[20]) < 280 && Double.parseDouble(r[20]) >= 240) && (!r[20].startsWith("250"))){
                	r[20] = "Neoplasms";
                }
                else if(r[20].startsWith("250")){
                	r[20] = "Diabetes";
                	
                }
                else if(Double.parseDouble(r[20]) < 290 && Double.parseDouble(r[20]) >= 280){
                	r[20] = "Other";
                }
                else if(r[20].startsWith("E")){
                	r[20] = "Other";
                }
                else if(r[20].startsWith("V")){
                	r[20] = "Other";
                }
                else if(Double.parseDouble(r[20]) < 320 && Double.parseDouble(r[20]) >= 290){
                	r[20] = "Other";
                }
                else if(Double.parseDouble(r[20]) < 360 && Double.parseDouble(r[20]) >= 320){
                	r[20] = "Other";
                }
                else if(Double.parseDouble(r[20]) < 390 && Double.parseDouble(r[20]) >= 360){
                	r[20] = "Other";
                }
                else if((Double.parseDouble(r[20]) < 460 && Double.parseDouble(r[20]) >= 390) || Double.parseDouble(r[20]) == 785) {
                	r[20] = "Circulatory";
                }
                else if((Double.parseDouble(r[20]) < 520 && Double.parseDouble(r[20]) >= 460) || Double.parseDouble(r[20]) == 786) {
                	r[20] = "Circulatory";
                }
                else if((Double.parseDouble(r[20]) < 580 && Double.parseDouble(r[20]) >= 520) || Double.parseDouble(r[20]) == 787) {
                	r[20] = "Circulatory";
                }
                else if((Double.parseDouble(r[20]) < 630 && Double.parseDouble(r[20]) >= 580) || Double.parseDouble(r[20]) == 788) {
                	r[20] = "Genitourinary";
                }
                else if(Double.parseDouble(r[20]) < 680 && Double.parseDouble(r[20]) >= 630){
                	r[20] = "Other";
                }
                else if((Double.parseDouble(r[20]) < 710 && Double.parseDouble(r[20]) >= 680) || Double.parseDouble(r[20]) == 782) {
                	r[20] = "Neoplasms";
                }
                else if(Double.parseDouble(r[20]) < 740 && Double.parseDouble(r[20]) >= 710){
                	r[20] = "Musculoskeletal";
                }
                else if(Double.parseDouble(r[20]) < 760 && Double.parseDouble(r[20]) >= 740){
                	r[20] = "Other";
                }
                else if(Double.parseDouble(r[20]) == 780 || Double.parseDouble(r[20]) == 781 || Double.parseDouble(r[20]) == 783 || Double.parseDouble(r[20]) == 784 || Double.parseDouble(r[20]) == 789){
                	r[20] = "Neoplasms";
                }
                else if(Double.parseDouble(r[20]) < 800 && Double.parseDouble(r[20]) >= 790){
                	r[20] = "Neoplasms";
                }
                else if(Double.parseDouble(r[20]) < 1000 && Double.parseDouble(r[20]) >= 800){
                	r[20] = "Neoplasms";
                }
    		}
    	}// end of loop    
    }

    public static void classStatistics(Instances data) {
        AttributeStats status = data.attributeStats(data.classIndex());
        System.out.println("total number of class 0 (NO and >30) instances: " + status.nominalCounts[0]);
        System.out.println("total number of class 1 (<30) instances: " + status.nominalCounts[1]);
    }

    private static void evaluateForest(Instances data) throws Exception {
        
        // shuffle data
        Instances randData = new Instances(data);
        randData.randomize(new Random(56));
        
        // split 80% for training and rest for testing
        int trainSize = (int) Math.round(randData.numInstances() * 0.8);
        int testSize = randData.numInstances() - trainSize;
        Instances train = new Instances(randData, 0, trainSize);
        Instances test = new Instances(randData, trainSize, testSize);
        RandomForestBoosting.evaluate(train, test);
           
    }
}
