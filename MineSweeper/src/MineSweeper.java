import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class MineSweeper {
    Scanner input = new Scanner(System.in);
    int rowNumber;
    int colNumber;
    int numberOfMine;
    String[][] firstMap;
    String[][] gameMap;
    int row;
    int col;

    MineSweeper() {
        int rowNumber = this.rowNumber;
        int colNumber = this.colNumber;
        int numberOfMine = this.numberOfMine;
        String[][] firstMap = this.firstMap;
        String[][] gameMap = this.gameMap;
        int row = this.row;
        int col = this.col;

    }
    // Girilen 2 boyutlu diziyi ekrana basan metot
    String[][] showMatris(String[][] arr) {
        for (int row = 0; row < arr.length; row++) {
            for (int column = 0; column < arr[row].length; column++) {
                System.out.print(arr[row][column] + " ");
            }
            System.out.println();
        }
        return arr;
    }
    // Kullanıcıdan alınan mayıntarlasının boyutlarını kontrol eden metot
    boolean isMineSweeperSizeTrue() {
        if (this.colNumber < 2 || this.rowNumber < 2) {
            return false;
        } else {
            return true;
        }
    }
    // Kullanıcının girdiği değerleri 2x2'nin altındaysa tekrar veri girilmesini isteyen metot.
    void mineSweeperSizeCheck() {
        do {
            System.out.print("Please Enter a Row Number : ");
            this.rowNumber = input.nextInt();
            System.out.print("Please Enter a Columb Number : ");
            this.colNumber = input.nextInt();
            if (!isMineSweeperSizeTrue()) {
                System.out.println("Minimum Values must be greater than 2. Try Again.\n");
            }
        }
        while (!isMineSweeperSizeTrue());
        System.out.println("Game Start..\n");
    }
    // Toplam mayın sayısını bulan metot.
    void findNumberOfMine() {
        this.numberOfMine = (this.colNumber * this.rowNumber) / 4;
        System.out.println(this.numberOfMine +" Number of Mines");
    }
    //Mayın sayısını rasgele şekilde diziye yerleştiren metot.
    public void createSweeperWithMine() {
        findNumberOfMine();
        this.firstMap = new String[this.rowNumber][this.colNumber];
        for (int x = 0; x < this.firstMap.length; x++) {
            String[] row = this.firstMap[x];
            for (int y = 0; y < row.length; y++) {
                this.firstMap[x][y] = "-";
            }
        }
        System.out.println("Location of Mines");
        Random random = new Random();

        while (this.numberOfMine != 0) {
            int randomIndexRow = random.nextInt(this.firstMap.length);
            int randomIndexCol = random.nextInt(this.firstMap[0].length);
//            System.out.println(randomIndexRow + "<---Row " + "Col--->" + randomIndexCol);
            if (this.firstMap[randomIndexRow][randomIndexCol] == "-") {
                this.numberOfMine--;
            }
            this.firstMap[randomIndexRow][randomIndexCol] = "*";
        }
        showMatris(this.firstMap);

        System.out.println("===========================");
        System.out.println("Welcome to the Minesweeper Game !");
    }
    // Oyunu başlaması için 2. diziyi ekrana basıp oyunu başlatan metot.
    void startGame() {
        this.gameMap = new String[this.rowNumber][this.colNumber];
        for (int x = 0; x < this.gameMap.length; x++) {
            String[] row = this.gameMap[x];
            for (int y = 0; y < row.length; y++) {
                this.gameMap[x][y] = "-";
            }
        }
        showMatris(this.gameMap);
    }

    /*
    control(): girdiğimiz değerin etrafındaki minimum 3 maksimum 8 kareyi
    kontrol edip o noktanın etrafındaki mayın sayısını bulan ve bulduğu sayıyı
    String'e çevirip ardından kontrol ettiği konuma String olarak atayan metot.
     */
    void control() {
        int counter = 0;
        for (int i = (this.row - 1); i <= (this.row + 1); i++) {
            for (int j = (this.col - 1); j <= (this.col + 1); j++) {
                if (i < 0 || j < 0 || i >= this.rowNumber || j >= this.colNumber) {
                    continue;
                }
                if (this.firstMap[i][j] == "*") {
                    counter++;
                }
            }
        }
        String mineNumber = String.valueOf(counter);
        this.gameMap[row][col] = mineNumber;
        this.firstMap[row][col] = mineNumber;
        showMatris(this.gameMap);

    }

    // Oyunun bitmesi için "-" elemanının kalmadığını ve tüm mayınların temizlendiğini gösteren metot.
    boolean finishGame() {

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.colNumber; j++) {
                if (this.firstMap[i][j] == "-") {
                    return false;
                }
            }
        }
        System.out.println("Congratulations you won. All mines have been cleared");
        return true;
    }
    /*
     chooseIndis(): Kullanıcıdan mayının konumunu aldığımız ve o konumda ki gerekli şartları
     kontrol ettiğimiz metot.
     */
    void chooseIndis() {

        int maxChoicesNumber = (this.colNumber * this.rowNumber) - this.numberOfMine;
        while (maxChoicesNumber != 1) {
            System.out.println("======================");
            System.out.print("Please enter a row number : ");
            this.row = input.nextInt();
            System.out.print("Please enter a column number : ");
            this.col = input.nextInt();

            if (this.row < 0 || this.col < 0 || this.col >= this.colNumber || this.row >= this.rowNumber) {
                System.out.println("Invalid Location. Try again.");
                maxChoicesNumber++;
                continue;
            } else if (this.firstMap[row][col].equals("*")) {
                System.out.println("GAME OVER !");
                break;
            } else if(this.firstMap[row][col] != "-"){
                System.out.println("This location has been entered before, enter another location.");
                maxChoicesNumber++;
            } else {
                control();
                if (finishGame()) {
                    break;
                }
                maxChoicesNumber++;
            }
            maxChoicesNumber--;
        }
    }
    // tüm oyunu çalıştırdığımız metot
    void run() {
        mineSweeperSizeCheck();
        createSweeperWithMine();
        startGame();
        chooseIndis();
    }
}

