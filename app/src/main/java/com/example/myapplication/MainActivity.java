package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> songs;
    ArrayList<Integer> image;
    ArrayList<String> shuffledSongs;
    ArrayList<Integer> image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("MainActivity", "HERE IAM");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find resource id of the ListView from xml to the activity
        ListView myListView = (ListView) findViewById(R.id.list_item);
        //find resource id of textView
        //creating an array adapter to send the items from arraylist to ListView
        //Passing 4 parameters to the ArrayAdapter constructor
        // 1 -> this or MainActivity.java or getApplicationContext()
        // 2 -> R.layout.activity_main -> Main xml activity
        // 3 -> R.layout.text_view file , this parameter should only contain textView layout file or the id of
        // TextView from the xml file should be given
        //  MUST contain a TextView with and id(the third parameter)
        //  that you pass to your ArrayAdapter so it can know where to put the Strings in the row layout.

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, R.id.text, setSongs());

        //sent the list of songs into ArrayAdapter , now providing it to ListView
//       myListView.setAdapter(arrayAdapter);

        //find resource id of shuffle
        ImageView shuffle = (ImageView) findViewById(R.id.shuffle);

        //onCLick listener for shuffle . It is not associated with AdapterView, so it will have different method for clickListener
        shuffle.setOnClickListener(this);
        CustomListView customListView = new CustomListView(this, setImageForSong(), setSongs());
        myListView.setAdapter(customListView);

        //onClick listener for adapter view in android
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Context context = getApplicationContext();
                CharSequence text = "The selected item is : " + selectedItem + " position = " + position;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent = new Intent(MainActivity.this, MusicPlayer.class);
                intent.putExtra("songKeyName", selectedItem);
                intent.putExtra("keyPosition", position);






//                intent.putExtra("ArrayList ", songs);
//
//
//                    String previousOfSelectedItem = (String) parent.getItemAtPosition((position - 1) % (songs.size() - 1));
//                    intent.putExtra("previousSongKeyName", previousOfSelectedItem);
//                    String nextOfSelectedItem = (String) parent.getItemAtPosition((position + 1) % (songs.size() - 1));
//                    intent.putExtra("nextSongKeyName", nextOfSelectedItem);


//                if(position-1 < 0)
//                {
//                    Context context2 = getApplicationContext();
//                    CharSequence text2 = "No more previous song " ;
//                    int duration2 = Toast.LENGTH_SHORT;
//                    Toast toast2 = Toast.makeText(context2, text2, duration2);
//                    toast2.show();
//                }
//
//                if(position + 1 > songs.size() - 1)
//                {
//                    Context context3 = getApplicationContext();
//                    CharSequence text3 = "No more next song " ;
//                    int duration3 = Toast.LENGTH_SHORT;
//                    Toast toast3 = Toast.makeText(context3, text3, duration3);
//                    toast3.show();
//                }

                startActivity(intent);
            }
        });


    }

    public ArrayList<String> setSongs() {
        // adding songs in a array list
        songs = new ArrayList<>();
        songs.add("Happy by Pharrell Williams");
        songs.add("Counting Stars by OneRepublic");
        songs.add("Happier by Marshmello");
        songs.add("Silence by Marshmello,Khalid");
        songs.add("Hey Brother ! by Avicii");
        songs.add("Fireflies by Owl City");
        return songs;

    }

    public ArrayList<Integer> setImageForSong() {
        image = new ArrayList<>();
        image.add(R.drawable.happy_image);
        image.add(R.drawable.counting_stars_image);
        image.add(R.drawable.happier_by_marshmello_image);
        image.add(R.drawable.silence_by_khalid);
        image.add(R.drawable.hey_brother_by_avicii);
        image.add(R.drawable.fireflies);
        return image;
    }
//public int returnSizeOfArrayList()
//{
//
//}

//    @Override
//    public void onClick(View v) {
//        ArrayList<String> shuffledSongs = setSongs();
//        Collections.shuffle(shuffledSongs);
//
//        ListView myListView = (ListView) findViewById(R.id.list_item);
//        myListView.setAdapter(null);
//
////        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, R.id.text, shuffledSongs);
////        myListView.setAdapter(arrayAdapter);
//
//        CustomListView customListView = new CustomListView(this, setImageForSong(), shuffledSongs);
//        myListView.setAdapter(customListView);
//    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
//        ArrayList<String> shuffledSongs = setSongs();

//        ArrayList<Integer> image1 = setImageForSong();
        ListView myListView = (ListView) findViewById(R.id.list_item);

        if(shuffledSongs != null)
        {
            Map<String, Integer> map = IntStream.range(0, shuffledSongs.size())
                    .boxed()
                    .collect(Collectors.toMap(i -> shuffledSongs.get(i), i -> image1.get(i)));

            Collections.shuffle(Collections.singletonList(map));
//
            Log.v("MainActivity","KeySet()" + map.keySet());

            shuffledSongs = new ArrayList<>(map.keySet());
            image1 = new ArrayList<>(map.values());
        }
           else
        {
            Map<String, Integer> map = IntStream.range(0, songs.size())
                    .boxed()
                    .collect(Collectors.toMap(i -> songs.get(i), i -> image.get(i)));

            Collections.shuffle(Collections.singletonList(map));
//
            Log.v("MainActivity","KeySet()" + map.keySet());

            shuffledSongs = new ArrayList<>(map.keySet());
            image1 = new ArrayList<>(map.values());

        }

        myListView.setAdapter(null);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, R.id.text, shuffledSongs);
//        myListView.setAdapter(arrayAdapter);


        CustomListView customListView = new CustomListView(this, image1, shuffledSongs);
        myListView.setAdapter(customListView);
    }

}