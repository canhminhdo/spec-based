package cloudsync.main;

import java.util.ArrayList;

import cloudsync.jpf.CloudSyncConfiguration;
import cloudsync.parser.CloudSyncMessageParser;
import config.CaseStudy;
import gov.nasa.jpf.vm.Verify;

public class TestCloudSync {

	public static void main(String[] args) {
		if (args.length > 0) {

			// Read arguments
			if (CaseStudy.JPF_MODE)
				Verify.beginAtomic();
			
			// Need to implement for our environment with message passing
			CloudSyncConfiguration config = CloudSyncMessageParser.parse(args[0]);
			ArrayList<PC> pcList = config.getPcList();
			
			if (CaseStudy.JPF_MODE)
				Verify.endAtomic();
			
			CloudSync cloudsync = new CloudSync();
			cloudsync.begin(pcList);
		} else {
			
			if (CaseStudy.JPF_MODE)
				Verify.beginAtomic();
			
			Cloud cloud = new Cloud(Cloud.LabelC.idlec, 2);
			PC p1 = new PC(Constants.p1, PC.LabelP.idlep, 1, 0, cloud);
			PC p2 = new PC(Constants.p2, PC.LabelP.idlep, 2, 0, cloud);
			PC p3 = new PC(Constants.p3, PC.LabelP.idlep, 3, 0, cloud);
			ArrayList<PC> pcList = new ArrayList<PC>();
			
			pcList.add(p1);
			pcList.add(p2);
			pcList.add(p3);
			
			if (CaseStudy.JPF_MODE)
				Verify.endAtomic();
			
			CloudSync cloudsync = new CloudSync();
			cloudsync.begin(pcList);
		}
	}
}
