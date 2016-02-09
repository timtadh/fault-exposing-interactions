package cwru.selab.interactions.avl;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

public class test_Node {

    @Test
    public void HasSelf() {
        Node<String,Integer> n = new Node<>("wizard", 1);
        assertThat(n.Has("wizard"), is(true));
    }

    @Test
    public void GetSelf() throws Node.Error {
        Node<String,Integer> n = new Node<>("wizard", 1);
        assertThat(n.Get("wizard"), is(1));
    }

    @Test
    public void PutLeft() throws Node.Error {
        Node<Integer,String> n = new Node<>(3, "wizard");
        n.Put(1, "wally");
        assertThat(n.left, not(nullValue()));
        assertThat(n.left.key, is(1));
        assertThat(n.left.value, is("wally"));
        assertThat(n.Get(1), is("wally"));
    }

    @Test
    public void PutRight() throws Node.Error {
        Node<Integer,String> n = new Node<>(3, "wizard");
        n.Put(5, "wally");
        assertThat(n.right, not(nullValue()));
        assertThat(n.right.key, is(5));
        assertThat(n.right.value, is("wally"));
        assertThat(n.Get(5), is("wally"));
    }

    @Test
    public void PutLeft2x() throws Node.Error {
        Node<Integer,String> n = new Node<>(3, "wizard");
        n.Put(2, "wally");
        assertThat(n.left, not(nullValue()));
        assertThat(n.left.key, is(2));
        assertThat(n.left.value, is("wally"));
        assertThat(n.Get(2), is("wally"));
        n.Put(1, "wacky");
        assertThat(n.left, not(nullValue()));
        assertThat(n.left.key, is(2));
        assertThat(n.left.left, not(nullValue()));
        assertThat(n.left.left.key, is(1));
        assertThat(n.left.left.value, is("wacky"));
        assertThat(n.Get(1), is("wacky"));
    }

    @Test
    public void PutRight2x() throws Node.Error {
        Node<Integer,String> n = new Node<>(3, "wizard");
        n.Put(4, "wally");
        assertThat(n.right, not(nullValue()));
        assertThat(n.right.key, is(4));
        assertThat(n.right.value, is("wally"));
        assertThat(n.Get(4), is("wally"));
        n.Put(5, "wacky");
        assertThat(n.right, not(nullValue()));
        assertThat(n.right.key, is(4));
        assertThat(n.right.right, not(nullValue()));
        assertThat(n.right.right.key, is(5));
        assertThat(n.right.right.value, is("wacky"));
        assertThat(n.Get(5), is("wacky"));
    }

    @Test
    public void PutLeft3xBalances() throws Node.Error {
        Node<Integer,String> n = new Node<>(3, "wizard");
        n = n.Put(2, "wally");
        assertThat(n.left, not(nullValue()));
        assertThat(n.left.key, is(2));
        assertThat(n.left.value, is("wally"));
        assertThat(n.Get(2), is("wally"));
        n = n.Put(1, "wacky");
        assertThat(n.left, not(nullValue()));
        assertThat(n.left.key, is(2));
        assertThat(n.left.left, not(nullValue()));
        assertThat(n.left.left.key, is(1));
        assertThat(n.left.left.value, is("wacky"));
        assertThat(n.Get(1), is("wacky"));
        n = n.Put(0, "whinny");
        assertThat(n.key, is(2));
        assertThat(n.value, is("wally"));
        assertThat(n.right.key, is(3));
        assertThat(n.right.value, is("wizard"));
        assertThat(n.left, not(nullValue()));
        assertThat(n.left.key, is(1));
        assertThat(n.left.value, is("wacky"));
        assertThat(n.left.left.key, is(0));
        assertThat(n.left.left.value, is("whinny"));
        assertThat(n.Get(0), is("whinny"));
        assertThat(n.Get(1), is("wacky"));
        assertThat(n.Get(2), is("wally"));
        assertThat(n.Get(3), is("wizard"));
    }

    @Test
    public void PutRight3xBalances() throws Node.Error {
        Node<Integer,String> n = new Node<>(3, "wizard");
        n = n.Put(4, "wally");
        n = n.Put(5, "wacky");
        n = n.Put(6, "whinny");
        assertThat(n.key, is(4));
        assertThat(n.value, is("wally"));
        assertThat(n.left.key, is(3));
        assertThat(n.left.value, is("wizard"));
        assertThat(n.right, not(nullValue()));
        assertThat(n.right.key, is(5));
        assertThat(n.right.value, is("wacky"));
        assertThat(n.right.right.key, is(6));
        assertThat(n.right.right.value, is("whinny"));
        assertThat(n.Get(6), is("whinny"));
        assertThat(n.Get(5), is("wacky"));
        assertThat(n.Get(4), is("wally"));
        assertThat(n.Get(3), is("wizard"));
    }

}

