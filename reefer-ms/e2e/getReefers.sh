if [[ $# -ne 0 ]]
then
   EDP=$1
else
  EDP=localhost:8083
fi

curl -X GET http://$EDP/api/v1/reefers/ -H 'accept: application/json'