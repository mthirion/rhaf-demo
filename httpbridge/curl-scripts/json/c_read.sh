curl --location --request GET 'https://kafka-dev-my-bridge-service-staging.apps.cluster-dqx5g.dqx5g.sandbox2859.opentlc.com/consumers/cdc-demo-consumer-group/instances/orderconsumer/records?user_key=7b1e0bce4577b411238ebb868e2218f7' \
--header 'Accept: application/vnd.kafka.json.v2+json' | jq '.[1].value'
