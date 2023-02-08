# Fully automated install with Openshift GitOps

## Prerequisites
Openshift Gitops, based on ArgoCD, must be installed.  

_oc apply -f gitops/00-gitops-operator.yaml_  
_oc apply -f gitops/01-gitops.yaml_  


## Repository structre
The structure consists in 1 directory per team/project.  
So each there is:
- devteam
- opsteam  
- adminteam

Each of those dev and ops team directory contains one sub-directory per environment which expects an Helm Chart.  
The adminteam manages specific configuration at the cluster level above the concept of environment.  This could be cluster monitoring or some operators.  

In addition, the gitops/ directory contains a 'resources' sub-directory containing KafkaTopics and KafkaUsers yaml files that will be used to demonstrate the Topic and Users management capabilities.  

This structure allows to immediately see the content of each environment for the resources manbelonging to each team/project.  

Structure:

     gitops
       |
       |- devteam
    
            |- dev
        
            |- int
        
       |- opsteam
    
            |- dev
        
            |- int 

       |- adminteam


## Troubeshooting
