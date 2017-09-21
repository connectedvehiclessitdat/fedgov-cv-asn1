package gov.usdot.asn1.j2735;

import static org.junit.Assert.assertTrue;

import gov.usdot.asn1.generated.j2735.J2735;
import gov.usdot.asn1.generated.j2735.dsrc.MapData;
import gov.usdot.asn1.generated.j2735.dsrc.SPAT;
import gov.usdot.asn1.generated.j2735.semi.IntersectionRecord;
import gov.usdot.asn1.generated.j2735.semi.IntersectionSituationData;
import gov.usdot.asn1.generated.j2735.semi.IntersectionSituationDataAcceptance;
import gov.usdot.asn1.generated.j2735.semi.IntersectionSituationDataBundle;
import gov.usdot.asn1.generated.j2735.semi.DataRequest;
import gov.usdot.asn1.generated.j2735.semi.SpatRecord;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.oss.asn1.AbstractData;
import com.oss.asn1.Coder;
import com.oss.asn1.ControlTableNotFoundException;
import com.oss.asn1.DecodeFailedException;
import com.oss.asn1.DecodeNotSupportedException;
import com.oss.asn1.EncodeFailedException;
import com.oss.asn1.EncodeNotSupportedException;
import com.oss.asn1.InitializationException;
import com.oss.util.HexTool;

public class IntersectionSituationDataTest {
	
	private static Coder coder;
	private static final String MESSAGE_DIR = "./src/test/resources/CVMessages/";
	private static CoderHelper helper;
	
	@BeforeClass
	public static void init() throws ControlTableNotFoundException, InitializationException {
		J2735.initialize();
		coder = J2735.getPERUnalignedCoder();
		coder.enableEncoderDebugging();
		coder.enableDecoderDebugging();
		helper = new CoderHelper(coder, MESSAGE_DIR);
	}
	
	@Test
	public void testIntersectionSitData() throws EncodeFailedException, DecodeFailedException, FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		IntersectionSituationData data = IntersectionSitDataBuilder.buildIntersectionSituationData();
		helper.encodeDecodeMessage(data, "IntersectionSitData.uper");
	}
	
	@Test
	public void testIntersectionSituationdataAcceptance() throws IOException, EncodeFailedException, DecodeFailedException, EncodeNotSupportedException, DecodeNotSupportedException {
		IntersectionSituationDataAcceptance dataAcceptance = IntersectionSitDataBuilder.buildIntersectionSituationDataAcceptance();
		helper.encodeDecodeMessage(dataAcceptance, "IntersectionSituationDataAcceptance.uper");
	}
	
	@Test
	public void testIntersectionSituationDataRequest() throws EncodeFailedException, DecodeFailedException, FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		DataRequest data = IntersectionSitDataBuilder.buildIntersectionSituationDataRequest();
		helper.encodeDecodeMessage(data, "IntersectionSituationDataRequest.uper");
	}
	
	@Test
	public void testIntersectionSituationDataBundle() throws IOException, EncodeFailedException, DecodeFailedException, EncodeNotSupportedException, DecodeNotSupportedException {
		IntersectionSituationDataBundle dataBundle = IntersectionSitDataBuilder.buildIntersectionSituationDataBundle();
		helper.encodeDecodeMessage(dataBundle, "IntersectionSituationDataBundle.uper");
	}
	
	@Test @Ignore 
	public void createIntersectionSitDataMessages() throws EncodeFailedException, DecodeFailedException, FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		
		String spats[] = {
			"304B80010DA2463044810400000095820100A539301182020102830104860200C48701038B0103301182030304058301018601008701038B0103301182020607830104860200C48701038B0103",
			"304B80010DA2463044810400000095820100A539301182020102830104860200C38701038B0103301182030304058301018601008701038B0103301182020607830104860200C38701038B0103",
			"304B80010DA2463044810400000095820100A539301182020102830104860200C28701038B0103301182030304058301018601008701038B0103301182020607830104860200C28701038B0103",
		//	"303A80010DA2353033810400000C21820100A528301282030102038301018602014D8701038B0103301282030405068301048602018E8701038B0103",
		};
		String map = "3082012380010781010f830103a58201123082010e800331343981020095a20c8004193aa4588104ce7fdddea781f43047a245800a4e6f727468426f756e64810101a23430188001018102012c820106a30c04040278fccb04040522f6fd30188001028102012c820102a30c040403d1fd9e04040543f6fd3060a25e800957657374426f756e64810102a24e30188001038102012c820106a30c0404056c019a04041c9910a430188001048102012c82010aa30c040404b702ba04041ca1109930188001058102012c820108a30c0404042403e604041c78106c3047a245800a536f757468426f756e64810103a23430188001068102012c820102a30c0404fcc303980404f4a411c430188001078102012c82010aa30c0404fb7202e70404f4cd11a38702c7c6";
		//String map = "305D800107810164A551304F81020C21A20C8004194C75A28104CE448608A73B3039A237810101A232303080010181020BB882010AA3240404FC51FA530404FCBCFB100404FCF5FB5D0404FD2FFB7F0404FD60FB950404FD1EFBF98702067E";

		int i = 0;
		for ( String spat : spats )
			createIntersectionSitDataMessage(++i, map, spat);
	}
	
	public void createIntersectionSitDataMessage(int i, String hexMap, String hexSpat) throws EncodeFailedException, DecodeFailedException, FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		byte[] spat = HexTool.parseHex(hexSpat, true);
		byte[] map  = HexTool.parseHex(hexMap, true);

		AbstractData pduMap = J2735Util.decode(coder, map);
		assertTrue(pduMap instanceof MapData);
		MapData mapData = (MapData)pduMap;
		AbstractData abstractSpat = J2735Util.decode(coder, spat);
		assertTrue(abstractSpat instanceof SPAT);
		
		SPAT pduSpat = (SPAT)abstractSpat;
		SpatRecord spatData = new SpatRecord(J2735Util.expireInMin(0), pduSpat.getIntersections().get(0));
		
		IntersectionSituationData data = IntersectionSitDataBuilder.buildIntersectionSituationData();
		helper.encodeDecodeMessage(data, "IntersectionSitData.ber");
		IntersectionRecord rec = data.getIntersectionRecord();
		rec.setMapData(mapData);
		rec.setSpatData(spatData);
		data.setIntersectionRecord(rec);
		ByteArrayOutputStream sink = new ByteArrayOutputStream();
		coder.encode(rec, sink);
		System.out.println("----------------------------- IntersectionRecord" + i + " -----------------------------");
		HexTool.printHex(sink.toByteArray());
		System.out.println("---------------------------------------------------------------------------------------");
		helper.encodeDecodeMessage(data, "IntersectionSituationData" + i + ".ber");
	}
	
}