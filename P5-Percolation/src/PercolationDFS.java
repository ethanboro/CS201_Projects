import java.util.Stack;

public class PercolationDFS extends PercolationDefault {
    public PercolationDFS(int n) {
        super(n);
    }

    @Override
    protected void search(int row, int col) {
        int[] rowDelta = {-1,1,0,0};
        int[] colDelta = {0,0,-1,1};

        if (! inBounds(row, col) || myGrid[row][col] != OPEN) return;

        Stack<int[]> stack = new Stack<>();
        myGrid[row][col] = FULL;  // mark pixel
        stack.push(new int[]{row,col});
        while (stack.size() != 0){
            int[] coords = stack.pop();
            for(int k=0; k < rowDelta.length; k++){
                row = coords[0] + rowDelta[k];
                col = coords[1] + colDelta[k];
                if (inBounds(row,col) && myGrid[row][col] == OPEN){
                    stack.push(new int[]{row,col});
                    myGrid[row][col] = FULL;
                }
            }
        }
    }


}
