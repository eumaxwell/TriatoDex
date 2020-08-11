package dextaxonomia.com.triatodex;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Max on 18/07/2015.
 */
public final class Especies {

    private Map<String, Vector<String>> mapEspecieAtributosArquivo;
    private Map<String, Vector<String>> mapEspecieAtributosAtual;
    private Map<String, DescricaoEspecie> mapEspecieDescricao;
    private Vector<String> listaPerguntas;
    private ArrayList<String> listaRespostas;
    private ArrayList<String> listaEstruturas;

    private int idPergunta;
    private int incremento;
    private int idUltimaResposta;

    public Especies(Resources resources) {
        mapEspecieAtributosArquivo = new HashMap<String, Vector<String>>();
        mapEspecieDescricao = new HashMap<String, DescricaoEspecie>();

        listaPerguntas = new Vector<String>();
        setListaEstruturas(new ArrayList<String>());

        InputStream fileEspecies = resources.openRawResource(R.raw.especies);
        carregaArquivoEspecies(fileEspecies);

        InputStream fileEstruturas = resources.openRawResource(R.raw.estruturas);
        carregaArquivoEstruturas(fileEstruturas);

        mapEspecieAtributosAtual = new HashMap<String,Vector<String>>(mapEspecieAtributosArquivo);
        idUltimaResposta = mapEspecieAtributosArquivo.size() / 2;
        listaRespostas = new ArrayList<String>();
        for (int i=0;i<listaPerguntas.size();i++)
            listaRespostas.add("");

        incremento = 1;
    }

