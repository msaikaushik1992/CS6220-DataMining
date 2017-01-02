import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RemoveUseless;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by prashr on 13/12/16.
 */
public class PreProcessor {

    public static Instances preprocess() throws Exception {
        // modify the class values to 1(re-admitted) and 0(not re-admitted)
        modifyClasses();

        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("processed.csv"));
        Instances data = loader.getDataSet();

        // convert numeric attributes to nominal (discrete) attribute
        NumericToNominal nm = new NumericToNominal();
        nm.setInputFormat(data);
        nm.setAttributeIndicesArray(new int[]{6, 7, 8, 49});
        data = Filter.useFilter(data, nm);

        // remove string attributes
        Remove r = new Remove();

        r.setAttributeIndicesArray(new int[]{Constants.ENCOUNTER_ID,
                Constants.PATIENT_NBR,
                Constants.WEIGHT,
                Constants.PAYER_CODE
        });

        r.setInputFormat(data);
        data = Filter.useFilter(data, r);


        // replace all the missing values
        ReplaceMissingValues rm = new ReplaceMissingValues();
        rm.setInputFormat(data);
        data = Filter.useFilter(data, rm);

        // remove the attributes that may not affect the classification
        RemoveUseless ru = new RemoveUseless();
        ru.setInputFormat(data);
        ru.setMaximumVariancePercentageAllowed(75);
        //data = Filter.useFilter(data, ru);

        // specify which attribute holds the classification value
        data.setClassIndex(data.numAttributes() - 1);

        return data;
    }

    private static void modifyClasses() throws Exception {
        CSVReader reader = new CSVReader(new FileReader("data/diabetic_data.csv"), ',');
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
        Utils.converttoicd9(csvBody);
        CSVWriter writer = new CSVWriter(new FileWriter("processed.csv"), ',');
        writer.writeAll(csvBody);
        writer.flush();
    }
}
