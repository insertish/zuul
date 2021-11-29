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

    /**
     * Construct a new Empty Tree node.
     */
    public Tree() {}

    /**
     * Construct a new Tree node with a parent only.
     * @param parent Tree node which owns this node
     */
    public Tree(Tree<K, V> parent) {
        this.parent = parent;
    }

    /**
     * Construct a new Tree node with parent and value.
     * @param parent Tree node which owns this node
     * @param value The value this node should hold
     */
    public Tree(Tree<K, V> parent, V value) {
        this.parent = parent;
        this.value = value;
    }

    /**
     * Get a child of this Tree node with a given key K.
     * @param key Given key
     * @return The child represented by this key if it exists, otherwise returns null
     */
    public Tree<K, V> getChild(K key) {
        return this.children.get(key);
    }

    /**
     * Private method used to accumulate the edges travelled up to the root node.
     * @param acc Accumulator value
     * @return The current accumulator value
     */
    private int getHeight(int acc) {
        if (this.parent == null) return acc;
        return this.parent.getHeight(++acc);
    }

    /**
     * The height of the Tree from this point.
     * This is the number of edges to get from this node to the root node.
     * @return The height of the tree
     */
    public int getHeight() {
        return this.getHeight(0);
    }

    /**
     * Whether this Tree node has a value.
     * @return True if this node has a value
     */
    public boolean hasValue() {
        return this.value != null;
    }

    /**
     * Get the value of this Tree node.
     * @return Value stored if there is one, otherwise null.
     */
    public V getValue() {
        return this.value;
    }

    /**
     * Add a child to this Tree node represented by a key K.
     * @param key Key to represent this new child
     * @param node Child node to add
     */
    public void addChild(K key, Tree<K, V> node) {
        this.children.put(key, node);
    }

    /**
     * Recurse through a given key path and add value as a node at the bottom of the path.
     * @param keys Keys to iterate through
     * @param value Value to add at the end of the path
     */
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
