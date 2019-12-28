package nspk.main;

import gov.nasa.jpf.vm.Verify;

public class NSPK {

	public NSPK() {}
	
	public void begin(Principal p, Principal q, Intruder intrdr) {
		try {
			System.out.println("==========START==========");

			p.start();
			q.start();
			intrdr.start();

			p.join();
			q.join();
			intrdr.join();
			
			System.out.println("==========END==========");
			
			System.out.println(p.getNw().toString());
			System.out.println(p.getRand().toString());
			System.out.println(p.getNonces().toString());
			System.out.println(p.getPrins().toString());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void begin(Network<Message<Cipher>> nw, MultiSet<Rand> rand, MultiSet<Nonce> nonces, Flag finish) {
		try {
			Verify.beginAtomic();
			MultiSet<Principal> prins = new MultiSet<Principal>(Constants.prins);
			Principal p = new Principal(Constants.p, nw, rand, nonces, prins, true);
			Principal q = new Principal(Constants.q, nw, rand, nonces, prins, true);
			Intruder intrdr = new Intruder(Constants.intrdr, nw, rand, nonces, prins, false);
			prins.add(p);
			prins.add(q);
			prins.add(intrdr);
			Verify.endAtomic();
			
			System.out.println("==========START==========");

			p.start();
			q.start();
			intrdr.start();

			p.join();
			q.join();
			intrdr.join();
			
			System.out.println("==========END==========");
			
			System.out.println(nw.toString());
			System.out.println(rand.toString());
			System.out.println(nonces.toString());
			System.out.println(prins.toString());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
