package nslpk.parser;

import nspk.main.Intruder;
import nspk.main.Principal;

public class MessageOC {
	
	Principal p;
	Principal q;
	Intruder intrdr;
	
	public MessageOC(Principal p, Principal q, Intruder intrdr) {
		this.p = p;
		this.q = q;
		this.intrdr = intrdr;
	}
	
	public Principal getP() {
		return p;
	}

	public Principal getQ() {
		return q;
	}

	public Intruder getIntrdr() {
		return intrdr;
	}

	public void setP(Principal p) {
		this.p = p;
	}

	public void setQ(Principal q) {
		this.q = q;
	}

	public void setIntrdr(Intruder intrdr) {
		this.intrdr = intrdr;
	}

	public void debug() {
		System.out.println(this.p.getNw());
		System.out.println(this.p.getRand());
		System.out.println(this.p.getNonces());
		System.out.println(this.p.getPrins());
		System.out.println("rw_p: " + this.p.getRwController());
		System.out.println("rw_q: " + this.q.getRwController());
		System.out.println("rw_intrdr: "+ this.intrdr.getRwController());
	}
}
