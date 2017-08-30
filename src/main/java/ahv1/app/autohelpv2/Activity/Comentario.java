package ahv1.app.autohelpv2.Activity;

/**
 * Created by bella on 26/07/2017.
 */

public class Comentario {

    private String dataPost;
    private String txt_comentario;
    private String usuario;
    private String assunto;

    public Comentario() {
    }

    public Comentario(String dataPost, String txt_comentario, String usuario) {
        this.dataPost = dataPost;
        this.txt_comentario = txt_comentario;
        this.usuario = usuario;
        this.assunto = assunto;
    }

    public String getDataPost() {
        return dataPost;
    }

    public void setDataPost(String dataPost) {
        this.dataPost = dataPost;
    }

    public String getTxt_comentario() {
        return txt_comentario;
    }

    public void setTxt_comentario(String txt_comentario) {
        this.txt_comentario = txt_comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }
}

