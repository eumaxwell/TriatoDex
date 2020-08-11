package dextaxonomia.com.triatodex;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Max on 24/09/2015.
 */
public class DetailSpecieActivity extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button m_buttonOK;
    private Button m_buttonCancel;

    private static DetailSpecieActivity m_detailActivity;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DetailSpecieActivity getInstance(int sectionNumber) {

        if (m_detailActivity == null) {
            DetailSpecieActivity m_detailActivity = new DetailSpecieActivity();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            m_detailActivity.setArguments(args);
        }
        return m_detailActivity;
    }

    public DetailSpecieActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detailspecie, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listView_lista);

        List<String> listaEspecies = MainActivity.especies.getEspecies();
        
        
        return rootView;
    }

}
