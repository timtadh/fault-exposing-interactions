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
}

