package gameoflife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args.length < 1) {
            System.err.println("Please indicate the initiation file (ie: blinker.gol)");
            System.exit(1);
        }

        Path cithPath = Paths.get(args[0]);
        File cith = cithPath.toFile();
        FileReader fr = new FileReader(cith);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String templine;
        String temppattern = "";

        int col = 0;
        int row = 0;
        int startx = 0;
        int starty = 0;

        while ((line = br.readLine()) != null) {
            String[] inputarray = line.trim().split(" ");

            if (inputarray[0].equals("#")) {
                // ignore this line
                continue;
            }

            if (inputarray[0].equals("GRID")) {
                col = Integer.parseInt(inputarray[1]);
                row = Integer.parseInt(inputarray[2]);

            }

            if (inputarray[0].equals("START")) {
                startx = Integer.parseInt(inputarray[1]);
                starty = Integer.parseInt(inputarray[2]);

            }

            // Once DATA is found , read the rest of the file and assign to temppattern
            if (inputarray[0].equals("DATA")) {
                while ((templine = br.readLine()) != null) {
                    temppattern = temppattern + templine + ";";
                }
            }

        }

        // initiate the matrix with size and symbol "-"
        String[][] grid = new String[row][col];
        String[][] gridnew = new String[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = "-";
            }
        }

        // Split the temppattern and assign it to grid matrix,
        String[] temprow = temppattern.split(";"); // Split the temppattern to temprow array
        for (int i = 0; i < temprow.length; i++) {
            String[] tempcol = temprow[i].split(""); // Split the temppattern to tempcol array
            for (int j = 0; j < tempcol.length; j++) {
                if (tempcol[j].equals(" ")) {
                    tempcol[j] = "-"; // Replace empty space with "-"
                }
                grid[starty + i][startx + j] = tempcol[j]; // transpose the position based on start position
            }
        }

        // Print the matrix pattern (Before starting gol)
        System.out.println("Cycle 0");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.printf("%s ", grid[i][j]);
            }
            System.out.printf("\n");
        }

        // Start Game of life....
        for (int generation = 1; generation <= 5; generation++) {
            System.out.println("Cycle " + generation);
            for (int rowtest = 0; rowtest < row; rowtest++) {
                for (int coltest = 0; coltest < col; coltest++) {

                    // Counting total neighbour with star....
                    int totalstar = 0;
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            if (rowtest + i >= 0 && coltest + j >= 0 && rowtest + i < row && coltest + j < col) {
                                if (i == 0 && j == 0) {
                                    // this is the target cell, ignore this
                                } else {
                                    if (grid[rowtest + i][coltest + j].equals("*")) {
                                        totalstar += 1;
                                    }
                                }

                            }

                        }
                    }

                    // Apply Game of Life Rules....
                    switch (grid[rowtest][coltest]) {
                        case "*":
                            if (totalstar <= 1) {
                                gridnew[rowtest][coltest] = "-"; // die
                            } else if (totalstar == 2 || totalstar == 3) {
                                gridnew[rowtest][coltest] = "*"; // survive
                            } else if (totalstar >= 4) {
                                gridnew[rowtest][coltest] = "-"; // die
                            }
                            break;

                        case "-":
                            if (totalstar == 3) {
                                gridnew[rowtest][coltest] = "*"; // revive
                            } else {
                                gridnew[rowtest][coltest] = "-"; // remain die
                            }
                            break;

                    }
                }

            }

            // Print aand Replace the matrix pattern (After gol)
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    System.out.printf("%s ", gridnew[i][j]);
                    grid[i][j] = gridnew[i][j]; // grid = gridnew; Don't do this!!!! Not Working !!!
                }
                System.out.printf("\n");
            }

        }

    }

}
