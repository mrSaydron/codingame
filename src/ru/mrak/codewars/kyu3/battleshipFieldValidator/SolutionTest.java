package ru.mrak.codewars.kyu3.battleshipFieldValidator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;


public class SolutionTest {
    
    private static int[][] battleField =
            {{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
             {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
             {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
             {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
             {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
             {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
             {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
             {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
             {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    
    @Test
    public void SampleTest() {
        assertEquals(true, BattleField.fieldValidator(battleField));
    }
}
