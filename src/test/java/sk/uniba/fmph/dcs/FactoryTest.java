package sk.uniba.fmph.dcs;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FactoryTest {

    @Test
    public void testHasFourOnStart(){
        Factory factory = new Factory();
        assertEquals("Size is four", 4, factory.state().length());
    }

    @Test
    public void clearsAfterTake(){
        Factory factory = new Factory();
        factory.take(0);
        assertTrue("Factory is empty", factory.isEmpty());
        assertTrue("TableCenter has more than 1", TableCenter.getInstance().state().length() > 2);
        factory.startNewRound();
        assertFalse("Factory is no longer empty", factory.isEmpty());
    }

    @Test
    public void takenWrong(){
        Factory factory = new Factory();
        assertNull("Has taken null", factory.take(5));
        assertEquals("TableCenter has not changed", 1, TableCenter.getInstance().state().length());
        assertEquals("Factory has not changed", 4, factory.state().length());
    }
}
