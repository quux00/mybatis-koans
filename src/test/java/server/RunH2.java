package server;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunH2 {

	private static final Logger log = LoggerFactory.getLogger(RunH2.class);

	public static void main(final String... args) throws Exception {

		log.debug("init");

		/** make a working copy of the data base */

		final File source = new File("db/h2/sakila.h2.db");

		final File target = new File("target/h2/sakila.h2.db");

		FileUtils.copyFile(source, target);

		/** start H2 */

		final String baseDir = target.getParentFile().getAbsolutePath();

		final String[] conf = new String[] { "-ifExists", "-baseDir", baseDir };

		/** web console */
		final Server serverWEB = Server.createWebServer();
		serverWEB.start();
		log.debug("web port : {}", serverWEB.getPort());

		/** jdbc server */
		final Server serverTCP = Server.createTcpServer(conf);
		serverTCP.start();
		log.debug("jdbc port : {}", serverTCP.getPort());

		log.debug("ready for koans");

		Thread.sleep(1000 * 1000);

		serverTCP.stop();
		serverWEB.stop();

		log.debug("done");

	}

}
