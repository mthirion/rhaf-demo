#OC=<path_to_oc>
OC=oc

POD=$1

if [[ -z $POD ]]
then
        echo "pod name not specified";
        exit 1
fi


$OC exec $POD -- psql -U postgres -d postgres -c "select * from pg_catalog.pg_tables where schemaname = 'inventory';"
$OC exec $POD -- psql -U postgres -d postgres -c "select * from inventory.orders;"


