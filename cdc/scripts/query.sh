#OC=<path_to_oc>
OC=oc

POD=$1

if [[ -z $POD ]]
then
        echo "database pod name must be specified as parameter";
        exit 1
fi


$OC exec $POD -- psql -U postgres -d postgres -c "select * from pg_catalog.pg_tables where schemaname = 'inventory';"
$OC exec $POD -- psql -U postgres -d postgres -c "select * from inventory.orders;"
$OC exec $POD -- psql -U postgres -d postgres -c "select * from inventory.products;"

