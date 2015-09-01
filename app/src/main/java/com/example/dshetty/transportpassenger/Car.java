package com.example.dshetty.transportpassenger;

public abstract class Car {

    private int __XPos;
    public int getXPos() {
        return __XPos;
    }

    public void setXPos(int value) {
        __XPos = value;
    }

    private int __YPos;
    public int getYPos() {
        return __YPos;
    }

    public void setYPos(int value) {
        __YPos = value;
    }

    private Passenger __Passenger;
    public Passenger getPassenger() {
        return __Passenger;
    }

    public void setPassenger(Passenger value) {
        __Passenger = value;
    }

    private City __City;
    public City getCity() {
        return __City;
    }

    public void setCity(City value) {
        __City = value;
    }

    public Car(int xPos, int yPos, City city, Passenger passenger) {
        setXPos(xPos);
        setYPos(yPos);
        setCity(city);
        setPassenger(passenger);
    }

    protected void printPosition() {
        System.out.println(String.format("Car moved to x - %d y - %d", new Object[] {getXPos(), getYPos()}));
    }

    public void pickupPassenger(Passenger passenger) {
        setPassenger(passenger);
    }

    public abstract void moveUp();

    public abstract void moveDown();

    public abstract void moveRight();

    public abstract void moveLeft();

}
