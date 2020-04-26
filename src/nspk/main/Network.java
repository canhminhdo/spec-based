package nspk.main;

import java.util.ArrayList;

public class Network<E extends Message<Cipher>> extends MultiSet<E> {

	public Network(String oc) {
		super(oc);
	}
	
	public ArrayList<Message<Cipher>> responseMatching(Principal p) {
		ArrayList<Message<Cipher>> list = new ArrayList<Message<Cipher>>();
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> message = multiset.get(i);
			if (message.getName().equals(Constants.m1) && message.getReceiver().equals(p)) {
				Cipher1 c1 = (Cipher1) message.getCipher();
				if (c1.getEnc().equals(p) && c1.getGen().equals(message.getSender())) {
					list.add(message);
				}
			}
		}
		return list;
	}
	
	public ArrayList<Pair<Message<Cipher>, Message<Cipher>>> confirmationMatching(Principal p) {
		ArrayList<Pair<Message<Cipher>, Message<Cipher>>> pairs = new ArrayList<Pair<Message<Cipher>, Message<Cipher>>>();
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> m1 = multiset.get(i);
			if (m1.getName().equals(Constants.m1) && 
				m1.getCreator().equals(p) &&
				m1.getSender().equals(p) &&
				!m1.getReceiver().equals(p)
			) {
				Cipher1 c1 = (Cipher1) m1.getCipher();
				if (c1.getEnc().equals(m1.getReceiver()) && c1.getGen().equals(p)) {
					for (int j = 0; j < multiset.size(); j ++) {
						if (j != i) {
							Message<Cipher> m2 = multiset.get(j);
							if (m2.getName().equals(Constants.m2) &&
								m2.getSender().equals(m1.getReceiver()) &&
								m2.getReceiver().equals(m1.getSender())) {
								
								Cipher2 c2 = (Cipher2) m2.getCipher();
								if (c2.getEnc().equals(m2.getReceiver()) &&
										c2.getGen().equals(m2.getSender()) &&
										c2.getNonce1().equals(c1.getNonce())
								) {
									Pair<Message<Cipher>, Message<Cipher>> pair = new Pair<Message<Cipher>, Message<Cipher>>(m1, m2);
									pairs.add(pair);
								}
							}
						}
					}
				}
			}
		}
		return pairs;
	}
	
	public ArrayList<Message<Cipher>> getMessageByTypeAndExceptMe(String type, Principal p) {
		ArrayList<Message<Cipher>> list = new ArrayList<Message<Cipher>>();
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> m = multiset.get(i);
			if (m.getName().equals(type) && !m.getCreator().equals(p)) {
				list.add(m);
			}
		}
		return list;
	}
	
	public boolean checkMessage1(Message<Cipher> m1) {
		if (m1.getName().equals(Constants.m1) && 
				!m1.getCreator().isIntruder() && 
				m1.getCreator().equals(m1.getSender())) {
			Principal p = m1.getCreator();
			Principal q = m1.getReceiver();
			Cipher1 c1 = (Cipher1) m1.getCipher();
			Nonce n = c1.getNonce();
			if (c1.getEnc().equals(q) && 
					c1.getGen().equals(p) &&
					n.getGen().equals(p) && 
					n.getForWhom().equals(q)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkMessage2(Message<Cipher> m1, Message<Cipher> m2) {
		Principal p = m1.getCreator();
		Principal q = m1.getReceiver();
		Cipher1 c1 = (Cipher1) m1.getCipher();

		if (m2.getName().equals(Constants.m2) &&
				!m2.getCreator().equals(q) &&	// we do not need check this case if equals
				m2.getSender().equals(q) &&
				m2.getReceiver().equals(p)) {
			Cipher2 c2 = (Cipher2) m2.getCipher();
			if (c2.getGen().equals(q) &&
					c2.getEnc().equals(p) &&
					c2.getNonce1().equals(c1.getNonce())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkOriginalMessage2(Message<Cipher> m1, Message<Cipher> m2) {
		boolean flag = false;
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> m = multiset.get(i);
			Principal p = m1.getCreator();
			Principal q = m1.getReceiver();

			if (m.getName().equals(Constants.m2) &&
					m.getCreator().equals(q) &&
					m.getSender().equals(q) &&
					m.getReceiver().equals(p) &&
					m.getCipher().equals(m2.getCipher())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public boolean checkMessage2(Message<Cipher> m2) {
		if (m2.getName().equals(Constants.m2) && 
				!m2.getCreator().isIntruder() && 
				m2.getCreator().equals(m2.getSender())) {
			Principal q = m2.getCreator();
			Principal p = m2.getReceiver();
			Cipher2 c2 = (Cipher2) m2.getCipher();
			Nonce n2 = c2.getNonce2();
			if (c2.getEnc().equals(p) && 
					c2.getGen().equals(q) &&
					n2.getGen().equals(q) && 
					n2.getForWhom().equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkMessage3(Message<Cipher> m2, Message<Cipher> m3) {
		Principal q = m2.getCreator();
		Principal p = m2.getReceiver();
		Cipher2 c2 = (Cipher2) m2.getCipher();
		
		if (m3.getName().equals(Constants.m3) &&
				!m3.getCreator().equals(p) &&	// we do not need check this case
				m3.getSender().equals(p) &&
				m3.getReceiver().equals(q)) {
			Cipher3 c3 = (Cipher3)m3.getCipher();
			if (c3.getEnc().equals(q) &&
					c3.getNonce().equals(c2.getNonce2())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkOriginalMessage3(Message<Cipher> m2, Message<Cipher> m3) {
		boolean flag = false;
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> m = multiset.get(i);
			Principal q = m2.getCreator();
			Principal p = m2.getReceiver();

			if (m.getName().equals(Constants.m3) &&
					m.getCreator().equals(p) &&
					m.getSender().equals(p) &&
					m.getReceiver().equals(q) &&
					m.getCipher().equals(m3.getCipher())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public void oneToManyAgreementProperty() {
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> m1 = multiset.get(i);
			if (checkMessage1(m1)) {
//				System.out.println("Have m1");
				for (int j = 0; j < multiset.size(); j ++) {
					Message<Cipher> m2 = multiset.get(j);
					if (checkMessage2(m1, m2)) {
//						System.out.println("Have m1 m2");
						assert checkOriginalMessage2(m1, m2) == true : "Do not exist original m2";
//						System.out.println("Have m2 satisfaction");
					}
				}
			}
			
		}
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> m2 = multiset.get(i);
			if (checkMessage2(m2)) {
//				System.out.println("Have m2");
				for (int j = 0; j < multiset.size(); j ++) {
					Message<Cipher> m3 = multiset.get(j);
					if (checkMessage3(m2, m3)) {
//						System.out.println("Have m2 m3");
						assert checkOriginalMessage3(m2, m3) == true :  "Do not exist original m3";
//						System.out.println("Have m3 satisfaction");
					}
				}
			}
		}
	}
}
