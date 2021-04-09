package org.tudelft.cloud.resizr.service.resource.management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tudelft.cloud.resizr.model.jpa.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class ComputeEngineManagerImpl implements RemoteResourceManager {

    private String response;
    private static final String RESOURCE_SLAVE_PREFIX = "resizr-slave-";

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPass;

    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private static final String SHELL_PREFIX = IS_WINDOWS ? "cmd.exe /c" : "sh -c";

    /**
     * Make a call to Google Cloud through the gcloud command line tool and create a VM with
     * the resizr_slave docker image and run that image.
     * @return A resource object that contains the name of the VM in Google Cloud, its assigned IP and is
     * marked as redundant.
     */
    @Override
    public Resource createRemoteResource() {
        response = "";
        Consumer<String> processResponse = (s) -> response += s;
        String resourceName = RESOURCE_SLAVE_PREFIX + UUID.randomUUID().toString();
        executeCommand(prepareCreateSlaveInstanceScript(resourceName),
                processResponse);
        String resourceIp = response.split("\\s+")[10];
        Resource resource = new Resource();
        resource.setUrl(resourceIp);
        resource.setName(resourceName);
        return resource;
    }

    @Override
    public void deleteRemoteResource(Resource resource) {
        response = "";
        Consumer<String> processResponse = (arg) -> response += arg;
        executeCommand(prepareDeleteSlaveInstanceScript(resource.getName()), processResponse);
    }

    private void executeCommand(String command, Consumer<String> consumer) {
        Runtime rt = Runtime.getRuntime();
        try {
            Process process = rt.exec(command);


            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

// read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

// read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }



            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), consumer);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            assert exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String prepareCreateSlaveInstanceScript(String resourceName) {
        if (IS_WINDOWS) {
            return SHELL_PREFIX + " gcloud compute instances create-with-container " + resourceName +
                    " --container-image eu.gcr.io/woven-woodland-218112/resizr_slave --metadata " +
                    "startup-script=\"#! /bin/bash \\n docker run --name resizr_slave -p 8090:8090 " +
                    "--env=\\\"DB_URL=" + dbUrl + "\\\" --env=\\\"DB_USERNAME=" + dbUsername + "\\\" " +
                    "--env=\\\"DB_PASSWORD=" + dbPass + "\\\" --env=\\\"INSTANCE_UUID=" + resourceName + "\\\" " +
                    "eu.gcr.io/woven-woodland-218112/resizr_slave\"";
        } else {
            String wholeCommand = "gcloud compute instances create-with-container " + resourceName +
                    " --container-image eu.gcr.io/woven-woodland-218112/resizr_slave --metadata " +
                    "startup-script=\"#! /bin/bash \\n docker run --name resizr_slave -p 8090:8090 " +
                    "--env=\\\"DB_URL=" + dbUrl + "\\\" --env=\\\"DB_USERNAME=" + dbUsername + "\\\" " +
                    "--env=\\\"DB_PASSWORD=" + dbPass + "\\\" --env=\\\"INSTANCE_UUID=" + resourceName + "\\\" " +
                    "eu.gcr.io/woven-woodland-218112/resizr_slave\"";
            String[] splittedCommand = wholeCommand.split(" ");
            for (int i=0; i<splittedCommand.length; i++) {
                splittedCommand[i] = "'" + splittedCommand[i] + "'";
            }
            String reconstructed = SHELL_PREFIX + " ";
            reconstructed += String.join(" ", splittedCommand);
            return reconstructed;
        }
    }

    private String prepareDeleteSlaveInstanceScript(String resourceName) {

        return SHELL_PREFIX + " gcloud compute instances delete " + resourceName + " -q";
    }
}
