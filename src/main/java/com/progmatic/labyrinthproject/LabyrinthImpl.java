package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sun.security.util.Length;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    CellType[][] labyrinth;
    Coordinate playerPosition;

    public LabyrinthImpl() {
        //read file
        loadLabyrinthFile("labyrinth1");
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());

            setSize(width, height);

            

            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            labyrinth[hh][ww] = CellType.WALL;
                            break;
                        case 'E':
                            labyrinth[hh][ww] = CellType.END;
                            break;
                        case 'S':
                            labyrinth[hh][ww] = CellType.START;
                            //set playerposition to START
                            playerPosition = new Coordinate(ww, ww);
                            break;
                    }
                }
            }

            

        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }

    }

    @Override
    public int getWidth() {
        if (labyrinth == null) {
            return -1;
        }
        return labyrinth.length;
    }

    @Override
    public int getHeight() {
        if (labyrinth == null) {
            return -1;
        }
        return labyrinth[0].length;

    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if (labyrinth.length < c.getRow() || labyrinth[c.getRow()].length < c.getCol()) {
            throw new CellException(c, "There is no such coordinate!");
        }
        return labyrinth[c.getRow()][c.getCol()];
    }

    @Override
    public void setSize(int width, int height) {
        labyrinth = new CellType[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                labyrinth[i][j] = CellType.EMPTY;
            }
        }
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (labyrinth.length < c.getRow() || labyrinth[c.getRow()].length < c.getCol()) {
            throw new CellException(c, "There is no such coordinate!");
        }
        if (type == CellType.START) {
            playerPosition = new Coordinate(c.getRow(), c.getCol());
        }
        labyrinth[c.getRow()][c.getCol()] = type;
    }

    @Override
    public Coordinate getPlayerPosition() {
        return this.playerPosition;
    }

    @Override
    public boolean hasPlayerFinished() {
        if (labyrinth[getPlayerPosition().getRow()][getPlayerPosition().getCol()] == CellType.END) {
            return true;
        }
        return false;
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> moves = new ArrayList<>();
        if (labyrinth[getPlayerPosition().getRow() + 1][getPlayerPosition().getCol()] != CellType.WALL
                && getPlayerPosition().getRow() >= 1) {
            moves.add(Direction.EAST);
        }
        if (labyrinth[getPlayerPosition().getRow() - 1][getPlayerPosition().getCol()] != CellType.WALL
                && getPlayerPosition().getRow() < getWidth() - 1) {
            moves.add(Direction.WEST);
        }
        if (labyrinth[getPlayerPosition().getRow()][getPlayerPosition().getCol() - 1] != CellType.WALL
                && getPlayerPosition().getCol() >= 1) {
            moves.add(Direction.NORTH);
        }
        if (labyrinth[getPlayerPosition().getRow()][getPlayerPosition().getCol() + 1] != CellType.WALL
                && getPlayerPosition().getCol() <= labyrinth[getPlayerPosition().getCol()].length - 1) {
            moves.add(Direction.SOUTH);
        }

        return moves;

    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        if (!possibleMoves().contains(direction)) {
            throw new InvalidMoveException();
        }
        
        if (direction == Direction.EAST) {
            
            playerPosition = new Coordinate(playerPosition.getRow() - 1, playerPosition.getCol());

        } else if (direction == Direction.WEST) {
           
            playerPosition = new Coordinate(playerPosition.getRow() + 1, playerPosition.getCol());
        } else if (direction == Direction.NORTH) {
            
            playerPosition = new Coordinate(playerPosition.getRow(), playerPosition.getCol() - 1);
        } else {
            
            playerPosition = new Coordinate(playerPosition.getRow(), playerPosition.getCol() + 1);
        }
        
        

    }

}
