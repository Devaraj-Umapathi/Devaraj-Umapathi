public class Main {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>(8, 0.75f);

        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        map.put("date", 4);
        map.put("elderberry", 5);

        System.out.println("get(\"banana\") = " + map.get("banana"));
        System.out.println("get(\"cherry\") = " + map.get("cherry"));
        System.out.println("size = " + map.size());

        System.out.println("\n--- Bucket layout ---");
        map.printBuckets();

        map.put("banana", 20);
        System.out.println("\nAfter update banana -> 20: " + map.get("banana"));

        map.remove("apple");
        System.out.println("After remove apple, contains? " + map.containsKey("apple"));
        System.out.println("size = " + map.size());

        for (int i = 0; i < 20; i++) {
            map.put("k" + i, i);
        }
        System.out.println("\nAfter inserting 20 more keys (resize triggered):");
        System.out.println("size = " + map.size());
        System.out.println("get(\"k15\") = " + map.get("k15"));
    }
}
