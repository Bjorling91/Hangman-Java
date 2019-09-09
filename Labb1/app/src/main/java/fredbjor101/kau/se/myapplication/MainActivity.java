package fredbjor101.kau.se.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    protected Button myButton;
    String myString = "Det här är en toast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton = (Button)findViewById(R.id.buttonId);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V);
        });

    }
}
