# Second Operation scenario (day 2 operation)

## Monitoring the Kafka cluster  
Shutdown a broker node.  

Observe the changes in the Grafana dashboard.  

TODO: create a prometheus alert thanks to the info given in the Grafana console.  


## Detecting a consumer lag
To create a consumer lag:
- deploy the simple kafka producer from the quarkus-clients, as also described in the "developer install case2"
- deploy the simple kafka consumer from the quarkus-clients, as also described in the "developer install case2"

Scale the producer to 4 or 5 pods, keeping a single instance for the consumer.  

Observe the consumer lag in the Grafana console.  

Also observe that a Prometheus alert is triggered and shows up in the Openshift console.  


## Troubleshooting
