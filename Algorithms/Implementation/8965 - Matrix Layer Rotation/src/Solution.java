import java.awt.geom.Point2D;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    // Complete the matrixRotation function below.
    static void matrixRotation(List<List<Integer>> matrix, int r)
    {
        if (matrix.isEmpty())
            return;

        // Indexes for row left and right bounds, column top and bottom bounds
        // 2 <= m, n <= 300
        short row_l = 0, row_r = (short)(matrix.size() - 1),
                col_l = 0, col_r = (short)(matrix.get(0).size() - 1);

        while (col_l < col_r && row_l < row_r)
        {
            // Spiral length is number of elements it takes to rotate element back to its current position
            int row_width = (row_r - col_l) + 1; //number of elements between row bounds
            int col_width = (col_r - col_l) + 1; //number of elements between col bounds
            int spiralLength = 2 * (row_width - 1) + 2 * (col_width - 1);
            int rModulo = r % spiralLength; // Optimization: reduce rotation from r <= 10^9 space to r <= [0, spiralLength)

            // If rModulo is 0, no rotation is needed as identity will be returned
            if (rModulo != 0)
            {
                for (int i = 0; i < rModulo; ++i)
                    performSingleRotation(matrix, row_l, row_r, col_l, col_r);
            }

            // Move to inner grid while inner grid exists
            ++row_l;
            --row_r;
            ++col_l;
            --col_r;
        }

        // Print solution to stdout
        printMatrix(matrix);
    }

    private static void performSingleRotation(List<List<Integer>> matrix, int row_l, int row_r, int col_l, int col_r)
    {
        int firstElementTmp = matrix.get(row_l).get(col_l);

        // Rotate left, elements in (row_l, ?)
        for (int i = col_l; i < col_r; ++i)
            matrix.get(row_l).set(i, matrix.get(row_l).get(i+1));
        matrix.get(row_l).set(col_r, matrix.get(row_l+1).get(col_r));

        // Rotate up
        for (int i = row_l; i < row_r; ++i)
            matrix.get(i).set(col_r, matrix.get(i+1).get(col_r));
        matrix.get(row_r).set(col_r, matrix.get(row_r).get(col_r-1));

        // Rotate right
        for (int i = col_r; i > col_l; --i)
            matrix.get(row_r).set(i, matrix.get(row_r).get(i-1));
        matrix.get(row_r).set(col_l, matrix.get(row_r-1).get(col_l));

        // Rotate down
        for (int i = row_r; i > row_l; --i)
            matrix.get(i).set(col_l, matrix.get(i-1).get(col_l));

        matrix.get(row_l+1).set(col_l, firstElementTmp);
    }

    private static void testCases()
    {
        List<List<Integer>> inputMatrix = new ArrayList<>();

        // Sample Test Case
        inputMatrix.add(Arrays.asList(1,2,3,4));
        inputMatrix.add(Arrays.asList(5,6,7,8));
        inputMatrix.add(Arrays.asList(9,10,11,12));
        inputMatrix.add(Arrays.asList(13,14,15,16));

        matrixRotation(inputMatrix, 2);

        assert(inputMatrix.get(0).equals(Arrays.asList(3,4,8,12)));
        assert(inputMatrix.get(1).equals(Arrays.asList(2,11,10,16)));
        assert(inputMatrix.get(2).equals(Arrays.asList(1,7,6,15)));
        assert(inputMatrix.get(3).equals(Arrays.asList(5,9,13,14)));
        inputMatrix.clear();

        // Test Case 1
        inputMatrix.add(Arrays.asList(9718805,60013003,5103628,85388216,21884498,38021292,73470430,31785927));
        inputMatrix.add(Arrays.asList(69999937,71783860,10329789,96382322,71055337,30247265,96087879,93754371));
        inputMatrix.add(Arrays.asList(79943507,75398396,38446081,34699742,1408833,51189,17741775,53195748));
        inputMatrix.add(Arrays.asList(79354991,26629304,86523163,67042516,54688734,54630910,6967117,90198864));
        inputMatrix.add(Arrays.asList(84146680,27762534,6331115,5932542,29446517,15654690,92837327,91644840));
        inputMatrix.add(Arrays.asList(58623600,69622764,2218936,58592832,49558405,17112485,38615864,32720798));
        inputMatrix.add(Arrays.asList(49469904,5270000,32589026,56425665,23544383,90502426,63729346,35319547));
        inputMatrix.add(Arrays.asList(20888810,97945481,85669747,88915819,96642353,42430633,47265349,89653362));
        inputMatrix.add(Arrays.asList(55349226,10844931,25289229,90786953,22590518,54702481,71197978,50410021));
        inputMatrix.add(Arrays.asList(9392211,31297360,27353496,56239301,7071172,61983443,86544343,43779176));

        matrixRotation(inputMatrix, 40);

        assert(inputMatrix.get(0).equals(Arrays.asList(93754371,53195748,90198864,91644840,32720798,35319547,89653362,50410021)));
        assert(inputMatrix.get(1).equals(Arrays.asList(31785927,25289229,10844931,97945481,5270000,69622764,27762534,43779176)));
        assert(inputMatrix.get(2).equals(Arrays.asList(73470430,90786953,42430633,96642353,88915819,85669747,26629304,86544343)));
        assert(inputMatrix.get(3).equals(Arrays.asList(38021292,22590518,90502426,67042516,54688734,32589026,75398396,61983443)));
        assert(inputMatrix.get(4).equals(Arrays.asList(21884498,54702481,17112485,5932542,29446517,2218936,71783860,7071172)));
        assert(inputMatrix.get(5).equals(Arrays.asList(85388216,71197978,15654690,58592832,49558405,6331115,10329789,56239301)));
        assert(inputMatrix.get(6).equals(Arrays.asList(5103628,47265349,54630910,56425665,23544383,86523163,96382322,27353496)));
        assert(inputMatrix.get(7).equals(Arrays.asList(60013003,63729346,51189,1408833,34699742,38446081,71055337,31297360)));
        assert(inputMatrix.get(8).equals(Arrays.asList(9718805,38615864,92837327,6967117,17741775,96087879,30247265,9392211)));
        assert(inputMatrix.get(9).equals(Arrays.asList(69999937,79943507,79354991,84146680,58623600,49469904,20888810,55349226)));
    }

    private static void printMatrix(List<List<Integer>> matrix)
    {
        for (int i = 0; i < matrix.size(); ++i)
        {
            for (int j = 0; j < matrix.get(0).size(); ++j)
            {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] mnr = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(mnr[0]);

        int n = Integer.parseInt(mnr[1]);

        int r = Integer.parseInt(mnr[2]);

        List<List<Integer>> matrix = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                matrix.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        matrixRotation(matrix, r);

        bufferedReader.close();
    }
}
