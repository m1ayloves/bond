package cyou.mayloves.bond.extension.p6spy;

/**
 * 输出 SQL 日志
 *
 */
public class StdoutLogger extends com.p6spy.engine.spy.appender.StdoutLogger {

    @Override
    public void logText(String text) {
        // 打印红色 SQL 日志
        System.err.println(text);
    }
}
