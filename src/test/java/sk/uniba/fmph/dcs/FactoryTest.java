package sk.uniba.fmph.dcs;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FactoryTest {

    @Test
    public void testHasFourOnStart(){
        Factory factory = new Factory();
        assertEquals("Size is four", factory.state().length(), 4);
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
        assertEquals("TableCenter has not changed", TableCenter.getInstance().state().length(), 1);
        assertEquals("Factory has not changed", factory.state().length(), 4);
    }
}
