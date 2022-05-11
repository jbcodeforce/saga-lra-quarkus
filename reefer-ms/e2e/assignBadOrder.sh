curl -X POST http://localhost:8083/api/v1/reefers/assignOrder \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{   "orderID": "Order-2","productID": "P01", "quantity": 10,  "destinationCity":  "ABadDestination"}'