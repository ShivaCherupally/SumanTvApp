package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import info.dkapp.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizProceedFragment extends Fragment {

    Button buttonProceed;
    EditText editTextLocation;

    public QuizProceedFragment() {
        // Required empty public constructor
    }
    public static QuizProceedFragment newInstance(String param1, String param2) {
        QuizProceedFragment fragment = new QuizProceedFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_quiz_proceed, container, false);

        initializeViews(root);

        return root;
    }

    public void  initializeViews(View root){

        editTextLocation = root.findViewById(R.id.editTextLocation);
        buttonProceed = root.findViewById(R.id.buttonProceed);

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextLocation.getText().toString().isEmpty()){
                    editTextLocation.setError("Enter location");
                }else {
//                    Intent intent = new Intent(getGlobalActivity(), QuizActivity.class);
//                    startActivity(intent);
                }
            }
        });
    }
}
