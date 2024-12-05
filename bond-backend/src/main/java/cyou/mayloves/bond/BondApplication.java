package cyou.mayloves.bond;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

@Slf4j
@Import(cn.hutool.extra.spring.SpringUtil.class)
@ComponentScan(basePackages = {"cn.hutool.extra.spring", "cyou.mayloves.bond"})
@SpringBootApplication
public class BondApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(BondApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        logEndpointInfo();
    }

    /**
     * 日志记录端点信息
     */
    private void logEndpointInfo() {
        // 获取服务器端口号
        String port = SpringUtil.getProperty("server.port");
        log.info("----------------------------------------------");
        log.info("启动成功");

        try {
            log.info("API 地址列表：");
            // 记录本地主机的API地址
            log.info("API 地址：http://localhost:{}", port);
            // 获取所有的网络接口
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            // 遍历每个网络接口
            for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
                // 获取网络接口的所有地址
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                // 遍历每个地址
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    // 过滤掉回环地址和非本地地址，只记录本地非回环地址
                    if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                        // 构造API地址并记录
                        String address = "http://" + inetAddress.getHostAddress() + ":" + port;
                        log.info("API 地址：{}", address);
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取本机 IP 地址失败", e);
        }
        log.info("----------------------------------------------");
    }
}
