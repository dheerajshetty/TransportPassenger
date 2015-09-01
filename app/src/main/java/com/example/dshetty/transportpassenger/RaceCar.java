package com.example.dshetty.transportpassenger;

/**
 * Created by dheerajshetty on 8/31/2015.
 */
public class RaceCar extends Car {

    public RaceCar(int xPos, int yPos, City city, Passenger passenger) {
        super(xPos, yPos, city, passenger);
    }


    public void moveUp() {
        if (getYPos() < getCity().getYMax() - 1)
        {
            setYPos(getYPos() + 2);
            printPosition();
        }
    }


    public void moveDown() {
        if (getYPos() > 1)
        {
            setYPos(getYPos() - 2);
            printPosition();
        }

    }


    public void moveRight() {
        if (getXPos() < getCity().getXMax() - 1)
        {
            setXPos(getXPos() + 2);
            printPosition();
        }
    }


    public void moveLeft() {
        if (getXPos() > 1)
        {
            setXPos(getXPos() - 2);
            printPosition();
        }
    }
}
