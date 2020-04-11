import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static ArrayList<Integer> leftDiagonal = new ArrayList<>();
    private static ArrayList<Integer> rightDiagonal = new ArrayList<>();
    private static ArrayList<Queen> q = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> q1 = new ArrayList<>();
    private static int n = 4;

    static class Queen{
        private int col;
        private int left;
        private int right;

        Queen(int col) {
            this.col = col;
        }

        public Queen(int col, int left, int right) {
            this.col = col;
            this.left = left;
            this.right = right;
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
                for (int i = 0; i<q.size(); ++i){
                    for (int j = i+1; j<q.size(); ++j){
                        if(is_attacked(i) || is_attacked(j)){
                            if(isSwappable(i,j)){
                                performSwap(i,j);
                                nSwaps++;
                            }
                        }
                    }
                }
                if(!checkCollision()){
                    break;
                }
            }while (nSwaps == 0);
        }while (checkCollision());

        System.out.println("Kesz");

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

    private static boolean isSwappable(int i, int j){
        int count = 0;
        for(int k = 0; k<leftDiagonal.size(); ++k){
            count += leftDiagonal.get(k);
            count += rightDiagonal.get(k);
        }
        performSwap(i, j);
        for(int k = 0; k<leftDiagonal.size(); ++k){
            count -= leftDiagonal.get(k);
            count -= rightDiagonal.get(k);
        }
        if(count > 0){
            performSwap(j, i);
            return false;
        }else{
            performSwap(j, i);
            return true;
        }
    }

    private static void performSwap(int col1, int col2){
        q1.get(col1).set(q.get(col1).getCol(),0);
        q1.get(col1).set(q.get(col2).getCol(),1);
        q1.get(col2).set(q.get(col2).getCol(),0);
        q1.get(col2).set(q.get(col1).getCol(),1);

        int temp = q.get(col1).getLeft();
        int temp2 = q.get(col1).getRight();
        q.get(col1).setLeft(q.get(col2).getLeft());
        q.get(col1).setRight(q.get(col2).getRight());
        q.get(col2).setLeft(temp);
        q.get(col2).setRight(temp2);

        q.get(col1).setCol(q.get(col2).getCol());
        q.get(col2).setCol(q.get(col1).getCol());

        calculateDiagonals();
    }

    private static boolean checkCollision() {
        for(int i = 0; i< leftDiagonal.size(); ++i){
            if(leftDiagonal.get(i) > 1  || rightDiagonal.get(i) > 1){
                return true;
            }
        }
        return false;
    }

    private static void randomPermutation(int n){
        leftDiagonal.clear();
        rightDiagonal.clear();
        q.clear();
        q1.clear();

        for(int i = 0; i<n; ++i){
            q1.add(i,new ArrayList<>());
        }

        for (int i = 0; i<n; ++i) {
            for (int j = 0; j < n; ++j) {
                q1.get(i).add(0);
            }
        }

        for (int i = 0; i<n; ++i){
            q.add(new Queen(-1));
        }

        for(int i = 0; i<2*n-1; ++i){
            leftDiagonal.add(0);
            rightDiagonal.add(0);
        }


        for(int i = 0; i<n; ++i){
            q.get(i).setCol(i);
        }

        Collections.shuffle(q);

        for(int i = 0; i<n; ++i){
            q1.get(i).set(q.get(i).getCol(), 1);
        }

        for(int i = 0; i<n; ++i){
            for(int j = 0; j<n; ++j){
                System.out.print(q1.get(i).get(j)+" ");
            }
            System.out.println();
        }

        calculateDiagonals();

        print(rightDiagonal);
        print(leftDiagonal);
    }

    private static void calculateDiagonals(){
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
