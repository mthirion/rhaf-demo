# Preparation for the second Operator flow


## Gitops Install
TODO

## Manual install

### Enable Metrics
The AMQ Streams Brokers and Zookeepers ship with an embedded JMX agent that exports Prometheus-compatible metrics about the control and data nodes of the cluster.  

To enable the exporter endpoint on the cluster nodes, run:  
_oc apply -f amqstreams/01-kafka-metrics.yaml_ -n [demo-dev]  

The "Kafka" operator resource also embeds the configuration to deploy a Kafka Exporter pod.  
The Exporter application queries the Kafka cluster's topics at runtime and exports Prometheus-compatible metrics about consumers lag. 

![Amq streams metrics](images/ops1_metrics.png?raw=true)


### Enable the Openshift User Workload Monitoring

Create the "openshif-user-workload-monitoring" project :   
_oc apply -f monitoring/00-userworkload-project.yaml_  

Trigger the creation of the required user workload monitoring resources in that namespace:  
_oc apply -f monitoring/01-cluster-monitoring-config.yaml -n openshift-monitoring_  

Configure the user workload monitoring :   
_oc apply -f  monitoring/02-user-workload-monitoring-config.yaml -n openshift-user-workload-monitoring_  

### Add monitoring resources to the User Monitoring namespace

You can use a PodMonitor to monitor Pods or a ServiceMonitor for a monitoring based on an Openshift Service.  PodMonitor are used in the present case.

_oc apply -f amqstreams/03-kafka-monitor.yaml -n [demo-dev]_

### Deploy Grafana

_oc apply -f monitoring/03-grafana-operator.yaml_  
_oc apply -f monitoring/04-grafana.yaml_  

The above command creates a Grafana instance and a "grafana-serviceaccount" Service Account.  

### Configure Grafana
Configure the service account  

_oc adm policy add-cluster-role-to-user cluster-monitoring-view -z grafana-serviceaccount -n openshift-user-workload-monitoring_  

_oc create token grafana-serviceaccount -n openshift-user-workload-monitoring_

Replace the $GRAFANA_TOKEN within the monitoring/05-grafana-datasource.yaml then run :  
_oc apply -f monitoring/05-grafana-datasource.yaml_  

Create a custom folder for the AMQ Streams dashboards:  
_oc apply -f monitoring/grafana-folder.yaml_  

Add the required dahsboards from https://github.com/strimzi/strimzi-kafka-operator/tree/main/examples/metrics/grafana-dashboards  
_oc apply -f monitoring/07*.yaml_

### Add a ConsumerLag alert rule
_oc apply -f monitoring/08-prometheus-alert.yaml -n [demo-dev]_  


![user workload monitoring](images/ops1_userworkload.png?raw=true)

## Troubleshooting
