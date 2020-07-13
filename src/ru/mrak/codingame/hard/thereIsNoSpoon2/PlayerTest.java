package ru.mrak.codingame.hard.thereIsNoSpoon2;

import org.junit.*;

import java.io.*;
import java.util.*;

public class PlayerTest {
    
    @Test
    public void simplest() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("3 3\n" +
                        "1.2\n" +
                        "...\n" +
                        "..1").getBytes()));
    
        Player.main(new String[0]);
    
        System.setIn(in);
    }
    
    @Test
    public void simpler() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("2 2\n" +
                        "2.\n" +
                        "42").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void simple() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("3 3\n" +
                        "1.3\n" +
                        "...\n" +
                        "123").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void basic() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("4 3\n" +
                        "14.3\n" +
                        "....\n" +
                        ".4.4").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void intermediate1() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("5 4\n" +
                        "4.544\n" +
                        ".2...\n" +
                        "..5.4\n" +
                        "332..").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void intermediate2() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("7 5\n" +
                        "2..2.1.\n" +
                        ".3..5.3\n" +
                        ".2.1...\n" +
                        "2...2..\n" +
                        ".1....2").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void intermediate3() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("4 4\n" +
                        "25.1\n" +
                        "47.4\n" +
                        "..1.\n" +
                        "3344").getBytes()));
        
        Player.main(new String[]{"test"});
        
        System.setIn(in);
    }
    
    @Test
    public void advanced() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("8 8\n" +
                        "3.4.6.2.\n" +
                        ".1......\n" +
                        "..2.5..2\n" +
                        "1.......\n" +
                        "..1.....\n" +
                        ".3..52.3\n" +
                        ".2.17..4\n" +
                        ".4..51.2").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void multipleSolution1() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("2 2\n" +
                        "33\n" +
                        "33").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }

    @Test
    public void backToBasics() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("3 1\n" +
                        "1.1").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void CG() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("5 14\n" +
                        "22221\n" +
                        "2....\n" +
                        "2....\n" +
                        "2....\n" +
                        "2....\n" +
                        "22321\n" +
                        ".....\n" +
                        ".....\n" +
                        "22321\n" +
                        "2....\n" +
                        "2....\n" +
                        "2.131\n" +
                        "2..2.\n" +
                        "2222.").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void multipleSolution2() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("5 5\n" +
                        ".12..\n" +
                        ".2421\n" +
                        "24442\n" +
                        "1242.\n" +
                        "..21.").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void expert() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("23 23\n" +
                        "3..2.2..1....3........4\n" +
                        ".2..1....2.6.........2.\n" +
                        "..3..6....3............\n" +
                        ".......2........1..3.3.\n" +
                        "..1.............3..3...\n" +
                        ".......3..3............\n" +
                        ".3...8.....8.........3.\n" +
                        "6.5.1...........1..3...\n" +
                        "............2..6.31..2.\n" +
                        "..4..4.................\n" +
                        "5..........7...7...3.3.\n" +
                        ".2..3..3..3............\n" +
                        "......2..2...1.6...3...\n" +
                        "....2..................\n" +
                        ".4....5...3............\n" +
                        ".................2.3...\n" +
                        ".......3.3..2.44....1..\n" +
                        "3...1.3.2.3............\n" +
                        ".2.....3...6.........5.\n" +
                        "................1......\n" +
                        ".1.......3.6.2...2...4.\n" +
                        "5...............3.....3\n" +
                        "4...................4.2").getBytes()));
        
        Player.main(new String[0]);
        
        System.setIn(in);
    }
    
    @Test
    public void advanced2_test() {
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream(
                ("9 9\n" +
                        "3.....1..\n" +
                        ".2..4..21\n" +
                        ".3.2..1..\n" +
                        "..2.5.3..\n" +
                        ".3...3.3.\n" +
                        "......2..\n" +
                        "..2..3..3\n" +
                        ".3..3.3..\n" +
                        "3......44").getBytes()));
    
        Player.main(new String[]{"test"});
        
        System.setIn(in);
    }
}