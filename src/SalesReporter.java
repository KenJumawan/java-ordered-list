package edu.commonwealthu.mrodriguez.cmsc230.listlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A class for generating sales reports.
 */
public class SalesReporter {

    /**
     * Prints the correct format for command-line arguments.
     */
    private static void printUsage() {
        System.out.println("Usage: SalesReporter [filepath] [k]");
        System.out.println("       filepath: path to sales data. In an IDE, the path is relative to project root.");
        System.out.println("       k: The number of sales to report per salesperson.");
    }

    /**
     * The entry point for generating sales reports.
     *
     * @param args The command-line arguments.
     * @throws IOException On any exception to do with file I/O.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            printUsage();
            return;
        }

        String fileName = args[0]; // The path of the file to read, relative to project root.
        int k;

        try {
            k = Integer.parseInt(args[1]); // The number of sales to report per salesperson.
        } catch (NumberFormatException e) {
            e.printStackTrace();
            printUsage();
            return;
        }

        // STUDENTS: This is the entry point for your implementation of the sales report problem.
        // I suggest that you do not change the lines above.
        // Below, open and parse the file in the variable [fileName],
        // Then print the [k] top sales per reporter.
        // A good solution will involve declaring new methods and class(es).
        // If you create any new classes, be sure to explicitly import them,
        // as failing to do so may break Autolab's build process.

        List<Salesperson> people = readPeople(fileName);
        java.util.Collections.sort(people);

        for (Salesperson sp : people) {
            OrderedList<Float> salesAsc = sp.getSales(); // ascending
            int n = salesAsc.getLength();

            //
            StringBuilder sb = new StringBuilder();
            sb.append(sp.getId()).append(' ')
                    .append(sp.getFirstName()).append(' ')
                    .append(sp.getLastName());

            for (int i = 0; i < k; i++) {
                float v = salesAsc.get(n - 1 - i);
                sb.append(' ').append(String.format(java.util.Locale.US, "$%.2f", v));
            }
            System.out.println(sb.toString());
        }
        return;
    }

    // Reads two-line records
    private static List<Salesperson> readPeople(String fileName) throws IOException {
        List<Salesperson> out = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (true) {
                String header = nextNonBlank(br);
                if (header == null) break;

                // header
                StringTokenizer st = new StringTokenizer(header);
                if (st.countTokens() < 5) throw new IOException("Bad header: " + header);
                st.nextToken(); // year (unused)
                int id = Integer.parseInt(st.nextToken());
                String first = st.nextToken();
                String last = st.nextToken();
                int count = Integer.parseInt(st.nextToken());

                // gather exactly 'count' floats
                OrderedList<Float> sales = new OrderedList<>();
                int taken = 0;
                while (taken < count) {
                    String line = br.readLine();
                    if (line == null) throw new IOException("Missing sales values for id=" + id);
                    StringTokenizer st2 = new StringTokenizer(line);
                    while (st2.hasMoreTokens() && taken < count) {
                        sales.insert(Float.parseFloat(st2.nextToken()));
                        taken++;
                    }
                }

                Salesperson existing = null;
                for (Salesperson s : out) {
                    if (s.getId() == id) { existing = s; break; }
                }
                if (existing == null) {
                    out.add(new Salesperson(id, first, last, sales));
                } else {
                    existing.mergeSales(sales);
                }
            }
        }
        return out;
    }

    private static String nextNonBlank(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) return line;
        }
        return null;
    }

    // format floats
    private static String fmt(float v) {
        if (v == Math.rint(v)) return Long.toString((long) v);
        // Use DecimalFormat without adding imports
        return new java.text.DecimalFormat("0.######").format(v);
    }

    private static class Salesperson implements Comparable<Salesperson> {
        private final int id;
        private final String firstName;
        private final String lastName;
        private OrderedList<Float> sales;

        Salesperson(int id, String firstName, String lastName, OrderedList<Float> sales) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.sales = (sales == null) ? new OrderedList<>() : sales;
        }

        int getId() { return id; }
        String getFirstName() { return firstName; }
        String getLastName() { return lastName; }
        OrderedList<Float> getSales() { return sales; }

        void mergeSales(OrderedList<Float> more) {
            if (more == null || more.getLength() == 0) return;
            this.sales = this.sales.merge(more);
        }

        @Override public int compareTo(Salesperson o) {
            return Integer.compare(this.id, o.id);
        }
        @Override public boolean equals(Object o) {
            return (o instanceof Salesperson) && this.id == ((Salesperson) o).id;
        }
        @Override public int hashCode() {
            return Integer.hashCode(id);
        }
    }
}