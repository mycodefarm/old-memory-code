package hh.wp.livegame.face;

import hh.wp.livegame.entity.Cell;
import hh.wp.livegame.entity.ConstantValue;
import hh.wp.livegame.service.LiveGameAlgorithm;

import javax.swing.JFrame;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by root on 17-6-3.
 * 主界面：包括网格和按钮
 */
public class MainPanel {

    private GridPanel gridPanel;
    private JFrame frame;
    private Button btnBegin;
    private LiveGameAlgorithm liveGameAlgorithm;

    public MainPanel() {
        liveGameAlgorithm = new LiveGameAlgorithm();
    }

    /**
     * @Date: 17-6-3 下午5:02
     * @Author:root
     * @Desc: 初始化界面
     */
    public void initInterface() {
        frame = new JFrame("生命游戏[胡红|王棚]--平衡时可以点击打破平衡");
        frame.setSize(ConstantValue.HEIGHT, ConstantValue.WIDTH);
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridPanel = new GridPanel();
        gridPanel.addMouseListener(new MyMouseListener());

        frame.add(gridPanel);

        btnBegin = new Button("开始");

        btnBegin.addActionListener(new MyBtnActionListener());
        frame.add("South", btnBegin);

        frame.setVisible(true);
    }

    class MyBtnActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            btnBegin.setEnabled(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    play();
                }
            }).start();
        }
    }

    class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int row = x / ConstantValue.CELL_HEIGHT;
            int col = y / ConstantValue.CELL_WIDTH;
            gridPanel.refreshGrid(liveGameAlgorithm.setCellStateLive(row, col));
        }
    }

    private void play() {
        //实现演化
        Cell[][] cells = liveGameAlgorithm.playCells();
//        gridPanel.refreshGrid(cells);
        while (cells != null) {
            gridPanel.refreshGrid(cells);
            cells = liveGameAlgorithm.playCells();
            try {
                Thread.sleep(1000);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        //当结束时
        if (cells == null) {
            btnBegin.setEnabled(true);
        }
    }
}
