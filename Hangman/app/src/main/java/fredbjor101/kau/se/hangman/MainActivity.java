package fredbjor101.kau.se.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends Activity {

    //deklarerade variabler

    //En char vecktor och en Arraylista för att samla orden i
    char[] charArray;
    ArrayList<String> myWords;


    //Kopplar ihop variablerna med GUI
    TextView txtWordUser;
    String wordUser;
    String wordDisplayedString;


    EditText editInput;
    TextView txtTried;
    String lettersTried;

    TextView txtTriesLeft;
    String triesLeft;

    //Konstanter
    final String MSG_TO_USER = "Bokstäver använda: ";
    final String WIN_MSG = "Du vann!";
    final String LOST_MSG = "Du förlorade!";


    Animation scaleAnimation;
    Animation results;
    TableRow  trTriesLeft;


    //void metoded gameStart() som inte returnerar någon data
    void gameStart() {
        //blandar vektorn
        Collections.shuffle(myWords);

        //tar första elemementet från vektorn
        wordUser = myWords.get(0);

        //tar bort första elementet från vektorn
        myWords.remove(0);

        //initierar char vektorn, och returnerar en char vektor från strängen
        charArray = wordUser.toCharArray();

        //Skapar understreck från andra bokstaven upp till den näst sista bokstaven
        for (int i = 1; i < charArray.length - 1; i++) {
            charArray[i] = '_';
        }

        //refererar till funktionen wordReveal för att visa första bokstaven
        wordReveal(charArray[0]);

        //visar den sista bokstaven
        wordReveal(charArray[charArray.length - 1]);

        //initierar en sträng från charArray
        wordDisplayedString = String.valueOf(charArray);

        //visar ordet
        displayWord();

        //input
        editInput.setText("");

        //försök av bokstäver
        //Måste ha en blank sträng för att se om strängen finns
        lettersTried = " ";


        //Visa i appen
        txtTried.setText(MSG_TO_USER);

        //Antal försök kvar
        triesLeft = "I I I I I";
        txtTriesLeft.setText(triesLeft);

    }


    //skapar en funktion med metoden wordReveal, som parameter med en char variabel
    void wordReveal(char letter) {
        int indexOfLetter = wordUser.indexOf(letter);

        //
        while (indexOfLetter >= 0) {
            charArray[indexOfLetter] = wordUser.charAt(indexOfLetter);
            indexOfLetter = wordUser.indexOf(letter, indexOfLetter + 1);
        }

        wordDisplayedString = String.valueOf(charArray);

    }

    void displayWord() {
        String formattedString = "";
        for (char character : charArray) {
            formattedString += character + " ";
        }

        txtWordUser.setText(formattedString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initierar variabler

        //instanserar en Array
        myWords = new ArrayList<String>();

        //Hittar layout från activity_main.xml
        txtWordUser = findViewById(R.id.txtWordGuessed);
        editInput = findViewById(R.id.editInput);
        txtTried = findViewById(R.id.txtTried);
        txtTriesLeft = findViewById(R.id.txtTriesLeft);

        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        results = AnimationUtils.loadAnimation(this, R.anim.results);
        results.setFillAfter(true);
        trTriesLeft = findViewById(R.id.trTiesLeft);


        //Instanserar InputStream för att komma åt min fil med ord
        InputStream myInputStream = null;
        //Komma åt användarens input
        Scanner in = null;
        String xWord = "";

        //Öppnar filen capital_cities.txt
        try {
            myInputStream = getAssets().open("capital_cities.txt");
            in = new Scanner(myInputStream);
            //in.hasNext kollar om filen har mer ord att läsa från, om det inte några mer ord avslutas while loopen
            //Lägger till ord från myWords
            while (in.hasNext()) {
                xWord = in.next();
                myWords.add(xWord);

            }

        } catch (IOException e) {
            Toast.makeText(MainActivity.this,
                    e.getClass().getSimpleName() + ": " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } finally {
            //Stänger ner Scanner när den är skilt från null
            if (in != null) {
                in.close();
            }

            //Stänger ner InputStream
            try {
                if (myInputStream != null) {
                    myInputStream.close();
                }

            } catch (IOException e) {
                Toast.makeText(MainActivity.this,
                        e.getClass().getSimpleName() + ": " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        }

        // Initierar funktionen gameStart()
        gameStart();

        //lyssnare för editText fältet
        editInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    isAlpha(charSequence.charAt(0));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    void isAlpha(char letter) {
        //kollar om bokstaven fanns i det gissade ordet
        if (wordUser.indexOf(letter) >= 0) {

            //om ordet inte visats
            if (wordDisplayedString.indexOf(letter) < 0) {

                //Animation för om användaren gissar rätt
                txtWordUser.startAnimation(scaleAnimation);

                //ersätter understrecken med bokstaven
                wordReveal(letter);

                //uppdaterar ändringarna
                displayWord();

                //kollar om användaren har vunnit
                if (!wordDisplayedString.contains("_")) {
                    trTriesLeft.startAnimation(results);
                    txtTriesLeft.setText(WIN_MSG);

                }
            }
        }
        //om inte bokstaven finns i det gissade ordet
        else {
            //Antalet försök kvar
            triesLeftMethod();

            //kollar om användaren har förlorat
            if (triesLeft.isEmpty()) {
                trTriesLeft.startAnimation(results);
                txtTriesLeft.setText(LOST_MSG);
                txtWordUser.setText(wordUser);
            }

        }

        //visa det gissade ordet
        if (lettersTried.indexOf(letter) < 0) {
            lettersTried += letter + ", ";
            String msgDisplayed = MSG_TO_USER + lettersTried;
            txtTried.setText(msgDisplayed);

        }
    }

    void triesLeftMethod() {
        if (!triesLeft.isEmpty()) {

            //Animerar antal försök kvar
            txtTriesLeft.startAnimation(scaleAnimation);

            if (triesLeft.length() == 1) {
                triesLeft = "";
            } else {
                triesLeft = triesLeft.substring(0, triesLeft.length() -2);
            }
            txtTriesLeft.setText(triesLeft);
        }
    }



   public void resetGame(View v) {

        //Tar bort animationen vid nytt spel
       trTriesLeft.clearAnimation();

        gameStart();
    }

}
