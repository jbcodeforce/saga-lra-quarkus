# reefer-ms Project

Quarkus App as a Saga Participant using microprofile long running action

## What is added to base code

* Long running action dependencies
* two methods in the Resource class to define the method as part of the saga, and one for compensation.
* The method to be part of the saga is expecting that a LRA is started, and the URI is set in the HTTP Header 
named `Long-Running-Action`

```java
 @LRA(value = LRA.Type.REQUIRED, end=false)
 @POST
 @Path("/assignOrder")
 @Consumes(MediaType.APPLICATION_JSON)
 public Response processOrder(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId, OrderDTO order) {
```