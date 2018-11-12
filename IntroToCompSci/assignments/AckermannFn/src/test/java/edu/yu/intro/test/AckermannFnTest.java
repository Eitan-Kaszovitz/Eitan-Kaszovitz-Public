package edu.yu.intro.test;

import java.util.Scanner;
import java.io.*; 
import java.util.*;
import java.lang.IllegalArgumentException;
import org.junit.*;
import static org.junit.Assert.*;
import edu.yu.intro.AckermannFn;

public class AckermannFnTest {
	
	@Test 
	public void Ackermann0and4() {
		assertEquals("botched input of 0 and 4", 5, AckermannFn.ackermann(0, 4));
	}
	
	@Test 
	public void Ackermann1and2() {
		assertEquals("botched input of 1 and 2", 4, AckermannFn.ackermann(1, 2));
	}
	
	@Test 
	public void Ackermann2and2() {
		assertEquals("botched input of 2 and 2", 7, AckermannFn.ackermann(2, 2));
	}
	
}
