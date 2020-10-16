package ru.mrak.codingame.botProgramming.dotsAndBoxes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class PlayerTest {

    private InputStream in;
    private PrintStream out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void before() {
        in = System.in;
        //out = System.out;
        //System.setOut(new PrintStream(outContent));
    }

    @After
    public void after() {
        System.setIn(in);
        //System.setOut(out);
    }

    @Test
    public void test1() {
        System.setIn(new ByteArrayInputStream(
                ("2 A 0 0 4 " +
                "A1 LTRB " +
                "A2 LTRB " +
                "B1 LTRB " +
                "B2 LTRB").getBytes()));
        Player.main(new String[0]);
    }

    @Test
    public void test2() {
        System.setIn(new ByteArrayInputStream(
                ("2 A 0 0 4\n" +
                "A1 LRB\n" +
                "A2 LTR\n" +
                "B1 LTR\n" +
                "B2 LTRB").getBytes()));
        Player.main(new String[0]);
    }

    @Test
    public void test3() {
        System.setIn(new ByteArrayInputStream(
                ("2 A 0\n" +
                "0\n" +
                "4\n" +
                "A1 RB\n" +
                "A2 LTR\n" +
                "B1 LTRB\n" +
                "B2 LTRB").getBytes()));
        Player.main(new String[0]);
    }

}
