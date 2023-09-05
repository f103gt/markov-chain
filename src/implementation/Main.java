package implementation;

import implementation.MarkovChain;

public class Main {
    public static void main(String[] args) {
        double mb = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();
        System.out.println("memory available to be allocated: " + runtime.maxMemory() / mb +
                "\nmemory allocated at the start of the program: " + runtime.totalMemory() / mb
                + "\n");
        String[] words = FileReader.read("lyrics.txt");
        runtime.gc();
        System.out.println("memory occupied after reading the file: " +
                (runtime.totalMemory() - runtime.freeMemory()) / mb + "\n");
        MarkovChain chain = new MarkovChain(2);
        chain.preparation(words);
        System.out.println("memory occupied after creating a hash map: " +
                (runtime.totalMemory() - runtime.freeMemory()) / mb + "\n");
        chain.compose();
        System.out.println("\n\ntotal amount of memory occupied by the program: " +
                (runtime.totalMemory() - runtime.freeMemory()) / mb);
    }
}