    private void carregaArquivoEspecies(InputStream file) {
        int iNumLinha = 0;
        try {

            InputStreamReader isr = new InputStreamReader(file);
            BufferedReader br = new BufferedReader(isr);
            String line = null;


            String distribuicao="";
            String importanciaMedica="";
            String descritor="";
            String habitat="";
            String tamanho="";

            while ((line = br.readLine()) != null) {

                String[] listStrings = line.split(";");

                iNumLinha++;
                if (iNumLinha == 1) {
                    for (String s : listStrings) {
                        listaPerguntas.add(s);
                    }
                    listaPerguntas.remove(0);

                    descritor = listaPerguntas.elementAt(0);
                    listaPerguntas.remove(0);
                    tamanho = listaPerguntas.elementAt(0);
                    listaPerguntas.remove(0);
                    distribuicao = listaPerguntas.elementAt(0);
                    listaPerguntas.remove(0);
                    importanciaMedica = listaPerguntas.elementAt(0);
                    listaPerguntas.remove(0);
                    habitat = listaPerguntas.elementAt(0);
                    listaPerguntas.remove(0);

                } else {
                    String strNomeEspecie = listStrings[0];
                    Vector<String> vecAtributos = new Vector<String>();
                    for (String s : listStrings) {
                        vecAtributos.add(s);
                    }
                    vecAtributos.remove(0);

                    DescricaoEspecie descricaoEspecie = new DescricaoEspecie();

                    descricaoEspecie.setDescritor(descritor+": " + vecAtributos.elementAt(0));
                    descricaoEspecie.setTamanho(tamanho+": " + vecAtributos.elementAt(1));
                    descricaoEspecie.setDistribuicao(distribuicao+": "+vecAtributos.elementAt(2));
                    descricaoEspecie.setImportanciaMedica(importanciaMedica+": " + vecAtributos.elementAt(3));
                    descricaoEspecie.setHabitat(habitat+": " + vecAtributos.elementAt(4));

                    String strImagem="";
                    int index = strNomeEspecie.indexOf("[");
                    if (index != -1) {
                        strImagem = strNomeEspecie.substring(index+1, strNomeEspecie.length()-1);
                        strNomeEspecie = strNomeEspecie.substring(0, index);
                    }
                    descricaoEspecie.setImagemEspecie(strImagem);

                    vecAtributos.remove(0);
                    vecAtributos.remove(0);
                    vecAtributos.remove(0);
                    vecAtributos.remove(0);
                    mapEspecieDescricao.put(strNomeEspecie, descricaoEspecie);

                    vecAtributos.remove(0);

                    for (int i = vecAtributos.size(); i < listaPerguntas.size(); i++) {
                        vecAtributos.add("");
                    }

                    mapEspecieAtributosArquivo.put(strNomeEspecie, vecAtributos);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    private void carregaArquivoEstruturas(InputStream file) {
        int iNumLinha = 0;

        try {

            InputStreamReader isr = new InputStreamReader(file);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null) {

                String[] listStrings = line.split(";");

                iNumLinha++;
                if (iNumLinha == 1) {
                    continue;
                } else {
                    getListaEstruturas().add(line);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }


    public void setResposta(String strResposta) {
        listaRespostas.set(idPergunta, strResposta);

        idUltimaResposta = idPergunta;
        ArrayList<String> listaApagar = new ArrayList<String>();
        for (Map.Entry<String, Vector<String>> entry : mapEspecieAtributosAtual.entrySet()) {
            if (!entry.getValue().elementAt(idPergunta).isEmpty() && !entry.getValue().elementAt(idPergunta).equals(strResposta)) {
                listaApagar.add(entry.getKey());
            }
        }

        for (String s : listaApagar) {
            mapEspecieAtributosAtual.remove(s);
        }
        incrementaPergunta();
    }

    public void incrementaPergunta() {
        idPergunta+= incremento;
        if (idPergunta == listaPerguntas.size()){
            idPergunta=0;
        }
        else if (idPergunta == -1){
            idPergunta=listaPerguntas.size()-1;
        }
    }

    public void setIncremento(int inc)
    {
        incremento=inc;
    }

    public String[] getPerguntas()
    {
        String[] respostas = new String[listaPerguntas.size()];

        for(int i=0; i < listaPerguntas.size();i++){
            respostas[i] = listaPerguntas.elementAt(i);
        }
        return respostas;
    }

    public String getPergunta()
    {
        return listaPerguntas.elementAt(idPergunta);
    }
    public Set<String> getRespostasPerguntaAtual()
    {
        Set<String> setRespostas = new LinkedHashSet<String>();
        for (Map.Entry<String, Vector<String>> entry : mapEspecieAtributosAtual.entrySet()){
            if (!entry.getValue().elementAt(idPergunta).isEmpty())
                setRespostas.add(entry.getValue().elementAt(idPergunta));
        }
        return setRespostas;
    }

    public ArrayList<String> getRespostas()
    {
        return listaRespostas;
    }


    public List<String> getEspecies()
    {
        List<String> listEspecies = new ArrayList<>();
        for (Map.Entry<String, Vector<String>> entry : mapEspecieAtributosAtual.entrySet()){
            listEspecies.add(entry.getKey());
        }
        return listEspecies;
    }

    public int getIdPergunta() {
        return idPergunta;
    }

    public int getNumeroEspecies()
    {
        return mapEspecieAtributosAtual.size();
    }

    public boolean temResultado() {
        if ((mapEspecieAtributosAtual.size() == 1) || (idUltimaResposta == idPergunta)){
            return true;
        }
        return false;
    }

    public void recalcular() {
        mapEspecieAtributosAtual.clear();
        mapEspecieAtributosAtual = new HashMap<String,Vector<String>>(mapEspecieAtributosArquivo);
        for (int idPergunta=0;idPergunta<listaPerguntas.size();idPergunta++) {
            try {
                String strResposta = listaRespostas.get(idPergunta);
                if (!strResposta.isEmpty())
                {
                    ArrayList<String> listaApagar = new ArrayList<String>();
                    for (Map.Entry<String, Vector<String>> entry : mapEspecieAtributosAtual.entrySet()) {
                        if (!entry.getValue().elementAt(idPergunta).equals(strResposta)) {
                            listaApagar.add(entry.getKey());
                        }
                    }

                    for (String s : listaApagar) {
                        mapEspecieAtributosAtual.remove(s);
                    }
                }
            }catch (Exception e)
            {
                int a=0;
            }

        }
        this.idPergunta = 0;
        this.idUltimaResposta = mapEspecieAtributosArquivo.size() / 2;
    }

    public void apagaRespostas( Set<Integer> apagar) {
        for (Integer i : apagar) {
            listaRespostas.set(i, "");
        }
    }

    public Map<String, DescricaoEspecie> getMapEspecieDescricao() {
        return mapEspecieDescricao;
    }

    public ArrayList<String> getListaEstruturas() {
        return listaEstruturas;
    }

    public void setListaEstruturas(ArrayList<String> listaEstruturas) {
        this.listaEstruturas = listaEstruturas;
    }
}
