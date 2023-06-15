import java.util.*;

public class BattleShips {
    public static int numRows = 10;
    public static int numCols = 10;
    public static int playerShips;
    public static int computerShips;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];

    public static void main(String[] args) {
        System.out.println("**** Selamat Datang ****");
        System.out.println("Lautan Masih Kosong\n");

        //Step 1 – Create the ocean map
        createOceanMap();

        //Step 2 – Deploy player’s ships
        deployPlayerShips();

        //Step 3 - Deploy computer's ships
        deployComputerShips();

        //Step 4 Battle
        do {
            Battle();
        } while (BattleShips.playerShips != 0 && BattleShips.computerShips != 0);

        //Step 5 - Game over
        gameOver();
    }

    public static void createOceanMap() {
        //First section of Ocean Map
        System.out.print("  ");
        for (int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        //Middle section of Ocean Map
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "|" + grid[i][j]);
                else if (j == grid[i].length - 1)
                    System.out.print(grid[i][j] + "|" + i);
                else
                    System.out.print(grid[i][j]);
            }
            System.out.println();
        }

        //Last section of Ocean Map
        System.out.print("  ");
        for (int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void deployPlayerShips() {
        Scanner input = new Scanner(System.in);

        System.out.println("\nMasukkan Kapal Mu:");
        //Deploying five ships for player
        BattleShips.playerShips = 5;
        for (int i = 1; i <= BattleShips.playerShips; ) {
            System.out.print("Masukkan Kordinat Y Untuk Kapal ke " + i + " : ");
            int y = input.nextInt();
            System.out.print("Masukkan Kordinat X Untuk Kapal ke " + i + " : ");
            int x = input.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y].equals(" "))) {
                grid[x][y] = "@";
                i++;
            } else if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y].equals("@"))
                System.out.println("Kamu tidak bisa menyimpan kapal di tempat yang sama");
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
        }
        printOceanMap();
    }

    public static void deployComputerShips() {
        System.out.println("\nComputer Memasukkan kapalnya");
        //Deploying five ships for computer
        BattleShips.computerShips = 5;
        for (int i = 1; i <= BattleShips.computerShips; ) {
            int x = (int) (Math.random() * numRows);
            int y = (int) (Math.random() * numCols);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y].equals(" "))) {
                grid[x][y] = "x";
                System.out.println(i + ". Kapal Dimasukkan");
                i++;
            }
        }
        printOceanMap();
    }

    public static void Battle() {
        playerTurn();
        if (BattleShips.computerShips != 0) { // Only allow computer to take a turn if it still has ships
            computerTurn();
        }

        printOceanMap();

        System.out.println();
        System.out.println("KAPAL MU: " + BattleShips.playerShips + " | KAPAL COMPUTER: " + BattleShips.computerShips);
        System.out.println();
    }

    public static void playerTurn() {
        System.out.println("\nGILIRAN MU");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Masukkan Angka kordinat Y: ");
            y = input.nextInt();
            System.out.print("Masukkan Angka kordinat X: ");
            x = input.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y].equals("x")) //if computer ship is already there; computer loses ship
                {
                    System.out.println("BOOM! KAMU MELEDAKKAN KAPAL!");
                    grid[x][y] = "!"; //Hit mark
                    --BattleShips.computerShips;
                } else if (grid[x][y].equals("@")) {
                    System.out.println("OH TIDAK, KAPAL MU HANCUR :(");
                    grid[x][y] = "x";
                    --BattleShips.playerShips;
                } else if (grid[x][y].equals(" ")) {
                    System.out.println("KAMU MELESET");
                    grid[x][y] = "-";
                }
            } else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //invalid guess
                System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
        } while ((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }


    public static void computerTurn() {
        System.out.println("\nGILIRAN COMPUTER");
        //Guess coordinates
        int x = -1, y = -1;
        do {
            x = (int) (Math.random() * numRows);
            y = (int) (Math.random() * numCols);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y].equals("@")) //if player ship is already there; player loses ship
                {
                    System.out.println("COMPUTER MELEDAKKAN KAPALMU!");
                    grid[x][y] = "x";
                    --BattleShips.playerShips;
                    --BattleShips.computerShips; // Deduct one computer ship as penalty
                } else if (grid[x][y].equals("x")) {
                    System.out.println("COMPUTER MENEMBAK KAPALNYA SENDIRI!");
                    grid[x][y] = "!";
                } else if (grid[x][y].equals(" ")) {
                    System.out.println("COMPUTER MELESET");
                    if (missedGuesses[x][y] != 1) {
                        missedGuesses[x][y] = 1;
                    }
                }
            }
        } while ((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }

    public static void gameOver() {
        System.out.println("KAPAL MU: " + BattleShips.playerShips + " | KAPAL COMPUTER: " + BattleShips.computerShips);
        if (BattleShips.playerShips > 0 && BattleShips.computerShips <= 0)
            System.out.println("BOOYAH! KAMU MENANG :)");
        else
            System.out.println("BERLATIH LAGI, MASIH ADA KESEMPATAN LAIN!");
        System.out.println();
    }

    public static void printOceanMap() {
        //First section of Ocean Map
        System.out.print("  ");
        for (int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        //Middle section of Ocean Map
        for (int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y].equals("@")) {
                    System.out.print("@");
                } else if (grid[x][y].equals("x")) {
                    System.out.print(" ");
                } else if (grid[x][y].equals("!")) {
                    System.out.print("!");
                } else if (grid[x][y].equals("-")) {
                    System.out.print("-");
                } else {
                    System.out.print(" ");
                }
            }

            System.out.println("|" + x);
        }

        //Last section of Ocean Map
        System.out.print("  ");
        for (int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }
}
