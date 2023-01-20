ELASTIC_URL=http://eastic-elastic-system.apps.cluster-dqx5g.dqx5g.sandbox2859.opentlc.com
ELK_INDEX=productindex


if [[ -n $1 ]]
then	
	ELASTIC_URL=$1
fi
if [[ -n $2 ]]
then
	ELK_INDEX=$2
fi


curl -X DELETE $ELASTIC_URL/$ELK_INDEX
