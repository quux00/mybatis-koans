package h2server;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * you can use provided server classes to work with koans interactively
 * 
 * note: h2 stored procedures are compiled on demand from java source; java
 * version used for starting this tool must be compatible with java version of
 * unit test invocations
 * 
 * note: this server instance will interfere with batch mode ant or maven
 * invocations; remember to stop it before doing batch work;
 */
public class RunH2 {

	private static final Logger log = LoggerFactory.getLogger(RunH2.class);

	public static void main(final String... args) throws Exception {

		log.info("info");

		/** make a working copy of the data base */

		final File source = new File("db/h2/sakila.h2.db");

		final File target = new File("target/h2/interactive/sakila.h2.db");

		FileUtils.copyFile(source, target);

		/** start H2 */

		final String baseDir = target.getParentFile().getAbsolutePath();

		final String[] conf = new String[] { "-ifExists", "-baseDir", baseDir };

		/** web console */
		final Server serverWEB = Server.createWebServer();
		serverWEB.start();
		log.info("web conlsole port : {}", serverWEB.getPort());

		/** jdbc server */
		final Server serverTCP = Server.createTcpServer(conf);
		serverTCP.start();
		log.info("jdbc driver port : {}", serverTCP.getPort());

		log.info("ready for koans");

		Thread.sleep(3 * 1000 * 1000);

		serverTCP.stop();
		serverWEB.stop();

		log.info("done");

	}

}
