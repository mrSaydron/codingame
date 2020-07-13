package ru.mrak.codingame.easy.rectanglePartition;

import org.junit.Assert;
import org.junit.Test;
import ru.mrak.codewars.kyu4.snail.Snail;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class RectanglePartitionTest {
    
    @Test
    public void SnailTest1() {
        
        
//        Runnable solution = () -> { Solution.main(new String[] {""}); };
//        (new Thread(solution, "solution")).start();
    
        String data =
                "10\n" +
                "5\n" +
                "2\n" +
                "1\n" +
                "2\n" +
                "5\n" +
                "3\n";
        
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);
    
        Solution2.main(new String[] {""});
    
        System.out.println("test end");
    }
    
    
    public String int2dToString(int[][] a) {
        return Arrays.stream(a).map(row -> Arrays.toString(row)).collect(joining("\n"));
    }
    
    public void test(int[][] array, int[] result) {
        String text = int2dToString(array) + " should be sorted to " + Arrays.toString(result);
        System.out.println(text);
        Assert.assertArrayEquals( result, Snail.snail(array));
    }
    
}
