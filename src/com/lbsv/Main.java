//package com.lbsv;

import java.util.Scanner;

public class Main {
    public static void printMatrix(int[][] matrix) { // outputs the matrix
        var line = 0; // puts a line between two bricks
        var dash = 2; // separates the tiles in a brick
        StringBuilder sb = new StringBuilder(""); // separates the lines of bricks
        for(var i = 0; i <matrix[0].length; i++)
        {
            sb.insert(i, "______");
        }
        for (var i = 0; i < matrix.length; i++) {
            if(i % 2 == 0) {
                System.out.println(sb.toString());
            }
            for (var j = 0; j < matrix[i].length; j++) {
                System.out.printf("%3d",matrix[i][j]);
                line++;
                if(dash == 2)
                {
                    System.out.print("  -");
                    dash = 0;
                }
                dash++;
                if(line==2)
                {
                    if(j != matrix[i].length-1) {
                        System.out.print("  |");
                        line = 0;
                    }
                    else if(j == matrix[i].length-1) line=0;
                }
            }
            System.out.println();
        }
        System.out.println(sb.toString());
    }
    public static boolean  validateMatrix(int [][] matrix) { //checks if the first layer of bricks is valid
        for (var i = 0; i < matrix.length; i++) {
            for (var j = 0; j < matrix[i].length; j++) {
                if (i + 1 < matrix.length && j + 1 < matrix[i].length) { //no arrayoutofbounds error
                    if (matrix[i][j] == matrix[i][j + 1]) { //horizontal brick check
                        j++;
                    }
                    else if (matrix[i][j] == matrix[i + 1][j]) { // vertical check
                        j++;
                    }
                    else if(j>0)
                    {
                        if(matrix[i][j] == matrix[i][j-1]) //if first is vertical checks second and third places
                        {
                            j++;
                        }
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int[][] buildLayer(int[][] matrix) { //builds the second layer of bricks
        var buffer = 0; // temp variable for brick manipulation
        for (var i = 0; i < matrix.length; i++) {
            var skipIndex = 2; //skips every 3rd number in order to manipulate bricks properly
            if (matrix.length >= 2 && matrix[i].length >= 4) { // checks if matrix is bigger than or equal to 2x4
                for (var j = 0; j < matrix[i].length; j++) {
                    if (j + 3 < matrix[i].length) { //no arrayoutofbounds error
                        if (matrix[i][j] == matrix[i + 1][j] && matrix[i][j + 3] == matrix[i + 1][j + 3]) { // swaps bricks places if first and fourth bricks in 2x4 are vertical
                            buffer = matrix[i][j];
                            matrix[i][j] = matrix[i][j + 2];
                            matrix[i][j + 2] = buffer;
                            buffer = matrix[i][j + 3];
                            matrix[i][j + 3] = matrix[i + 1][j + 2];
                            matrix[i + 1][j + 2] = buffer;
                            buffer = matrix[i][j + 3];
                            matrix[i][j + 3] = matrix[i + 1][j];
                            matrix[i + 1][j] = buffer;
                            j += 3;
                        } else if (matrix[i][j] == matrix[i][j + 1] && matrix[i][j + 2] == matrix[i][j + 3]) { // swaps bricks places if first and second bricks in 2x4 are horizontal
                            buffer = matrix[i][j];
                            matrix[i][j] = matrix[i][j + 2];
                            matrix[i][j + 2] = buffer;
                            buffer = matrix[i][j + 3];
                            matrix[i][j + 3] = matrix[i + 1][j];
                            matrix[i + 1][j] = buffer;
                            buffer = matrix[i][j + 3];
                            matrix[i][j + 3] = matrix[i + 1][j + 2];
                            matrix[i + 1][j + 2] = buffer;
                            j += 3;
                        } else if (j != skipIndex) {//if no matches detected
                            if (j > skipIndex) {
                                skipIndex += 3;
                            }
                            buffer = matrix[i][j + 1];
                            matrix[i][j + 1] = matrix[i + 1][j];
                            matrix[i + 1][j] = buffer;
                        }
                    }
                }
                i++;
            } else if (matrix[i].length < 4) { //if matrix is smaller than 2x4
                for (var j = 0; j < matrix[i].length; j++) {
                    if (j + 1 < matrix[i].length && i + 1 < matrix.length) {
                        buffer = matrix[i][j + 1];
                        matrix[i][j + 1] = matrix[i + 1][j];
                        matrix[i + 1][j] = buffer;
                    }
                }
                i++;
            }
        }
        return matrix;
    }
    public static void brickBuilder(int m,int n, Scanner input) { //function that processes the first layer of bricks
        int[][] matrix = new int[m][n]; //the matrix that contains the first layer of bricks
        for (var i = 0; i < m; i++) { //loop that inserts the values into the matrix
            for (var j = 0; j < n; j++) {
                matrix[i][j] = input.nextInt();
            }
        }
        if(validateMatrix(matrix))
        {
            printMatrix(buildLayer(matrix));
        }
        else
        {
            System.out.println("invalid matrix");
        }
    }

    public static void main(String[] args) {
        int m, n; //m = rows n = columns
        Scanner input = new Scanner(System.in); //input
        System.out.println("Insert m : ");
        m = input.nextInt();
        System.out.println("Insert n : ");
        n = input.nextInt();
        if ((m <= 0 || n <= 0) || (m > 100 || n > 100) || (m*n) % 2 != 0) {
            System.out.println(-1);
        } else {
            System.out.println("Insert the bricks");
            brickBuilder(m, n, input);
        }
    }
}

