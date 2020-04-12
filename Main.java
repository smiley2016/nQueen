import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    private static ArrayList<Integer> leftDiagonal = new ArrayList<>();
    private static ArrayList<Integer> rightDiagonal = new ArrayList<>();
    private static ArrayList<Queen> q = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> q1 = new ArrayList<>();
    private static int n = 100;

    static class Queen{
        private int col;
        private int left;
        private int right;

        Queen(int col) {
            this.col = col;
        }

        int getCol() {
            return col;
        }

        int getLeft() {
            return left;
        }

        int getRight() {
            return right;
        }

        void setCol(int col) {
            this.col = col;
        }

        void setLeft(int left) {
            this.left = left;
        }

        void setRight(int right) {
            this.right = right;
        }
    }

    public static void main(String[] args) {
        System.out.print("Adja meg a kiralynok szamat: ");
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();

        for (int i = 0; i<n; ++i){
            q.add(new Queen(i));
        }

        for(int i = 0; i<n; ++i){
            q1.add(i,new ArrayList<>());
        }

        for (int i = 0; i<n; ++i) {
            for (int j = 0; j < n; ++j) {
                q1.get(i).add(0);
            }
        }

        do{
            int nSwaps;
            randomPermutation(n);
            do{
                nSwaps = 0;
                for (int i = 0; i<n; ++i){
                    for (int j = i+1; j<n; ++j){
                        if(is_attacked(i) || is_attacked(j)){
                            boolean check = worthSwapping(i,j);
                            if(check){
                                nSwaps+=1;
                            }
                        }
                    }
                }
            }while (nSwaps != 0);
        }while (checkCollision());

        System.out.println("Done");

        System.out.print("Queens: ");

        for (Queen i : q) {
            System.out.print(i.getCol()+ " ");
        }
        System.out.println();

        System.out.println("Diagonals");
        print(rightDiagonal);
        print(leftDiagonal);

        System.out.println("Solved matrix");

        printMatrix();
    }

    private static boolean is_attacked(int x) {
        int i ,j;
        i = q.get(x).getLeft();
        j = q.get(x).getRight();

        return isQueenAttacked(i,j);
    }

    private static boolean isQueenAttacked(int leftIndex, int rightIndex){
        return leftDiagonal.get(leftIndex) > 1 || rightDiagonal.get(rightIndex) > 1;
    }

    private static void printMatrix(){
        for(int i = 0; i<n; ++i){
            for(int j = 0; j<n; ++j){
                System.out.print(q1.get(i).get(j)+" ");
            }
            System.out.println();
        }
    }

    private static boolean worthSwapping(int i, int j){
        int before = 0;
        int after = 0;
        for(int k = 0; k<2*n-1; ++k){
            if(leftDiagonal.get(k) > 1){
                before+=leftDiagonal.get(k)-1;
            }
            if(rightDiagonal.get(k) > 1){
                before+=rightDiagonal.get(k)-1;
            }
        }

//        System.out.println("before swap " + before);
        performSwap(i, j);
        calculateDiagonals();
        for(int k = 0; k<2*n-1; ++k){
            if(leftDiagonal.get(k) > 1){
                after+=leftDiagonal.get(k)-1;
            }
            if(rightDiagonal.get(k) > 1){
                after+=rightDiagonal.get(k)-1;
            }
        }
        //      System.out.println("after swap " + after);
//        System.out.println("rightdiagonal:");
//        print(rightDiagonal);
//        System.out.println("leftdiagonal:");
//        print(leftDiagonal);
        if(before > after){
            return true;
        }else{
            performSwap(j, i);
            calculateDiagonals();
            return false;
        }
    }

    private static void performSwap(int col1, int col2){
        q1.get(col1).set(q.get(col1).getCol(),0);
        q1.get(col1).set(q.get(col2).getCol(),1);
        q1.get(col2).set(q.get(col2).getCol(),0);
        q1.get(col2).set(q.get(col1).getCol(),1);

        int tempLeft = q.get(col1).getLeft();
        int tempRight = q.get(col1).getRight();
        q.get(col1).setLeft(q.get(col2).getLeft());
        q.get(col1).setRight(q.get(col2).getRight());
        q.get(col2).setLeft(tempLeft);
        q.get(col2).setRight(tempRight);

        int temp = q.get(col1).getCol();
        q.get(col1).setCol(q.get(col2).getCol());
        q.get(col2).setCol(temp);
    }

    private static boolean checkCollision() {
        for(int i = 0; i< 2*n-1; ++i){
            if(leftDiagonal.get(i) > 1  || rightDiagonal.get(i) > 1){
                return true;
            }
        }
        return false;
    }

    private static void randomPermutation(int n){
        for(int i = 0; i<n; ++i){
            q1.get(i).set(q.get(i).getCol(),0);
        }

        Collections.shuffle(q);
        leftDiagonal.clear();
        rightDiagonal.clear();

        for(int i = 0; i<2*n-1; ++i){
            leftDiagonal.add(0);
            rightDiagonal.add(0);
        }

        for(int i = 0; i<n; ++i){
            q1.get(i).set(q.get(i).getCol(),1);
        }

        calculateDiagonals();
    }

    private static void calculateDiagonals(){
        for (int i =0 ; i < 2*n-1; ++i){
            leftDiagonal.set(i,0);
            rightDiagonal.set(i,0);
        }

        for( int k = 0 ; k < n * 2 ; k++ ) {
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if( i < n && j < n ) {
                    if(q1.get(i).get(j) == 1){
                        leftDiagonal.set(k, leftDiagonal.get(k)+1);
                        q.get(i).setLeft(k);
                    }
                }
            }
        }
        int diagonal = 0;
        for(int i = 0; i<n; ++i){
            int j  = n-1;
            int k = i;
            while(j>=k && k >= 0) {
                if(q1.get(k).get(j) == 1){
                    rightDiagonal.set(i, rightDiagonal.get(i) + 1);
                    q.get(k).setRight(i);
                }
                k--;
                j--;
            }
            diagonal = i;
        }
        diagonal++;

        for(int j = n-2; j>=0; --j){
            int i = n-1;
            int k = j;
            while (i>0 && k>=0){

                if(q1.get(i).get(k)==1){
                    rightDiagonal.set(diagonal, rightDiagonal.get(diagonal)+1);
                    q.get(i).setRight(diagonal);
                }
                k--;
                i--;
            }
            diagonal++;
        }
    }

    private static void print(ArrayList list){
        for (Object o : list) {
            System.out.print(o + " ");
        }
        System.out.println();
    }

}