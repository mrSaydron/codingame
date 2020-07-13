package ru.mrak.codingame.hard.bender2;

import org.junit.*;

import java.io.*;
import java.util.*;

public class SolutionTest {
    
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
    public void oneRoom() {
        System.setIn(new ByteArrayInputStream(
                ("1\n" +
                        "0 200 E E").getBytes()));
        Solution.main(new String[0]);
        //Assert.assertEquals(Integer.parseInt((new String(outContent.toByteArray()).trim())), 200);
    }
    
    @Test
    public void room3() {
        System.setIn(new ByteArrayInputStream(
                ("3\n" +
                        "0 20 1 2\n" +
                        "1 17 E E\n" +
                        "2 20 E E").getBytes()));
        Solution.main(new String[0]);
        //Assert.assertEquals(Integer.parseInt((new String(outContent.toByteArray()).trim())), 40);
    }
    
    @Test
    public void roomsSmallRange15() {
        System.setIn(new ByteArrayInputStream(
                ("15\n" +
                        "0 17 1 2\n" +
                        "1 15 3 4\n" +
                        "2 15 4 5\n" +
                        "3 20 6 7\n" +
                        "4 12 7 8\n" +
                        "5 11 8 9\n" +
                        "6 18 10 11\n" +
                        "7 19 11 12\n" +
                        "8 12 12 13\n" +
                        "9 11 13 14\n" +
                        "10 13 E E\n" +
                        "11 14 E E\n" +
                        "12 17 E E\n" +
                        "13 19 E E\n" +
                        "14 15 E E").getBytes()));
        Solution.main(new String[0]);
        //Assert.assertEquals(Integer.parseInt((new String(outContent.toByteArray()).trim())), 88);
    }
    
    @Test
    public void rooms55() {
        System.setIn(new ByteArrayInputStream(
                ("55\n" +
                        "0 46 1 2\n" +
                        "1 29 3 4\n" +
                        "2 26 4 5\n" +
                        "3 34 6 7\n" +
                        "4 36 7 8\n" +
                        "5 10 8 9\n" +
                        "6 21 10 11\n" +
                        "7 21 11 12\n" +
                        "8 12 12 13\n" +
                        "9 16 13 14\n" +
                        "10 21 15 16\n" +
                        "11 22 16 17\n" +
                        "12 38 17 18\n" +
                        "13 34 18 19\n" +
                        "14 49 19 20\n" +
                        "15 21 21 22\n" +
                        "16 37 22 23\n" +
                        "17 38 23 24\n" +
                        "18 31 24 25\n" +
                        "19 42 25 26\n" +
                        "20 21 26 27\n" +
                        "21 48 28 29\n" +
                        "22 24 29 30\n" +
                        "23 14 30 31\n" +
                        "24 23 31 32\n" +
                        "25 40 32 33\n" +
                        "26 11 33 34\n" +
                        "27 22 34 35\n" +
                        "28 25 36 37\n" +
                        "29 11 37 38\n" +
                        "30 13 38 39\n" +
                        "31 16 39 40\n" +
                        "32 43 40 41\n" +
                        "33 24 41 42\n" +
                        "34 47 42 43\n" +
                        "35 46 43 44\n" +
                        "36 15 45 46\n" +
                        "37 36 46 47\n" +
                        "38 36 47 48\n" +
                        "39 31 48 49\n" +
                        "40 17 49 50\n" +
                        "41 27 50 51\n" +
                        "42 22 51 52\n" +
                        "43 46 52 53\n" +
                        "44 19 53 54\n" +
                        "45 34 E E\n" +
                        "46 21 E E\n" +
                        "47 14 E E\n" +
                        "48 16 E E\n" +
                        "49 25 E E\n" +
                        "50 47 E E\n" +
                        "51 37 E E\n" +
                        "52 39 E E\n" +
                        "53 38 E E\n" +
                        "54 32 E E").getBytes()));
        Solution.main(new String[0]);
        //Assert.assertEquals(Integer.parseInt((new String(outContent.toByteArray()).trim())), 358);
    }
    
    @Test
    public void many() {
        int roomCount = 50;
        int roomExit = 25;
        int addRoom = 0;
        List<String> roomStringList = new ArrayList<>();
        for (int i = 0; i < roomCount; i++) {
            roomStringList.add(String.format("%d %d %d %d", i, Math.round(Math.random() * 30), i + 1 + addRoom, i + 2 + addRoom));
            if ((i % roomExit) == 0) addRoom++;
        }
        for (int i = roomCount; i < roomCount + addRoom + 2; i++) {
            roomStringList.add(String.format("%d %d %s %s", i, Math.round(Math.random() * 30), "E", "E"));
        }
        System.setIn(new ByteArrayInputStream(
                ((roomCount + addRoom + 2) + "\n" + String.join("\n", roomStringList) + "\n").getBytes()));
        Solution.main(new String[0]);
        //Assert.assertEquals(Integer.parseInt((new String(outContent.toByteArray()).trim())), 200);
    }
}