package com.example.dshetty.transportpassenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dshetty.transportpassenger.Car;
import com.example.dshetty.transportpassenger.City;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button newRunButton = (Button) findViewById(R.id.newRunButton);
        final TextView currPos = (TextView) findViewById(R.id.currentPositionText);
        final TextView carStartPos = (TextView) findViewById(R.id.carStartPos);
        final TextView passengerStartPos = (TextView) findViewById(R.id.passengerPickupPos);
        final TextView passengerDropPos = (TextView) findViewById(R.id.passengerDropPos);
        final TextView passengerPickupNotification = (TextView)
                findViewById(R.id.passengerPickedUpNotification);

        newRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Use a new thread so that the UI thread is not blocked
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Random rand = new Random();
                        int cityLength = 10;
                        int cityWidth = 10;
                        City myCity = new City(cityLength, cityWidth);
                        Car mCar = myCity.addCarToCity(rand.nextInt(cityLength - 1),
                                rand.nextInt(cityWidth - 1));
                        com.dheeraj.transportproj.Passenger mPassenger = myCity.addPassengerToCity(
                                rand.nextInt(cityLength - 1),
                                rand.nextInt(cityWidth - 1),
                                rand.nextInt(cityLength - 1),
                                rand.nextInt(cityWidth - 1));

                        Point passengerPos = new Point(mPassenger.getCurrentXPos(),
                                mPassenger.getCurrentYPos());
                        Point carPos = new Point(mCar.getXPos(), mCar.getYPos());
                        Point destinationPos = new Point(mPassenger.getDestinationXPos(),
                                mPassenger.getDestinationYPos());

                        System.out.println("Car position:" + carPos.toString());
                        System.out.println("Passenger position:" + passengerPos.toString());

                        List<Point> pathToPassenger = findPath(myCity.getCityGrid(), carPos,
                                passengerPos);

                        final String carStartPosString = carPos.toString();
                        final String passengerPickupPos = passengerPos.toString();
                        final String passengerDropPosString = destinationPos.toString();

                        StringBuilder pathToPassengerSb = new StringBuilder();
                        StringBuilder pathToDestinationSb = new StringBuilder();

                        pathToPassengerSb.append(carPos.toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Disable the button until the run is complete
                                newRunButton.setEnabled(false);
                                carStartPos.setText(carStartPosString);
                                passengerStartPos.setText(passengerPickupPos);
                                passengerDropPos.setText(passengerDropPosString);
                                passengerPickupNotification.setText(
                                        "Passenger has been picked up!");
                                passengerPickupNotification.setVisibility(View.INVISIBLE);
                            }
                        });

                        for (Point p: pathToPassenger) {
                            System.out.println("Curr point:" + p.toString());
                            pathToPassengerSb.append("->");
                            pathToPassengerSb.append(p.toString());
                            tick(mCar, mPassenger, p);
                            final String posString = p.toString();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    currPos.setText(posString);
                                }
                            });

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        // Represent the point for passenger pick up as -1, -1
                        tick(mCar, mPassenger, new Point(-1, -1));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                passengerPickupNotification.setVisibility(View.VISIBLE);
                            }
                        });

                        carPos = new Point(mCar.getXPos(), mCar.getYPos());

                        System.out.println("Car position:" + carPos.toString());
                        System.out.println("Destination position:" + destinationPos.toString());

                        List<Point> pathToDestination = findPath(myCity.getCityGrid(), carPos,
                                destinationPos);

                        pathToDestinationSb.append(carPos.toString());

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        for (Point p: pathToDestination) {
                            System.out.println("Curr point:" + p.toString());
                            pathToDestinationSb.append("->");
                            pathToDestinationSb.append(p.toString());
                            tick(mCar, mPassenger, p);
                            final String posString = p.toString();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    currPos.setText(posString);
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                newRunButton.setEnabled(true);
                                passengerPickupNotification.setText(
                                        "Passenger has been dropped!");
                            }
                        });

                        Intent showRoute = new Intent();
                        showRoute.setClass(getApplicationContext(), Main2Activity.class);
                        showRoute.putExtra("pathToPassenger", pathToPassengerSb.toString());
                        showRoute.putExtra("pathToDestination", pathToDestinationSb.toString());

                        startActivity(showRoute);

                    }
                }).start();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            System.out.println("Bundle is not null!");
            if(bundle.getBoolean("click")) {
                System.out.println(newRunButton.performClick());
            }
        }

        System.out.println("Done");
    }

    /**
     * Use BFS to find the shortest path. Important when there are obstacles like buildings
     * @param startPos starting position of the path
     * @param cityGrid current state of the city grid
     * @param endPos end position of the path
     *
     * @return List of points representing the shortest path
     * */
    private List<Point> findPath(int[][] cityGrid, Point startPos, Point endPos) {
        boolean[][] visited = new boolean[10][10];
        Point[][] parent = new Point[10][10];

        if (startPos.getX() == endPos.getX() && startPos.getY() == endPos.getY()) {
            return Collections.emptyList();
        }

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
            {
                visited[i][j] = false;
                parent[i][j] = null;
            }

        List<Point> path = new LinkedList<>();
        int pathLength = Integer.MAX_VALUE;
        Queue<Point> q = new LinkedList<>();
        q.add(startPos);
        while (!q.isEmpty())
        {
            Point curr = q.remove();
            visited[curr.getX()][curr.getY()] = true;

            if (curr.getX() == endPos.getX() && curr.getY() == endPos.getY())
            {
                List<Point> currPath = new LinkedList<>();
                while (parent[curr.getX()][curr.getY()] != startPos)
                {
                    curr = parent[curr.getX()][curr.getY()];
                    currPath.add(curr);

                }

                if (currPath.size() < pathLength)
                {
                    path = currPath;
                    pathLength = currPath.size();
                }
            }

            if (curr.getY() + 1 < 10 && cityGrid[curr.getX()][curr.getY() + 1] != 0 &&
                    !visited[curr.getX()][curr.getY() + 1])
            {
                q.add(new Point(curr.getX(), curr.getY() + 1));
                parent[curr.getX()][curr.getY() + 1] = curr;
            }
            if (curr.getY() - 1 >= 0 && cityGrid[curr.getX()][curr.getY() - 1] != 0 &&
                    !visited[curr.getX()][curr.getY() - 1])
            {
                q.add(new Point(curr.getX(), curr.getY() - 1));
                parent[curr.getX()][curr.getY() - 1] = curr;
            }
            if (curr.getX() - 1 >= 0 && cityGrid[curr.getX() - 1][curr.getY()] != 0 &&
                    !visited[curr.getX() - 1][curr.getY()])
            {
                q.add(new Point(curr.getX() - 1, curr.getY()));
                parent[curr.getX() - 1][curr.getY()] = curr;
            }
            if (curr.getX() + 1 < 10 && cityGrid[curr.getX() + 1][curr.getY()] != 0 &&
                    !visited[curr.getX() + 1][curr.getY()])
            {
                q.add(new Point(curr.getX() + 1, curr.getY()));
                parent[curr.getX() + 1][curr.getY()] = curr;
            }
        }
        Collections.reverse(path);

        // need one tick for the last point
        path.add(endPos);
        return path;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Takes one action (move the car one spot or pick up the passenger).
     *
     *  @param car The car to move
     *  @param passenger The passenger to pick up
     *  @param p The next point in the path
     */
    private void tick(Car car, Passenger passenger, Point p) {

        //Passenger pick up
        if(p.getX() == -1 && p.getY() == -1) {
            return;
        }

        if(car.getXPos() < p.getX()) {
            car.moveRight();
        }

        if(car.getXPos() > p.getX()) {
            car.moveLeft();
        }

        if(car.getYPos() < p.getY()) {
            car.moveUp();
        }

        if(car.getYPos() > p.getY()) {
            car.moveDown();
        }
    }


}
