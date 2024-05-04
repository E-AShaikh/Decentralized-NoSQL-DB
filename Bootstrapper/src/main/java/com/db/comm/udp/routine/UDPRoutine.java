package com.db.comm.udp.routine;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public abstract class UDPRoutine {

    public abstract void execute(DatagramPacket packet, DatagramSocket socket, int port );
}
