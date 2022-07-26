//Реализовать волновой алгоритм
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class waveAlgoritm {

    public static int[][] readingMatrix() throws Exception { // считывание матрицы из файла
        BufferedReader br = new BufferedReader(new FileReader("file.txt"));
        List<String> lines = new ArrayList<>();
        while (br.ready()) {
            lines.add(br.readLine());
        }
        int matrixWidth = lines.get(0).split(" ").length;
        int matrixHeight = lines.size();
        int[][] matrix = new int[matrixHeight][matrixWidth];
        for (int i = 0; i < matrixHeight; i++) {
            String[] line = lines.get(i).split(" ");
            for (int j = 0; j < matrixWidth; j++) {
                matrix[i][j] = Integer.parseInt(line[j]);
            }
        }
        br.close();
        return matrix;
    }

    static ArrayList<int[]> findBarrier(int[][] matrix) { // Arraylist массивов координат создаем из барьеров
        ArrayList<int[]> barrier = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    barrier.add(new int[] { i, j });
                }
            }
        }
        return barrier;
    }

    public boolean equals(Object obj) { // сравнивание сущностей
        return (this == obj);
    }

    public static boolean isInList(final ArrayList<int[]> list, final int[] candidate) { // метод определения находиться
                                                                                         // ли в эррейлисте массив с
                                                                                         // двумя координатами

        for (final int[] item : list) {
            if (Arrays.equals(item, candidate)) {
                return true;
            }
        }
        return false;
    }

    public static void printMatrix(int[][] matrix) { // метод печати матрицы
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf(" %d ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static boolean findPoint(int[][] matrix, int x, int y, int x1, int y1) { // Нахождение искомой точки
        int yNumber = matrix.length;
        int xNumber = matrix[0].length;
        matrix[x][y] = 1;
        int[] element = new int[3];
        Deque<int[]> deque = new ArrayDeque<>();
        deque.addFirst(new int[] { x, y, matrix[x][y] });
        int x0 = 0;
        int y0 = 0;
        int value = 0;
        int x2 = 0;
        int y2 = 0;
        while (deque.size() != 0 && matrix[x1][y1] == 0) {
            element = deque.pollFirst();
            x0 = element[0];
            y0 = element[1];
            if (((x0 + 1) < xNumber) && matrix[x0 + 1][y0] == 0) {
                value = element[2] + 1;
                x2 = element[0] + 1;
                matrix[x2][y0] = value;
                deque.addLast(new int[] { x2, y0, value });
            }
            if (matrix[x0][y0 - 1] == 0 && ((y0 - 1) >= 0)) {
                value = element[2] + 1;
                y2 = element[1] - 1;
                matrix[x0][y2] = value;
                deque.addLast(new int[] { x0, y2, value });
            }
            if (((x0 - 1) >= 0) && matrix[x0 - 1][y0] == 0) {
                value = element[2] + 1;
                x2 = element[0] - 1;
                matrix[x2][y0] = value;
                deque.addLast(new int[] { x2, y0, value });
            }
            if (((y0 + 1) < yNumber) && matrix[x0][y0 + 1] == 0) {
                value = element[2] + 1;
                y2 = element[1] + 1;
                matrix[x0][y2] = value;
                deque.addLast(new int[] { x0, y2, value });
            }
        }
        if (deque.isEmpty())
            return false;
        else
            return true;
    }

    public static void findPath(int[][] matrix, int x, int y, ArrayList<int[]> path, ArrayList<int[]> barriers) { // находим
                                                                                                                  // обратный
                                                                                                                  // путь

        path.add(new int[] { x, y });
        if (matrix[x][y] == 1) {
            return;
        } else {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int x1 = x + i;
                    int y1 = y + j;
                    int[] coord = new int[] { x1, y1 };
                    if (!isInList(barriers, coord) && x1 >= 0 && y1 >= 0 && x1 < matrix.length && y1 < matrix[x1].length
                            && matrix[x1][y1] == matrix[x][y] - 1) {
                        findPath(matrix, x1, y1, path, barriers);
                    }
                }
            }
        }
    }

    static void printPath(ArrayList<int[]> arList, int x, int y) { // печать пути
        for (int i = 0; i < arList.size(); i++) {
            int[] array = arList.get(i);
            if (array[0] == x && array[1] == y) {
                System.out.printf("( %d, %d )", array[0], array[1]);
                break;
            } else {
                System.out.printf("( %d, %d ) ->", array[0], array[1]);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int[][] someMatrix = readingMatrix();
        printMatrix(someMatrix);
        int startX = 9;
        int startY = 2;
        int finishX = 0;
        int finishY = 9;
        System.out.println();
        ArrayList<int[]> barriers = findBarrier(someMatrix);
        if (findPoint(someMatrix, startX, startY, finishX, finishY)) {
            printMatrix(someMatrix);
            System.out.println("Вариант пути:");
            ArrayList<int[]> waveWay = new ArrayList<>();
            findPath(someMatrix, finishX, finishY, waveWay, barriers);
            printPath(waveWay, startX, startY);
        } else {
            System.out.println("нет решения");
            printMatrix(someMatrix);
        }
    }
}
