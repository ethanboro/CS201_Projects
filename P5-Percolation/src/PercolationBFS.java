import java.util.*;

public class PercolationBFS extends PercolationDefault{
    public PercolationBFS(int n) {
        super(n);
    }

    @Override
    public void search(int row, int col) {
        // out of bounds?
		int[] rowDelta = {-1,1,0,0};
        int[] colDelta = {0,0,-1,1};

        if (! inBounds(row, col) || myGrid[row][col] != OPEN) return;

        Queue<int[]> queue = new LinkedList<>();
        myGrid[row][col] = FULL;  // mark pixel
        queue.add(new int[]{row,col});
        while (queue.size() != 0){
            int[] coords = queue.remove();
            for(int k=0; k < rowDelta.length; k++){
                row = coords[0] + rowDelta[k];
                col = coords[1] + colDelta[k];
                if (inBounds(row,col) && myGrid[row][col] == OPEN){
                    queue.add(new int[]{row,col});
                    myGrid[row][col] = FULL;
                }
            }
        }
    }
}
