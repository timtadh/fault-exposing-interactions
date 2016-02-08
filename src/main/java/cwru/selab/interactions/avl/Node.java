package cwru.selab.interactions.avl;

import java.lang.Math;

public class Node<K extends Comparable, V> {

    public static class Error extends Exception {
        public Error(String msg) {
            super(msg);
        }
    }

    public static class KeyNotFound extends Error {
        public Object key;
        public KeyNotFound(Object key) {
            super(String.format("key '%s' not found", key));
            this.key = key;
        }
    }

    public static class KeyExists extends Error {
        public Object key;
        public KeyExists(Object key) {
            super(String.format("key '%s' is already in the tree.", key));
            this.key = key;
        }
    }

    K key;
    V value;
    int height = 1;
    Node<K,V> left = null;
    Node<K,V> right = null;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public int Height() {
        return height;
    }

    public int LeftHeight() {
        if (this.left != null) {
            return this.left.height;
        }
        return 0;
    }

    public int RightHeight() {
        if (this.right != null) {
            return this.right.height;
        }
        return 0;
    }

    public boolean Has(K key) {
        int cmp = key.compareTo(this.key);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            if (this.left != null) {
                return this.left.Has(key);
            } else {
                return false;
            }
        } else {
            if (this.right != null) {
                return this.right.Has(key);
            } else {
                return false;
            }
        }
    }

    public V Get(K key) throws Node.KeyNotFound {
        int cmp = key.compareTo(this.key);
        if (cmp == 0) {
            return this.value;
        } else if (cmp < 0) {
            if (this.left != null) {
                return this.left.Get(key);
            }
        } else {
            if (this.right != null) {
                return this.right.Get(key);
            }
        }
        throw new Node.KeyNotFound(key);
    }

    public Node<K,V> Put(K key, V value) throws Node.Error {
        int cmp = key.compareTo(this.key);
        if (cmp == 0) {
            throw new Node.KeyExists(key);
        } else if (cmp < 0) {
            if (this.left != null) {
                this.left = this.left.Put(key, value);
            } else {
                this.left = new Node(key, value);
            }
        } else {
            if (this.right != null) {
                this.right = this.right.Put(key, value);
            } else {
                this.right = new Node(key, value);
            }
        }
        this.height++;
        return this.balance();
    }

    public Node<K,V> balance() throws Node.Error {
        if (Math.abs(LeftHeight() - RightHeight()) > 2) {
            if (LeftHeight() > RightHeight()) {
                return rotateRight();
            } else {
                return rotateLeft();
            }
        }
        return this;
    }

    public Node<K,V> rotateRight() throws Node.Error {
        if (this.left == null) {
            return this;
        }
        Node<K,V> newRoot = this.left.rightmostDescendent();
        Node<K,V> self = this.popNode(newRoot);
        newRoot.left = self.left;
        newRoot.right = self.right;
        self.left = null;
        self.right = null;
        return newRoot.pushNode(self);
    }

    public Node<K,V> rotateLeft() throws Node.Error {
        if (this.right == null) {
            return this;
        }
        Node<K,V> newRoot = this.right.leftmostDescendent();
        Node<K,V> self = this.popNode(newRoot);
        newRoot.left = self.left;
        newRoot.right = self.right;
        self.left = null;
        self.right = null;
        return newRoot.pushNode(self);
    }

    public Node<K,V> pushNode(Node<K,V> node) throws Node.Error {
        if (node == null) {
            throw new Node.Error("`node` cannot be null");
        } else if (node.left != null || node.right != null) {
            throw new Node.Error(String.format("`node` '%s' must be a leaf node", node));
        }
        int cmp = node.key.compareTo(this.key);
        if (cmp == 0) {
            throw new KeyExists(node.key);
        } else if (cmp < 0) {
            this.left = this.left.pushNode(node);
        } else {
            this.right = this.right.pushNode(node);
        }
        this.height = Math.max(LeftHeight(), RightHeight()) + 1;
        return this;
    }

    public Node<K,V> popNode(Node<K,V> node) throws Node.Error {
        if (node == null) {
            throw new Node.Error("`node` cannot be null");
        } else if (node.left != null && node.right != null) {
            throw new Node.Error(String.format(
              "`node` '%s' must not have both left and right", node));
        }
        if (this == node) {
            Node<K,V> kid = null;
            if (node.left != null) {
                kid = node.left;
            } else if (node.right != null) {
                kid = node.right;
            }
            node.left = null;
            node.right = null;
            return kid;
        }
        int cmp = node.key.compareTo(this.key);
        if (cmp == 0) {
            throw new Node.Error(String.format(
              "`node` %s had same key as `this` %s node but not the same object.",
                node, this));
        } else if (cmp < 0) {
            this.left = this.left.popNode(node);
        } else {
            this.right = this.right.popNode(node);
        }
        this.height = Math.max(LeftHeight(), RightHeight()) + 1;
        return this;
    }

    public Node<K,V> rightmostDescendent() throws Node.Error {
        if (this.right != null) {
            return this.right.rightmostDescendent();
        }
        return this;
    }

    public Node<K,V> leftmostDescendent() throws Node.Error {
        if (this.left != null) {
            return this.left.leftmostDescendent();
        }
        return this;
    }

}
