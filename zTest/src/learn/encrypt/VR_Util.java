package learn.encrypt;

import com.vr.show.client.common.VR_ParamsRSAUtils;

public class VR_Util {
	public static String encode(String str) {
		return VR_ParamsRSAUtils.encryptParamsRsa(str);
	}
	public static String decode(String str) throws Exception {
		return VR_ParamsRSAUtils.decrypParamsRsa(str);
	}
	public static String decodeLoginName(String str) throws Exception {
		return VR_DesUtils.decrypt(str);
	}
	public static void main(String[] args) throws Exception {
		System.out.println(decode("ZBTOps1j/M1EaW4ckSg5+hTq1pGWhuXo/zwNa6t76XvnAmhhrZiLPeb3WD5Rq5nXsup5eoZbIugT6Bd7x/8m81diYcTGoJoLhD5FkfQWbnpR2CtPr4aKDihmAtOeWgxwF/59zBXlxhv7YXK/jyFjkVTapJzKljAofuIKtWQe244="));
//		System.out.println(encode("15019434615"));
//		String str = encode("15019434615");
//		// System.out.println(decode(str));
//		System.out.println(decodeLoginName("4wv8ybpuFOIrycZlNowS8P+KfG3pK/DT"));
//		System.out.println(decode("fUpOTaVr5m+jnz0Rk38ej8wKlE4SFlVce//+KSrNxepVq9uVuvjWAcbYG7wo2ENQo4H8g9CHe6v2Otg8O960Z4xFk8YUaUfIIzWyzLndFXGq6bIbrWk+VO/A6GCsTlnrjUc8tGiKqp0pdseVxN+vf0RvTlcAU/TIk74+gD7r5m8="));
//		System.out.println(VR_DesUtils.encrypt("15019434615"));
		String str = encode("15019434615");
		System.out.println(decode(str));
		System.out.println(str);
		System.out.println(decode(str));
	}
}
