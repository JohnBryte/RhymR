package at.ac.univie.hci.rhymr00908843;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    //create an ArrayList object to store selected item
    ArrayList<String> selectedItems = new ArrayList<>();
    ArrayList<String> savedItems = new ArrayList<>();
    private String originalWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setEnabled(false);
        Intent intent = getIntent();
        originalWord = intent.getStringExtra("OriginalWord");
        ArrayList<String> myArray = intent.getStringArrayListExtra("WordList");


        ListView checkableListView = (ListView) findViewById(R.id.checkableListView);
        checkableListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.rowlayout, R.id.txt_title , myArray);
        checkableListView.setAdapter(arrayAdapter);
        checkableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedWord = ((TextView)view).getText().toString();
                if(selectedItems.contains(selectedWord)){
                    selectedItems.remove(selectedWord); //uncheck Word
                    if(selectedItems.size() == 0){
                        btnSave.setEnabled(false);
                    }

                }
                else{
                    selectedItems.add(selectedWord); //check Word
                    savedItems.add(selectedWord);
                    if(selectedItems.size() > 0){
                        btnSave.setEnabled(true);
                    }
                }

            }
        });
    }
    public void saveSelectedItems(View view){

        HashMap<String, List<String>> savedWords = new HashMap<String, List<String>>();
        savedWords.put(originalWord, new ArrayList<String>());
        for(String word : savedItems){
            if(selectedItems.contains(word)){
                savedWords.get(originalWord).add(word);
            }
        }

        //savedWords.put(originalWord, selectedItems);

        //Toast Msg to notice which items were saved
        String items = "";
        for(String word : selectedItems){
            items += "-" + word + "\n";
        }
        Toast.makeText(this,"Words saved: \n" + items, Toast.LENGTH_LONG).show();

        ViewPagerAdapter adapter = MainActivity.getAdapter();
        FragmentFavourite fragmentFavourite = (FragmentFavourite) adapter.getItem(1);
        fragmentFavourite.putArguments(originalWord, savedWords);
    }
}
