package dextaxonomia.com.triatodex;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by maxwell on 06/08/2015.
 */
public class AsksActivity extends Fragment implements View.OnClickListener {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button m_buttonApagar;
    private ListView listViewPerguntas;
    //private ListView listViewRespostas;
    private TextView textViewNumeroEspecies;

    private static AsksActivity m_asksActivity;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     * @return
     */
    public static androidx.fragment.app.Fragment getInstance(int sectionNumber) {

        if (m_asksActivity == null) {
            m_asksActivity = new AsksActivity();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            m_asksActivity.setArguments(args);
        }
        return m_asksActivity;
    }

    public AsksActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.respostas_layout, container, false);

        listViewPerguntas = (ListView)rootView.findViewById(R.id.listViewPerguntas_asks);
        listViewPerguntas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //listViewRespostas = (ListView)rootView.findViewById(R.id.listViewRespostas_asks);
        textViewNumeroEspecies = (TextView) rootView.findViewById(R.id.textView_asks);

        m_buttonApagar = (Button) rootView.findViewById(R.id.buttonApagar_asks);
        m_buttonApagar.setOnClickListener(this);

        update();

        return rootView;
    }

    private void update() {

        String[] listaPerguntas = MainActivity.especies.getPerguntas();
        ArrayList<String> listaRespostas = MainActivity.especies.getRespostas();

        ArrayList<Item> itens = new ArrayList<>();
        short aux = 0;
        for (String s : listaRespostas)
        {
            if (s != "") {
                String resposta;
                int index = s.indexOf("[");
                if (index != -1) {
                    resposta = s.substring(0, index);
                }
                else {
                    resposta = s;
                }
                itens.add(new Item(listaPerguntas[aux], resposta, aux));
            }
            aux++;
        }

        ItemAdapter adapterPerguntas = new ItemAdapter(getActivity().getApplication().getApplicationContext(), itens);

        listViewPerguntas.setAdapter(adapterPerguntas);
        //listViewPerguntas.setSelector(R.drawable.selector_for_position_list);
    }

    @Override
    public void onClick(View v)
    {
        if(v == m_buttonApagar)
        {
            ItemAdapter a = (ItemAdapter) listViewPerguntas.getAdapter();
            Set<Integer> listIdItens = a.getItemsSelecteds();

            MainActivity.especies.apagaRespostas(listIdItens);
            MainActivity.especies.recalcular();
            update();
        }
    }


    public class Item {
        public String mPergunta;
        public String mResposta;
        public int mId;

        Item(String pergunta, String resposta, int id)
        {
            mPergunta = pergunta;
            mResposta = resposta;
            mId = id;
        }
    }

    public class ItemAdapter extends ArrayAdapter<Item> {

        private Set<Integer> itensSelecteds;

        public ItemAdapter(Context c, List<Item> items) {
            super(c, R.layout.itens_asks, items);
            itensSelecteds = new HashSet<Integer>();

            //super(ctx, R.layout.list_products_row, productList);
            //this.productList = productList;
            //this.context = ctx;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = null;

            if (convertView == null) {
                row = inflater.inflate(R.layout.itens_asks, parent, false);
            } else {
                row = convertView;
            }

            TextView pergunta = (TextView) row.findViewById(R.id.textView1_itensAsks);
            TextView resposta = (TextView) row.findViewById(R.id.textView2_itensAsks);
            CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox_itensAsks);
            pergunta.setText(getItem(position).mPergunta);
            resposta.setText(getItem(position).mResposta);
            checkBox.setClickable(true);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    if (!itensSelecteds.add(getItem(position).mId))
                        itensSelecteds.remove(getItem(position).mId);
                    //final boolean isChecked = view.isChecked();
                    // Do something here.
                }
            });
            pergunta.setTextColor(Color.BLACK);
            resposta.setTextColor(Color.BLACK);

            return row;
        }

        public Set<Integer> getItemsSelecteds() {
            return itensSelecteds;
        }
    }
}
