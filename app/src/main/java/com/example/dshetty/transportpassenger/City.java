package com.example.dshetty.transportpassenger;

public class City {

    private int __YMax;



    public int getYMax() {
        return __YMax;
    }

    public void setYMax(int value) {
        __YMax = value;
    }

    private int __XMax;
    public int getXMax() {
        return __XMax;
    }

    public void setXMax(int value) {
        __XMax = value;
    }

    private int[][] __cityGrid;

    public int[][] getCityGrid() {
        return __cityGrid;
    }

    public City(int xMax, int yMax) {
        setXMax(xMax);
        setYMax(yMax);
        initCityGrid();
    }

    private void initCityGrid() {
        __cityGrid = new int[__XMax][__YMax];
        for (int i = 0; i < __XMax; i++) {
            for (int j = 0; j < __YMax; j++) {
                __cityGrid[i][j] = 1;
            }
        }
    }

    public void addBuilding(int x, int y) {
        __cityGrid[x][y] = 0;
    }

    public Car addCarToCity(int xPos, int yPos) {
        return new Sedan(xPos,yPos,this,null);
    }

    public Passenger addPassengerToCity(int startXPos, int startYPos, int destXPos, int destYPos) {
        return new Passenger(startXPos,startYPos,destXPos,destYPos,this);
    }
}
