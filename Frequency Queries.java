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

    // Complete the freqQuery function below.
    static List<Integer> freqQuery(List<List<Integer>> queries) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> valueToFreq = new HashMap<>();
        Map<Integer, Integer> freqToCount = new HashMap<>();

        for (List<Integer> query : queries) {
            int operation = query.get(0);
            int value = query.get(1);

            if (operation == 1) {
                // Operation 1: Insert value
                int oldFreq = valueToFreq.getOrDefault(value, 0);
                int newFreq = oldFreq + 1;

                valueToFreq.put(value, newFreq);

                // Update frequency counts
                if (oldFreq > 0) {
                    freqToCount.put(oldFreq, freqToCount.get(oldFreq) - 1);
                }
                freqToCount.put(newFreq, freqToCount.getOrDefault(newFreq, 0) + 1);

            } else if (operation == 2) {
                // Operation 2: Delete one occurrence of value
                int oldFreq = valueToFreq.getOrDefault(value, 0);

                if (oldFreq > 0) {
                    int newFreq = oldFreq - 1;

                    if (newFreq > 0) {
                        valueToFreq.put(value, newFreq);
                    } else {
                        valueToFreq.remove(value);
                    }

                    // Update frequency counts
                    freqToCount.put(oldFreq, freqToCount.get(oldFreq) - 1);
                    if (newFreq > 0) {
                        freqToCount.put(newFreq, freqToCount.getOrDefault(newFreq, 0) + 1);
                    }
                }

            } else if (operation == 3) {
                // Operation 3: Check if any integer has exact frequency of 'value'
                if (freqToCount.getOrDefault(value, 0) > 0) {
                    result.add(1);
                } else {
                    result.add(0);
                }
            }
        }

        return result;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, q).forEach(i -> {
            try {
                queries.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> ans = freqQuery(queries);

        bufferedWriter.write(
            ans.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
