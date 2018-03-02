package baby.yy.com.babyhit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import at.markushi.ui.CircleButton;


public class BabyMoveFragment extends Fragment {
    TextView tvResult1;
    TextView tvResult2;
    CircleButton btn1;
    CircleButton btn2;
    Button btn3;
    int counter1;
    int counter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baby_move, container, false);
        tvResult1 = (TextView) view.findViewById(R.id.textView1);
        tvResult2 = (TextView) view.findViewById(R.id.textView2);
        btn1 = (CircleButton) view.findViewById(R.id.button1);
        btn2 = (CircleButton) view.findViewById(R.id.button2);
        btn3 = (Button) view.findViewById(R.id.button);
        init(view);
        return view;
    }




    private void init(View parentView){

        btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                counter1++;
                tvResult1.setText("" + counter1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                counter2++;
                tvResult2.setText("" + counter2);

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                counter1 = 0;
                counter2 = 0;
                tvResult1.setText("0");
                tvResult2.setText("0");
            }
        });

    }
}
