package ru.mrak.codingame.hard.tanNetwork;

import org.junit.*;

import java.io.*;

public class SolutionTest {
    
    private InputStream in;
    
    @Before
    public void before() {
        in = System.in;
    }
    
    @After
    public void after() {
        System.setIn(in);
    }
    
    @Test
    public void one() {
        System.setIn(new ByteArrayInputStream(
                ("StopArea:ABDU\n" +
                        "StopArea:ABLA\n" +
                        "3\n" +
                        "StopArea:ABDU,\"Abel Durand\",,47.22019661,-1.60337553,,,1,\n" +
                        "StopArea:ABLA,\"Avenue Blanche\",,47.22973509,-1.58937990,,,1,\n" +
                        "StopArea:ACHA,\"Angle Chaillou\",,47.26979248,-1.57206627,,,1,\n" +
                        "2\n" +
                        "StopArea:ABDU StopArea:ABLA\n" +
                        "StopArea:ABLA StopArea:ACHA").getBytes()));
        Solution.main(new String[0]);
    }
    
}