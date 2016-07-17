import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.*;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by lgm on 16/7/17.
 */
public class Client {

    public static void main(String[] args) throws NoSuchMethodException {
        HelloService helloService = (HelloService) Proxy.newProxyInstance(new HelloService(){
            public String hello(String name) {
                return null;
            }

            public String hello2(String name) {
                return null;
            }

        }.getClass().getClassLoader(), new Class[]{HelloService.class}, new InvocationHandler() {
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request();
                request.setRequestId(UUID.randomUUID().toString());
                request.setMethodName("hello");
                request.setParameters(objects);
                request.setParameterTypes(method.getParameterTypes());
                request.setServiceName("hello:helloservice");

                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Class.class, new ClassJsonAdapter());
                Gson gson = builder.create();

                RequestBody body = new FormEncodingBuilder()
                        .add("stringRequest", gson.toJson(request))
                        .build();
                com.squareup.okhttp.Request sendrequest = new com.squareup.okhttp.Request.Builder()
                        .url("http://127.0.0.1:8081/test")
                        .post(body)
                        .build();

                Response response = client.newCall(sendrequest).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
        System.out.println(helloService.hello("lasdas"));


    }

}
