package rpiccio.examples.azure;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.Option;

@Command(
    abbreviateSynopsis = true,
    commandListHeading = "%nCommands:%n%n",
    headerHeading = "",
    name = "email",
    optionListHeading = "%nOptions:%n%n",
    separator = " ",
    subcommands = {
        SubcommandEmailSend.class
    },
    synopsisHeading = "%nUsage: "
)
@Component
public class SubcommandEmail implements Callable<Integer> {

    @Option(description = "Display this help and exit", names = { "-h", "--help" }, usageHelp = true)
    private boolean help;

    @Override
    public Integer call() {
        return ExitCode.OK;
    }

}
