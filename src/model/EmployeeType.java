package model;

public enum EmployeeType {
   FULL_TIME("Full-Time"),
   CONTRACTOR("Contractor");

   public final String label;

   EmployeeType(String label) {
      this.label = label;
   }
}
