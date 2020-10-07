# cs-means clustering algorithm
### Pre-requisites 
  - Java 1.8

### Running the application
  1.	Update the “data.file.csv.location” attribute in “application.properties” file and mention the directory/folder where all the csv files are located.
      
      Note- All the data files need to be in csv format and must be placed in same directory
  2.	Update the log file location in “application.properties” and “log4J.properties” file
  3.	Start the application and access the url-
  
      http://<server>:8080/api/cs-mean/create-cluster?fileName=iris_dataset&noOfFeature=4&clusterStrictness=10
      
      filename- Name of the data file (in csv) 
      noOfFeature- Number of the features
      clusterStrictness- Cluster strictness (level-of-similarity)
  
  ### Associated Paper
  
Lamsal, R., Katiyar, S. cs-means: Determining optimal number of clusters based on a level-of-similarity. SN Appl. Sci. 2, 1774 (2020). https://doi.org/10.1007/s42452-020-03582-5
