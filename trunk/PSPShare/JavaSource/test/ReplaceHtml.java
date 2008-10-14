package test;

public class ReplaceHtml {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String html = "<TABLE cellSpacing=0 cellPadding=0 width=538 border=0>AAAAdafdasfa</b>bbbCCCCCCdfafBBa<br />dafdsadsafas你好好";
		String s = html.replaceAll("<.*?>", "");
		System.out.println(s);

	}

}
