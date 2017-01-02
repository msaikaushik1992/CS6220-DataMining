import java.util.List;

/**
 * Created by prashr on 12/12/16.
 */
public class Utils {

    public   static void converttoicd9(List<String[]> csv){
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
}
