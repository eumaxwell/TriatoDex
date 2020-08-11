package dextaxonomia.com.triatodex;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Max on 27/07/2015.
 */
public class InicialActivity extends Fragment implements View.OnClickListener{
/**
 * The fragment argument representing the section number for this
 * fragment.
 */
    private static final String ARG_SECTION_NUMBER = "section_about";

    private static InicialActivity m_aboutActivity;
/**
 * Returns a new instance of this fragment for the given section
 * number.
 */

    private Button m_buttonPesquisar;

    public InicialActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inicial_layout, container, false);

        m_buttonPesquisar = (Button) rootView.findViewById(R.id.buttonInitialSearch);
        m_buttonPesquisar.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == m_buttonPesquisar)
        {
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, SearchActivity.getInstance(0))
                    .commit();
        }
    }
}
