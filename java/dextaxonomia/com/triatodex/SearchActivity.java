package dextaxonomia.com.triatodex;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Max on 18/07/2015.
 */
public class SearchActivity extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button m_buttonResponde;
    private Button m_buttonProximo;
    private Button m_buttonAnterior;
    private TextView m_textViewPergunta;
    private ListView m_listView;
    private TextView m_textViewNumEspecies;

    View test;

    private static SearchActivity searchActivity;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     * @return
     */
    public static androidx.fragment.app.Fragment getInstance(int sectionNumber) {

        //if (searchActivity == null) {
            searchActivity = new SearchActivity();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            searchActivity.setArguments(args);
        //}
        return searchActivity;
    }

    public SearchActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pesquisar_layout, container, false);

        test = rootView;

        m_textViewPergunta = (TextView) rootView.findViewById(R.id.textView_search);

        m_buttonResponde = (Button) rootView.findViewById(R.id.buttonOK_search);
        m_buttonResponde.setOnClickListener(this);

        //m_buttonProximo = (Button) rootView.findViewById(R.id.buttonProximo_search);
        //m_buttonProximo.setOnClickListener(this);

        //m_buttonAnterior = (Button) rootView.findViewById(R.id.buttonAnterior_search);
        //m_buttonAnterior.setOnClickListener(this);

        m_listView = (ListView) rootView.findViewById(R.id.listView);
        m_listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        m_textViewNumEspecies = (TextView) rootView.findViewById(R.id.textViewNumEspecies_search);
        update();

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        if (m_buttonResponde == v) {
            if (m_listView.getAdapter() != null)
            {
                int itemSelecionado = ((ItemAdapter) (m_listView.getAdapter())).getIdItemChecked();
                if (itemSelecionado != -1) {
                    String item = m_listView.getAdapter().getItem(
                            itemSelecionado).toString();
                    MainActivity.especies.setResposta(item);

                    if (!update()) {
                        this.getFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, ListaActivity.getInstance(2))
                                .commit();
                    }
                }
            }
        }
        else if (m_buttonProximo == v) {
            MainActivity.especies.setIncremento(1);
            MainActivity.especies.incrementaPergunta();
            update();
        }
        else if (m_buttonAnterior == v) {
            MainActivity.especies.setIncremento(-1);
            MainActivity.especies.incrementaPergunta();
            update();
        }
    }

    public boolean update(){

        Set<String> respostas;
        do {
            respostas = MainActivity.especies.getRespostasPerguntaAtual();
            if (respostas.size() < 2)
                MainActivity.especies.incrementaPergunta();
            if (MainActivity.especies.temResultado())
                return false;
        } while (respostas.size() < 2);

        String strPergunta = MainActivity.especies.getPergunta();
        m_textViewPergunta.setText(strPergunta);

        List<String> arrayOpcoes = new ArrayList<String>();
        for (String s : respostas) {
            arrayOpcoes.add(s);
        }
        java.util.Collections.sort(arrayOpcoes);
        ItemAdapter adapterPerguntas = new ItemAdapter(getActivity().getApplication().getApplicationContext(), arrayOpcoes);

        m_listView.setAdapter(adapterPerguntas);

        Integer aux = MainActivity.especies.getNumeroEspecies();
        m_textViewNumEspecies.setText(aux.toString());

        return true;
    }


    public class ItemAdapter extends ArrayAdapter<String> {
        String textCheckedItem;
        int idItemChecked;
        ArrayList<TextView> listCheckbox;

        public ItemAdapter(Context c, List<String> items) {
            super(c, R.layout.itens_search, items);
            idItemChecked = -1;

            listCheckbox = new ArrayList<TextView>();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = null;

            if (convertView == null) {
                row = inflater.inflate(R.layout.itens_search, parent, false);
            } else {
                row = convertView;
            }

            TextView resposta = (TextView) row.findViewById(R.id.textView_itensSearch);
            ImageButton botaoDetalhe = (ImageButton) row.findViewById(R.id.imageButton_itensSearch);
            //CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox_itensSearch);


            String respostaAux = getItem(position);
            String strImagemAux="";
            int index = respostaAux.indexOf("[");
            if (index != -1) {
                strImagemAux = respostaAux.substring(index+1, respostaAux.length()-1);
                resposta.setText(respostaAux.substring(0, index));
            }
            else {
                resposta.setText(respostaAux);
            }
            listCheckbox.add(resposta);

            final String strImagem = strImagemAux;

            botaoDetalhe.setClickable(true);
            botaoDetalhe.setOnClickListener(new View.OnClickListener() {
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

            resposta.setClickable(true);
            resposta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (TextView r : listCheckbox) {
                        r.setPadding(0,0,0,0);
                    }

                    int a = v.getId();
                    if (idItemChecked == position) {
                        idItemChecked = -1;
                        v.setPadding(0,0,0,0);
                    }
                    else {
                        idItemChecked = position;
                        v.setPadding(40,0,0,0);
                    }

                    /*
                    for (TextView r : listCheckbox) {
                        if (r == v) {
                            if (idItemChecked == position) {
                                idItemChecked = -1;
                                r.setPadding(0,0,0,0);
                            }
                            else {
                                idItemChecked = position;
                                r.setPadding(40,0,0,0);
                            }
                        }
                        else {
                            r.setPadding(0,0,0,0);
                        }

                    }*/
                }
            });
            /*listCheckbox.add(checkBox);
            checkBox.setClickable(true);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (CheckBox r : listCheckbox) {
                        if (r != v) {
                            r.setChecked(false);
                        }
                        else {
                            if (idItemChecked == position)
                                idItemChecked = -1;
                            else
                                idItemChecked = position;
                        }
                    }
                }
            });
            */

            return row;
        }

        public int getIdItemChecked() { return idItemChecked; }
        public String getTextCheckedItem()
        {
            return textCheckedItem;
        }
    }
}
/*
imgButton =(ImageButton)findViewById(R.id.imageButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Toast.makeText(getApplicationContext(),"You download is resumed",Toast.LENGTH_LONG).show();
        }
        });
*/


/*
*

File imgFile = new  File("/sdcard/Images/test_image.jpg");

if(imgFile.exists()){

    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

    ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

    myImage.setImageBitmap(myBitmap);

}

@@@@

File imageFile = new  File(“/sdcard/example/image.jpg”);
    if(imageFile.exists()){
             ImageView imageView= (ImageView) findViewById(R.id.imageviewTest);
             imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
}

@@@

Bitmap bmImg = BitmapFactory.decodeFile("path of your img1");
imageView.setImageBitmap(bmImg);

@@@

File imgFile = new  File(“filepath”);
    if(imgFile.exists())
    {
        ImageView myImage = new ImageView(this);
        myImage.setImageURI(Uri.fromFile(imgFile));

    }


*
* */