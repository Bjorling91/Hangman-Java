package fredbjor101.kau.se.mytoast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton = (Button) findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast();
            }

            public void showToast() {
                LayoutInflater myInflater = getLayoutInflater();
                View myLayout = myInflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(myLayout);

                toast.show();
            }
        });
    }
}
