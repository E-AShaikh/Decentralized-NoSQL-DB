package com.db;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

import java.util.Arrays;
import java.util.List;

public class DockerService {

    private DockerClient dockerClient;

    public DockerService() {
//        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withDockerHost("unix:///var/run/docker.sock")
//                .build();
//        this.dockerClient = DockerClientBuilder.getInstance(config).build();
////        try {
////            DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
////                    .withDockerHost("unix:///var/run/docker.sock") // For macOS, use the Unix socket
////                    .build();
////            this.dockerClient = DockerClientBuilder.getInstance(config).build();
////            System.out.println("Docker client created successfully.");
////        } catch (Exception e) {
////            System.err.println("Error creating Docker client: " + e.getMessage());
////            e.printStackTrace();
////        }
//    }
//
//    public void createAndStartContainer(String broadCastAddress, int bootstrapperPort, int tcpPort, int udpPort, int nodeNumber) {
//        List<String> environmentVariables = Arrays.asList("NODE_NUMBER=" + nodeNumber,
//                "UDP_PORT=" + 4000,
//                "BROADCAST_IP=" + broadCastAddress,
//                "BOOTSTRAPPER_PORT=" + bootstrapperPort);
//
//        Ports portBindings = new Ports();
//        ExposedPort tcpExposedPort = ExposedPort.tcp(tcpPort);
//        ExposedPort udpExposedPort = ExposedPort.udp(udpPort);
//
//        portBindings.bind(tcpExposedPort, Ports.Binding.bindPort(3000));
//        portBindings.bind(udpExposedPort, Ports.Binding.bindPort(4000));
//
//        CreateContainerResponse container = dockerClient.createContainerCmd("database-node")
//                .withExposedPorts(tcpExposedPort)
//                .withExposedPorts(udpExposedPort)
//                .withPortBindings(portBindings)
//                .withEnv(environmentVariables)
//                .exec();
//
//        dockerClient.connectToNetworkCmd()
//                .withContainerId(container.getId())
//                .withNetworkId("NoSqlNetwork")
//                .exec();
//
//        dockerClient.startContainerCmd(container.getId()).exec();
    }
}
