package nspk.main;

import config.CaseStudy;

/**
 * c2(p,n1,n2,q)
 * 
 * Message is encrypted by p's public key with information
 * n1 is nonce from p
 * n2 is nonce from q
 * p is the sender information (sender identifier)
 * 
 * @author OgataLab
 *
 */
public class Cipher2 implements Cipher {
	
	private Principal p;
	private Nonce n1;
	private Nonce n2;
	private Principal q;
	
	public Cipher2(Principal p, Nonce n1, Nonce n2, Principal q) {
		this.p = p;
		this.n1 = n1;
		this.n2 = n2;
		this.q = q;
	}
	
	public Cipher2(Principal p, Nonce n1, Nonce n2) {
		this.p = p;
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public Nonce getNonce1() {
		return n1;
	}
	
	public Nonce getNonce2() {
		return n2;
	}
	
	// for nslpk
	public Principal getGen() {
		return q;
	}
	
	public Principal getEnc() {
		return p;
	}
	
	// only use when rand is empty
	public boolean mustHave() {
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		Cipher2 c2 = (Cipher2)obj;
		
		return this.p.equals(c2.getEnc()) &&
				this.n1.equals(c2.getNonce1()) &&
				(!CaseStudy.CASE_STUDY_NAME.equalsIgnoreCase("nslpk") || this.q.equals(c2.getGen())) && // for nslpk
				this.n2.equals(c2.getNonce2());
	}

	@Override
	public String toString() {
		if (this.q == null)
			return "c2(" + this.p.toString() + "," + this.n1.toString() + "," + this.n2.toString() + ")";
		return "c2(" + this.p.toString() + "," + this.n1.toString() + "," + this.n2.toString() + "," + this.q.toString() + ")";
	}

}
