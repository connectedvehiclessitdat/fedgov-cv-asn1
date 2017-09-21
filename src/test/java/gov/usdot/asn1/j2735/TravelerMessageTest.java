package gov.usdot.asn1.j2735;

import gov.usdot.asn1.generated.j2735.J2735;
import gov.usdot.asn1.generated.j2735.dsrc.TemporaryID;
import gov.usdot.asn1.generated.j2735.semi.AdvisorySituationData;
import gov.usdot.asn1.generated.j2735.semi.AdvisorySituationDataDistribution;
import gov.usdot.asn1.generated.j2735.semi.DataAcceptance;
import gov.usdot.asn1.generated.j2735.semi.DataConfirmation;
import gov.usdot.asn1.generated.j2735.semi.DataReceipt;
import gov.usdot.asn1.generated.j2735.semi.DataRequest;
import gov.usdot.asn1.generated.j2735.semi.GeoRegion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.oss.asn1.Coder;
import com.oss.asn1.ControlTableNotFoundException;
import com.oss.asn1.DecodeFailedException;
import com.oss.asn1.DecodeNotSupportedException;
import com.oss.asn1.EncodeFailedException;
import com.oss.asn1.EncodeNotSupportedException;
import com.oss.asn1.InitializationException;
import com.oss.util.HexTool;

public class TravelerMessageTest {

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
	
	// Can use this method to build AdvisoryMessages from Frank's TIM Hex strings
	//@Test
	public void testBuildTIMsFromHex() throws EncodeFailedException, DecodeFailedException, 
	EncodeNotSupportedException, DecodeNotSupportedException, IOException  {
		
		String timDir = "./src/test/resources/TIMMessages/";
		CoderHelper helper2 = new CoderHelper(coder, timDir);
		
		AdvisorySituationData advSitData = TravelerSampleMessageBuilder.buildAdvisorySituationData();
		GeoRegion serviceRegion = new GeoRegion();
		serviceRegion.setNwCorner(TravelerSampleMessageBuilder.getPosition3D(42.301942, -83.738753));
		serviceRegion.setSeCorner(TravelerSampleMessageBuilder.getPosition3D(42.273836, -83.692637));
		advSitData.setServiceRegion(serviceRegion);
		
		// For each file in timDir (1 line hex per file), build an AdvisoryMessage .ber file
		int id = 3000;
		File dir = new File(timDir);
		File[] timTextFiles = dir.listFiles();
		if (timTextFiles != null) {
			for (File child : timTextFiles) {
				if (!child.getName().endsWith("ber")) {
					try (BufferedReader br = new BufferedReader(new FileReader(child))) {
						String tim = br.readLine();
						byte [] tim_ba = HexTool.parseHex(tim, false);
						advSitData.setRequestID(new TemporaryID(ByteBuffer.allocate(4).putInt(id).array()));
						advSitData.setRecordID(new TemporaryID(ByteBuffer.allocate(4).putInt(id).array()));
						advSitData.getAsdmDetails().getAdvisoryMessage().setValue(tim_ba);
						
						String fname = child.getName();
						int pos = fname.lastIndexOf(".");
						if (pos > 0) {
						    fname = fname.substring(0, pos);
						}
						
						helper2.encodeDecodeMessage(advSitData, fname + ".ber");
						id++;
					}
				}
		    }
		}
	}
	
	// The advisory message was given to us from Frank Perry but set the record id to null.
	@Test @Ignore
	public void testTravelerInformationMessage0() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		String tim = "3081C68001108109000000000000003714830101A481AE3081AB800102A11BA119A0108004194FBA1F8104CE45CE2382020A0681020006820102820207DE830301C17084027D00850102A6108004194FC1988104CE45DA4082020A008702016E880100A92430228002000EA21CA01AA31804040CE205A104040ADA04F70404068004D60404034D0704AA3AA0383006A004800235293006A0048002010C3006A004800231283006A004800222113006A0048002010C3006A004800231203006A0048002221185021001";
		byte [] tim_ba = HexTool.parseHex(tim, false);
		
