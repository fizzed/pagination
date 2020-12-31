
import com.fizzed.blaze.Config;
import com.fizzed.blaze.Contexts;
import static com.fizzed.blaze.Systems.exec;
import static com.fizzed.blaze.Systems.requireExec;
import com.fizzed.blaze.Task;
import org.slf4j.Logger;

public class blaze {
    private final Logger log = Contexts.logger();
    private final Config config = Contexts.config();
    private final String machine = config.value("docker.machine").getOr("default");
    
    @Task(order = 1, value = "Setup dependencies (e.g. minio, redis via docker)")
    public void setup() {
        requireExec("docker", "Please install docker.").run();
    
//        // minio
//        exec("docker", "run", "--name", "chassis-minio", 
//            "-e", "MINIO_ACCESS_KEY=minio",
//            "-e", "MINIO_SECRET_KEY=changeme", 
//            "-p", "26378:9000", "-d", "minio/minio:RELEASE.2017-07-24T18-27-35Z", "server", "/export")
//            .exitValues(0, 125).run();
//        
//        // fix windows for docker port forwarding
//        if (System.getProperty("os.name").contains("Win")) {
//            exec("VBoxManage", "controlvm", machine, "natpf1", "tcp26378,tcp,,26378,,26378")
//                .exitValues(0, 1).run();
//        }
    }
    
    @Task(order = 2, value = "Teardown dependencies (e.g. minio, redis via docker)")
    public void nuke() {
        requireExec("docker").run();
        
//        exec("docker", "rm", "-f", "chassis-minio")
//            .exitValues(0, 1)
//            .run();
//        
//        // fix windows for docker port forwarding
//        if (System.getProperty("os.name").contains("Win")) {
//            exec("VBoxManage", "controlvm", machine, "natpf1", "delete", "tcp26378")
//                .exitValues(0, 1).run();
//        }
    }
}