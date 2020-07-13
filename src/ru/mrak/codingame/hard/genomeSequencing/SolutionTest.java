package ru.mrak.codingame.hard.genomeSequencing;

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
    public void AACCTT() {
        System.setIn(new ByteArrayInputStream(
                ("2\n" +
                        "AAC\n" +
                        "CCTT").getBytes()));
        Solution.main(new String[0]);
    }
    
    @Test
    public void AGATTACAGA() {
        System.setIn(new ByteArrayInputStream(
                ("3\n" +
                        "AGATTA\n" +
                        "GATTACA\n" +
                        "TACAGA").getBytes()));
        Solution.main(new String[0]);
    }
    
    @Test
    public void AACTT() {
        System.setIn(new ByteArrayInputStream(
                ("3\n" +
                        "TT\n" +
                        "AA\n" +
                        "ACT").getBytes()));
        Solution.main(new String[0]);
    }
    
    @Test
    public void ATCG() {
        System.setIn(new ByteArrayInputStream(
                ("2\n" +
                        "AT\n" +
                        "CG").getBytes()));
        Solution.main(new String[0]);
    }
}