		AdvisorySituationData advSitData = TravelerSampleMessageBuilder.buildAdvisorySituationData();
		advSitData.setRequestID(new TemporaryID(ByteBuffer.allocate(4).putInt(1001).array()));
		advSitData.setRecordID(null);
		advSitData.getAsdmDetails().getAdvisoryMessage().setValue(tim_ba);
		helper.encodeDecodeMessage(advSitData, "TravelerInformationMessage0.ber");
	}
	
	// The advisory message was given to us from Frank Perry.
	@Test @Ignore
	public void testTravelerInformationMessage1() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		String tim = "3081C68001108109000000000000003714830101A481AE3081AB800102A11BA119A0108004194FBA1F8104CE45CE2382020A0681020006820102820207DE830301C17084027D00850102A6108004194FC1988104CE45DA4082020A008702016E880100A92430228002000EA21CA01AA31804040CE205A104040ADA04F70404068004D60404034D0704AA3AA0383006A004800235293006A0048002010C3006A004800231283006A004800222113006A0048002010C3006A004800231203006A0048002221185021001";
		byte [] tim_ba = HexTool.parseHex(tim, false);
		
		AdvisorySituationData advSitData = TravelerSampleMessageBuilder.buildAdvisorySituationData();
		advSitData.setRequestID(new TemporaryID(ByteBuffer.allocate(4).putInt(12345).array()));
		advSitData.setRecordID(new TemporaryID(ByteBuffer.allocate(4).putInt(12345).array()));
		advSitData.getAsdmDetails().getAdvisoryMessage().setValue(tim_ba);
		helper.encodeDecodeMessage(advSitData, "TravelerInformationMessage1.ber");
	}
	
	// The advisory message was given to us from Frank Perry.
	@Test @Ignore
	public void testTravelerInformationMessage2() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		String tim = "3081CC8001108109000000000000003719830101A481B43081B1800102A11BA119A0108004194FF86E8104CE460092820209FD81020380820102820207DE830301C17084027D00850102A6108004194FED628104CE460184820209FA8702016E880100A92A302880020380A222A020A31E0404008EF4F70404FF3BF3470404FDA0F9310404FA8BFB140404F7B6FC08AA3AA0383006A0048002352A3006A0048002010C3006A004800231283006A004800222113006A0048002010C3006A004800231203006A0048002221185021001";
		byte [] tim_ba = HexTool.parseHex(tim, false);
		
		AdvisorySituationData advSitData = TravelerSampleMessageBuilder.buildAdvisorySituationData();
		advSitData.setRequestID(new TemporaryID(ByteBuffer.allocate(4).putInt(23456).array()));
		advSitData.setRecordID(new TemporaryID(ByteBuffer.allocate(4).putInt(23456).array()));
		advSitData.getAsdmDetails().getAdvisoryMessage().setValue(tim_ba);
		helper.encodeDecodeMessage(advSitData, "TravelerInformationMessage2.ber");
	}
	
	// The advisory message was given to us from NextEnergy.
	@Test @Ignore
	public void testTraverlInformationMessage3() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		String tim = "3082020A8002009C8101058204198D0AEEA31CA00C800419A147808104CD560780A10C800418701A808104CF1FCB00A48201DB8004198D0AEE81010082020400858201CA308201C68004198D0AEE810105820100A31CA00C8004194047038104CE7C224DA10C8004193B8F4B8104CE8529F6A4820198A04B800300A3678109434F4D505557415245820C3939204D6F6E726F652053748307446574726F697484024D49850300BC62860101A70C8004193B8F4B8104CE80410CA808A106800108810106A1518003009048810B4E45585420454E45524759821034363120427572726F756768732053748307446574726F697484024D49850300BC4A860101A70C8004194047038104CE7C224DA808A106800101810101A2558003009DF9810F44455420494E5354204F462041525482103233342046726564657269636B2053748307446574726F697484024D49850300BC4A860101A70C8004193FA11A8104CE7DA208A808A106800102810101A34E8003008A95810A44544520454E45524759820E3120456E6572677920506C617A618307446574726F697484024D49850300BC62860101A70C8004193BB4398104CE7E7EC9A808A106800104810101A44F800300A033810F554157474D2032303057414C4B4552820A3230302057616C6B65728307446574726F697484024D49850300BC4F860101A70C8004193C6A908104CE8529F6A808A106800102810101";
		byte [] tim_ba = HexTool.parseHex(tim, false);
			
		AdvisorySituationData advSitData = TravelerSampleMessageBuilder.buildAdvisorySituationData();
		advSitData.setRequestID(new TemporaryID(ByteBuffer.allocate(4).putInt(34567).array()));
		advSitData.setRecordID(new TemporaryID(ByteBuffer.allocate(4).putInt(34567).array()));
		advSitData.getAsdmDetails().getAdvisoryMessage().setValue(tim_ba);
		helper.encodeDecodeMessage(advSitData, "TravelerInformationMessage3.ber");
	}
	
	// Vienna TIM  Curve Speed Warning
	@Test @Ignore
	public void testTraverlInformationMessage4() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		String tim = "3081a8800110810900000000005f435357830101a4819030818d800102a11ba119a01080041cbea8a6810409c9d103820200a581020ff0820102820207de830304ba6b84027d00850102a61080041cbe9946810409c9b6fd820200a58702016e880100a91e301c80020ff0a216a014a3120404fa35fc2504040386fbe3040406ccfa64aa22a0203006a0048002352a3006a0048002010c3006a004800231193006a0048002221185020000";
		byte [] tim_ba = HexTool.parseHex(tim, false);
			
		AdvisorySituationData advSitData = TravelerSampleMessageBuilder.buildAdvisorySituationData();
		advSitData.setRequestID(new TemporaryID(ByteBuffer.allocate(4).putInt(45678).array()));
		advSitData.setRecordID(new TemporaryID(ByteBuffer.allocate(4).putInt(45678).array()));
		advSitData.getAsdmDetails().getAdvisoryMessage().setValue(tim_ba);
		helper.encodeDecodeMessage(advSitData, "TravelerInformationMessage4.ber");
	}

	// Vienna TIM Ice Warning
	@Test @Ignore
	public void testTraverlInformationMessage5() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		String tim = "308182800110810900000000005f494345830101a46b3069800101a11ba119a01080041cbea5db810409c9dcb0820200a5810200f0820102820207de830304d14584027d00850102a61080041cbea3f9810409c9df36820200a5870202dc880100a9123010800200f0a20aa008a3060404256ae496aa0aa0083006a0048002171885020000";
		byte [] tim_ba = HexTool.parseHex(tim, false);
			
		AdvisorySituationData advSitData = TravelerSampleMessageBuilder.buildAdvisorySituationData();
		advSitData.setRequestID(new TemporaryID(ByteBuffer.allocate(4).putInt(56789).array()));
		advSitData.setRecordID(new TemporaryID(ByteBuffer.allocate(4).putInt(56789).array()));
		advSitData.getAsdmDetails().getAdvisoryMessage().setValue(tim_ba);
		helper.encodeDecodeMessage(advSitData, "TravelerInformationMessage5.ber");
	}

	@Test
	public void testRSUAdvisorySitDataRequest() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		DataRequest rsuReq = TravelerSampleMessageBuilder.buildRsuAdvisorySituationDataRequest();
		helper.encodeDecodeMessage(rsuReq, "RSUAdvisorySitDataRequest.uper");
	}
	
	@Test
	public void testUnitedStatesDataRequest() throws EncodeFailedException, DecodeFailedException, 
	FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		// Coordinates: NWPos [48.374353, -131.643968] and SEPos [24.156250, -72.347240]
		
		DataRequest rsuReq = TravelerSampleMessageBuilder.buildRsuAdvisorySituationDataRequest();
		helper.encodeDecodeMessage(rsuReq, "UnitedStatesDataRequest.uper");
	}
	
	@Test
	public void testIntersectsSoutheastMichiganDataRequest() throws EncodeFailedException, DecodeFailedException, 
	FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		// Coordinates: NWPos [42.0, -86.0] and SEPos [40.0, -83.0]
		
		DataRequest rsuReq = TravelerSampleMessageBuilder.buildRsuAdvisorySituationDataRequest();
		GeoRegion serviceRegion = new GeoRegion();
		serviceRegion.setNwCorner(TravelerSampleMessageBuilder.getPosition3D(42.0, -86.0));
		serviceRegion.setSeCorner(TravelerSampleMessageBuilder.getPosition3D(40.0, -83.0));
		rsuReq.setServiceRegion(serviceRegion);
		helper.encodeDecodeMessage(rsuReq, "IntersectsSoutheastMichiganDataRequest.uper");
	}
	
	@Test
	public void testOutsideSoutheastMichiganDataRequest() throws EncodeFailedException, DecodeFailedException, 
	FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		// Coordinates: NWPos [49.0, -81.0] and SEPos [44.0, -78.0]
		
		DataRequest rsuReq = TravelerSampleMessageBuilder.buildRsuAdvisorySituationDataRequest();
		GeoRegion serviceRegion = new GeoRegion();
		serviceRegion.setNwCorner(TravelerSampleMessageBuilder.getPosition3D(49.0, -81.0));
		serviceRegion.setSeCorner(TravelerSampleMessageBuilder.getPosition3D(44.0, -78.0));
		rsuReq.setServiceRegion(serviceRegion);
		helper.encodeDecodeMessage(rsuReq, "OutsideSoutheastMichiganDataRequest.uper");
	}
	
	@Test
	public void testAdvisorySituationData() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		AdvisorySituationData advSitData = TravelerSampleMessageBuilder.buildAdvisorySituationData();
		helper.encodeDecodeMessage(advSitData, "AdvisorySitData.uper");
	}
	
	@Test
	public void testRsuAdvisorySituationDataBundle() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		AdvisorySituationDataDistribution sitBundle = TravelerSampleMessageBuilder.buildRsuAdvisorySituationDataBundle();
		helper.encodeDecodeMessage(sitBundle, "RsuAdvisorySituationDataBundle.uper");
	}
	
	@Test
	public void testDataConfirmation() throws IOException, EncodeFailedException, DecodeFailedException, EncodeNotSupportedException, DecodeNotSupportedException {
		DataConfirmation confirmation = TravelerSampleMessageBuilder.buildDataConfirmation();
		helper.encodeDecodeMessage(confirmation, "DataConfirmation.uper");
	}
	
	@Test
	public void testDataAcceptance() throws IOException, EncodeFailedException, DecodeFailedException, EncodeNotSupportedException, DecodeNotSupportedException {
		DataAcceptance acceptance = TravelerSampleMessageBuilder.buildDataAcceptance();
		helper.encodeDecodeMessage(acceptance, "DataAcceptance.uper");
	}
	
	@Test
	public void testDataReceipt() throws IOException, EncodeFailedException, DecodeFailedException, EncodeNotSupportedException, DecodeNotSupportedException {
		DataReceipt receipt = TravelerSampleMessageBuilder.buildDataReceipt();
		helper.encodeDecodeMessage(receipt, "DataReceipt.uper");
	}

	@AfterClass
	public static void deinit() throws ControlTableNotFoundException, InitializationException {
		J2735.deinitialize();
	}

}
