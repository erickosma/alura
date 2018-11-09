package leilao;

import com.jayway.restassured.path.xml.XmlPath;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class UsuarioWsTest {


    @Test
    public void getDeveRetornarListaDeUsuarios(){
        XmlPath path = get("/usuarios?_format=xml").andReturn().xmlPath();
    }
}
