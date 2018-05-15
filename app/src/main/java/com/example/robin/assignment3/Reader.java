package com.example.robin.assignment3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Scanner;

public class Reader extends AppCompatActivity {

    static LinkedList<String> hintHolder = new LinkedList<String>();
    static LinkedList<String> wordsSent = new LinkedList<String>();
    static int hintNumber = 0;
    static int wordsNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scanFile();
        Log.d("debug", "Made it past scanFile");

        //gets and sends the first hint
        String hint = hintHolderHandler(0);
        TextView hintText = (TextView) findViewById(R.id.texthint);
        hintText.setText(hint);

        TextView countertext = (TextView) findViewById(R.id.wordcounter);
        countertext.setText((hintNumber-wordsNumber) + " Words left!");

    }




    //send button is pressed, entered words are taken and logged.
    public void sendWord(View view) {
        EditText enteredWord = (EditText) findViewById(R.id.enteredWord);
        TextView hintText = (TextView) findViewById(R.id.texthint); //hint display
        TextView countertext = (TextView) findViewById(R.id.wordcounter); //number of words left

        wordsSent.add(enteredWord.getText().toString());
        Log.d("debug", "text entered: " + enteredWord.getText().toString() + " text put in Linked List (should match): " + wordsSent.get(wordsNumber));
        ++wordsNumber;

        String hint = hintHolderHandler(wordsNumber);

        if (wordsNumber == hintNumber) {
            scanFile();
            //all the hints have been sent, call scanFile
            //again to go to the finished story activity.
        } else {
            hintText.setText(hint);
        }

        countertext.setText((hintNumber-wordsNumber) + " Words left!");
    }


    //This method will return a string from hintHolder at the given index that's
    //useable for the textview.
    public String hintHolderHandler (int index) {
        Log.d("debug", "in hintHolderHandler");
        String hint = "";
        if (wordsNumber < hintNumber) {
            char[] holder = hintHolder.get(index).toCharArray();


            for (int i = 0; i < holder.length; ++i) {
                if (holder[i] == '<' || holder[i] == '>') {
                    ///do nothing we don't want this character
                } else if (holder[i] == '-') {
                    hint = hint + " "; //add a space instead of -
                } else {
                    hint = hint + holder[i];
                }
            }

            if (hint.startsWith("a") || hint.startsWith("A") ||
                    hint.startsWith("e") || hint.startsWith("E") ||
                    hint.startsWith("i") || hint.startsWith("I") ||
                    hint.startsWith("o") || hint.startsWith("O") ||
                    hint.startsWith("u") || hint.startsWith("U")) { //starts with a vowel
                hint = "Please type an " + hint;
            } else {
                hint = "Please type a " + hint;
            }
        }

        return hint;
    }

    //this method will be called twice - once when the program is first being built to build
    //the hintHolder linked list and set our hintNumber, and again to build the actual story.
    public void scanFile() {
        Log.d("debug", "in scanfile file");
        try {
            Scanner scan = new Scanner(getResources().openRawResource(R.raw.example));
            String allText = "";
            Log.d("debug", "in scanfile file");

            //this is the first run through; when the program is first run.
            if (hintHolder.isEmpty()) { //hintHolder is empty and therefore has not been built
                while (scan.hasNext()) { //we're going through token by token
                    String word = scan.next();
                        if (word.startsWith("<")) { //to find our hints
                            hintHolder.add(word);
                            hintNumber++;
                            Log.d("debug", word + hintNumber);
                        } //else do nothing. We'll build the actual story next time; we don't
                        //need it the first time through.
                }
                scan.close();
            } else { //hintHolder has been filled, and by our code, so has wordsSent. We're building the story.
                int counter = 0;
                while (scan.hasNext()) {
                    String word = scan.next();
                    if (word.startsWith("<") && counter-2 < wordsNumber) {
                        Log.d("debug", "story creation found a < >");
                        allText = allText + " " + wordsSent.get(counter);
                        //gets the user sent word at the currently needed index
                        Log.d("debug", "word sent " + wordsSent.get(counter) + " counter " + counter);
                        counter++;
                    } else {
                        allText = allText + " " + word;
                    }
                }
                //Intent stuff to go onto the Display activity

                scan.close();
                Context context = Reader.this;
                Class destinationActivity = Display.class;
                Intent startDisplay = new Intent(context, destinationActivity);
                startDisplay.putExtra(Intent.EXTRA_TEXT, allText);

                //clear current stuffs for another story
                hintHolder.clear();
                wordsSent.clear();
                hintNumber = 0;
                wordsNumber = 0;

                startActivity(startDisplay);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
