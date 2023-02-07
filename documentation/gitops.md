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



## Gitlab
ArgoCD synchronizes some content from a git-based repository with a Kubernetes namespace.  
The content of the gitops/ sub-directory of the current github repository can be used.  
Alternatively, the gitlab/ directory can be used to install a Gitlab server.  

_oc apply -f gitlab/0-gitlab-project.yaml_  
_oc apply -f gitlab/00-gitlab-operator.yaml_  

Edit the version and the Openshift domain name in the gitlab/01-gitlab.yaml file then:  
_oc apply -f gitlab/01-gitlab.yaml_  

## Troubeshooting
