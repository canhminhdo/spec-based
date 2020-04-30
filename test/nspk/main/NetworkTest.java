package nspk.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NetworkTest {
	Network<Message<Cipher>> nw;
	MultiSet<Rand> rand;
	MultiSet<Nonce> nonces;
	Rand r1;
	Rand r2;
	MultiSet<Principal> prins;
	Principal p;
	Principal q;
	Intruder intrdr;

	@Before
	public void setUp() throws Exception {
		nw = new Network<Message<Cipher>>(Constants.nw);
		rand = new MultiSet<Rand>(Constants.rand);
		nonces = new MultiSet<Nonce>(Constants.nonces);

		r1 = new Rand(Constants.r1);
		r2 = new Rand(Constants.r2);
		rand.add(r1);
		rand.add(r2);

		prins = new MultiSet<Principal>(Constants.prins);
		p = new Principal(Constants.p, nw, rand, nonces, prins, true);
		q = new Principal(Constants.q, nw, rand, nonces, prins, true);
		intrdr = new Intruder(Constants.intrdr, nw, rand, nonces, prins, false);
		prins.add(p);
		prins.add(q);
		prins.add(intrdr);
	}

	@After
	public void tearDown() throws Exception {
	}

	// TESTING START WITH INVARIANT 1
	// invariant (not(p = intruder) and
	// m1(p, p, q, enc1(q, n(p, q, r), p)) \in nw(s) and
	// m2(q1, q, p, enc2(p, n(p, q, r), n, q)) \in nw(s)
	// implies m2(q, q, p, enc2(p, n(p, q, r), n, q)) \in nw(s)) ,

	// test correct m1 format
	@Test
	public void testCheckMessage1_1() {
		Nonce n = new Nonce(p, q, r1);
		Cipher1 c1 = new Cipher1(q, n, p);
		Message<Cipher> m1 = new Message<Cipher>(Constants.m1, p, p, q, c1);
		nw._add(m1);
		assert nw.checkMessage1(m1) == true;
	}

	// test incorrect m1 format
	@Test
	public void testCheckMessage1_2() {
		Nonce n = new Nonce(p, q, r1);
		Cipher1 c1 = new Cipher1(q, n, p);
		Message<Cipher> m1 = new Message<Cipher>(Constants.m1, intrdr, p, q, c1);
		nw._add(m1);
		assert nw.checkMessage1(m1) == false;
	}

	// test incorrect m1 format
	@Test
	public void testCheckMessage1_3() {
		Nonce n = new Nonce(p, q, r1);
		Cipher1 c1 = new Cipher1(p, n, q);
		Message<Cipher> m1 = new Message<Cipher>(Constants.m1, p, p, q, c1);
		nw._add(m1);
		assert nw.checkMessage1(m1) == false;
	}

	// test correct m1 and m2 format
	@Test
	public void testCheckMessage2_1() {
		this.testCheckMessage1_1();
		// Sending back to sender
		Message<Cipher> m1 = nw.getTop();
		Principal q = m1.getReceiver(); // will be sender now
		Principal p = m1.getSender(); // will be receiver now
		Cipher1 c1 = (Cipher1) m1.getCipher();
		Nonce n1 = c1.getNonce();
		Nonce n2 = new Nonce(q, p, r2);
		Cipher2 c2 = new Cipher2(p, n1, n2, q);
		Message<Cipher> m2 = new Message<Cipher>(Constants.m2, intrdr, q, p, c2);
		nw._add(m2);
		assert nw.checkMessage2(m1, m2) == true;
	}

	// test correct m1 and incorrect m2 format
	@Test
	public void testCheckMessage2_2() {
		this.testCheckMessage1_1();
		// Sending back to sender
		Message<Cipher> m1 = nw.getTop();
		Principal q = m1.getReceiver(); // will be sender now
		Principal p = m1.getSender(); // will be receiver now
		Cipher1 c1 = (Cipher1) m1.getCipher();
		Nonce n1 = c1.getNonce();
		Nonce n2 = new Nonce(q, p, r2);
		Cipher2 c2 = new Cipher2(p, n1, n2, q);
		Message<Cipher> m2 = new Message<Cipher>(Constants.m2, q, q, p, c2);
		nw._add(m2);
		assert nw.checkMessage2(m1, m2) == false;
	}

	// test incorrect m1 and correct m2 format
	@Test
	public void testCheckMessage2_3() {
		this.testCheckMessage1_1();
		// Sending back to sender
		Message<Cipher> m1 = nw.getTop();
		Principal q = m1.getReceiver(); // will be sender now
		Principal p = m1.getSender(); // will be receiver now
		Nonce n1 = new Nonce(p, q, r2); // create a diff Nonce n1
		Nonce n2 = new Nonce(q, p, r2);
		Cipher2 c2 = new Cipher2(p, n1, n2, q);
		Message<Cipher> m2 = new Message<Cipher>(Constants.m2, intrdr, q, p, c2);
		nw._add(m2);
		assert nw.checkMessage2(m1, m2) == false;
	}

	// testing existing original message 2
	@Test
	public void testCheckOriginalMessage2_1() {
		this.testCheckMessage1_1();
		this.testCheckMessage2_1();
		this.testCheckMessage2_2();
		assert nw.checkOriginalMessage2(nw.getTop(), nw.getLast()) == true;
	}

	// testing non-existing original message 2
	@Test
	public void testCheckOriginalMessage2_2() {
		this.testCheckMessage1_1();
		this.testCheckMessage2_1();
		assert nw.checkOriginalMessage2(nw.getTop(), nw.getLast()) == false;
	}

	// testing existing original message 2
	@Test
	public void testOneToManyAgreementProperty_1() {
		this.testCheckMessage1_1();
		this.testCheckMessage2_1();
		this.testCheckMessage2_2();
		nw.oneToManyAgreementProperty();
	}

	// testing non-existing original message 2
	@Test
	public void testOneToManyAgreementProperty_2() {
		this.testCheckMessage1_1();
		this.testCheckMessage2_1();
		try {
			nw.oneToManyAgreementProperty();
			// should not run this
			assert false;
		} catch (AssertionError e) {
			// should be here
			System.out.println(e.getMessage());
		}
	}

	// testing non-existing fake message 2
	@Test
	public void testOneToManyAgreementProperty_3() {
		this.testCheckMessage1_1();
		this.testCheckMessage2_2();
		nw.oneToManyAgreementProperty();
	}

	// TESTING START WITH INVARIANT 2
	// invariant (not(q = intruder) and
	// m2(q, q, p, enc2(p, n, n(q, p, r), q)) \in nw(s) and
	// m3(p1, p, q, enc3(q, n(q, p, r))) \in nw(s)
	// implies m3(p, p, q, enc3(q, n(q, p, r))) \in nw(s)) .

	// correct m2 format
	@Test
	public void testCheckMessage22_1() {
		Nonce n1 = new Nonce(p, q, r1);
		Nonce n2 = new Nonce(q, p, r2);
		Cipher2 c = new Cipher2(p, n1, n2, q);
		Message<Cipher> m2 = new Message<Cipher>(Constants.m2, q, q, p, c);
		nw._add(m2);
		assert nw.checkMessage2(m2) == true;
	}

	// incorrect m2 format
	@Test
	public void testCheckMessage22_2() {
		Nonce n1 = new Nonce(p, q, r1);
		Nonce n2 = new Nonce(q, p, r2);
		Cipher2 c = new Cipher2(p, n1, n2, q);
		Message<Cipher> m2 = new Message<Cipher>(Constants.m2, intrdr, q, p, c);
		nw._add(m2);
		assert nw.checkMessage2(m2) == false;
	}

	// incorrect m2 format
	@Test
	public void testCheckMessage22_3() {
		Nonce n1 = new Nonce(p, q, r1);
		Nonce n2 = new Nonce(q, p, r2);
		Cipher2 c = new Cipher2(p, n1, n2, q);
		Message<Cipher> m2 = new Message<Cipher>(Constants.m2, q, intrdr, p, c);
		nw._add(m2);
		assert nw.checkMessage2(m2) == false;
	}

	// correct m3 format
	@Test
	public void testCheckMessage3_1() {
		this.testCheckMessage22_1();
		Message<Cipher> m2 = nw.getTop();
		Cipher2 c2 = (Cipher2) m2.getCipher();
		Nonce n = c2.getNonce2();
		Cipher3 c3 = new Cipher3(q, n);
		Message<Cipher> m3 = new Message<Cipher>(Constants.m3, intrdr, p, q, c3);
		nw._add(m3);
		assert nw.checkMessage3(m2, m3) == true;
	}

	// incorrect m3 format
	@Test
	public void testCheckMessage3_2() {
		this.testCheckMessage22_1();
		Message<Cipher> m2 = nw.getTop();
		Cipher2 c2 = (Cipher2) m2.getCipher();
		Nonce n = c2.getNonce2();
		Cipher3 c3 = new Cipher3(q, n);
		Message<Cipher> m3 = new Message<Cipher>(Constants.m3, p, p, q, c3);
		nw._add(m3);
		assert nw.checkMessage3(m2, m3) == false;
	}

	// incorrect m3 format
	@Test
	public void testCheckMessage3_3() {
		this.testCheckMessage22_1();
		Message<Cipher> m2 = nw.getTop();
		Cipher2 c2 = (Cipher2) m2.getCipher();
		Nonce n = new Nonce(q, p, r1);
		Cipher3 c3 = new Cipher3(q, n);
		Message<Cipher> m3 = new Message<Cipher>(Constants.m3, intrdr, p, q, c3);
		nw._add(m3);
		assert nw.checkMessage3(m2, m3) == false;
	}

	// incorrect m3 format
	@Test
	public void testCheckMessage3_4() {
		this.testCheckMessage22_1();
		Message<Cipher> m2 = nw.getTop();
		Cipher2 c2 = (Cipher2) m2.getCipher();
		Nonce n = new Nonce(q, p, r2);
		Cipher3 c3 = new Cipher3(q, n);
		Message<Cipher> m3 = new Message<Cipher>(Constants.m3, intrdr, intrdr, q, c3);
		nw._add(m3);
		assert nw.checkMessage3(m2, m3) == false;
	}

	// incorrect m3 format
	@Test
	public void testCheckMessage3_5() {
		this.testCheckMessage22_1();
		Message<Cipher> m2 = nw.getTop();
		Cipher2 c2 = (Cipher2) m2.getCipher();
		Nonce n = new Nonce(q, p, r2);
		Cipher3 c3 = new Cipher3(q, n);
		Message<Cipher> m3 = new Message<Cipher>(Constants.m3, intrdr, q, p, c3);
		nw._add(m3);
		assert nw.checkMessage3(m2, m3) == false;
	}
	
	// non-existing original m3 message
	@Test
	public void checkOriginalMessage3_1() {
		this.testCheckMessage22_1();
		this.testCheckMessage3_1();
		assert nw.checkOriginalMessage3(nw.getTop(), nw.getLast()) == false;
	}
	
	// existing original m3 message
	@Test
	public void checkOriginalMessage3_2() {
		this.testCheckMessage22_1();
		this.testCheckMessage3_1();
		this.testCheckMessage3_2();
		assert nw.checkOriginalMessage3(nw.getTop(), nw.getAll().get(1)) == true;
	}
	
	// existing original m3 message
	@Test
	public void checkOriginalMessage3_3() {
		this.testCheckMessage22_1();
		this.testCheckMessage3_1();
		this.testCheckMessage3_2();
		assert nw.checkOriginalMessage3(nw.getTop(), nw.getAll().get(2)) == true;
	}
	
	// testing non-existing original message 3
	@Test
	public void testOneToManyAgreementProperty_4() {
		this.testCheckMessage22_1();
		this.testCheckMessage3_1();
		try {
			nw.oneToManyAgreementProperty();
			// should not run this
			assert false;
		} catch (AssertionError e) {
			// should be here
			System.out.println(e.getMessage());
		}
	}
	
	// testing existing original message 3
	@Test
	public void testOneToManyAgreementProperty_5() {
		this.testCheckMessage22_1();
		this.testCheckMessage3_1();
		this.testCheckMessage3_2();
		nw.oneToManyAgreementProperty();
	}
	
	// testing non-existing fake message 3
	@Test
	public void testOneToManyAgreementProperty_6() {
		this.testCheckMessage22_1();
		this.testCheckMessage3_2();
		nw.oneToManyAgreementProperty();
	}

}
