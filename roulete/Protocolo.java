package roulete;
import java.awt.*;
import java.net.*;
import java.io.*;

public class Protocolo implements Serializable
{
    Object arg1 = null;
    Object arg2 = null;
    Object arg3 = null;
    Object arg4 = null;
    public void envia(ObjectOutputStream out) throws Exception
    {
        out.writeObject(this);
        out.flush();
        out.reset();
    }

    public static Protocolo recebe(ObjectInputStream in) throws Exception
    {
        return (Protocolo)in.readObject();
    }
}