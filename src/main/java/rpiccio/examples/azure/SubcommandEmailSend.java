package rpiccio.examples.azure;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailAddress;
import com.azure.communication.email.models.EmailContent;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailRecipients;
import com.azure.communication.email.models.SendEmailResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.Option;


@Command(
    abbreviateSynopsis = true,
    headerHeading = "",
    name = "send",
    optionListHeading = "%nOptions:%n%n",
    separator = " ",
    synopsisHeading = "%nUsage: "
)
@Component
public class SubcommandEmailSend implements Callable<Integer> {

    @Option(names = { "--connection-string" }, required = true)
    private String connectionString;

    @Option(description = "Display this help and exit", names = { "-h", "--help" }, usageHelp = true)
    private boolean help;

    private Logger logger = LoggerFactory.getLogger(SubcommandEmailSend.class);

    public Integer call() throws Exception {

        EmailClient emailClient = new EmailClientBuilder()
            .connectionString(connectionString)
            .buildClient();

        EmailContent content = new EmailContent("Welcome to Azure Communication Services Email")
            .setPlainText("This email message is sent from Azure Communication Services Email using the Python SDK.");

        EmailAddress emailAddress = new EmailAddress("rpiccio@gmail.com");
        ArrayList<EmailAddress> addressList = new ArrayList<>();
        addressList.add(emailAddress);

        EmailRecipients emailRecipients = new EmailRecipients(addressList);

        EmailMessage emailMessage = new EmailMessage(
            "donotreply@mail.cross.d-consumer.com",
            content
        );

        emailMessage.setRecipients(emailRecipients);

        SendEmailResult response = emailClient.send(emailMessage);

        logger.info(response.getMessageId());

        return ExitCode.OK;

    }

}
