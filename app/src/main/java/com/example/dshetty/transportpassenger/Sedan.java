package com.example.dshetty.transportpassenger;


public class Sedan extends Car {
    
    public Sedan(int xPos, int yPos, City city, Passenger passenger) {
        super(xPos, yPos, city, passenger);
    }

    public void moveUp() {
        if (getYPos() < getCity().getYMax())
        {
            setYPos(getYPos() + 1);
            printPosition();
        }

    }

    public void moveDown() {
        if (getYPos() > 0)
        {
            setYPos(getYPos() - 1);
            printPosition();
        }

    }

    public void moveRight() {
        if (getXPos() < getCity().getXMax())
        {
            setXPos(getXPos() + 1);
            printPosition();
        }

    }

    public void moveLeft() {
        if (getXPos() > 0)
        {
            setXPos(getXPos() - 1);
            printPosition();
        }

    }

    protected void printPosition() {
        System.out.println(String.format("Sedan moved to x - %d y - %d", new Object[] {getXPos(),getYPos()}));
    }
}
