package dextaxonomia.com.triatodex;

/**
 * Created by Max on 19/10/2016.
 */
public class DescricaoEspecie {


    private String distribuicao;
    private String importanciaMedica;
    private String descritor;
    private String habitat;
    private String imagemEspecie;
    private String tamanho;

    public String getImagemEspecie() {
        return imagemEspecie;
    }

    public void setImagemEspecie(String imagemEspecie) {
        this.imagemEspecie = imagemEspecie;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getDistribuicao() {
        return distribuicao;
    }

    public void setDistribuicao(String distribuicao) {
        this.distribuicao = distribuicao;
    }

    public String getImportanciaMedica() {
        return importanciaMedica;
    }

    public void setImportanciaMedica(String importanciaMedica) {
        this.importanciaMedica = importanciaMedica;
    }

    public String getDescritor() {
        return descritor;
    }

    public void setDescritor(String descritor) {
        this.descritor = descritor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
