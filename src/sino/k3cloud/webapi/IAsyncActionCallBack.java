package sino.k3cloud.webapi;

public abstract interface IAsyncActionCallBack<T> {

    public abstract void CallBack(AsyncResult<T> paramAsyncResult);
}
