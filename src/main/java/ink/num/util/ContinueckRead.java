package ink.num.util;
import gnu.io.*;
import ink.num.dao.NumberDao;
import ink.num.model.Number;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ContinueckRead extends Thread implements SerialPortEventListener { // SerialPortEventListener
    // 监听器,我的理解是独立开辟一个线程监听串口数据
    static CommPortIdentifier portId; // 串口通信管理类
    static Enumeration<?> portList; // 有效连接上的端口的枚举
    InputStream inputStream; // 从串口来的输入流
    static OutputStream outputStream;// 向串口输出的流
    static SerialPort serialPort; // 串口的引用
    // 堵塞队列用来存放读到的数据
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();


    NumberDao dao;

    @Override
    /**
     * SerialPort EventListene 的方法,持续监听端口上是否有数据流
     */
    public void serialEvent(SerialPortEvent event) {//


        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据
                byte[] readBuffer = new byte[50];
                try {

                    int numBytes = -1;
                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);//从输入流中读取一定数量的字节，
                        // 并将其存储在缓冲区数组 b 中。以整数形式返回实际读取的字节数。

                        Date nowTime = new Date(System.currentTimeMillis());
                        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
                        String retStrFormatNowDate = sdFormatter.format(nowTime);


                        if (numBytes > 0) {
                            msgQueue.add("真实收到的数据为：-----" + new String(readBuffer)+retStrFormatNowDate);

//                            System.out.println("byte");
//                            System.out.println(readBuffer[0]);

                            char[] readData = getChars(readBuffer);
                            String s = String.valueOf(readData);
                            System.out.println("++++++++++++++++++++++++++++++++++");
                            System.out.println(s);
                            System.out.println("++++++++++++++++++++++++++++++++++");
                            String[] split = s.split("\\s+");
                            for(String s8:split) {
                                System.out.println(s8);
                            }
//                            char[] h1 = {readData[0],readData[1],readData[2]};
//                            char[] h2 = {readData[4],readData[5],readData[6]};
//                            char[] h3 = {readData[8],readData[9],readData[10]};
//                            char[] h4 = {readData[12],readData[13],readData[14]};
//                            String s1 = String.valueOf(h1);
//                            String s2 = String.valueOf(h2);
//                            String s3 = String.valueOf(h3);
//                            String s4 = String.valueOf(h4);
                            String s1 = split[0];
                            String s2 = split[1];
                            String s3 = split[2];
                            String s4 = split[3];
                            System.out.println("high_1:");
                            System.out.println(s1);
                            System.out.println("high_2:");
                            System.out.println(s2);
                            System.out.println("high_3:");
                            System.out.println(s3);
                            System.out.println("high_4:");
                            System.out.println(s4);



                            Number build = Number.builder()
                                    .high_1(s1)
                                    .high_2(s2)
                                    .high_3(s3)
                                    .high_4(s4)
                                    .time(retStrFormatNowDate)
                                    .build();
                            dao = BeanUtil.getBean(NumberDao.class);
                            dao.addData(build);
                            readBuffer = new byte[50];// 重新构造缓冲对象，否则有可能会影响接下来接收的数据
                        } else {
                            msgQueue.add("额------没有读到数据");
                        }
                    }
                } catch (IOException e) {
                }
                break;
        }
    }


    /**
     *   byte[]--->char[]
     * @param bytes
     * @return
     */
    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }







    /**
     *
     * 通过程序打开COM4串口，设置监听器以及相关的参数
     *
     * @return 返回1 表示端口打开成功，返回 0表示端口打开失败
     */
    public int startComPort() {
        // 通过串口通信管理类获得当前连接上的串口列表
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {

            // 获取相应串口对象
            portId = (CommPortIdentifier) portList.nextElement();

            System.out.println("设备类型：--->" + portId.getPortType());
            System.out.println("设备名称：---->" + portId.getName());
            // 判断端口类型是否为串口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // 判断如果COM4串口存在，就打开该串口
                if (portId.getName().equals("COM4")) {
                    try {
                        // 打开串口名字为COM_4(名字任意),延迟为2毫秒
                        serialPort = (SerialPort) portId.open("COM_4", 2000);

                    } catch (PortInUseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 设置当前串口的输入输出流
                    try {
                        inputStream = serialPort.getInputStream();

                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 给当前串口添加一个监听器
                    try {
                        serialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 设置监听器生效，即：当有数据时通知
                    serialPort.notifyOnDataAvailable(true);

                    // 设置串口的一些读写参数
                    try {
                        // 比特率、数据位、停止位、奇偶校验位
                        serialPort.setSerialPortParams(9600,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println("--------------任务处理线程运行了--------------");
            while (true) {
                // 如果堵塞队列中存在数据就将其输出
                if (msgQueue.size() > 0) {
                    System.out.println(msgQueue.take());
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void kaishi() {
        ContinueckRead cRead = new ContinueckRead();
        int i = cRead.startComPort();
        if (i == 1) {
            // 启动线程来处理收到的数据
            cRead.start();
            try {
                String st = "哈哈----你好";
                System.out.println("发出字节数：" + st.getBytes("gbk").length);
                outputStream.write(st.getBytes("gbk"), 0,
                        st.getBytes("gbk").length);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            return;
        }
    }
}