package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	@Test
	public void sampleTest() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	/*
	Q1 - Part1
	TFF
	A0 != B0
	A1 == B1
	A2 == B2
	 */
	@Test
	public void When_allSidesEqualExceptForTheSmallestSide_ThenReturn_False() {
		Triangle t1 = new Triangle(1, 3, 4);
		Triangle t2 = new Triangle(2, 3, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	/*
	FTF
	A0 == B0
	A1 != B1
	A2 == B2
	 */
	@Test
	public void When_allSidesEqualExceptForTheMiddleSide_ThenReturn_False() {
		Triangle t1 = new Triangle(1, 2, 4);
		Triangle t2 = new Triangle(1, 3, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	/*
	FFT
	A0 == B0
	A1 == B1
	A2 != B2
	 */
	@Test
	public void When_allSidesEqualExceptForTheLargestSide_ThenReturn_False() {
		Triangle t1 = new Triangle(1, 2, 3);
		Triangle t2 = new Triangle(1, 2, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

		/*
	FFF
	A0 == B0
	A1 == B1
	A2 == B2
	 */
	@Test
	public void When_allSidesEqual_ThenReturn_False() {
		Triangle t1 = new Triangle(1, 2, 3);
		Triangle t2 = new Triangle(1, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	/*
		Q1 - Part2
		CC
		A0 < 0 -> T
		A0 + A1 < A2 -> T
	 */
	@Test
	public void When_TrianglesAreNotCongruentAndTheSmallestSideOfT1IsNegative_ThenReturn_False() {
		Triangle t1 = new Triangle(-1, 2, 3);
		Triangle t2 = new Triangle(-1, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	/*
		CC & CACC
		A0 >= 0 -> F
		A0 + A1 >= A2 -> F
 */
	@Test
	public void When_TrianglesAreNotCongruentAndTheSmallestSideOfT1IsPositiveAndSumOfThe2SmallestSidesIsGreaterThanTheOtherSide_ThenReturn_True() {
		Triangle t1 = new Triangle(3, 4, 5);
		Triangle t2 = new Triangle(3, 4, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	/*
		CACC
		A0 < 0 -> T
		A0 + A1 >= A2 -> F
 */
	@Test
	public void When_TrianglesAreNotCongruentAndTheSmallestSideOfT1IsNegativeAndSumOfThe2SmallestSidesIsGreaterThanTheOtherSide_ThenReturn_True() {
		Triangle t1 = new Triangle(-1, 9, 5);
		Triangle t2 = new Triangle(-1, 9, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	/*
	CACC
	A0 >= 0 -> F
	A0 + A1 < A2 -> T
*/
	@Test
	public void When_TrianglesAreNotCongruentAndTheSmallestSideOfT1IsPositiveAndSumOfThe2SmallestSidesIsLessThanTheOtherSide_ThenReturn_True() {
		Triangle t1 = new Triangle(-1, 2, 5);
		Triangle t2 = new Triangle(-1, 2, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	/*
	f = xy + pq
	 CUTPNFP:
		 implicant xy has 3 unique true points: {TTFT, TTFF, TTTF}
			for clause x, we can pair unique true point TTFT with near false point FTFT
			for clause y, we can pair unique true point TTFT with near false point TFFT
		 implicant pq has 3 unique true points: {FFTT, FTTT, TFTT}
			for clause p, we can pair unique true point FTTT with near false point FTFT
			for clause q, we can pair unique true point FTTT with near false point FTTF

		 CUTPNFP: {TTFT, FTTT, FTFT, TFFT, FTTF}

	 UTP:
		 ~f = ~(xy + pq) = (~x + ~y)(~p + ~q) = ~x~p + ~x~q + ~y~p + ~y~q
		 implicants: {xy, pq, ~x~p, ~x~q, ~y~p, ~y~q}

	UTPC doesn't subsume CUTPNFP because there exists 6 implicants in UTP, but there exists only 5 implicants in CUTPNFP

	 */
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = false;
//		predicate = a predicate with any number of clauses
		predicate = (a && b) || (c && d);
		return predicate;
	}
}
