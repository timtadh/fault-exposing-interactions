package cwru.selab.interactions.avl;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.security.SecureRandom;

public class test_Tree {

    class KV<K,V> {
        K key;
        V value;
        public KV(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    Random random = new SecureRandom();

    @Test
    public void RandomPuts() throws Tree.Error {
        Tree<Integer, Integer> tree = new Tree<>();
        List<KV<Integer,Integer>> kvs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            KV<Integer, Integer> kv = new KV<>(random.nextInt(1000000), random.nextInt(1000000));
            kvs.add(kv);
            tree.Put(kv.key, kv.value);
            assertThat(tree.Has(kv.key), is(true));
            assertThat(tree.Get(kv.key), is(kv.value));
        }
        for (KV<Integer,Integer> kv : kvs) {
            assertThat(tree.Has(kv.key), is(true));
            assertThat(tree.Get(kv.key), is(kv.value));
        }
    }

    // @Test
    public void RandomPutsRemoves() throws Tree.Error {
        Tree<Integer, Integer> tree = new Tree<>();
        List<KV<Integer,Integer>> kvs = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            KV<Integer, Integer> kv = new KV<>(random.nextInt(1000), random.nextInt(1000));
            kvs.add(kv);
            tree.Put(kv.key, kv.value);
            assertThat(tree.Has(kv.key), is(true));
            assertThat(tree.Get(kv.key), is(kv.value));
        }
        for (KV<Integer,Integer> kv : kvs) {
            assertThat(tree.Has(kv.key), is(true));
            assertThat(tree.Get(kv.key), is(kv.value));
        }
        System.out.println(tree.root);
        for (int i = 0; i < kvs.size(); i++) {
            tree.Remove(kvs.get(i).key);
            System.out.println(String.format("removed %s %s", kvs.get(i).key, tree.root));
            assertThat(tree.Has(kvs.get(i).key), is(false));
            for (int j = i+1; j < kvs.size(); j++) {
                KV<Integer, Integer> kv = kvs.get(j);
                System.out.println(String.format("check key %s", kv.key));
                assertThat(tree.Has(kv.key), is(true));
                assertThat(tree.Get(kv.key), is(kv.value));
            }
        }
        System.out.println(tree.root);
    }

    @Test
    public void Bug1() throws Tree.Error {
        /* bug situation:
         * - create a tree such as: (5:0 (3:0 () 4:0) (7:0 () 9:0))
         * - remove the root 5
         * - 3 will be incorrectly selected to replace 5 (4 or 7 would be correct
         *   choices).
         * - then 4 will be on the left side of 3 which causes it to be "lost"
         *   (3:0 4:0 (7:0 () 9:0))
         * - Thus the interaction is:
         *      a certain sequence of `Put`s followed by a certain `Remove` can
         *      cause an incorrect AVL tree structure. However, it does not
         *      happen with every interaction between `Put` and `Remove`. Only
         *      ones that cause a "left side promotion" when that left side has
         *      a right subtree.
         */
        Tree<Integer, Integer> tree = new Tree<>();
        tree.Put(5, 0);
        tree.Put(3, 0);
        tree.Put(4, 0);
        tree.Put(7, 0);
        tree.Put(9, 0);
        assertThat(tree.Has(5), is(true));
        assertThat(tree.Has(3), is(true));
        assertThat(tree.Has(4), is(true));
        assertThat(tree.Has(7), is(true));
        assertThat(tree.Has(9), is(true));
        System.out.println(tree.root);
        tree.Remove(5);
        System.out.println(String.format("removed %s %s", 5, tree.root));
        assertThat(tree.Has(3), is(true));
        assertThat(tree.Has(4), is(true));
        assertThat(tree.Has(7), is(true));
        assertThat(tree.Has(9), is(true));
    }
}


