# Customer Profile Spark Example

This demo shows how we can define a guided decision table in Red Hat decision manager and get the kjar imported as dependency
inside of a spark application. The spark job reads a customer profile (in json format) with real time events and a set of
training models which can be fed in from within a big data analysis engine (using AI/ML). The job when complete outputs the 
CustomerProfile with useful events which can be sent to downstream systems for taking action. The events are filtered by passing 
them through a guided decision table which analyses real time events against training models analyzed over a historic time period.

![Image of unnamed]
(https://github.com/snandakumar87/CustomerProfileSparkExample/blob/master/unnamed.png)

Refer to https://github.com/snandakumar87/PersonalizationRules for the rules project. The project can be imported on 
Red Hat Decision Manager by importing the Repository link. Since the Spark Application code needs the mvn dependency for the rule,
this has build and deployed so that it is available in the local maven repo. For the purpose of testing, i have added 
a static version of the rule mvn jar in the repo. 


# Installing Spark:
Download spark from https://spark.apache.org/downloads.html

# Starting Spark Master:
From the Spark directory: ./sbin/start-master.sh

# Starting Spark Slave (Single Node Cluster):
./sbin/start-slave.sh <master-spark-URL> --webui-port 8090

# Configuration in the Spark Application Code:
Adjust the location of the jsonFileSrc and jsonFileDest relative to where the project is available.

# Build the Application code:
cd CustomerProfileSparkExample
mvn clean install

# Submit Spark Job:
spark-submit  --deploy-mode cluster 
--num-executors 8 --executor-cores 5 
--executor-memory 4G --conf "spark.driver.extraJavaOptions=-Diop.version=4.1.0.0" 
--class com.Main --master spark://username-mac:7077 
/Users/sadhananandakumar/Documents/Developer/PersonalizationRules/CustomerProfile/target/CustomerProfile-1.0-SNAPSHOT.jar

Change the Spark Master URL and the location of the jar file in the above command.

Once the job completes, navigate to the jsonFileDest to see the generated output file.

# CustomerProfileInput:
```
{
   "customerName":"TEST_CUSTOMER",
   "customerAccountNumber":"6544678990008",
   "customerGeo":"EST",
   "events":[
      {
         "eventCategory":"CC_BALANCE_PAYMENT",
         "eventValue":"LATE_PAYMENT",
         "eventSource":"CUSTOMERCARE",
         "eventDate":"2018-02-23T18:48:43.511Z",
         "eventId":"87987"
      },
      {
         "eventCategory":"CC_TRANSACTION",
         "eventValue":"GROCERY",
         "eventSource":"POS",
         "eventDate":"2018-02-23T14:25:43.511Z",
         "eventId":"87989"
      },
      {
         "eventCategory":"DISPUTE",
         "eventValue":"CASE_CREATED",
         "eventSource":"ONLINE",
         "eventDate":"2018-02-23T10:25:43.511Z",
         "eventId":"87990"
      },
      {
         "eventCategory":"CC_BALANCE_PAYMENT",
         "eventValue":"MIN_DUE",
         "eventSource":"CUSTOMERCARE",
         "eventDate":"2018-02-23T18:48:43.511Z",
         "eventId":"87987"
      }
   ],
   "models":[
      {
         "modelName":"CUSTOMER_GOOD_STANDING",
         "confidence":"100"
      },
      {
         "modelName":"HIGH_BALANCE_DEBT",
         "confidence":"100"
      }
   ]
}
```

# Customer Profile Output:
```
{
  "customerName" : "TEST_CUSTOMER",
  "customerAccountNumber" : "6544678990008",
  "customerGeo" : "EST",
  "events" : [ {
    "eventCategory" : "CC_BALANCE_PAYMENT",
    "eventSource" : "CUSTOMERCARE",
    "eventDate" : 1519411723511,
    "eventId" : "87987",
    "eventValue" : "LATE_PAYMENT"
  }, {
    "eventCategory" : "CC_TRANSACTION",
    "eventSource" : "POS",
    "eventDate" : 1519395943511,
    "eventId" : "87989",
    "eventValue" : "GROCERY"
  }, {
    "eventCategory" : "DISPUTE",
    "eventSource" : "ONLINE",
    "eventDate" : 1519381543511,
    "eventId" : "87990",
    "eventValue" : "CASE_CREATED"
  }, {
    "eventCategory" : "CC_BALANCE_PAYMENT",
    "eventSource" : "CUSTOMERCARE",
    "eventDate" : 1519411723511,
    "eventId" : "87987",
    "eventValue" : "MIN_DUE"
  } ],
  "models" : [ {
    "modelName" : "CUSTOMER_GOOD_STANDING",
    "confidence" : 100
  }, {
    "modelName" : "HIGH_BALANCE_DEBT",
    "confidence" : 100
  } ],
  "eventAnalysisModels" : [ {
    "eventEffectiveness" : "ALERT",
    "eventResponsePayload" : "Late Payment Waiver Offered"
  }, {
    "eventEffectiveness" : "OFFER",
    "eventResponsePayload" : "Balance Transfer offer"
  } ]
}
```

# Common Issues:

Some of the common issues you might see during the above process are:
1) Exception in thread "main" java.lang.SecurityException: Invalid signature file digest for Manifest main attributes
  Resolution: Navigate to the target path where the spark application jar exists
  zip -d sparkbrms-poc-1.1.jar META-INF/*.RSA META-INF/*.DSA META-INF/*.SF
2) Caused by: java.io.FileNotFoundException: File file:/tmp/spark-events does not exist
  Resolution: mkdir /tmp/spark-events





