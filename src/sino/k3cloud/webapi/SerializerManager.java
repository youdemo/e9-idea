package sino.k3cloud.webapi;

public class SerializerManager {

    public static ISerializerProxy Create()
            throws Exception {
        return new JsonSerializerProxy();
    }
}
