package baby.yy.com.babyhit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import at.markushi.ui.CircleButton;


public class BabyMoveFragment extends Fragment {
    TextView tvResult1,tvResult4;
    TextView tvResult2,tvResult5;
    CircleButton btn1;
    CircleButton btn2;
    Button btn3;
    int counter1;
    long last_count_time1;
    int counter2;
    long last_count_time2;
    View view;
    Handler handler = new Handler();
    private Context ctx;
    CountDownTimer timer1, timer2;
    private boolean hasReset = false;

    public void setContext(Context ctx) {
        this.ctx = ctx;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.baby_move, container, false);
            init();
            //
            reSetCounter();
        }
        return view;
    }
    final long intverTimeMillis = 5*60*1000;

    private void init(){
        tvResult1 = (TextView) view.findViewById(R.id.textView1);
        tvResult2 = (TextView) view.findViewById(R.id.textView2);
        tvResult4 = (TextView) view.findViewById(R.id.textView4);
        tvResult5 = (TextView) view.findViewById(R.id.textView5);
        btn1 = (CircleButton) view.findViewById(R.id.button1);
        btn2 = (CircleButton) view.findViewById(R.id.button2);
        btn3 = (Button) view.findViewById(R.id.button);
        final String msg = "5分钟内只计算1次";
        btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(last_count_time1 == 0 || System.currentTimeMillis() - last_count_time1 >= intverTimeMillis) {
                    counter1++;
                    last_count_time1 = System.currentTimeMillis();
                    timer1 = showTimer(tvResult4, intverTimeMillis);
                    saveCounter();
                }else{
                    //showToast(msg);
                }
                tvResult1.setText("" + counter1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(last_count_time2 == 0 || (System.currentTimeMillis() - last_count_time2 >= intverTimeMillis) ) {
                    counter2++;
                    last_count_time2 = System.currentTimeMillis();
                    timer2 = showTimer(tvResult5, intverTimeMillis);
                    saveCounter();
                }else{
                    //showToast(msg);
                }
                tvResult2.setText("" + counter2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                counter1 = 0;
                last_count_time1 = 0;
                counter2 = 0;
                last_count_time2 = 0;
                tvResult1.setText("0");
                tvResult2.setText("0");
                tvResult4.setText("");
                tvResult5.setText("");
                if(timer1 != null) {
                    timer1.cancel();
                }
                if(timer2 != null) {
                    timer2.cancel();
                }
                saveCounter();
            }
        });

    }


    private void saveCounter() {
        //if(ctx != null) {
            SharedPreferences sp = ctx.getSharedPreferences("move_counter", Context.MODE_PRIVATE);
            sp.edit().putInt("counter1", counter1).putInt("counter2", counter2)
                    .putLong("last_count_time1", last_count_time1).putLong("last_count_time2", last_count_time2).commit();
        //}
    }

    private void reSetCounter() {
        if(hasReset){
            return;
        }
        hasReset = true;
        SharedPreferences sp = ctx.getSharedPreferences("move_counter", Context.MODE_PRIVATE);

        counter1 = sp.getInt("counter1",0);
        last_count_time1 = sp.getLong("last_count_time1", 0);
        counter2 = sp.getInt("counter2",0);
        last_count_time2 = sp.getLong("last_count_time2", 0);
        tvResult1.setText("" + counter1);
        tvResult2.setText("" + counter2);
        if(last_count_time1 != 0){
            if(System.currentTimeMillis() - last_count_time1 < intverTimeMillis) {
                timer1 = showTimer(tvResult4, last_count_time1 + intverTimeMillis - System.currentTimeMillis());
            } else {
                tvResult4.setText("倒计时完毕");
            }
        }
        if(last_count_time2 != 0){
            if(System.currentTimeMillis() - last_count_time2 < intverTimeMillis) {
                timer2 = showTimer(tvResult5, last_count_time2 + intverTimeMillis - System.currentTimeMillis());
            } else {
                tvResult5.setText("倒计时完毕");
            }
        }
    }

    public CountDownTimer showTimer(final TextView textView, long intverTimeMillis) {
        CountDownTimer timer = new CountDownTimer(intverTimeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("还剩"+millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                textView.setText("倒计时完毕");
            }
        };
        timer.start();
        return timer;
    }

    public void showToast(final String msg) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(ctx, msg,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}
