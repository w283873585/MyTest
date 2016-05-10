package work;

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
		System.out.println(encode("15019434615"));
		String str = encode("15019434615");
		// System.out.println(decode(str));
		System.out.println(decodeLoginName("4wv8ybpuFOIrycZlNowS8P+KfG3pK/DT"));
	}
}
