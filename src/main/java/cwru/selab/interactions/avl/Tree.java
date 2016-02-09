package cwru.selab.interactions.avl;

public class Tree<K extends Comparable,V> {

    public static class Error extends Exception {
        public Error(String msg) {
            super(msg);
        }
    }

    Node<K,V> root = null;

    public Tree() { }

    public void Put(K key, V value) throws Tree.Error {
        if (this.root == null) {
            this.root = new Node<K,V>(key, value);
        } else {
            this.root = this.root.Put(key, value);
        }
    }

    public V Get(K key) throws Tree.Error {
        if (this.root == null) {
            throw new Node.KeyNotFound(key);
        }
        return this.root.Get(key);
    }

    public boolean Has(K key) {
        if (this.root == null) {
            return false;
        }
        return this.root.Has(key);
    }
}


