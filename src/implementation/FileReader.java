package implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class FileReader {
    public static String[] read(String path) {
        ArrayList<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            scanner.useDelimiter("[^!-z]+");
            while (scanner.hasNextLine()) words.add(scanner.next());
        } catch (FileNotFoundException e) {
            try (Scanner scanner = new Scanner("lyrics.txt")) {
                scanner.useDelimiter("[^!-z]+");
                while (scanner.hasNextLine()) words.add(scanner.next());
                System.out.println(e + "Your file was replaces by default one");
            }
        }
        return words.toArray(new String[0]);
    }
}

class MarkovChain {
    HashMap<String, Values> map;
    int prefix;

    private static class Values {
        public ArrayList<String> data;
        public int index;

        public Values(String str) {
            this.data = new ArrayList<>();
            this.data.add(str);
            this.index = 0;
        }
    }

    public MarkovChain(int prefix) {
        this.map = new HashMap<>();
        this.prefix = prefix;
    }

    public void preparation(String[] words) {
        for (int i = 0; i < words.length - prefix; i++) {
            StringBuilder key = new StringBuilder();
            for (int j = i + 1; j < i + prefix + 1; j++) key.append(' ').append(words[j]);
            String value = i + prefix + 1 < words.length ? words[i + prefix + 1] : "";
            if (!this.map.containsKey(key.toString())) {
                this.map.put(key.toString(), new Values(value));
            } else {
                this.map.get(key.toString()).data.add(value);
            }
        }
    }

    public void compose() {
        String[] keys = map.keySet().toArray(new String[0]);
        for (int i = 0; i < keys.length % 35; i++) {
            Random rand = new Random();
            for (int j = 0; j < 2 + rand.nextInt(2); j++) {
                int n = rand.nextInt(keys.length);
                System.out.print(keys[n] + " " +
                        this.map.get(keys[n]).data.get(this.map.get(keys[n]).index
                                % this.map.get(keys[n]).data.size()) + " ");
                this.map.get(keys[n]).index++;
            }
            if(i!=0 && i%5==0) System.out.println();
            System.out.println();
        }
    }
}



