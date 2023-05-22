import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import com.google.pubsub.v1.PubsubMessage.Builder;
import com.google.pubsub.v1.Publisher;
import com.google.pubsub.v1.Publisher.Builder;

public class PubSubPublisher {
    public static void main(String[] args) throws Exception {
        // Set your project ID and topic ID
        String projectId = "your-project-id";
        String topicId = "your-topic-id";

        // Create the topic name
        TopicName topicName = TopicName.of(projectId, topicId);

        // Create a publisher
        try (Publisher publisher = Publisher.newBuilder(topicName).build()) {
            // Create a message
            String message = "Hello, Pub/Sub!";
            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

            // Publish the message and get the message ID
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            String messageId = messageIdFuture.get();
            
            System.out.println("Message published to Pub/Sub topic: " + topicName);
            System.out.println("Message ID: " + messageId);
        }
    }
}
