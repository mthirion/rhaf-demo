# Preparation for the first developer flow


## Architecture
The first case is an integration workflow that consists in reacting to events occuring in a database and use them to feed some visualizations in Elasticsearch.

![dev case 1](images/dev_case1.png?raw=true)

## Gitops Install
[The install is automated with GitOps.] to be done

## Manual install
### Prerequisites
The install of the AMQ Streams has already been performed by the operator.

### Deploy CDC
From the CDC directory:
- create the Debezium example Postgres database  
- create the KafkaConnect node  
- set the debezium Secret that contains the authentication properties for Kafka
- create the KafkaConnect connector configuration  
- create the KafkaUser and KafkaTopic
- create the KafkaConnect connector  
  
_oc apply -f cdc/*.yaml_  

For the purpose of the demo, there are a couple of scripts to add orders and products in the database in the cdc/scripts directory.

### Deploy the Camelk components
Install the Red Hat Camelk operator.  
Install the Red Hat serverless operator and deploy Knative Serving and Eventing.  

The kafka-source and the elasticsearch-index-sink kamelets should be found in the cluster.  
In the current version (1.8) of Camelk, the kafka-source kamelet only supports the Plain protocol, and the elasticsearch-index-sink does not support custom certificate yet.  
To work around the certificate issue, we'll deployed a non-secured Elasticsearch database.  We'll however modify the kamelet and add a json filter so that only the debezium payload will be sent to Elasticsearch, we won't need the rest.  
To work around the kafka autnetication issue, we'll create a new kamelet with scram-sha-512 support.

_oc apply -f camelk/04-kafkasoure-scram.kamelet.yaml_  
_oc apply -f camelk/05-elasticsearchsink-jsonpath.kamelet.yaml_  
  
Then, we can deploy the KameletBinding that will create an integration between those 2 kamelets.  
The first Kameletbinding synchronizes the content of the "inventory.orders" table/topic to the "demorhaf" Elasticsearch index.  
_oc apply -f camelk/06-kafla-elk.kameletbinding.yaml_  

For demonstration purpose, there is a second Kameletbinding that synchronizes the content of the "inventory.products" table/topic to the "productindex" Elasticsearch index.  
_oc apply -f eck camelk/07-kafka-elk.next.kameletbinding.yaml_  


### Elasticsearch and kibana
We'll use the Red Hat certified operator for ECK (Elastic Cloud) to deploy ECK in the elastic-system namespace.  
First, let's deploy the ECK operator.  
_oc apply -f eck/0-elastic-project.yaml_  
_oc apply -f eck/00-elastic-operator.yaml_  

Then we can create the ElasticSearch and Kibana components:  
_oc apply -f eck/01-elasticsearch-nossl.yaml_  
_oc apply -f eck/02-kibana-nossl.yaml_  

The ECK operator does not support turning off SSL between Kibana and Elasticsearch at the level of he Kibana resource.  
To do that, we need to manually change the Kibana ConfigMap.  
However, this resource is reconciled by the ECK operator.  
We therefore need to de-activate the operator, by scaling down to zero the ECK operator pod from the Deployment.  
<_oc_>  

Then, we can update the ConfigMap and restart the Kibana pod:  
_oc apply -f eck/03-kibana-nossl-config.yaml_  

A route needs to be created for Kibana:  
_oc apply -f eck/04-kibana-route.yaml_  


#### Kibana dashboard
The dashboard containing the "orders" and the "products" visualization can be imported into Kibana from the eck/06-kibana-dashboard.ndjson file.  

By default, Debezium uses an integer for the date format, which represents the number of days since 1970.  
For those values to be used as a grouping factor in Kibana, we need to convert them into pure epoch times using a scriptedfield with the name "fdate" and the type "date".  
The scriptedfield content is in the eck/05-kibana-scriptedfield.txt  


## Troubleshooting
### CDC
The deployment of the KafkaConnect debezium resource should lead to the start of an Openshift S2I build.  Monitor the build to ensure everything is fine.  
Then, verify that the KafkaConnector is in state "Ready" in the Operator page.

### Camelk
The deployment of a KameletBinding generates a camelk build.  
...  
Then, verify that the Integration is in state "Ready" in the Operator page.
