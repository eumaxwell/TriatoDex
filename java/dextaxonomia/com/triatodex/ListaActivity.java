package dextaxonomia.com.triatodex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.text.Collator;
import java.util.Collections;
import java.util.List;

/**
 * Created by Max on 19/07/2015.
 */

public class ListaActivity extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button m_buttonOK;
    private Button m_buttonCancel;

    private static ListaActivity m_listaActivity;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     * @return
     */
    public static ListaActivity getInstance(int sectionNumber) {

        if (m_listaActivity == null) {
            m_listaActivity = new ListaActivity();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            m_listaActivity.setArguments(args);
        }
        return m_listaActivity;
    }

    public ListaActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.possiveis_especies_layout, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listView_lista);

        List<String> listaEspecies = MainActivity.especies.getEspecies();
        Collections.sort(listaEspecies, Collator.getInstance());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplication().getApplicationContext(), android.R.layout.simple_list_item_1, listaEspecies){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);
                textView.setTypeface(null, Typeface.ITALIC);

                textView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getActivity());

                        LayoutInflater li = getLayoutInflater(savedInstanceState);
                        View myView = li.inflate(R.layout.item_specie, null);

                        TextView textViewDescricao = (TextView) myView.findViewById(R.id.textView_item_specie);
                        ImageView imageViewEspecie = (ImageView) myView.findViewById(R.id.imageView_item_specie);
                        helpBuilder.setView(myView);

                        TextView titulo = new TextView(getActivity());
                        titulo.setGravity(Gravity.CENTER_HORIZONTAL);
                        titulo.setTextSize(20);
                        titulo.setTypeface(null, Typeface.ITALIC);
                        titulo.setTextColor(Color.BLACK);

                        String strNomeEspecie = ((TextView) v).getText().toString();
                        titulo.setText(strNomeEspecie);
                        //set custom title
                        helpBuilder.setCustomTitle(titulo);

                        DescricaoEspecie descricaoEspecie = MainActivity.especies.getMapEspecieDescricao().get(((TextView)v).getText());


                        if (!descricaoEspecie.getImagemEspecie().isEmpty())
                        {
                            int idImagem = getResources().getIdentifier(descricaoEspecie.getImagemEspecie(), "mipmap", getContext().getPackageName());
                            imageViewEspecie.setImageResource(idImagem);
                        }

                        String mensagem = "";

                        String strImagemAux="", distribuicao="";
                        int index = descricaoEspecie.getDistribuicao().indexOf("[");
                        if (index != -1) {
                            strImagemAux = descricaoEspecie.getDistribuicao().substring(index+1, descricaoEspecie.getDistribuicao().length()-1);
                            distribuicao += descricaoEspecie.getDistribuicao().substring(0, index);
                        }
                        else {
                            distribuicao += descricaoEspecie.getDistribuicao();
                        }

                        mensagem += descricaoEspecie.getDescritor()+"\n\n";
                        mensagem += distribuicao+"\n\n";
                        mensagem += descricaoEspecie.getTamanho()+"\n\n";
                        mensagem += descricaoEspecie.getImportanciaMedica()+"\n\n";
                        mensagem += descricaoEspecie.getHabitat();

                        final String strImagem = strImagemAux;

                        textViewDescricao.setText(mensagem);
                        //helpBuilder.setMessage(mensagem);

                        helpBuilder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                    }
                                });

                        helpBuilder.setNegativeButton(R.string.mapa,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
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

                        // Remember, create doesn't show the dialog
                        AlertDialog helpDialog = helpBuilder.create();
                        helpDialog.show();

                        Button nbutton = helpDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        nbutton.setTextColor(Color.BLACK);
                        Button pbutton = helpDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setTextColor(Color.BLACK);

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
