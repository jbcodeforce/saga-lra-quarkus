package org.acme.freezerms.domain;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Freezer {
   public String reeferID;
   public long capacity;
   public String type; 
   public String brand;
   public String status;
   public String location;
   public String creationDate;

   public Freezer(){}

   public String toString(){
      return "Freezer: " + reeferID + " capacity: " + capacity;
   }
}
