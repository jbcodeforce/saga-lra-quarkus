if [[ $# -ne 0 ]]
then
   EDP=$1
else
  EDP=localhost:8082
fi

curl -X GET http://$EDP/api/v1/voyages/ -H 'accept: application/json'