# Second developer flow


## Architecture
The second case is an integration workflow through AMQ Streams from a producer and a consumer developed in Quarkus and leveraging the Avro data format.  
Red Hat Service Registry is used to store and centraly govern the Avro schemas.  
In addition, the producer and the consumer emit openTelemetry data to a central Jaeger server for end-to-end workflow traceability.  

![dev case 2](images/dev_case2.png?raw=true)

## Gitops Install
[The install is automated with GitOps.] to be done

## Manual install
### Prerequisites
The install of the AMQ Streams has already been performed by the operator.

### Deploy ServiceRegistry
Create the apicurio namespace.  
_oc apply -f serviceregistry/0-apicurio-project.yaml_  

Install the red Hat Service Registry Operator.  

We chose to decouple apicurio from AMQ Streams and use a Postgres database for the persistence instead.  
Deploy the Postgres database for Service Registry
_oc apply -f serviceregistry/01-apicuriodb.yaml_  

Deploy the Apicurio resource.  
_oc apply -f serviceregistry/02-apicurio.yaml_  


### Deploy the Jaeger server
Install the Red Hat Tracing operator and deploy the jaeger resource.  

_oc apply -f jaeger/01-jaeger.yaml_  

### Deploy the Quarkus clients
NOTE  
[At the moment there are 2 Quarkus producers and 2 consumers.  
The quarkus-consumer and quarkus-producer exchange simple text messages via the "event" topic.  They are tracing-enabed.  
The quarkus-consumer-sr and quarkus-producer-sr exchange avro messages via the "event-sr" topic.  They are not tracing-enabled.  
The clients will be merge in the coming time. ]  

The quarkus clients can be deployed on Openshift with:  
_cd quarkus-clients/[client]_  
_mvn install -Dquarkus.kubernetes.deploy=true_  

The producers automatically start to emit events periodically.  

#### Authentication
The quarkus applications authenticate to AMQ Streams by scram-sa-512.  
KafkaTopics, KafkaUsers, and their passwords need to be created.  
_oc apply -f quarkus-clients/0*.yaml_  

## Troubleshooting
### ...TO DO

