package gov.usdot.asn1.j2735;

import gov.usdot.asn1.generated.j2735.J2735;
import gov.usdot.asn1.j2735.J2735Util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.oss.asn1.AbstractData;
import com.oss.asn1.Coder;
import com.oss.asn1.ControlTableNotFoundException;
import com.oss.asn1.DecodeFailedException;
import com.oss.asn1.DecodeNotSupportedException;
import com.oss.asn1.InitializationException;
import com.oss.util.HexTool;

public class PrintUtil {

	/**
	 * @param args
	 * @throws DecodeNotSupportedException 
	 * @throws DecodeFailedException 
	 * @throws InitializationException 
	 * @throws ControlTableNotFoundException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws DecodeFailedException, DecodeNotSupportedException, ControlTableNotFoundException, InitializationException, FileNotFoundException, IOException {
		
		J2735.initialize();
		Coder coder = J2735.getPERUnalignedCoder();
		coder.enableEncoderDebugging();
		coder.enableDecoderDebugging();
		
		// if base64 encoded 
		//String base64String = "MIIBDYACAIqBAQiiS6AvoBOAAgfegQECggEKgwEIhAEehQEZgQTORXQlggQZTQZugwIDSIQBAIUCADeHASSBAQCCAQKjEoMQ/+7+tAAAAGT/+f7IAAAAZKOBtjBZgAQxMjM0oROAAgfegQECggEKgwEIhAEehQEZogyABBlXrjaBBM4zhyyDAgA3hAFahQEyhgcAZADIAAu4hwIJZagGgAEIgQEMrA+gA4ABQaEDgAFBogOAAUEwWYAEMTIzNKETgAIH3oEBAoIBCoMBCIQBHoUBGaIMgAQZV642gQTOM4csgwIAN4QBWoUBMoYHAGQAyAALuIcCCWWoBoABCIEBDKwPoAOAAUGhA4ABQaIDgAFB";
//		String base64String = "MIIDZYABioEBAKJ1oCagFIACB92BAQyCAQmDAQmEAR6FAnUwgQQZTQZuggTORXQlgwIg0IEBCaJI//u/60AAAGT//n/sgAAAZP/+P+mgAABkAAA/6yAAAGT//v/qj5wAZP//v+sgAABk//7/6cAAAGT//X/r4AAAZP/7f+qAAABko4IC5DBIgAQgAT7WoRSAAgfdgQEMggEJgwEJhAEehQJ1MKIQgAQZTQZugQTORXQlggIg5IMC5nKEAWiFAQGGBgH//ysAAIcBAIgDQQTsMEiABCABPtahFIACB92BAQyCAQmDAQmEAR6FAnTMohCABBlNBUOBBM5FdA6CAiDkgwLmcoQBaIUBAYYGAf//KwAAhwEAiANBBOwwSIAEIAE+1qEUgAIH3YEBDIIBCYMBCYQBHoUCdGiiEIAEGU0EKoEEzkV0DoICIOSDAuZyhAFohQEBhgYB//8rAACHAQCIA0EE7DBIgAQgAT7WoRSAAgfdgQEMggEJgwEJhAEehQJ0BKIQgAQZTQLogQTORXP8ggIg5IMC5nKEAWiFAQGGBgH//ysAAIcBAIgDQQTsMEiABCABPtahFIACB92BAQyCAQmDAQmEAR6FAnOgohCABBlNAbuBBM5Fc/yCAiDkgwLmcoQBaIUBAYYGAf//KwAAhwEAiANBBOwwSIAEIAE+1qEUgAIH3YEBDIIBCYMBCYQBHoUCczyiEIAEGU0AhYEEzkVz9YICINqDAuZyhAFohQEBhgYB//8rAACHAQCIA0EE7DBIgAQgAT7WoRSAAgfdgQEMggEJgwEJhAEehQJy2KIQgAQZTP9XgQTORXPzggIg2oMC5nKEAWiFAQGGBgH//ysAAIcBAIgDQQTsMEiABCABPtahFIACB92BAQyCAQmDAQmEAR6FAnJ0ohCABBlM/haBBM5Fc+2CAiDagwLmcoQBaIUBAYYGAf//KwAAhwEAiANBBOwwSIAEIAE+1qEUgAIH3YEBDIIBCYMBCYQBHoUCchCiEIAEGUz89IEEzkVz34ICINqDAuZyhAFohQEBhgYB//8rAACHAQCIA0EE7DBIgAQgAT7WoRSAAgfdgQEMggEJgwEJhAEehQJxrKIQgAQZTPu9gQTORXPIggIg2oMC5nKEAWiFAQGGBgH//ysAAIcBAIgDQQTs";
//		byte[] bytes = Base64.decodeBase64(base64String);
//		String helloHex = DatatypeConverter.printHexBinary(bytes);
//		System.out.println(helloHex);
//		IOUtils.write(bytes, new FileOutputStream("C:\\tmp\\test.ber"));
		
		// if BER encoded
		//String ourBerMsg =         "308203618002008A810101A28183A02FA013800207DD81010C82010983010984011E85011E8104CE4574248204194D066F8302034884010085020037870124810100820109A34A8348FFEEFEB400000064FFF9FEC800000064FFF8FE9A000000640000FEB200000064FFFBFEA8FF9C0064FFFEFEB200000064FFFBFE9C00000064FFF5FEBE00000064FFEDFEA800000064A38202D0304E800420013E16A113800207DD81010C82010983010984011E85011EA2108004194D066F8104CE457424820203488302E6728401688501318607000100012B000087020965A80880020104810200EC304E800420013E16A113800207DD81010C82010983010984011E85011EA2108004194D05438104CE45740E8202034A8302E6728401688501318607000100012B000087020965A80880020104810200EC304E800420013E16A113800207DD81010C82010983010984011E85011EA2108004194D042B8104CE45740E8202034A8302E6728401688501318607000100012B000087020965A80880020104810200EC304E800420013E16A113800207DD81010C82010983010984011E85011EA2108004194D02E88104CE4573FC8202034A8302E6728401688501318607000100012B000087020965A80880020104810200EC304E800420013E16A113800207DD81010C82010983010984011E85011EA2108004194D01BB8104CE4573FB8202034A8302E6728401688501318607000100012B000087020965A80880020104810200EC304E800420013E16A113800207DD81010C82010983010984011E85011EA2108004194D00858104CE4573F4820203498302E6728401688501318607000100012B000087020965A80880020104810200EC304E800420013E16A113800207DD81010C82010983010984011E85011EA2108004194CFF588104CE4573F3820203498302E6728401688501318607000100012B000087020965A80880020104810200EC304E800420013E16A113800207DD81010C82010983010984011E85011EA2108004194CFE178104CE4573EC820203498302E6728401688501318607000100012B000087020965A80880020104810200EC304E800420013E16A113800207DD81010C82010983010984011E85011EA2108004194CFCF48104CE4573DF820203498302E6728401688501318607000100012B000087020965A80880020104810200EC";
		
		String frankBerMsgOrig = "3082036580018A810100A275A026A014800207DD81010C82010983010984011E850275308104CE4574258204194D066E830220D0810109A248fffbbfeb40000064fffe7fec80000064fffe3fe9a000006400003feb20000064fffeffea8f9c0064ffffbfeb20000064fffeffe9c0000064fffd7febe0000064fffb7fea80000064A38202E43048800420013ED6A114800207DD81010C82010983010984011E85027530A2108004194D066E8104CE457425820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E850274CCA2108004194D05438104CE45740E820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E85027468A2108004194D042A8104CE45740E820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E85027404A2108004194D02E88104CE4573FC820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E850273A0A2108004194D01BB8104CE4573FC820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E8502733CA2108004194D00858104CE4573F5820220DA8302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E850272D8A2108004194CFF578104CE4573F3820220DA8302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E85027274A2108004194CFE168104CE4573ED820220DA8302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E85027210A2108004194CFCF48104CE4573DF820220DA8302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E850271ACA2108004194CFBBD8104CE4573C8820220DA8302E672840168850101860601FFFF2B000087010088034104EC";
		//String frankBerMsg =       "308203658002008A810100A275A026A014800207DD81010C82010983010984011E850275308104CE4574258204194D066E830220D0810109A248fffbbfeb40000064fffe7fec80000064fffe3fe9a000006400003feb20000064fffeffea8f9c0064ffffbfeb20000064fffeffe9c0000064fffd7febe0000064fffb7fea80000064A38202E43048800420013ED6A114800207DD81010C82010983010984011E85027530A2108004194D066E8104CE457425820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E850274CCA2108004194D05438104CE45740E820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E85027468A2108004194D042A8104CE45740E820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E85027404A2108004194D02E88104CE4573FC820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E850273A0A2108004194D01BB8104CE4573FC820220E48302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E8502733CA2108004194D00858104CE4573F5820220DA8302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E850272D8A2108004194CFF578104CE4573F3820220DA8302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E85027274A2108004194CFE168104CE4573ED820220DA8302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E85027210A2108004194CFCF48104CE4573DF820220DA8302E672840168850101860601FFFF2B000087010088034104EC3048800420013ED6A114800207DD81010C82010983010984011E850271ACA2108004194CFBBD8104CE4573C8820220DA8302E672840168850101860601FFFF2B000087010088034104EC";
		
		//String vehSerReqOrig = "30048002008B";
		//String vehSerReq =     "300380018B";
		//String vehSerReq =     "3003800101";
		
		String berHexString = "'" + frankBerMsgOrig + "'H";
		byte[] bytes = HexTool.parseHstring(berHexString);
		IOUtils.write(bytes, new FileOutputStream("C:\\tmp\\test.ber"));
		
		// decode and print
		AbstractData message = J2735Util.decode(coder, bytes);
		System.out.println(message);

	}

}
