import java.util.*;

public class Map<K, V> {
    List<Entry<K, V>> entries;

    public Map() {
        entries = new ArrayList<>();
    }

    public void put(K key, V value) {
        Entry<K, V> entry = getEntryByKey(key);

        if (entry != null) {
            // Update existing entry
            entry.setValue(value);
        } else {
            // Insert new entry
            entries.add(new Entry<>(key, value));
        }
    }

    public boolean containsKey(K key) {
        return getEntryByKey(key) != null;
    }

    public void remove(K key) {
        Entry<K, V> entry = getEntryByKey(key);

        if (entry != null) {
            entries.remove(entry);
        }
    }

    public V getOrDefault(K key, V defaultValue) {
        Entry<K, V> entry = getEntryByKey(key);

        if (entry != null) {
            return entry.getValue();
        } else {
            return defaultValue;
        }
    }

    public V get(K key) {
        Entry<K, V> entry = getEntryByKey(key);

        if (entry != null) {
            return entry.getValue();
        } else {
            return null; // Key not found
        }
    }

    public int size() {
        return entries.size();
    }


    private Entry<K, V> getEntryByKey(K key) {
        for (Entry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }
        return null; // Key not found
    }

    // Simple entry class with key-value pairs
    static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}