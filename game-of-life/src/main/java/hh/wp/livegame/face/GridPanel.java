package hh.wp.livegame.face;

import hh.wp.livegame.entity.Cell;
import hh.wp.livegame.entity.ConstantValue;
import hh.wp.livegame.myenum.State;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Created by root on 17-6-3.
 * 画网格的界面
 */
public class GridPanel extends JPanel {
    private int COL = ConstantValue.COLUMN;
    private int ROW = ConstantValue.ROW;
    private int CELL_WIDTH = ConstantValue.CELL_WIDTH;
    private int CELL_HEIGHT = ConstantValue.CELL_HEIGHT;
    private Cell[][] cells = null;

    public GridPanel() {
        setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        drawGrid(g2d);
        drawCells(g2d);
    }

    /**
     * @Date: 17-6-3 下午5:01
     * @Author:root
     * @Desc: 根据二维数组绘制活的细胞, 蓝色矩形显示, 死的用灰色
     */
    private void drawCells(Graphics2D g2d) {
        if (cells == null) {
            return;
        }
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                State state = cells[i][j].getState();
                if (state == State.LIVE) {
                    g2d.setColor(Color.BLUE);
                    g2d.fillRect(i * CELL_HEIGHT + 2, j * CELL_WIDTH + 2,
                            CELL_HEIGHT - 2, CELL_WIDTH - 2);
                } else {
                    g2d.setColor(Color.GRAY);
                    g2d.fillRect(i * CELL_HEIGHT + 2, j * CELL_WIDTH + 2,
                            CELL_HEIGHT - 2, CELL_WIDTH - 2);
                }
            }
        }
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < ROW; i++) {
            // 横线
            g.drawLine(i * CELL_HEIGHT, 0, i * CELL_HEIGHT, ConstantValue.WIDTH);
            // 竖线
            g.drawLine(0, i * CELL_WIDTH, ConstantValue.HEIGHT, i * CELL_WIDTH);
        }
    }

    public void refreshGrid(Cell[][] cells) {
        this.cells = cells;
        this.repaint();
    }

}
