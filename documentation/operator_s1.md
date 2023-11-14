# First Operation scenario (Day 1 Operations)

## Provisioning AMQStreams via the Operator

Either use the Openshift console to create a Kafka cluster or execute:  
_oc apply -f amqstreams/02-amqstreams-kafka.yaml -n [demo-dev]_  

Notice that the Operator is aware of the AMQ Streams topology.  
Open the web console in the [demo-dev] namespace on the 'pods' tab.  
You'll observe that the Operators:  
 - install the zookeeper cluster first
 - waits fo the control plane to be up, running and healthy
 - then continue with the install of the data plane
 - waits fo the data plane to reach an healthy state
 - continue with the install of the entity operator


## Troubleshooting
