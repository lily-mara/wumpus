package edu.miamioh.cse283.htw;

public class DevelServer {
	public static void main(String[] args) throws Exception {
		String[] caveSystemServerArgs = { "1234" };
		String[] caveServerArgs = { "localhost", "1234", "2000" };
		String[] clientArgs = { "localhost", "1234" };

		CaveSystemServer.main(caveSystemServerArgs);
		CaveServer.main(caveServerArgs);
		Client.main(clientArgs);
	}
}
