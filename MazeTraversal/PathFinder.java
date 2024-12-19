import java.io.*;
import java.util.*;

/* THIS CODE WAS MY (OUR) OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR ONLINE RESOURCES.
Leonardo Lazarevic Darin Andoh-Mensah*/

/*
 * self-referential class to represent a position in a path
 */
class Position {
    public int i; // row
    public int j; // column
    public char val; // 1, 0, or 'X'

    // reference to the previous position (parent) that leads to this position on a
    // path
    Position parent;

    Position(int x, int y, char v) {
        i = x;
        j = y;
        val = v;
    }

    Position(int x, int y, char v, Position p) {
        i = x;
        j = y;
        val = v;
        parent = p;
    }

}

public class PathFinder {

    // main method: reads in maze file and finds path using both stackSearch and
    // queueSearch
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("***Usage: java PathFinder maze_file");
            System.exit(-1);
        }

        char[][] maze;
        maze = readMaze(args[0]);
        printMaze(maze);
        Position[] path = stackSearch(maze);
        if (path == null) {
            System.out.println("Maze is NOT solvable (no valid path identified in stackSearch).");
        } else {
            System.out.println("stackSearch Solution:");
            printPath(path);
            printMaze(maze);
        }

        char[][] maze2 = readMaze(args[0]);
        path = queueSearch(maze2);
        if (path == null) {
            System.out.println("Maze is NOT solvable (no valid path identified in queueSearch).");
        } else {
            System.out.println("queueSearch Solution:");
            printPath(path);
            printMaze(maze2);
        }
    }
    // queueSearch:
    // Uses a queue search algorithim in order to find a path outside of a maze,
    // returning the maze path along a line of Xs or printing "Maze is not solveable" if it isn't solveable
    public static Position[] queueSearch(char[][] maze) {
        //Algorithim 1 implemented here
        //Must instantiate queue as a ArrayDeque due to it being an interface
        Queue<Position> path = new  ArrayDeque<Position>();

        // Adds first positon to array
        Position beginningPosition = new Position(0, 0, '0');
        path.add(beginningPosition);

        Position temp;
        Position[] result;

        while (!path.isEmpty()) {
            //pops element in path to use for traversing other viable locations
            temp = path.remove();
            int tempi = temp.i;
            int tempj = temp.j;
            if (tempi == maze.length - 1 && tempj == maze[0].length - 1) {
                //Algorithim 2 implememtned here
                ArrayList<Position> visited = new ArrayList<Position>();

                while (temp != null) {
                    maze[temp.i][temp.j] = 'X';
                    visited.add(temp);
                    temp = temp.parent;

                }

                result = new Position[visited.size()];

                for (int i = 0; i < result.length; i++) {
                    result[result.length - 1 - i] = visited.get(i);
                }

                return result;
            } else {
                // mark position visted and checks all other avaliable postions to add
                temp.val = '.';
                maze[tempi][tempj] = temp.val;
                if (temp.i != maze.length - 1 && maze[tempi + 1][tempj] == '0') {
                    Position newPosition = new Position(tempi + 1, tempj, '.', temp);
                    path.add(newPosition);
                }
                if (temp.i != 0 && maze[tempi - 1][tempj] == '0') {
                    Position newPosition = new Position(tempi - 1, tempj, '.', temp);
                    path.add(newPosition);
                }
                if (temp.j != maze[0].length - 1 && maze[tempi][tempj + 1] == '0') {
                    Position newPosition = new Position(tempi, tempj + 1, '.', temp);
                    path.add(newPosition);
                }
                if (temp.j != 0 && maze[tempi][tempj - 1] == '0') {
                    Position newPosition = new Position(tempi, tempj - 1, '.', temp);
                    path.add(newPosition);
                }

            }
            //if statement for when the maze isn't solveable
            if (path.isEmpty()) {
                System.out.println("Maze is NOT solveable");
            }
        }
        return null;
    }

    // stackSearch:
    // Uses a stack search algorithim in order to find a path outside of a maze,
    // returning the maze path along a line of Xs or printing "Maze is not solveable" if it isn't solveable
    public static Position[] stackSearch(char[][] maze) {
        //Algorithim 1 implemented here
        Stack<Position> path = new Stack<Position>();

        // Adds first positon to array
        Position beginningPosition = new Position(0, 0, '0');
        path.push(beginningPosition);

        Position temp;
        Position[] result;

        while (!path.isEmpty()) {
            //pops element in path to use for traversing other viable locations
            temp = path.pop();
            int tempi = temp.i;
            int tempj = temp.j;
            if (tempi == maze.length - 1 && tempj == maze[0].length - 1) {
                //Algorithim 2 implememtned here
                ArrayList<Position> visited = new ArrayList<Position>();

                while (temp != null) {
                    maze[temp.i][temp.j] = 'X';
                    visited.add(temp);
                    temp = temp.parent;

                }

                result = new Position[visited.size()];

                for (int i = 0; i < result.length; i++) {
                    result[result.length - 1 - i] = visited.get(i);
                }

                return result;
            } else {
                // mark position visted and checks all other avaliable postions to add
                temp.val = '.';
                maze[tempi][tempj] = temp.val;
                if (temp.i != maze.length - 1 && maze[tempi + 1][tempj] == '0') {
                    Position newPosition = new Position(tempi + 1, tempj, '.', temp);
                    path.push(newPosition);
                }
                if (temp.i != 0 && maze[tempi - 1][tempj] == '0') {
                    Position newPosition = new Position(tempi - 1, tempj, '.', temp);
                    path.push(newPosition);
                }
                if (temp.j != maze[0].length - 1 && maze[tempi][tempj + 1] == '0') {
                    Position newPosition = new Position(tempi, tempj + 1, '.', temp);
                    path.push(newPosition);
                }
                if (temp.j != 0 && maze[tempi][tempj - 1] == '0') {
                    Position newPosition = new Position(tempi, tempj - 1, '.', temp);
                    path.push(newPosition);
                }

            }

            //if statement for when the maze isn't solveable
            if (path.isEmpty()) {
                System.out.println("Maze is NOT solveable");
            }
        }
        return null;
    }

    // prints path through maze
    public static void printPath(Position[] path) {
        System.out.print("Path: ");
        for (Position p : path) {
            System.out.print("(" + p.i + "," + p.j + ") ");
        }
        System.out.println();
    }

    // reads in maze from file
    public static char[][] readMaze(String filename) throws IOException {
        char[][] maze;
        Scanner scanner;
        try {
            scanner = new Scanner(new FileInputStream(filename));
        } catch (IOException ex) {
            System.err.println("*** Invalid filename: " + filename);
            return null;
        }

        int N = scanner.nextInt();
        scanner.nextLine();
        maze = new char[N][N];
        int i = 0;
        while (i < N && scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("\\s+");
            int j = 0;
            for (; j < tokens.length; j++) {
                maze[i][j] = tokens[j].charAt(0);
            }
            if (j != N) {
                System.err.println("*** Invalid line: " + i + " has wrong # columns: " + j);
                return null;
            }
            i++;
        }
        if (i != N) {
            System.err.println("*** Invalid file: has wrong number of rows: " + i);
            return null;
        }
        return maze;
    }

    // prints maze array
    public static void printMaze(char[][] maze) {
        System.out.println("Maze: ");
        if (maze == null || maze[0] == null) {
            System.err.println("*** Invalid maze array");
            return;
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

}
