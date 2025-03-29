package org.example;

public class BrickBreaker {
    private int ballLife;
    private int points = 0;

    private final String[][] board = new String[7][7];
    private final int[] ballPosition = new int[2];

    final int totalBricks;
    final int debugDelayTime;
    final boolean debugBallMovement;

    BrickBreaker(int[][] bricksPosition, int ballLife, boolean debugBallMovement, float debugDelayTime) {
        this.ballPosition[0] = board.length-1;
        this.ballPosition[1] = (board.length-1)/2;
        this.board[ballPosition[0]][ballPosition[1]] = "o";
        this.ballLife = ballLife;
        this.debugBallMovement = debugBallMovement;
        this.debugDelayTime = (int) (debugDelayTime*1000);
        this.totalBricks = bricksPosition.length;
        prepareBoard(bricksPosition);
    }

    public void printBoard() {
        String temp = this.board[this.ballPosition[0]][this.ballPosition[1]];
        this.board[this.ballPosition[0]][this.ballPosition[1]] = "o";
        for (String[] strings : board) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
        this.board[this.ballPosition[0]][this.ballPosition[1]] = temp;
    }

    public void controlBall(String direction) throws InterruptedException {
        switch (direction) {
            case "st" -> hitBall(-1, 0);
            case "lt" -> hitBall(-1, -1);
            case "rt" -> hitBall(-1, 1);
            default -> System.out.println("Invalid direction");
        }
    }

    private void hitBall(int verticalDirection, int horizontalDirection) throws InterruptedException {
        if (getBallLife() == 0) exitGameWithMessage("Ball Damaged, Your total points: " + this.points);
        int x = this.ballPosition[0];
        int y = this.ballPosition[1];
        board[this.ballPosition[0]][this.ballPosition[1]] = "g";
        debugPrintBoard();

        boolean isBrickHit = false;
        int wallHitCount = 0;

        while (x != board.length-1 || (!isBrickHit && wallHitCount == 0) && this.totalBricks > this.points) {
            if (x == 0) {
                verticalDirection = 1;
                horizontalDirection = 0;
                wallHitCount++;
            }
            else if (y == board.length-1) {
                verticalDirection = 0;
                horizontalDirection = -1;
                wallHitCount++;
            }
            else if (y == 0) {
                verticalDirection = 0;
                horizontalDirection = 1;
                wallHitCount++;
            }
            else if (!board[x][y].equals(" ") && !board[x][y].equals("o") && !board[x][y].equals("w") && !board[x][y].equals("g")) {
                if (isBrickHit) {
                    board[x][y] = " ";
                    this.points++;
                }
                else {
                    board[x][y] = Integer.toString(Integer.parseInt(board[x][y]) - 1);
                    if (board[x][y].equals("0")) {
                        board[x][y] = " ";
                        this.points++;
                    }
                    isBrickHit = true;
                    verticalDirection = 1;
                    horizontalDirection = 0;
                }
            }

            x += verticalDirection;
            y += horizontalDirection;
            this.ballPosition[0] = x;
            this.ballPosition[1] = y;
            debugPrintBoard();
            if (checkWallHit(wallHitCount)) break;
        }

        if (points >= this.totalBricks) exitGameWithMessage("All Brick Hit, You won the game");
        if (isBrickHit) {
            this.ballLife--;
        }
        printBoard();
        this.board[this.ballPosition[0]][this.ballPosition[1]] = "o";
    }

    private void debugPrintBoard() throws InterruptedException {
        if (!this.debugBallMovement) return;
        Thread.sleep(this.debugDelayTime);
        printBoard();
    }

    private void prepareBoard(int[][] bricksPosition) {
        for (int[] ints : bricksPosition) {
            int x = ints[0];
            int y = ints[1];
            int k = ints[2];
            this.board[x][y] = Integer.toString(k);
        }

        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                if (board[i][j] != null) {
                    continue;
                }
                if (i == 0 || j == 0 || j == board[i].length-1) {
                    board[i][j] = "w";
                } else if (i == board.length-1) {
                    board[i][j] = "g";
                }
                else {
                    board[i][j] = " ";
                }
            }
        }
    }

    private int getBallLife() {
        return this.ballLife;
    }

    private boolean checkWallHit(int hitCount) {
        if (hitCount == 2) {
            this.ballPosition[0] = board.length-1;
            this.ballPosition[1] = (board.length-1)/2;
            return true;
        }
        return false;
    }

    private void exitGameWithMessage(String message){
        System.out.println(message);
        System.exit(0);
    }
}