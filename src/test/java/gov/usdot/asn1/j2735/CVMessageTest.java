package gov.usdot.asn1.j2735;

import gov.usdot.asn1.generated.j2735.J2735;
import gov.usdot.asn1.generated.j2735.semi.DataSubscriptionCancel;
import gov.usdot.asn1.generated.j2735.semi.DataSubscriptionRequest;
import gov.usdot.asn1.generated.j2735.semi.DataSubscriptionResponse;
import gov.usdot.asn1.generated.j2735.semi.IsdType;
import gov.usdot.asn1.generated.j2735.semi.ServiceRequest;
import gov.usdot.asn1.generated.j2735.semi.ServiceResponse;
import gov.usdot.asn1.generated.j2735.semi.VehSitDataMessage;
import gov.usdot.asn1.j2735.UnitTestHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.oss.asn1.Coder;
import com.oss.asn1.ControlTableNotFoundException;
import com.oss.asn1.DecodeFailedException;
import com.oss.asn1.DecodeNotSupportedException;
import com.oss.asn1.EncodeFailedException;
import com.oss.asn1.EncodeNotSupportedException;
import com.oss.asn1.InitializationException;

public class CVMessageTest {

	private static Coder coder;
	private static final String MESSAGE_DIR = "./src/test/resources/CVMessages/";
	private static CoderHelper helper;
	
	static final private boolean isDebugOutput = false;

	@BeforeClass
	public static void init() throws ControlTableNotFoundException,
			InitializationException {
		UnitTestHelper.initLog4j(isDebugOutput);
		
		J2735.initialize();
		coder = J2735.getPERUnalignedCoder();
		coder.enableEncoderDebugging();
		coder.enableDecoderDebugging();
		helper = new CoderHelper(coder, MESSAGE_DIR);
	}
	
	@Test
	public void testVehicleSituationDataServiceRequest() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		ServiceRequest vsr = CVSampleMessageBuilder.buildVehicleSituationDataServiceRequest();
		helper.encodeDecodeMessage(vsr, "VehicleSituationDataServiceRequest.uper");
	}
	
	@Test
	public void testVehicleSituationDataServiceResponse() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		ServiceResponse vsr = CVSampleMessageBuilder.buildVehicleSituationDataServiceResponse();
		helper.encodeDecodeMessage(vsr, "VehicleSituationDataServiceResponse.uper");
	}

	@Test
	public void testVehSitDataMessage() throws EncodeFailedException, EncodeNotSupportedException, 
		DecodeFailedException, DecodeNotSupportedException, IOException {
		VehSitDataMessage vsdm = CVSampleMessageBuilder.buildVehSitDataMessage();
		helper.encodeDecodeMessage(vsdm, "VehSitDataMessage.uper");
	}

	@Test
	public void testDataSubscriptionServiceRequest() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		ServiceRequest dsr = CVSampleMessageBuilder.buildDataSubscriptionServiceRequest();
		helper.encodeDecodeMessage(dsr, "DataSubscriptionServiceRequest.uper");
	}
		
	@Test
	public void testDataSubscriptionServiceResponse() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		ServiceResponse dsr = CVSampleMessageBuilder.buildDataSubscriptionServiceResponse();
		helper.encodeDecodeMessage(dsr, "DataSubscriptionServiceResponse.uper");
	}
		
	@Test
	public void testDataSubscriptionRequest() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException, UnknownHostException {
		DataSubscriptionRequest msg = CVSampleMessageBuilder.buildDataSubscriptionRequest();
		helper.encodeDecodeMessage(msg, "DataSubscriptionRequest.uper");
	}
	
	@Test
	public void testIsdDataSubscriptionRequest() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException, UnknownHostException  {
		DataSubscriptionRequest msg = CVSampleMessageBuilder.buildDataSubscriptionRequest();
		IsdType type = new IsdType(CVTypeHelper.IsdType.FUND.arrayValue());
		msg.setType(DataSubscriptionRequest.Type.createTypeWithIsdType(type));
		helper.encodeDecodeMessage(msg, "IsdDataSubscriptionRequest.uper");
	}
	
	@Test
	public void testDataSubscriptionResponse() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		DataSubscriptionResponse vsr = CVSampleMessageBuilder.buildDataSubscriptionResponse();
		helper.encodeDecodeMessage(vsr, "DataSubscriptionResponse.uper");
	}
		
	@Test
	public void testDataSubscriptionCancel() throws EncodeFailedException, DecodeFailedException, 
		FileNotFoundException, EncodeNotSupportedException, DecodeNotSupportedException {
		DataSubscriptionCancel dsc = CVSampleMessageBuilder.buildDataSubscriptionCancel();
		helper.encodeDecodeMessage(dsc, "DataSubscriptionCancel.uper");
	}
	
	@AfterClass
	public static void deinit() throws ControlTableNotFoundException,
			InitializationException {
		J2735.deinitialize();
	}

}
