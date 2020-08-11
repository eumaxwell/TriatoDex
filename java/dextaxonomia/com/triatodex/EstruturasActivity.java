package dextaxonomia.com.triatodex;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Max on 19/07/2015.
 */

public class EstruturasActivity extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button m_buttonOK;
    private Button m_buttonCancel;

    private static EstruturasActivity m_estruturasActivity;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     * @return
     */
    public static androidx.fragment.app.Fragment getInstance(int sectionNumber) {

        if (m_estruturasActivity == null) {
            m_estruturasActivity = new EstruturasActivity();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            m_estruturasActivity.setArguments(args);
        }
        return m_estruturasActivity;
    }

    public EstruturasActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.estruturas_layout, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listView_estruturas);

        List<String> listaEspecies = MainActivity.especies.getListaEstruturas();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplication().getApplicationContext(), android.R.layout.simple_list_item_1, listaEspecies){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);

                String respostaAux = getItem(position);
                String strImagemAux="";
                int index = respostaAux.indexOf("[");
                if (index != -1) {
                    strImagemAux = respostaAux.substring(index+1, respostaAux.length()-1);
                    textView.setText(respostaAux.substring(0, index));
                }
                else {
                    textView.setText(respostaAux);
                }

                final String strImagem = strImagemAux;

                textView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (!strImagem.isEmpty()) {
                            AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
                            LayoutInflater factory = LayoutInflater.from(getActivity());
                            View view = factory.inflate(R.layout.dialog_image, null);

                            ImageView foto = (ImageView) view.findViewById(R.id.imageDialog);

                            int idImagem = getResources().getIdentifier(strImagem, "mipmap", getContext().getPackageName());

                            foto.setImageResource(idImagem);

                            alertadd.setView(view);

                            alertadd.show();
                        }
                    }
                });
                return textView;
            }

        };
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClick(View v)
    {

    }
}
