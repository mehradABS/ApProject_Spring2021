package Models;

import Logic.Constant;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyBoard {
    public boolean removeFile = false;
    File BoardFile;
    private FileWriter writer;

    private boolean IsConfused = false;

    private final ImageIcon bigBoard = new ImageIcon(
            "finalproject\\photo\\bigboard.png");
    private final ImageIcon smallBoard = new ImageIcon(
            "finalproject\\photo\\smallboard.png");
    private final ImageIcon commonBoard = new ImageIcon(
            "finalproject\\\\photo\\launchpad.png");
    private final int Velocity = 10;

    private Image boardImage;

    private int x_board;

    private final int y_board;

    public MyBoard(String save) {
        y_board = 940;
    }

    public MyBoard() throws IOException {
        boardImage = commonBoard.getImage();
        x_board = 990 - boardImage.getWidth(null) / 2;
        y_board = 940;
        BoardFile = new File("src\\Save\\Players\\" +
                Player.id + "\\" + Game.id + "\\board.txt");
        Save();
    }

    public Image getBoardImage() {
        return boardImage;
    }

    public int getX_board() {
        return x_board;
    }

    public void setX_board(int x_board) {
        this.x_board = x_board;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSavedX_board(int x_board) {
        this.x_board = x_board;
    }

    public int getY_board() {
        return y_board;
    }

    public void RightAction() {
        if (this.getX_board() + this.getBoardImage().getWidth(null) <=
                Constant.PANEL_WIDTH - 101) {
            this.setX_board(this.getX_board() + Velocity);
        } else {
            this.setX_board(Constant.PANEL_WIDTH - 90 -
                    this.getBoardImage().getWidth(null));
        }
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LeftAction() {
        if (this.getX_board() >= 20) {
            this.setX_board(this.getX_board() - Velocity);
        } else {
            this.setX_board(0);
        }
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBoardImage(Image boardImage) {
        this.boardImage = boardImage;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSavedBoardImage(Image boardImage) {
        this.boardImage = boardImage;
    }

    public ImageIcon getBigBoard() {
        return bigBoard;
    }

    public ImageIcon getSmallBoard() {
        return smallBoard;
    }

    public ImageIcon getCommonBoard() {
        return commonBoard;
    }

    public boolean isNotConfused() {
        return !IsConfused;
    }

    public void setConfused(boolean confused) {
        IsConfused = confused;
        try {
            Save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSaveConfused(boolean confused) {
        IsConfused = confused;
    }

    public void setBoardFile(File boardFile) {
        BoardFile = boardFile;
    }

    public void Save() throws IOException {
        if (!removeFile) {
            if (!BoardFile.exists()) {
                BoardFile.getParentFile().mkdirs();
                BoardFile.createNewFile();
            }
            writer = new FileWriter(BoardFile, false);
            writer.write(IsConfused + "\n");
            if (boardImage == smallBoard.getImage()) {
                writer.write("small\n");
            } else if (boardImage == commonBoard.getImage()) {
                writer.write("common\n");
            } else {
                writer.write("big" + "\n");
            }
            writer.write(x_board + "\n");
            writer.flush();
            writer.close();
        }
    }
}
