package com.example.methodchannellearn;

import android.os.Bundle;

import android.widget.Toast;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  private EventChannel.EventSink eventSink;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);
    new MethodChannel(getFlutterView(), "flutter_method_channel").setMethodCallHandler(
        new MethodChannel.MethodCallHandler() {
          @Override public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
            if (methodCall.method.equals("_incrementCounter")) {
              if (methodCall.arguments instanceof Integer) {
                int count = (int) methodCall.arguments;
                count++;
                if (eventSink != null) {
                  eventSink.success(count);
                }
              }
            }
          }
        });
    new EventChannel(getFlutterView(), "flutter_event_channel").setStreamHandler(
        new EventChannel.StreamHandler() {
          @Override public void onListen(Object o, EventChannel.EventSink eventSink) {
            MainActivity.this.eventSink = eventSink;
          }

          @Override public void onCancel(Object o) {
            MainActivity.this.eventSink = null;
          }
        });
  }

  private void toast(Object object) {
    Toast.makeText(this, object.toString(), Toast.LENGTH_SHORT).show();
  }
}
