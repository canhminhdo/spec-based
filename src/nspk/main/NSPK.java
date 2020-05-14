package nspk.main;

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
//		p.initialize();
//		intrdr.initialize();
//		q.initialize();
//		p.challenge();
//		System.out.println("Challenge");
//		System.out.println(p.nw);
//		System.out.println(p.rand);
//		System.out.println(p.nonces);
//		intrdr.fake();
//		System.out.println("fake11");
//		System.out.println(p.nw);
//		System.out.println(p.rand);
//		System.out.println(p.nonces);
//		q.response();
//		System.out.println("response");
//		System.out.println(p.nw);
//		System.out.println(p.rand);
//		System.out.println(p.nonces);
//		intrdr.fake();
//		System.out.println("fake22");
//		System.out.println(p.nw);
//		System.out.println(p.rand);
//		System.out.println(p.nonces);
//		p.confirmation();
//		System.out.println("confirmation");
//		System.out.println(p.nw);
//		System.out.println(p.rand);
//		System.out.println(p.nonces);
		
	}
}
