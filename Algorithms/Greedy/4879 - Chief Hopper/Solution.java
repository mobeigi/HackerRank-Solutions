import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    // Complete the chiefHopper function below.
    static int chiefHopper(int[] arr) {
        // Start by finding max height O(log n)
        int[] sortedArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sortedArr);
        final int maxHeight = sortedArr[sortedArr.length - 1];
        final int minHeight = 1;

        // Use map to compute energy as we go for all valid starting energies
        HashMap<Integer, Long> currentEnergy = new HashMap<>();

        // Initially, all targets between minHeight and maxHeight are valid
        for (int target = minHeight; target <= maxHeight; ++target)
        {
            currentEnergy.put(target, (long) target);
        }

        // Check strictly decreasing case in O(N)
        for (int i = 0; i < arr.length - 1; ++i)
        {
            if (arr[i] <= arr[i + 1])
                break;

            if (i == arr.length - 2)
                return arr[0];
        }

        // For every building height
        for (int h : arr)
        {
            // Test all valid starting energies
            Iterator<Integer> iterator = currentEnergy.keySet().iterator();
            while (iterator.hasNext())
            {
                int target = iterator.next();
                Long newEnergy = currentEnergy.get(target);

                // Energy computations
                if (newEnergy > h)
                    newEnergy = newEnergy + (newEnergy - h);
                else if (newEnergy < h)
                    newEnergy = newEnergy - (h - newEnergy);

                // Update deathset and current energy
                if (newEnergy < 0)
                    iterator.remove();
                else
                    currentEnergy.put(target, newEnergy);

                // Early exit check
                if (currentEnergy.size() == 1)
                    return currentEnergy.entrySet().iterator().next().getKey();
            }
        }

        // Return minimum key value
        return Collections.min(currentEnergy.keySet());
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int result = chiefHopper(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
