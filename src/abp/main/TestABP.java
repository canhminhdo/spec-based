package abp.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abp.config.ABPStudy;
import config.CaseStudy;
import gov.nasa.jpf.vm.Verify;

public class TestABP {

	public static void main(String[] args) throws InterruptedException {
		if (CaseStudy.JPF_MODE)
			Verify.beginAtomic();
		// Initially variables
		List<String> sentPackets = null;
		List<String> recPackets = null;
		Integer index = null;
		Boolean finish = null;
		Boolean flag1 = null;
		Boolean flag2 = null;
		Channel<Pair<String, Boolean>> ch1 = new Channel<Pair<String, Boolean>>(ABPStudy.BOUND);
		Channel<Boolean> ch2 = new Channel<Boolean>(ABPStudy.BOUND);

		// Read arguments
		if (args.length > 0) {
			// 1st argument: packetsToBeSent
			String[] packetsToBeSent = args[0].split(",");
			sentPackets = Arrays.asList(packetsToBeSent);
			System.out.println("sentPackets: " + sentPackets);

			// 2nd argument: packetsReceived
			if (args.length >= 1) {
				String[] packetsReceived = null;
				recPackets = new ArrayList<String>();
				if (!args[1].equals("nil")) {
					packetsReceived = args[1].split(",");
					for (int i = 0; i < packetsReceived.length; i++) {
						recPackets.add(packetsReceived[i]);
					}
//	    			recPackets = Arrays.asList(packetsReceived); // return a fixed size -> So stupid
				}
				System.out.println("recPackets: " + recPackets);
			}

			// 3rd argument: index of packetsToBeSent
			if (args.length >= 2) {
				index = Integer.parseInt(args[2]);
				System.out.println("index: " + index);
			}

			// 4th argument: finish flag
			if (args.length >= 3) {
				finish = Boolean.parseBoolean(args[3]);
				System.out.println("finish: " + finish);
			}

			// 5th argument: flag1
			if (args.length >= 4) {
				flag1 = Boolean.parseBoolean(args[4]);
				System.out.println("flag1: " + flag1);
			}

			// 6th argument: flag2
			if (args.length >= 5) {
				flag2 = Boolean.parseBoolean(args[5]);
				System.out.println("flag2: " + flag2);
			}

			// 7th argument: channel1
			if (args.length >= 6) {
				String[] channel1 = null;
				if (!args[6].equals("nil")) {
					channel1 = args[6].split(",");
				}
				if (channel1 != null) {
					for (int i = 0; i < channel1.length; i++) {
						String[] ele = channel1[i].split("-");
						ch1.put_asyn(new Pair<String, Boolean>(ele[0], Boolean.parseBoolean(ele[1])));
					}
				}
				System.out.println("ch1: " + ch1);
			}

			// 8th argument: channel2
			if (args.length >= 7) {
				String[] channel2 = null;
				if (!args[7].equals("nil")) {
					channel2 = args[7].split(",");
				}
				if (channel2 != null) {
					for (int i = 0; i < channel2.length; i++) {
						ch2.put_asyn(Boolean.parseBoolean(channel2[i]));
					}
				}
				System.out.println("ch2: " + ch2);
			}
		} else {
			// default parameters
			sentPackets = Arrays.asList(ABPStudy.PACKETS);
			recPackets = new ArrayList<String>();
			index = 0;
			finish = false;
			flag1 = true;
			flag2 = true;
		}
		ABP<String> abp = new ABP<String>();

		if (CaseStudy.JPF_MODE)
			Verify.endAtomic();

		abp.begin(sentPackets, recPackets, ch1, ch2, index, finish, flag1, flag2);
//        System.out.println("Packets sent: " + sentPackets);
//        System.out.println("Packets rec: " + recPackets);
		if (CaseStudy.JPF_MODE)
			Verify.beginAtomic();
        assert sentPackets.equals(recPackets) : "Packets sent and Packets rec are not same";
        if (CaseStudy.JPF_MODE)
			Verify.endAtomic();
	}
}