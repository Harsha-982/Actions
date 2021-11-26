import com.google.cloud.tasks.v2.CloudTasksClient;
import com.google.cloud.tasks.v2.HttpMethod;
import com.google.cloud.tasks.v2.HttpRequest;
import com.google.cloud.tasks.v2.QueueName;
import com.google.cloud.tasks.v2.Task;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.nio.charset.Charset;

public class CreateHttpTask {

    public static void main(String[] args) throws IOException {
        // TODO(developer): Replace these variables before running the sample.
        String projectId = "awcore-practice-gcp";
        String locationId = "us-central1";
        String queueId = "my-queue";
        createTask(projectId, locationId, queueId);
    }

    // Create a task with a HTTP target using the Cloud Tasks client.
    public static void createTask(String projectId, String locationId, String queueId)
            throws IOException {

        // Instantiates a client.
        try (CloudTasksClient client = CloudTasksClient.create()) {
            String url = "https://example.com/taskhandler";
            String payload = "Hello, World!";

            // Construct the fully qualified queue name.
            String queuePath = QueueName.of(projectId, locationId, queueId).toString();

            // Construct the task body.
            Task.Builder taskBuilder =
                    Task.newBuilder()
                            .setHttpRequest(
                                    HttpRequest.newBuilder()
                                            .setBody(ByteString.copyFrom(payload, Charset.defaultCharset()))
                                            .setUrl(url)
                                            .setHttpMethod(HttpMethod.POST)
                                            .build());

            // Send create task request.
            Task task = client.createTask(queuePath, taskBuilder.build());
            System.out.println("Task created: " + task.getName());
        }
    }
}