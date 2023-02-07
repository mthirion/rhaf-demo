# Resource install for the first Operator flow

## Architecture
TODO

## Manual install
A manual intall of the amqstreams cluster can be done with the files located in the /amqstreams directory.  

#### Deploy AMQ Streams
_oc apply -f amqstreams/00-amqstreams-operator.yaml_  
_oc apply -f amqstreams/01-kafka-metrics.yaml_  
_oc apply -f amqstreams/02-amqstreams-kafka.yaml_  
_oc apply -f amqstreams/03-kafka-monitor.yaml_  

#### Manage users and topics


## Troubleshooting
