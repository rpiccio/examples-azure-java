package rpiccio.examples.azure;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.jar.Manifest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.IFactory;
import picocli.CommandLine.IVersionProvider;

@Component
public class ApplicationRunner implements CommandLineRunner, ExitCodeGenerator {

    @Autowired
    private IFactory factory;

    @Autowired
    private MainCommand command;

    private int exitCode;

    @Override
    public int getExitCode() {
        return exitCode;
    }

    @Override
    public void run(String... args) {

        exitCode = new CommandLine(command, factory)
            .setUsageHelpLongOptionsMaxWidth(45)
            .execute(args);

    }

    @Command(
        abbreviateSynopsis = true,
        commandListHeading = "%nCommands:%n%n",
        headerHeading = "",
        mixinStandardHelpOptions = true,
        name = "azure",
        optionListHeading = "%nOptions:%n%n",
        separator = " ",
        subcommands = {
            SubcommandEmail.class
        },
        synopsisHeading = "%nUsage: ",
        versionProvider = MainCommand.ManifestVersionProvider.class
    )
    @Component
    public static final class MainCommand implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            return ExitCode.OK;
        }

        private static final class ManifestVersionProvider implements IVersionProvider {

            @Override
            public String[] getVersion() throws Exception {

                InputStream inputStream = ApplicationRunner.class.getResourceAsStream("/META-INF/MANIFEST.MF");

                if (inputStream != null) {

                    Manifest manifest = new Manifest(inputStream);

                    String version = manifest.getMainAttributes().getValue("Implementation-Version");

                    if (version != null && !version.isBlank()) {
                        return new String[] { version };
                    }

                }

                return new String[0];

            }

        }

    }

}
