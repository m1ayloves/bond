package cyou.mayloves.bond.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Profile("dev")
@Component
public class ServletRequestHandledEventListener implements InitializingBean, ApplicationListener<ServletRequestHandledEvent> {

    private static final Logger log = LoggerFactory.getLogger(ServletRequestHandledEventListener.class);

    @Override
    public void afterPropertiesSet() {
        log.info("ServletRequestHandledEventListener is running");
    }

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        String clientAddress = event.getClientAddress();
        String requestUrl = event.getRequestUrl();
        String method = event.getMethod();
        long processingTimeMillis = event.getProcessingTimeMillis();
        Throwable failureCause = event.getFailureCause();
        if (failureCause != null) {
            String message = failureCause.getMessage();
            log.warn("[{} {}] 出现错误, 耗时 {}ms, 错误原因 [{}], 客户端地址 [{}]", method, requestUrl, processingTimeMillis, message, clientAddress);
            return;
        }
        log.info("[{} {}], 耗时 {}ms, 客户端地址 [{}]", method, requestUrl, processingTimeMillis, clientAddress);
    }

}