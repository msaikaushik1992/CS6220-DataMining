.PHONY: randomforest, knn, boosting

clean:
	rm -rf *.class
	rm -rf *.csv
	rm -rf RandomForest/*.class
	rm -rf KNN/*.class
	rm -rf Boosting/*.class
	
randomforest: clean
	javac -cp ":jars/*" -d . RandomForest/*.java
	java -cp ":jars/*" Main

knn: clean
	javac -cp ":jars/*" -d . KNN/*.java
	java -cp ":jars/*" EnsembleWithKNN
	
boosting: clean
	javac -cp ":jars/*" -d . Boosting/*.java
	java -cp ":jars/*" RFBoostingMain