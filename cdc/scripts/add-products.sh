OC=oc
#OC=<path_to_oc>

POD=$1
if [[ -z $POD ]]
then
        echo "database pod name must be specified as parameter";
        exit 1
fi


# group 1
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.products values (110,'bumper','a sporty front bumper',3);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.products values (111,'bumper','a sporty front bumper',3);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.products values (112,'lamp','a lamp',1);"

# group 2
#$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.products values (113,'bag','a hiking bag',2);"
#$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.products values (114,'solarpan','a portable solar panel',1);"
#$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.products values (115,'flamp','a frontal lamp',1);

