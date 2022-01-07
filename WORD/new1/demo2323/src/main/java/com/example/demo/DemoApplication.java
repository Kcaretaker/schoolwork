package com.example.demo;

import com.aliyun.alink.dm.api.DeviceInfo;
import com.aliyun.alink.dm.api.InitResult;
import com.aliyun.alink.linkkit.api.ILinkKitConnectListener;
import com.aliyun.alink.linkkit.api.IoTMqttClientConfig;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linkkit.api.LinkKitInitParams;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.demo.MqttSample.TAG;

@SpringBootApplication
public class DemoApplication {
    private static final String TAG = "HelloWorld";

    private String pk, dn;
    public static void main(String[] args) {
        DemoApplication manager = new DemoApplication();
        SpringApplication.run(DemoApplication.class, args);
        String diPath = System.getProperty("user.dir") + "/device_id.json";
        String deviceInfo = FileUtils.readFile(diPath);
        if (deviceInfo == null) {
            ALog.e(TAG, "main - need device info path.");
            return;
        }
        Gson mGson = new Gson();
        DeviceInfoData deviceInfoData = mGson.fromJson(deviceInfo, DeviceInfoData.class);
        if (deviceInfoData == null) {
            ALog.e(TAG, "main - deviceInfo format error.");
            return;
        }

        ALog.d(TAG, "测试一机一密和物模型");
        manager.init(deviceInfoData);
    }
    public void init(final DeviceInfoData deviceInfoData) {
        this.pk = deviceInfoData.productKey;
        this.dn = deviceInfoData.deviceName;
        LinkKitInitParams params = new LinkKitInitParams();
        /**
         * 设置 Mqtt 初始化参数
         */
        IoTMqttClientConfig config = new IoTMqttClientConfig();
        config.productKey = deviceInfoData.productKey;
        config.deviceName = deviceInfoData.deviceName;
        config.deviceSecret = deviceInfoData.deviceSecret;
        config.channelHost = pk + ".iot-as-mqtt." + deviceInfoData.region + ".aliyuncs.com:1883";
        /**
         * 是否接受离线消息
         * 对应 mqtt 的 cleanSession 字段
         */
        config.receiveOfflineMsg = false;
        params.mqttClientConfig = config;

        /**
         * 设置初始化设备认证信息，用户传入
         */
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.productKey = pk;
        deviceInfo.deviceName = dn;
        deviceInfo.deviceSecret = deviceInfoData.deviceSecret;

        params.deviceInfo = deviceInfo;



        LinkKit.getInstance().init(params, new ILinkKitConnectListener() {
            public void onError(AError aError) {
                ALog.e(TAG, "Init Error error=" + aError);
            }

            public void onInitDone(InitResult initResult) {
                ALog.i(TAG, "onInitDone result=" + initResult);
                testMqtt();
            }
        });
    }



    /**
     * 测试 Mqtt 基础Topic封装
     * 发布
     * 订阅
     * 取消订阅
     */
    private void testMqtt(){
        MqttSample sample = new MqttSample();
        sample.subscribe();
        sample.registerNotifyListener();
        //sample.property();
    }

}
