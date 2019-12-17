package com.example.reactiveprogrammingusingrxjava2._operators.concurrentcalls;

public class CustomerInfo {

    private Float employeeId;
    private Float firstName;
    private Float lastName;
    private Float central;
    private Float brandFactory;
    private Float bigBazaar;

    CustomerInfo(Float employeeId, Float firstName, Float lastName, Float central,
                 Float brandFactory, Float bigBazaar){
        this.employeeId =  employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.central = central;
        this.brandFactory = brandFactory;
        this.bigBazaar = bigBazaar;
    }

    public void setBrandFactory(Float brandFactory) {
        this.brandFactory = brandFactory;
    }

    public void setBigBazaar(Float bigBazaar) {
        this.bigBazaar = bigBazaar;
    }

    public void setLastName(Float lastName) {
        this.lastName = lastName;
    }

    public void setEmployeeId(Float employeeId) {
        this.employeeId = employeeId;
    }

    public void setCentral(Float central) {
        this.central = central;
    }

    public void setFirstName(Float firstName) {
        this.firstName = firstName;
    }
}
