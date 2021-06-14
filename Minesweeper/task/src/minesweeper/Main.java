package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static char[][] emptyFieldGenerator(int rows, int columns){
        char[][] field = new char[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                field[i][j] = '.';
            }
        }
        return field;
    }
    static char[][] randomMinesGenerator(char[][] field, int numOfMines, int rows, int columns){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                field[i][j] = '/';
            }
        }

        while (numOfMines > 0) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    int rand = (int) (Math.random() * 9);
                    if (field[i][j] == '/' && rand == j) {
                        if(numOfMines == 0){
                            break;
                        }else {
                            field[i][j] = 'X';
                            numOfMines--;
                            //System.out.println(numOfMines);
                        }
                    }
                }
            }
        }
        return field;
    }
    static char[][] warningsGenerator(char[][] field, int rows, int columns){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int num = 0;
                if(field[i][j] == '/'){
                    if(i != 0 && j != 0 && field[i-1][j-1] == 'X'){
                        num++;
                    }
                    if(i != 0 && field[i-1][j] == 'X'){
                        num++;
                    }
                    if(i != 0 && j != columns - 1 && field[i-1][j+1] == 'X'){
                        num++;
                    }
                    if(j != 0 && field[i][j-1] == 'X'){
                        num++;
                    }
                    if(j != columns - 1 && field[i][j+1] == 'X'){
                        num++;
                    }
                    if(i != rows - 1 && j != 0 && field[i+1][j-1] == 'X'){
                        num++;
                    }
                    if(i != rows - 1 && field[i+1][j] == 'X'){
                        num++;
                    }
                    if(i != rows - 1 && j != columns - 1 && field[i+1][j+1] == 'X'){
                        num++;
                    }
                    if(num != 0){
                        field[i][j] = (char)(num+'0');
                    }
                }
            }
        }
        return field;
    }
    //to print out the currentcharfield
    static void showField(char[][] field, int rows, int columns){
        System.out.print(" |123456789|\n" +
                "-|---------|\n");
        for (int i = 0; i < rows; i++) {
            System.out.print((i+1) + "|");
            for (int j = 0; j < columns; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }
     static char[][] expandingSlash(char[][] field, char[][] charfield, int rows, int columns, int r, int c) {
        //if not X, then either number or /
        if(field[r][c] != 'X'){
            charfield[r][c] = field[r][c];
        }
        //if / then do analysis
         if (field[r][c] == '/') {
             if (r > 0 && c > 0 && (charfield[r - 1][c - 1] == '.' || charfield[r - 1][c - 1] == '*')) {
                 //charfield[r - 1][c - 1] = field[r - 1][c - 1];
                 charfield = expandingSlash(field, charfield, rows, columns, r - 1, c - 1);
                 //showField(charfield, rows, columns);
             }
             if (r > 0 && (charfield[r - 1][c] == '.' || charfield[r - 1][c] == '*')) {
                 //charfield[r - 1][c] = field[r - 1][c];
                 charfield = expandingSlash(field, charfield, rows, columns, r - 1, c);
                 //showField(charfield, rows, columns);
             }
             if (r > 0 && c < columns - 1 && (charfield[r - 1][c + 1] == '.' || charfield[r - 1][c + 1] == '*')) {
                 //charfield[r - 1][c + 1] = field[r - 1][c + 1];
                 charfield = expandingSlash(field, charfield, rows, columns, r - 1, c + 1);
                 //showField(charfield, rows, columns);
             }
             if (c > 0 && (charfield[r][c - 1] == '.' || charfield[r][c - 1] == '*')) {
                 //charfield[r][c - 1] = field[r][c - 1];
                 charfield = expandingSlash(field, charfield, rows, columns, r, c - 1);
                 //showField(charfield, rows, columns);
             }
             if (c < columns - 1 && (charfield[r][c + 1] == '.' || charfield[r][c + 1] == '*')) {
                 //charfield[r][c + 1] = field[r][c + 1];
                 charfield = expandingSlash(field, charfield, rows, columns, r, c + 1);
                 //showField(charfield, rows, columns);
             }
             if (r < rows - 1 && c > 0 && (charfield[r + 1][c - 1] == '.' || charfield[r + 1][c - 1] == '*')) {
                 //charfield[r + 1][c - 1] = field[r + 1][c - 1];
                 charfield = expandingSlash(field, charfield, rows, columns, r + 1, c - 1);
                 //showField(charfield, rows, columns);
             }
             if (r < rows - 1 && (charfield[r + 1][c] == '.' || charfield[r + 1][c] == '*')) {
                 //charfield[r + 1][c] = field[r + 1][c];
                 charfield = expandingSlash(field, charfield, rows, columns, r + 1, c);
                 //showField(charfield, rows, columns);
             }
             if (r < rows - 1 && c < columns - 1 && (charfield[r + 1][c + 1] == '.' || charfield[r + 1][c + 1] == '*')) {
                 //charfield[r + 1][c + 1] = field[r + 1][c + 1];
                 charfield = expandingSlash(field, charfield, rows, columns, r + 1, c + 1);
                 //showField(charfield, rows, columns);
             }
         }

        return charfield;
    }
    static List<int[]> storeXcoord(int rows, int columns, char[][] field){
        List<int[]> Xcoord = new ArrayList<int[]>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(field[i][j] == 'X'){
                    Xcoord.add(new int[]{i, j});
                }
            }
        }
        return Xcoord;
    }
    static int theGame(int rows, int columns, char[][] field, int[][] XcoordArr, int numOfMines, List<int[]> Xcoord, char[][] charfield){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Set/unset mines marks or claim a cell as free: ");
        int c = scanner.nextInt() - 1;
        int r = scanner.nextInt() - 1;

        boolean free = false, mine = false;
        if(scanner.hasNext("free")){
            free = true;
        }else if(scanner.hasNext("mine")){
            mine = true;
        }

        if(free){
            if(field[r][c] == 'X'){
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        if (field[i][j] == 'X') {
                            charfield[i][j] = 'X';
                        }
                    }
                }
                showField(charfield, rows, columns);
                System.out.println("You stepped on a mine and failed!");
                System.exit(0);
            }
            else if(field[r][c] == '/') {
                charfield = expandingSlash(field, charfield, rows, columns, r, c);
            }else{ //if number
                charfield[r][c] = field[r][c];
            }
            showField(charfield, rows, columns);
        }

        if(mine) {

            if (charfield[r][c] != '.' && charfield[r][c] != '*') {
                System.out.println("There is a number/slash here!");
                return theGame(rows, columns, field, XcoordArr, numOfMines, Xcoord, charfield);
            }

            if (charfield[r][c] == '*') {
                boolean isX = false;
                for (int i = 0; i < Xcoord.size(); i++) {
                    if (Arrays.equals(new int[]{r, c}, XcoordArr[i])) {
                        isX = true;
                    }
                }
                if (isX) {
                    numOfMines++;
                }
                charfield[r][c] = '.';
                showField(charfield, rows, columns);
            }
            else if (charfield[r][c] == '.' && field[r][c] != 'X') {
                charfield[r][c] = '*';
                showField(charfield, rows, columns);
            }
            else if (charfield[r][c] == '.' && field[r][c] == 'X') {
                charfield[r][c] = '*';
                numOfMines--;
                showField(charfield, rows, columns);
                if (numOfMines == 0) {
                    return numOfMines;
                }
            }
            //System.out.println(numOfMines);
        }
        return numOfMines;
    }
    public static void main(String[] args) {
        //9x9 field
        //you control the amount of mines
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        int numOfMines = scanner.nextInt();
        int rows = 9, columns = 9;

        char[][] field = new char[rows][columns];
        field = randomMinesGenerator(field, numOfMines, rows, columns);
        field = warningsGenerator(field, rows, columns);
        //showField(field, rows, columns);
        //field is alr generated with warnings
        char[][] charfield = emptyFieldGenerator(rows, columns);

        showField(charfield, rows, columns);

        List<int[]> Xcoord = storeXcoord(rows, columns, field);
        int[][] XcoordArr = Xcoord.toArray(new int[Xcoord.size()][2]);

        while(true){
            numOfMines = theGame(rows, columns, field, XcoordArr, numOfMines, Xcoord, charfield);
            //System.out.println(numOfMines);
            if(numOfMines == 0){
                break;
            }
        }
        System.out.println("Congratulations! You found all the mines!");
    }
}
