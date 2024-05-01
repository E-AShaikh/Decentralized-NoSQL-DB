package com.db.config.udp.routine;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public abstract class UdpRoutine {

    public abstract void execute(DatagramPacket packet, DatagramSocket socket, int port );
}
