package hh.wp.livegame;

import hh.wp.livegame.entity.Cell;
import hh.wp.livegame.myenum.State;
import hh.wp.livegame.service.LiveGameAlgorithm;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by root on 17-6-3.
 */
public class LiveGameAlgorithmTest {

    private LiveGameAlgorithm liveGameAlgorithm = new LiveGameAlgorithm();

    @Test
    public void getState() throws Exception {
        Cell cell = new Cell();
        cell.setState(State.LIVE);
        State state = liveGameAlgorithm.getCellState(cell, 3);
        assertEquals(State.LIVE, state);

        cell.setState(State.LIVE);
        state = liveGameAlgorithm.getCellState(cell, 1);
        assertEquals(State.DIE, state);

        cell.setState(State.DIE);
        state = liveGameAlgorithm.getCellState(cell, 3);
        assertEquals(State.LIVE, state);

        cell.setState(State.DIE);
        state = liveGameAlgorithm.getCellState(cell, 2);
        assertEquals(State.DIE, state);
    }

    @Test
    public void getAroundCellsNumber() throws Exception {
        Cell[][] cells = {
                {new Cell(0, 0, State.LIVE), new Cell(0, 1, State.LIVE), new Cell(0, 2, State.LIVE)},
                {new Cell(1, 0, State.LIVE), new Cell(1, 1, State.LIVE), new Cell(1, 2, State.LIVE)},
                {new Cell(2, 0, State.LIVE), new Cell(2, 1, State.LIVE), new Cell(2, 2, State.LIVE)}
        };
        Cell cell = cells[1][1];
        int n = liveGameAlgorithm.getAroundCellsNumber(cells, cell);
        assertEquals(8, n);
    }

}
