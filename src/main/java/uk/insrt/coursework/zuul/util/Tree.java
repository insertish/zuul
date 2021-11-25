package uk.insrt.coursework.zuul.util;

import java.util.HashMap;
import java.util.List;

/**
 * This is an implementation of a Tree-like data structure.
 * Each node has a one or more children identified by a key
 * and each node can have more children or have a value.
 */
public class Tree<K, V> {
    private HashMap<K, Tree<K, V>> children = new HashMap<>();
    private Tree<K, V> parent;
    private V value;

    public Tree() {}

    public Tree(Tree<K, V> parent) {
        this.parent = parent;
    }

    public Tree(Tree<K, V> parent, V value) {
        this.parent = parent;
        this.value = value;
    }

    public Tree<K, V> getChild(K key) {
        return this.children.get(key);
    }

    public int getHeight(int acc) {
        if (this.parent == null) return acc;
        return this.parent.getHeight(++acc);
    }

    public int getHeight() {
        return this.getHeight(0);
    }

    public boolean hasValue() {
        return this.value != null;
    }

    public V getValue() {
        return this.value;
    }

    public void addChild(K key, Tree<K, V> node) {
        this.children.put(key, node);
    }

    public void addChildWithPath(List<K> keys, V value) {
        Tree<K, V> node = this;
        while (keys.size() > 0) {
            K key = keys.remove(0);
            Tree<K, V> child = node.getChild(key);
            if (child == null) {
                child = new Tree<>(node, keys.size() == 0 ? value : null);
                node.addChild(key, child);
            }

            node = child;
        }
    }
}
