package com.zb.common.http;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

public abstract class HttpEntityHandler<T>
  implements ResponseHandler<T>
{
  public T handleResponse(HttpResponse response)
    throws IOException
  {
    int code = response.getStatusLine().getStatusCode();
    if (code != 200) {
      throw new HttpStatusException(code, response.toString());
    }
    return handle(response.getEntity());
  }
  
  public abstract T handle(HttpEntity paramHttpEntity)
    throws IOException;
  
  public abstract String getName();
}