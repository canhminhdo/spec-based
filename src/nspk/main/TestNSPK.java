package nspk.main;

import config.CaseStudy;
import gov.nasa.jpf.vm.Verify;
import nspk.parser.MessageOC;
import nspk.parser.NspkMessageParser;

public class TestNSPK {
	
	public static void main(String[] args) {
//		args = new String[] {"{nw: (m1(p,p,intrdr,c1(intrdr,n(p,intrdr,r1),p)) m1(intrdr,p,q,c1(q,n(p,intrdr,r1),p)) m1(intrdr,p,q,c1(intrdr,n(p,intrdr,r1),p)) m3(intrdr,p,q,c3(q,n(p,intrdr,r1))) m2(q,q,p,c2(p,n(p,intrdr,r1),n(q,p,r2),q)) m2(intrdr,intrdr,p,c2(p,n(p,intrdr,r1),n(q,p,r2),q)) m3(p,p,intrdr,c3(intrdr,n(q,p,r2)))) rand: emp nonces: (n(p,intrdr,r1) n(q,p,r2)) prins: (p q intrdr) rw_p: emp rw_q: emp rw_intrdr: (Fake)}"};
//		args = new String[] {"{nw: (m1(q,q,intrdr,c1(intrdr,n(q,intrdr,r1),q)) m1(intrdr,intrdr,q,c1(intrdr,n(q,intrdr,r1),q)) m1(intrdr,q,p,c1(p,n(q,intrdr,r1),q)) m1(intrdr,p,q,c1(intrdr,n(q,intrdr,r1),q)) m2(p,p,q,c2(q,n(q,intrdr,r1),n(p,q,r2),p)) m2(intrdr,intrdr,q,c2(q,n(q,intrdr,r1),n(p,q,r2),p)) m3(q,q,intrdr,c3(intrdr,n(p,q,r2)))) rand: emp nonces: (n(q,intrdr,r1) n(p,q,r2)) prins: (p q intrdr) rw_p: (Confirmation) rw_q: (Confirmation) rw_intrdr: (Confirmation Fake)}"};
		if (args.length > 0) {
			// Read arguments
			if (CaseStudy.JPF_MODE)
				Verify.beginAtomic();
			
			// Have some series of state here due to GO TO command
			MessageOC oc = NspkMessageParser.parse(args[0]);
			Principal p = oc.getP();
			Principal q = oc.getQ();
			Intruder intrdr = oc.getIntrdr();
			
			System.out.println(p.getNw() + " " + p.getRand() + " " + p.getNonces() + " " + p.getPrins());
			
//			if (p.getNonces().size() == 2) {
//				p.setRwController(new ););
//				q.getRwController().clear();
//				intrdr.getRwController().clearExceptFor(new Fake());
//			}
			
			if (CaseStudy.JPF_MODE)
				Verify.endAtomic();
			
			// starting
			NSPK nspk = new NSPK();
			nspk.begin(p, q, intrdr);
		} else {
			if (CaseStudy.JPF_MODE)
				Verify.beginAtomic();
			
			Network<Message<Cipher>> nw = new Network<Message<Cipher>>(Constants.nw);
			MultiSet<Rand> rand = new MultiSet<Rand>(Constants.rand);		
			MultiSet<Nonce> nonces = new MultiSet<Nonce>(Constants.nonces);
			
			Rand r1 = new Rand(Constants.r1);
			Rand r2 = new Rand(Constants.r2);
			rand.add(r1);
			rand.add(r2);
			
			MultiSet<Principal> prins = new MultiSet<Principal>(Constants.prins);
			Principal p = new Principal(Constants.p, nw, rand, nonces, prins, true);
			Principal q = new Principal(Constants.q, nw, rand, nonces, prins, true);
			Intruder intrdr = new Intruder(Constants.intrdr, nw, rand, nonces, prins, false);
			prins.add(p);
			prins.add(q);
			prins.add(intrdr);
			
			if (CaseStudy.JPF_MODE)
				Verify.endAtomic();

			// starting
			NSPK nspk = new NSPK();
			nspk.begin(p, q, intrdr);
		}
	}
}