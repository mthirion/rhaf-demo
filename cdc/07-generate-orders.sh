#OC=<path_to_oc
OC=oc

POD=$1
if [[ -z $POD ]]
then
        echo "pod name not specified";
        exit 1
fi


$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10008,'2016-02-29',1003,1,106);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10009,'2016-02-29',1003,1,105);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10010,'2016-03-01',1003,2,106);"
sleep 3
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10011,'2016-03-02',1003,1,107);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10012,'2016-03-03',1003,3,106);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10013,'2016-03-07',1003,1,106);"
sleep 3
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10014,'2016-03-15',1003,5,108);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10015,'2016-03-17',1003,1,108);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10016,'2016-03-21',1003,2,107);"
sleep 3
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10017,'2016-04-02',1003,1,105);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10018,'2016-04-02',1003,1,106);"

# ------

$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10019,'2016-04-02',1003,2,106);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10020,'2016-04-03',1003,1,105);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10021,'2016-04-06',1003,1,106);"
sleep 3
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10022,'2016-04-07',1003,4,107);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10023,'2016-04-10',1003,2,106);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10024,'2016-04-13',1003,2,106);"
sleep 3
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10025,'2016-04-13',1003,3,108);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10026,'2016-04-13',1003,5,108);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10027,'2016-04-14',1003,1,107);"
$OC exec $POD -- psql -U postgres -d postgres -c "insert into inventory.orders values (10028,'2016-04-15',1003,2,105);"

