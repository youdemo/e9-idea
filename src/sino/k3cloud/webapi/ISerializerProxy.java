package sino.k3cloud.webapi;

public abstract interface ISerializerProxy {

    public abstract String Serialize(Object paramObject);

    public abstract Object Deserialize(String paramString, Class<?> paramClass);
}
