import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MinConflictsQueensSolver {

    private static class Board {

        /*
         * The row for each column, e.g. [3,7,0,4] represents
         *     __*_____
         *     _____*__
         *     *_______
         *     ___*____
	 */
        
        int[] rows;
        Random random = new Random();

        //Create N x N board and randomly fills it with one queen in each column.
        Board(int n) {
        	rows = new int[n];
        	scramble();
        }

        // Shuffle queens (1 at each column)
        void scramble() {
            for (int i = 0, n = rows.length; i < n; i++) {
                rows[i] = i;
            }
            for (int i = 0, n = rows.length; i < n; i++) {
                int j = random.nextInt(n);
                int rowToSwap = rows[i];
                rows[i] = rows[j];
                rows[j] = rowToSwap;
            }

        }


        // Returns the number of queens that conflict with queen at (row,col), not
        // counting the queen in column col.
        int conflicts(int row, int col) {
            int count = 0;
            for (int c = 0; c < rows.length; c++) {
                if (c == col) {
                	continue;
                	}
                int QueenColum = rows[c];
                
                // Check if in the vertical/horizontal and diagonal conflicts
                if (QueenColum == row || Math.abs(QueenColum-row) == Math.abs(c-col)) count++;
            }
            return count;
        }

        // Fill board with a legal arrangement of queens.
        void solve() {
            ArrayList<Integer> candidates = new ArrayList<Integer>();

            while (true) {
                // Find worst queen
                int maxConflicts = 0;
                candidates.clear();
                
                for (int c = 0; c < rows.length; c++) {
                    int conflicts = conflicts(rows[c], c);
                    	if (conflicts == maxConflicts) {
                    		candidates.add(c);
                    	} else if (conflicts > maxConflicts) {
                    		maxConflicts = conflicts;
                    		candidates.clear();
                    		candidates.add(c);
                    	}
                }

                if (maxConflicts == 0) {
                    // Checked every queen and found no conflicts
                    return;
                }

                // Pick a random queen from those that had the most conflicts
                int worstQueenColumn =
                        candidates.get(random.nextInt(candidates.size()));

                // Move her to the place with the least conflicts
                int minConflicts = rows.length;
                candidates.clear();
                
                for (int r = 0; r < rows.length; r++) {
                    int conflicts = conflicts(r, worstQueenColumn);
                    if (conflicts == minConflicts) {
                        candidates.add(r);
                    } else if (conflicts < minConflicts) {
                        minConflicts = conflicts;
                        candidates.clear();
                        candidates.add(r);
                    }
                }

                if (!candidates.isEmpty()) {
                    rows[worstQueenColumn] =
                        candidates.get(random.nextInt(candidates.size()));
                }
           }
        }

        
        //Prints the board
        void print() {
            for (int r = 0; r < rows.length; r++) {
                for (int c = 0; c < rows.length; c++) {
                	if(rows[c] == r ) {
                		System.out.print('*');
                	}
                	else {
                		System.out.print('_');
                	}
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
    	Scanner in = new Scanner(System.in);
    	int num = in.nextInt();
    	in.close();
    	
        Board board = new Board(num);
        board.solve();
        
        board.print();
    }
}