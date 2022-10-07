package by.it_academy.lesson10;

import java.util.*;

/**
 * @author Maxim Tereshchenko
 */
class Collections {

    public static void main(String[] args) {
        System.out.println("ArrayList");
        testList(new ArrayList<>());
        System.out.println("LinkedList");
        testList(new LinkedList<>());
        System.out.println("HashSet");
        testSet(new HashSet<>());
        System.out.println("HashMap");
        testMap(new HashMap<>());
    }

    private static void testMap(Map<String, Integer> map) {
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);

        System.out.println("map.size() = " + map.size());
        System.out.println("map.containsKey(\"0\") = " + map.containsKey("0"));
        System.out.println("map.containsKey(\"1\") = " + map.containsKey("1"));
        System.out.println("map.containsValue(0) = " + map.containsValue(0));
        System.out.println("map.containsValue(1) = " + map.containsValue(1));
        System.out.println("map.get(\"0\") = " + map.get("0"));
        System.out.println("map.get(\"1\") = " + map.get("1"));
        System.out.println("map.put(\"5\",0) = " + map.put("5", 0));
        System.out.println("map.remove(\"1\") = " + map.remove("1"));

        System.out.println("map = " + map);
        System.out.println("Iterator");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }

        map.clear();
        System.out.println("map.size() = " + map.size());
        System.out.println("map = " + map);
    }

    private static void testSet(Set<String> set) {
        fill(set);

        System.out.println("set.add(\"1\") = " + set.add("1"));
        System.out.println("set.size() = " + set.size());
        System.out.println("set.remove(\"5\") = " + set.remove("5"));
        System.out.println("set.contains(\"3\") = " + set.contains("3"));
        System.out.println("set.toArray() = " + Arrays.toString(set.toArray()));

        print(set);
        set.clear();
        print(set);
    }

    private static void testList(List<String> list) {
        fill(list);

        System.out.println("list.get(0) = " + list.get(0));
        System.out.println("list.get(1) = " + list.get(1));
        System.out.println("list.indexOf(\"2\") = " + list.indexOf("2"));
        System.out.println("list.indexOf(\"0\") = " + list.indexOf("0"));
        System.out.println("list.set(0,\"6\") = " + list.set(0, "6"));
        list.add(1, "7");
        System.out.println("list.remove(\"5\") = " + list.remove("5"));
        System.out.println("list.toArray() = " + Arrays.toString(list.toArray()));

        print(list);
        list.clear();
        print(list);
    }

    private static void fill(Collection<String> collection) {
        collection.add("1");
        collection.add("2");
        collection.add("3");
        collection.add("4");
        collection.add("5");
    }

    private static void print(Iterable<String> iterable) {
        System.out.println(iterable);
        System.out.println("Iterator");
        for (String str : iterable) {
            System.out.println(str);
        }
    }
}
