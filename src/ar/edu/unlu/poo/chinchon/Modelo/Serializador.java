package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.*;
import java.util.ArrayList;

public class Serializador {
    private String fileName;

    public Serializador(String nombreArchivo){
        this.fileName=nombreArchivo;
        File archivo = new File(fileName);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean writeOneObject(Object obj){
        boolean respuesta=false;
        try{
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(obj);
            oos.close();
            respuesta=true;
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public Object readObject(){
        Object respuesta=null;
        ArrayList<Object> listaDeObjetos=new ArrayList<Object>();
        try{
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(fileName));
            respuesta=ois.readObject();
            ois.close();
        }catch(EOFException e){
            System.out.println("Lectura Completada");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }
}
