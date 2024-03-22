package com.ssafy.ibalance.common.config;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
@Profile("test")
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        int port = redisPort;
        if(isRedisRunning()) {
            port = findAvailablePort();
        }

        redisServer = new RedisServer(port);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if(redisServer != null) {
            redisServer.stop();
        }
    }

    /**
     * 현재 PC/서버에서 사용가능한 포트 조회
     */
    private int findAvailablePort() throws IOException {
        for(int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if(!isRunning(process)) {
                return port;
            }
        }

        throw new IllegalArgumentException("10000 ~ 65535 사이의 포트를 찾을 수 없습니다.");
    }

    private boolean isRedisRunning() throws IOException {
        return isRunning(executeGrepProcessCommand(redisPort));
    }

    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while((line = reader.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch(Exception ignored) {}

        return !StringUtils.isEmpty(pidInfo.toString());
    }

    /**
     * 해당 port를 사용중인 프로세스 확인하는 sh 실행
     */
    private Process executeGrepProcessCommand(int port) throws IOException {

        try {
            String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
            String[] shell = {"/bin/sh", "-c", command};
            return Runtime.getRuntime().exec(shell);
        } catch(Exception e) {
            String command = String.format("netstat -nao | find \"LISTEN\" | find \"%d\"", port);
            String[] shell = {"cmd.exe", "/y", "/c", command};
            return Runtime.getRuntime().exec(shell);
        }
    }
}
