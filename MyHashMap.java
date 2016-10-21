import java.util.HashMap;

/**
 * Created by tiranid on 20.10.16.
 */





public class MyHashMap<K, V> {

    int defaultCapacity = 16;
    double coef = 0.75;
    HashElement[] table = new HashElement[defaultCapacity];
    int valCount = 0;
    boolean resizeRunning;


    static class HashElement<K, V> {
        K key;
        V value;
        final int hash;
        HashElement<K, V> next;

        public HashElement(K key, V value, int hash, HashElement next) {
            this.key = key;
            this.value = value;
            this.hash = hash;

            this.next = next;
        }


        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }
    }

    static final int hash(Object keyCode) {
        int h;
        return (keyCode == null) ? 0 : (h = keyCode.hashCode()) ^ (h >>> 16);
    }

    public void put(Object key, Object value) {
        put(key, value, table);
    }

    private void put(Object key, Object value, HashElement[] table) {
        // take hash of key's hashcode
        int i;
        int hash;
        resize();

        if (key == null) {
            i = 0;

            hash = 0;
        }
        else {
            hash = hash(key.hashCode());
            // n - length
            int n = table.length;
            // index of new element
            i = (n - 1) & hash;
        }
        // put element
        if (table[i] == null)
            table[i] = new HashElement<>(key, value, hash, null);
        else
            table[i] = new HashElement<>(key, value, hash, table[i]);
        valCount++;

    }

    /**
     * Неполная функция (в случае коллизии вернет false
     *
     * @param key
     * @return true, if successful remote, else - false
     */
    public boolean remote(K key) {
        if (key == null)
            return false;
        // take hash of key's hashcode
        int hash = hash(key.hashCode());

        // n - length
        int n = table.length;
        // index of element
        int i = (n - 1) & hash;
        if (table[i].next == null) {
            table[i] = null;
            valCount--;
            return true;
        }
        return false;
    }

    /**
     * Only true remote
     *
     * @param key
     * @param value
     */
    public boolean niceRemote(K key, V value) {
        int i;
        valCount--;
        if (key == null)
            i = 0;
        else {


            // take hash of key's hashcode
            int hash = hash(key.hashCode());



            // n - length
            int n = table.length;
            // index of new element
            i = (n - 1) & hash;
        }
        if (table[i] == null) {
            valCount++;
            return false;
        }

        if (table[i].next == null) {
            table[i] = null;
            return true;
        } else {
            HashElement current = table[i];
            if (current.value == value) {
                table[i] = table[i].next;
                return true;
            }

            HashElement prev = table[i];
            current = current.next;

            while (current != null && current.value != value) {
                prev = current;
                current = current.next;
            }
            // table hasnt value
            if (current == null) {
                valCount++;
                return false;
            }
            // table have value
            prev.next = current.next;
            current.next = null;
            return true;
        }
    }

    public void show() {
        HashElement current;
        for (int i = 0; i < table.length; i++) {
            current = table[i];
            while (current != null) {
                System.out.println(i + ":" + current.getKey() + ":" + current.getValue());
                current = current.next;
            }
        }
        System.out.println("\n");
    }


    private void resize() {
        if (resizeRunning) {
            return;
        }
        if (table.length * coef > valCount)
            return;
        int len = table.length;
        valCount = 0;
        HashElement[] newTable = new HashElement[(int) (len * 1.5)];
        resizeRunning = true;
        for (int i = 0; i < len; i++) {
            HashElement current = table[i];
            while (current != null) {
                put(current.getKey(), current.getValue(), newTable);
                current = current.next;
            }
        }
        resizeRunning = false;
        table = newTable;
    }
}

class Test {
    public static void main(String[] args) {

        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("Nikita", 16);  // 5
        map.put("Jopa", 21);    // 5
        map.remote("Jopa");     // nothing (collision)
        map.put("", 0);         // 0
        map.put(null, 15);      // 0
        map.put("kek", 0);      // 0
        map.put("0", 0);
        map.put("26", 26);      // 4
        map.put("Nikita", 16);  // 5
        map.niceRemote("Nikita", 16);
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.show();
        map.put("six", 6);
        map.put("seven", 7);
        map.put("eight", 8);
        map.show();



    }
}

