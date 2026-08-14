import java.lang.reflect.Method;
public class Helper {
    public static void main(String[] args) throws Exception {
        dump("com.bedrock.core.Context");
        dump("com.bedrock.core.Middleware");
        dump("com.bedrock.core.BedrockApp");
    }
    static void dump(String name) throws Exception {
        System.out.println("--- " + name + " ---");
        for (Method m : Class.forName(name).getDeclaredMethods()) {
            System.out.println(m);
        }
    }
}
