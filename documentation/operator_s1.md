# First Operator scenario (Day 1 Operations)

## Preparation
Pre-install the amqstreams metrics resources.

_oc apply -f amqstreams/01-kafka-metrics.yaml_ -n [demo-dev]
_oc apply -f amqstreams/03-kafka-monitor.yaml_  -n [demo-dev]

## Installing AMQStreams

The installation of an Openshift Operator can be done "as-code".  
_oc apply -f amqstreams/00-amqstreams-operator.yaml_  

The installation of the AMQ Streams cluster can also be done "as-code". 

Notice that the Operator is aware of the AMQ Streams topology.  
Open the web console in the [demo-dev] namespace on the 'pods' tab.  
Execute:  
_oc apply -f amqstreams/02-amqstreams-kafka.yaml_  -n [demo-dev]  

You'll observe that the Operators:  
 - install the zookeeper cluster first
 - waits fo the control plane to be up, running and healthy
 - then continue with the install of the data plane
 - waits fo the data plane to reach an healthy state
 - continue with the install of the entity operator


## Troubleshooting
