package br.com.caelum.leilao.teste;

import br.com.caelum.leilao.modelo.Leilao;
import br.com.caelum.leilao.modelo.Usuario;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;
import org.junit.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class LeilaoWSTest {

    /*@Test
    public void deveRetornarListaDeUsuarios() {
        XmlPath path =
                given()
                        .header("Accept", "application/xml")
                        .get("/leiloes")
                        .andReturn()
                        .xmlPath();

        List<Usuario> usuarios = path.getList("list.leiloes", Usuario.class);

        Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");

        assertEquals(esperado1, usuarios.get(0));
        assertEquals(esperado2, usuarios.get(1));
    }*/

    @Test
    public void deveRetornarLeilaoPorId() {
        JsonPath json =
                given()
                        .queryParam("leilao.id", 1)
                        .header("Accept", "application/json")
                        .get("/leiloes/show")
                        .andReturn()
                        .jsonPath();

        Leilao leilao = json.getObject("leilao", Leilao.class);
        Usuario usuario1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Leilao esperado = new Leilao(1L, "Geladeira", 800.0, usuario1, false);

        assertEquals(esperado, leilao);
    }

    @Test
    public void deveRetornarTotaldeLeiloes() {
        XmlPath path =
                given()
                        .header("Accept", "application/xml")
                        .get("/leiloes/total")
                        .andReturn()
                        .xmlPath();

        int total = path.getInt("int");
        assertEquals(2, total);
    }

    @Test
    public void deveAdicionarUmNovoLeilao() {
        Usuario joao = new Usuario("Joao da Silva", "joao@dasilva.com");
        Leilao leilao = new Leilao("Leilao PS4", 700.00, joao, false);

        XmlPath path =
                given()
                        .header("Accept", "application/xml")
                        .contentType("application/xml")
                        .body(leilao)
                        .expect()
                        .statusCode(200)
                        .when()
                        .post("/leiloes")
                        .andReturn()
                        .xmlPath();

        Leilao retornoLeilao = path.getObject("leilao", Leilao.class);
        assertEquals(leilao.getNome(), retornoLeilao.getNome());
        assertEquals(leilao.getValorInicial(), retornoLeilao.getValorInicial());

        given()
                .contentType("application/xml")
                .body(retornoLeilao)
                .expect()
                .statusCode(200)
                .when()
                .delete("/leiloes/deletar")
                .andReturn()
                .asString();
    }

    @Test
    public void deveGerarUmHeaderCookie() {
        expect()
                .header("novo-header", "abc")
                .when()
                .log().all()
                .get("/cookie/teste");
    }
}
