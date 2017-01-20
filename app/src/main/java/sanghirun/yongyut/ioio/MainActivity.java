/************************************************************************
 *
 * 	 TITLE : BASIC ANALOG READER
 *   CATAGORIES : ANALOG INPUT
 *   CREATED DATE : JUNE 6, 2012
 *   AUTHOR : INNOVATIVE EXPERIMENT CO., LTD. (INEX)
 *   WEBSITE : HTTP://WWW.INEX.CO.TH
 *
 ***********************************************************************/

package sanghirun.yongyut.ioio;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;

public class MainActivity extends IOIOActivity {
    // Declare text view instance
    TextView txtRead, txtGetVoltage;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign text view with widget on layout
        txtRead = (TextView)findViewById(R.id.txtRead);
        txtGetVoltage = (TextView)findViewById(R.id.txtGetV);
    }

    class Looper extends BaseIOIOLooper {
        // Declare analog input instance
        AnalogInput ain;

        protected void setup() throws ConnectionLostException {
            // Assign analog input with port on IOIO board
            ain = ioio_.openAnalogInput(31);

            runOnUiThread(new Runnable() {
                public void run() {
                    // Toast message "Connect"
                    // when android device connect with IOIO board
                    Toast.makeText(getApplicationContext(),
                            "Connected!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void loop() throws ConnectionLostException {
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        // Read analog voltage from port 15
                        txtRead.setText(String.format("%.3f", ain.read()));
                        txtGetVoltage.setText(String.format("%.3f", ain.getVoltage()) + " V");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ConnectionLostException e) {
                        e.printStackTrace();
                    }
                }
            });

            try {
                //Delay time 100 milliseconds
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected IOIOLooper createIOIOLooper() {
        return new Looper();
    }
}