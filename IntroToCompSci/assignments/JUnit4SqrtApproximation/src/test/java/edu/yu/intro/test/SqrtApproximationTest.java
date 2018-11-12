package edu.yu.intro.test;

import java.util.Scanner;
import java.io.*; 
import java.util.*;
import java.lang.IllegalArgumentException;
import org.junit.*;
import static org.junit.Assert.*;
import edu.yu.intro.JUnit4SqrtApproximation;

public class SqrtApproximationTest {
	
	@Test (expected = RuntimeException.class)
	public void sqrtRootCalculationInputCantBeNegative() {
		JUnit4SqrtApproximation.sqrt(-1);
	}
	
	@Test (expected = RuntimeException.class)
	public void calculateSquareRootsInputCantBeNegative() {
		JUnit4SqrtApproximation.calculateSquareRoots(-1);
	}
	
	@Test 
	public void sqrt0() {
		assertEquals("botched input of 0", 0, JUnit4SqrtApproximation.sqrt(0), .001);
	}
	@Test 
	public void sqrt2() {
		assertEquals("botched input of 2", 1.41421356237, JUnit4SqrtApproximation.sqrt(2), .001);
	}
	@Test 
	public void sqrt42() {
		assertEquals("botched input of 42", 6.48074069841 , JUnit4SqrtApproximation.sqrt(42), .001);
	}
	@Test 
	public void sqrt10() {
		assertEquals("botched input of 10", 3.16227766017 , JUnit4SqrtApproximation.sqrt(10), .001);
	}
	
	@Test 
	public void calculateSquareRootsMinimalLength() {
		JUnit4SqrtApproximation.calculateSquareRoots(0);
	}

	@Test 
	public void calculateSquareRootsHappyPaths() {
		JUnit4SqrtApproximation.calculateSquareRoots(4);
	}
}


