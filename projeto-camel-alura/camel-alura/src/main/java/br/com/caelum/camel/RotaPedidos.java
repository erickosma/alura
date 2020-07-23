package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidos {

    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();
        RouteBuilder builder = new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("file:pedidos").
                        log("${exchange.pattern}").
                        log("${id} - ${body}").
                        to("file:saida");
            }
        };
        context.addRoutes(builder); //aqui camel realmente começa a trabalhar
        context.start();
        Thread.sleep(1000); //esperando um pouco para dar um tempo para camel
    }
}
