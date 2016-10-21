import java.util.HashMap;

/**
 * Created by tiranid on 20.10.16.
 */





public class MyHashMap<K, V> {

    int defaultCapaciy = 16;
    double coeff = 0.75;



    static class HashElement<K, V> {
        K key;
        V value;
        final int hash;
        HashElement<K, V> next;

        public HashElement(K key, V value, int hash, HashElement prev, HashElement next) {
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

        public final String toString() { return key + "=" + value; }
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /*
    public void put(K key, V value) {
        n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
    }*/





}






