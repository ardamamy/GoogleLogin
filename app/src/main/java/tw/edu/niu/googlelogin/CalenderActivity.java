package tw.edu.niu.googlelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class CalenderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        //建立事件開始時間
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 9, 19, 17, 50);
//建立事件結束時間
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 9, 19, 19, 30);
//建立 CalendarIntentHelper 實體
        CalendarIntentHelper calIntent = new CalendarIntentHelper();
//設定值
        calIntent.setTitle("事件標題");
        calIntent.setDescription("事件內容描述");
        calIntent.setBeginTimeInMillis(beginTime.getTimeInMillis());
        calIntent.setEndTimeInMillis(endTime.getTimeInMillis());
        calIntent.setLocation("事件地點");
//全部設定好後就能夠取得 Intent
        Intent intent = calIntent.getIntentAfterSetting();
//送出意圖
        startActivity(intent);
    }
}
