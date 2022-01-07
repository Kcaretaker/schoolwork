package com.example.demo;

import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@RestController
public class MqttSample extends BaseSample {
    final static String TAG = "MqttSample";
    Map reportData  = new HashMap<String, ValueWrapper>();
//    //public MqttSample(String pk, String dn) {
//        super(pk, dn);
//    }

    /**
     * 属性上报
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public void addMyPlans(@RequestParam("temp") Double temp){
        System.err.println(temp);
        Map reportData  = new HashMap<String, ValueWrapper>();
        //reportData.put("MeasuredIlluminance",new ValueWrapper.DoubleValueWrapper(2345.0));
        //reportData.put("mlux",new ValueWrapper.IntValueWrapper(524));
        reportData.put("temp",new ValueWrapper.DoubleValueWrapper(temp));
        LinkKit.getInstance().getDeviceThing().thingPropertyPost(reportData, new IPublishResourceListener() {
            public void onSuccess(String s, Object o) {
                // 属性上报成功
            }
            public void onError(String s, AError aError) {
                // 属性上报失败
            }
        });
    }
    public void property() {
        // 设备上报
        Map reportData  = new HashMap<String, ValueWrapper>();
        //reportData.put("MeasuredIlluminance",new ValueWrapper.DoubleValueWrapper(2345.0));
        //reportData.put("mlux",new ValueWrapper.IntValueWrapper(524));
        reportData.put("CurrentHumidity",new ValueWrapper.IntValueWrapper(255));
        LinkKit.getInstance().getDeviceThing().thingPropertyPost(reportData, new IPublishResourceListener() {
            public void onSuccess(String s, Object o) {
                // 属性上报成功
            }
            public void onError(String s, AError aError) {
                // 属性上报失败
            }
        });
    }
    /**
     * 订阅接口示例
     */
    public void subscribe() {
        MqttSubscribeRequest request = new MqttSubscribeRequest();
        // topic 您需根据实际场景填写
        request.topic = "/" + productKey + "/" + deviceName + "/user/get";
        request.isSubscribe = true;
        LinkKit.getInstance().subscribe(request, new IConnectSubscribeListener() {
            @Override
            public void onSuccess() {
                // 订阅成功
                ALog.d(TAG, "onSuccess ");
            }

            @Override
            public void onFailure(AError aError) {
                // 订阅失败
                ALog.d(TAG, "onFailure " + getError(aError));
            }
        });
    }

    /**
     * 取消订阅接口示例
     */
    public void unSubscribe() {
        MqttSubscribeRequest request = new MqttSubscribeRequest();
        // Topic用户根据实际场景填写
        request.topic = "/sys/" + productKey + "/" + deviceName + "/thing/deviceinfo/update";
        request.isSubscribe = false;
        LinkKit.getInstance().unsubscribe(request, new IConnectUnscribeListener() {
            @Override
            public void onSuccess() {
                // 取消订阅成功
                ALog.d(TAG, "onSuccess ");
            }

            @Override
            public void onFailure(AError aError) {
                // 取消订阅失败
                ALog.d(TAG, "onFailure " + getError(aError));
            }
        });
    }
    private IConnectNotifyListener notifyListener = new IConnectNotifyListener() {
        public void onNotify(String connectId, String topic, AMessage aMessage) {
            // 云端下行数据通知
        }

        public void onConnectStateChange(String connectId, ConnectState connectState) {
            // 设备连接状态通知
        }
        public boolean shouldHandle(String connectId, String topic){
            return true; // 根据实际场景设置
        }
    };

    /**
     * 所有topic的下行数据入口（前提是先订阅了该 topic，才会在这里收到）
     * 如果想要在这里收到对应 topic 的下行数据，需要先订阅该 topic
     */
    public void registerNotifyListener(){
        LinkKit.getInstance().registerOnNotifyListener(notifyListener);
    }

    /**
     * 取消注册下行的监听器，该 listener 需要保持和注册的 listener 是同一个对象
     */
    public void unregisterNotifyListener() {
        LinkKit.getInstance().unRegisterOnNotifyListener(notifyListener);
    }

}
