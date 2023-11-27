package sk.uniba.fmph.dcs;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class TableAreaTest {

    @Test
    public void testMultiTool() {
        Factory factory = new Factory();
        TableArea tableArea = new TableArea(TableCenter.getInstance(), new ArrayList<>(Collections.singleton(factory)));

        assertNull("Invalid take", tableArea.take(5, 1));
        assertNull("Invalid take", tableArea.take(1, 5));
        assertFalse("Components not empty", tableArea.isRoundEnd());
        assertNotNull("ValidTake", tableArea.take(1, 0));
        assertNull("Invalid take as factory is now empty", tableArea.take(1, 0));
        assertNotNull("ValidTake", tableArea.take(0, 0));

        while (!TableCenter.getInstance().isEmpty()){
            TableCenter.getInstance().take(0);
        }
        assertTrue("All components empty", tableArea.isRoundEnd());

        tableArea.startNewRound();
        assertNotNull("Refilled and valid", tableArea.take(1, 0));
    }
}
