package at.ac.univie.hci.rhymr00908843;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentHome extends Fragment {
    View view;

    public FragmentHome() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.home_fragment, container, false);

        Button rhymeButton = (Button) view.findViewById(R.id.rhymeButton);
        Button synonymButton = (Button) view.findViewById(R.id.synonymButton);
        Button associationButton = (Button) view.findViewById(R.id.associationButton);

        final EditText wordInput = (EditText) view.findViewById(R.id.wordInput);
        rhymeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText wordInput = (EditText) view.findViewById(R.id.wordInput);
                String url = "https://api.datamuse.com/words?rel_rhy=";
                String word = wordInput.getText().toString();
                url += word;// + "&max=3";

                DownloadTask task = new DownloadTask(word, getContext());
                task.execute(url);
            }
        });
        synonymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.datamuse.com/words?rel_syn=";
                String word = wordInput.getText().toString();
                url += word;// + "&max=3";

                DownloadTask task = new DownloadTask(word, getContext());
                task.execute(url);
            }
        });

        associationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.datamuse.com/words?rel_trg=";
                String word = wordInput.getText().toString();
                url += word;// + "&max=3";

                DownloadTask task = new DownloadTask(word, getContext());
                task.execute(url);
            }
        });

        return view;
    }
}