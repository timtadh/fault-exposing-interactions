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
    public void RandomInserts() throws Tree.Error {
        Tree<Integer, Integer> tree = new Tree<>();
        List<KV<Integer,Integer>> kvs = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
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
        System.out.println(tree.root);
    }

}


