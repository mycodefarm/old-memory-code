package hh.wp.livegame.service;

import hh.wp.livegame.entity.Cell;
import hh.wp.livegame.entity.ConstantValue;
import hh.wp.livegame.myenum.State;

/**
 * Created by root on 17-6-3.
 */
public class LiveGameAlgorithm {
    public static final int ROW = ConstantValue.ROW;
    public static final int COLUMN = ConstantValue.COLUMN;
    private Cell[][] cells;

    public LiveGameAlgorithm() {
        initCells();
    }

    /**
     * @Date: 17-6-3 下午5:00
     * @Author:root
     * @Desc: 全部初始化为死
     */
    private Cell[][] initCells() {
        cells = new Cell[ROW][COLUMN];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                cells[i][j] = new Cell(i, j, State.DIE);
            }
        }
        return cells;
    }

    public Cell[][] setCellStateLive(int row, int col) {
        cells[row][col].setState(State.LIVE);
        return cells;
    }

    /**
     * @Date: 17-6-3 下午5:00
     * @Author:root
     * @Desc: 对于演化开始后每次返回变化的结果数组
     */
    public Cell[][] playCells() {
        int countLiveCells = 0;
        Cell[][] tempCells = new Cell[ROW][COLUMN];
        deepCopyArray(tempCells);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                Cell cell = tempCells[i][j];
                State state = getCellState(cell, getAroundCellsNumber(tempCells, cell));
                countLiveCells += (state == State.LIVE ? 1 : 0);
                cells[i][j].setState(state);
            }
        }
        /*游戏结束:1、没有存活的 2、构成平衡状态 3、活的填满数组*/
        if (countLiveCells <= 0) {
            return null;
        }
        return cells;
    }

    private void deepCopyArray(Cell[][] tempCells) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                Cell c = cells[i][j];
                Cell cell = new Cell(c.getRow(), c.getCol(), c.getState());
                tempCells[i][j] = cell;
            }
        }
    }

    public State getCellState(Cell cell, int aroundCellNumber) {
        State state = cell.getState();
        if (cell.getState() == State.DIE && aroundCellNumber == 3) {
            state = State.LIVE;
        } else if (aroundCellNumber < 2 || aroundCellNumber > 3) {
            state = State.DIE;
        }
        return state;
    }

    public int getAroundCellsNumber(Cell[][] cells, Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        int cnt = 0;
        cnt = getNeighborCellCount(cells, row, col)
                + getFourCornerCellCount(cells, row, col);
        return cnt;
    }

    /**
     * @Date: 17-6-3 下午5:00
     * @Author:root
     * @Desc: 取得四个角的活的细胞个数
     */
    private int getFourCornerCellCount(Cell[][] cells, int row, int col) {
        int cnt = 0;
        //right down
        if (isRightColOK(col) && isDownRowOk(row)) {
            cnt += getCellNumberByState(cells, row + 1, col + 1);
        }
        //left down
        if (isLeftColOK(col) && isDownRowOk(row)) {
            cnt += getCellNumberByState(cells, row + 1, col - 1);
        }
        //left up
        if (isLeftColOK(col) && isUpRowOk(row)) {
            cnt += getCellNumberByState(cells, row - 1, col - 1);
        }
        //right up
        if (isRightColOK(col) && isUpRowOk(row)) {
            cnt += getCellNumberByState(cells, row - 1, col + 1);
        }
        return cnt;
    }

    /**
     * @Date: 17-6-3 下午5:01
     * @Author:root
     * @Desc: 取得十字的四个活的细胞个数
     */
    private int getNeighborCellCount(Cell[][] cells, int row, int col) {
        int cnt = 0;
        //up
        if (isUpRowOk(row)) {
            cnt += getCellNumberByState(cells, row - 1, col);
        }
        //down
        if (isDownRowOk(row)) {
            cnt += getCellNumberByState(cells, row + 1, col);
        }
        //left
        if (isLeftColOK(col)) {
            cnt += getCellNumberByState(cells, row, col - 1);
        }
        //right
        if (isRightColOK(col)) {
            cnt += getCellNumberByState(cells, row, col + 1);
        }
        return cnt;
    }

    private boolean isLeftColOK(int col) {
        return col - 1 >= 0;
    }

    private boolean isDownRowOk(int row) {
        return row + 1 < ConstantValue.ROW;
    }

    public boolean isUpRowOk(int row) {
        return row - 1 >= 0;
    }

    public boolean isRightColOK(int col) {
        return col + 1 < ConstantValue.COLUMN;
    }

    public int getCellNumberByState(Cell[][] cells, int row, int col) {
        return cells[row][col].getState() == State.LIVE ? 1 : 0;
    }
}
